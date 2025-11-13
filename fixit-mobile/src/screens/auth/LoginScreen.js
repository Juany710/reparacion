import React, { useState, useContext } from 'react';import React, { useState, useContext } from 'react';

import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from 'react-native';import {

import { AuthContext } from '../../context/AuthContext';  View,

  Text,

export default function LoginScreen({ navigation }) {  StyleSheet,

  const [email, setEmail] = useState('');  SafeAreaView,

  const [password, setPassword] = useState('');  TouchableOpacity,

  const { login, selectedRole } = useContext(AuthContext);  TextInput,

  ActivityIndicator,

  const handleLogin = async () => {  Alert,

    if (!email || !password) {} from 'react-native';

      Alert.alert('Error', 'Por favor completa todos los campos');import { AuthContext } from '../../context/AuthContext';

      return;

    }const LoginScreen = ({ navigation }) => {

  const { loginWithEmail, loginWithOAuth, state } = useContext(AuthContext);

    try {  const [email, setEmail] = useState('');

      await login(email, password);  const [password, setPassword] = useState('');

    } catch (error) {  const [showPassword, setShowPassword] = useState(false);

      Alert.alert('Error', error.message || 'Error al iniciar sesión');  const [loading, setLoading] = useState(false);

    }  const [errors, setErrors] = useState({});

  };

  const validateForm = () => {

  return (    const newErrors = {};

    <View style={styles.container}>    if (!email) newErrors.email = 'El correo es requerido';

      <Text style={styles.title}>Iniciar Sesión</Text>    if (!password) newErrors.password = 'La contraseña es requerida';

      <Text style={styles.subtitle}>Como {selectedRole}</Text>    if (email && !email.includes('@')) newErrors.email = 'Correo inválido';

          setErrors(newErrors);

      <TextInput    return Object.keys(newErrors).length === 0;

        style={styles.input}  };

        placeholder="Email"

        value={email}  const handleLogin = async () => {

        onChangeText={setEmail}    if (!validateForm()) return;

        keyboardType="email-address"    setLoading(true);

        autoCapitalize="none"    // Aceptar email o username

      />    const success = await loginWithEmail(email, password);

          setLoading(false);

      <TextInput    if (!success) {

        style={styles.input}      // El error ya está en state.error, mostrado en el errorBox

        placeholder="Contraseña"    }

        value={password}  };

        onChangeText={setPassword}

        secureTextEntry  const handleGoogleLogin = async () => {

      />    // TODO: Implementar Google OAuth

          Alert.alert('Info', 'Google login coming soon');

      <TouchableOpacity style={styles.button} onPress={handleLogin}>  };

        <Text style={styles.buttonText}>Iniciar Sesión</Text>

      </TouchableOpacity>  const handleAppleLogin = async () => {

    // TODO: Implementar Apple OAuth

      <TouchableOpacity onPress={() => navigation.navigate('Register')}>    Alert.alert('Info', 'Apple login coming soon');

        <Text style={styles.linkText}>¿No tienes cuenta? Regístrate</Text>  };

      </TouchableOpacity>

  return (

      <TouchableOpacity onPress={() => navigation.goBack()}>    <SafeAreaView style={styles.container}>

        <Text style={styles.linkText}>Cambiar rol</Text>      <View style={styles.header}>

      </TouchableOpacity>        <TouchableOpacity onPress={() => navigation.goBack()}>

    </View>          <Text style={styles.backArrow}>‹</Text>

  );        </TouchableOpacity>

}        <Text style={styles.headerTitle}>Iniciar sesión</Text>

        <View style={{ width: 30 }} />

const styles = StyleSheet.create({      </View>

  container: {

    flex: 1,      <View style={styles.content}>

    justifyContent: 'center',        {/* Error Box */}

    padding: 20,        {state.error && (

    backgroundColor: '#f5f5f5',          <View style={styles.errorBox}>

  },            <Text style={styles.errorText}>❌ {state.error}</Text>

  title: {          </View>

    fontSize: 28,        )}

    fontWeight: 'bold',

    marginBottom: 10,        {/* Email Input */}

    textAlign: 'center',        <View style={styles.inputGroup}>

  },          <Text style={styles.label}>Correo electrónico</Text>

  subtitle: {          <View style={[styles.inputContainer, errors.email && styles.inputError]}>

    fontSize: 16,            <TextInput

    color: '#666',              style={styles.input}

    marginBottom: 30,              placeholder="usuario@ejemplo.com"

    textAlign: 'center',              placeholderTextColor="#9ca3af"

  },              value={email}

  input: {              onChangeText={(text) => {

    backgroundColor: 'white',                setEmail(text);

    padding: 15,                if (errors.email) setErrors({ ...errors, email: '' });

    borderRadius: 10,              }}

    marginBottom: 15,              editable={!loading}

    borderWidth: 1,              keyboardType="email-address"

    borderColor: '#ddd',              autoCapitalize="none"

  },            />

  button: {          </View>

    backgroundColor: '#007AFF',          {errors.email && <Text style={styles.errorText}>{errors.email}</Text>}

    padding: 15,        </View>

    borderRadius: 10,

    marginTop: 10,        {/* Password Input */}

  },        <View style={styles.inputGroup}>

  buttonText: {          <Text style={styles.label}>Contraseña</Text>

    color: 'white',          <View style={[styles.inputContainer, errors.password && styles.inputError]}>

    fontSize: 18,            <TextInput

    fontWeight: 'bold',              style={styles.input}

    textAlign: 'center',              placeholder="Tu contraseña"

  },              placeholderTextColor="#9ca3af"

  linkText: {              value={password}

    color: '#007AFF',              onChangeText={(text) => {

    textAlign: 'center',                setPassword(text);

    marginTop: 15,                if (errors.password) setErrors({ ...errors, password: '' });

  },              }}

});              secureTextEntry={!showPassword}

              editable={!loading}
            />
            <TouchableOpacity onPress={() => setShowPassword(!showPassword)}>
              <Text style={styles.eyeIcon}>{showPassword ? '👁️' : '👁️‍🗨️'}</Text>
            </TouchableOpacity>
          </View>
          {errors.password && <Text style={styles.errorText}>{errors.password}</Text>}
        </View>

        {/* Login Button */}
        <TouchableOpacity
          style={[styles.loginButton, loading && styles.loginButtonDisabled]}
          onPress={handleLogin}
          disabled={loading}
        >
          {loading ? (
            <ActivityIndicator color="#fff" />
          ) : (
            <Text style={styles.loginButtonText}>Iniciar sesión</Text>
          )}
        </TouchableOpacity>

        {/* Forgot Password */}
        <TouchableOpacity>
          <Text style={styles.forgotPassword}>¿Olvidaste tu contraseña?</Text>
        </TouchableOpacity>

        {/* Divider */}
        <View style={styles.divider}>
          <View style={styles.dividerLine} />
          <Text style={styles.dividerText}>O continúa con</Text>
          <View style={styles.dividerLine} />
        </View>

        {/* OAuth Buttons */}
        <View style={styles.oauthContainer}>
          <TouchableOpacity
            style={styles.oauthButton}
            onPress={handleGoogleLogin}
            disabled={loading}
          >
            <Text style={styles.oauthIcon}>🔵</Text>
            <Text style={styles.oauthText}>Google</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={styles.oauthButton}
            onPress={handleAppleLogin}
            disabled={loading}
          >
            <Text style={styles.oauthIcon}>⚫</Text>
            <Text style={styles.oauthText}>Apple</Text>
          </TouchableOpacity>
        </View>

        {/* Sign Up Link */}
        <View style={styles.signupContainer}>
          <Text style={styles.signupText}>¿No tienes cuenta? </Text>
          <TouchableOpacity onPress={() => {
            // Obtener el tipo del estado del contexto
            const tipo = state.selectedRole || 'CLIENTE';
            navigation.navigate('Register', { tipo });
          }}>
            <Text style={styles.signupLink}>Registrate aquí</Text>
          </TouchableOpacity>
        </View>

        {/* Terms */}
        <Text style={styles.termsText}>
          Al continuar, aceptas nuestros{' '}
          <Text style={styles.termsLink}>términos de servicio</Text> y{' '}
          <Text style={styles.termsLink}>política de privacidad</Text>
        </Text>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f9fafb' },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingVertical: 12,
    backgroundColor: '#fff',
    borderBottomWidth: 1,
    borderBottomColor: '#e5e7eb',
  },
  backArrow: { fontSize: 28, color: '#1f2937' },
  headerTitle: { fontSize: 18, fontWeight: '600', color: '#1f2937' },

  content: { flex: 1, padding: 16, justifyContent: 'center' },

  errorBox: {
    backgroundColor: '#fee2e2',
    borderLeftWidth: 4,
    borderLeftColor: '#dc2626',
    borderRadius: 8,
    padding: 12,
    marginBottom: 20,
  },
  errorText: { color: '#991b1b', fontSize: 12, fontWeight: '600' },

  inputGroup: { marginBottom: 16 },
  label: { fontSize: 13, fontWeight: '600', color: '#0f172a', marginBottom: 6 },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderRadius: 8,
    borderWidth: 1,
    borderColor: '#d1d5db',
    paddingHorizontal: 12,
  },
  inputError: { borderColor: '#dc2626' },
  input: { flex: 1, paddingVertical: 10, fontSize: 14, color: '#1f2937' },
  eyeIcon: { fontSize: 16 },

  loginButton: {
    backgroundColor: '#2563eb',
    borderRadius: 8,
    paddingVertical: 12,
    alignItems: 'center',
    marginTop: 24,
  },
  loginButtonDisabled: { opacity: 0.6 },
  loginButtonText: { color: '#fff', fontWeight: '700', fontSize: 16 },

  forgotPassword: { color: '#2563eb', textAlign: 'center', marginTop: 12, fontWeight: '600' },

  divider: { flexDirection: 'row', alignItems: 'center', marginVertical: 24 },
  dividerLine: { flex: 1, height: 1, backgroundColor: '#d1d5db' },
  dividerText: { marginHorizontal: 12, color: '#6b7280', fontSize: 12 },

  oauthContainer: { flexDirection: 'row', gap: 12 },
  oauthButton: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#fff',
    borderRadius: 8,
    borderWidth: 1,
    borderColor: '#d1d5db',
    paddingVertical: 10,
    gap: 6,
  },
  oauthIcon: { fontSize: 16 },
  oauthText: { fontWeight: '600', color: '#0f172a' },

  signupContainer: { flexDirection: 'row', justifyContent: 'center', marginTop: 16 },
  signupText: { color: '#6b7280', fontSize: 13 },
  signupLink: { color: '#2563eb', fontWeight: '700' },

  termsText: { fontSize: 11, color: '#6b7280', textAlign: 'center', marginTop: 20, lineHeight: 16 },
  termsLink: { color: '#2563eb', fontWeight: '600' },
});

export default LoginScreen;
