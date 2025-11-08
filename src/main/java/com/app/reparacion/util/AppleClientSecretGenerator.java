package com.app.reparacion.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Generador de client-secret JWT para Apple Sign In sin dependencias externas.
 * - Lee la clave privada en formato PKCS#8 (.p8) y firma el JWT con ES256.
 * - Produce un JWT válido para usar como client_secret en la registration de Apple.
 */
public class AppleClientSecretGenerator {

    public static void main(String[] args) throws Exception {
        String teamId = getenvOrArg("APPLE_TEAM_ID", args, 0);
        String keyId = getenvOrArg("APPLE_KEY_ID", args, 1);
        String clientId = getenvOrArg("APPLE_CLIENT_ID", args, 2);
        String privateKeyPath = getenvOrArg("APPLE_PRIVATE_KEY_PATH", args, 3);

        if (teamId == null || keyId == null || clientId == null || privateKeyPath == null) {
            System.out.println("Uso: AppleClientSecretGenerator [TEAM_ID] [KEY_ID] [CLIENT_ID] [PRIVATE_KEY_PATH]");
            System.out.println("También puedes pasar valores mediante variables de entorno: APPLE_TEAM_ID, APPLE_KEY_ID, APPLE_CLIENT_ID, APPLE_PRIVATE_KEY_PATH");
            return;
        }

        long expSeconds = 15552000L; // 180 días por defecto
        String expEnv = System.getenv("APPLE_CLIENT_SECRET_EXP_SECONDS");
        if (expEnv != null && !expEnv.isEmpty()) {
            try { expSeconds = Long.parseLong(expEnv); } catch (NumberFormatException ignored) {}
        }

        ECPrivateKey privateKey = loadECPrivateKey(privateKeyPath);
        String jwt = generateClientSecret(teamId, keyId, clientId, privateKey, expSeconds);
        System.out.println(jwt);
    }

    private static String getenvOrArg(String envName, String[] args, int index) {
        String v = System.getenv(envName);
        if (v != null && !v.isEmpty()) return v;
        if (args != null && args.length > index && args[index] != null && !args[index].isEmpty()) return args[index];
        return null;
    }

    private static ECPrivateKey loadECPrivateKey(String path) throws IOException, GeneralSecurityException {
        String pem = Files.readString(Path.of(path));
        String noHeader = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        byte[] der = Base64.getDecoder().decode(noHeader);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(der);
        KeyFactory kf = KeyFactory.getInstance("EC");
        return (ECPrivateKey) kf.generatePrivate(spec);
    }

    private static String generateClientSecret(String teamId, String keyId, String clientId, ECPrivateKey privateKey, long expSeconds) throws Exception {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expSeconds);

        Map<String,Object> header = new LinkedHashMap<>();
        header.put("alg","ES256");
        header.put("kid", keyId);
        header.put("typ","JWT");

        Map<String,Object> payload = new LinkedHashMap<>();
        payload.put("iss", teamId);
        payload.put("iat", now.getEpochSecond());
        payload.put("exp", exp.getEpochSecond());
        payload.put("aud", "https://appleid.apple.com");
        payload.put("sub", clientId);

        String headerB64 = base64UrlEncode(json(header));
        String payloadB64 = base64UrlEncode(json(payload));
        String signingInput = headerB64 + "." + payloadB64;

        byte[] derSig = signWithES256(signingInput.getBytes(StandardCharsets.UTF_8), privateKey);
        byte[] rawSig = derToConcatRS(derSig, 64);
        String signatureB64 = base64UrlEncode(rawSig);
        return signingInput + "." + signatureB64;
    }

    private static String json(Map<String,Object> m) {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        boolean first = true;
        for (Map.Entry<String,Object> e : m.entrySet()) {
            if (!first) sb.append(',');
            first = false;
            sb.append('"').append(escape(e.getKey())).append('"').append(':');
            Object v = e.getValue();
            if (v instanceof Number) sb.append(v.toString());
            else sb.append('"').append(escape(String.valueOf(v))).append('"');
        }
        sb.append('}');
        return sb.toString();
    }

    private static String escape(String s) {
        return s.replace("\\","\\\\").replace("\"","\\\"");
    }

    private static String base64UrlEncode(String s) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(s.getBytes(StandardCharsets.UTF_8));
    }

    private static String base64UrlEncode(byte[] b) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(b);
    }

    private static byte[] signWithES256(byte[] data, ECPrivateKey privateKey) throws GeneralSecurityException {
        Signature sig = Signature.getInstance("SHA256withECDSA");
        sig.initSign(privateKey);
        sig.update(data);
        return sig.sign(); // DER-encoded (ASN.1)
    }

    // Convert DER-encoded ASN.1 ECDSA signature to raw concat R||S of fixed length (expectedLen)
    private static byte[] derToConcatRS(byte[] derSig, int expectedLen) throws IOException {
        // Very small ASN.1 DER parser for sequence of two integers
        int idx = 0;
        if (derSig[idx++] != 0x30) throw new IOException("Invalid DER signature");
        int seqLen = derSig[idx++] & 0xff;
        if ((seqLen & 0x80) != 0) {
            int nb = seqLen & 0x7f; seqLen = 0; for (int i=0;i<nb;i++){ seqLen = (seqLen<<8) | (derSig[idx++] & 0xff);} }
        if (derSig[idx++] != 0x02) throw new IOException("Invalid DER signature (r)");
        int rLen = derSig[idx++] & 0xff;
        byte[] r = new byte[rLen]; System.arraycopy(derSig, idx, r, 0, rLen); idx += rLen;
        if (derSig[idx++] != 0x02) throw new IOException("Invalid DER signature (s)");
        int sLen = derSig[idx++] & 0xff;
        byte[] s = new byte[sLen]; System.arraycopy(derSig, idx, s, 0, sLen); idx += sLen;

        byte[] rTrim = unsignedToFixed(r, expectedLen/2);
        byte[] sTrim = unsignedToFixed(s, expectedLen/2);
        byte[] out = new byte[rTrim.length + sTrim.length];
        System.arraycopy(rTrim,0,out,0,rTrim.length);
        System.arraycopy(sTrim,0,out,rTrim.length,sTrim.length);
        return out;
    }

    private static byte[] unsignedToFixed(byte[] signedInt, int size) {
        // Remove leading zero if present
        int offset = 0; while (offset < signedInt.length-1 && signedInt[offset] == 0) offset++;
        int len = signedInt.length - offset;
        if (len > size) {
            // take last 'size' bytes
            byte[] res = new byte[size]; System.arraycopy(signedInt, signedInt.length - size, res, 0, size); return res;
        } else if (len == size) {
            byte[] res = new byte[size]; System.arraycopy(signedInt, offset, res, 0, size); return res;
        } else {
            byte[] res = new byte[size]; System.arraycopy(signedInt, offset, res, size - len, len); return res;
        }
    }
}
