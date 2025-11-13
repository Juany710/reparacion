import React, { useState, useContext } from 'react';import React, { useState, useContext } from 'react';

import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert, ScrollView } from 'react-native';import {

import { AuthContext } from '../../context/AuthContext';  View,

  Text,

export default function RegisterScreen({ navigation }) {  StyleSheet,

  const [formData, setFormData] = useState({  SafeAreaView,

    nombre: '',  TouchableOpacity,

    apellido: '',  TextInput,

    email: '',  ActivityIndicator,

    telefono: '',  ScrollView,

    password: '',} from 'react-native';

    confirmPassword: '',import { AuthContext } from '../../context/AuthContext';

  });

  const { register, selectedRole } = useContext(AuthContext);const RegisterScreen = ({ navigation, route }) => {

  const { register, state } = useContext(AuthContext);

  const handleRegister = async () => {  const tipo = route.params?.tipo || 'CLIENTE'; // CLIENTE o TECNICO

    if (!formData.nombre || !formData.email || !formData.password) {

      Alert.alert('Error', 'Por favor completa los campos requeridos');  const [formData, setFormData] = useState({

      return;    nombre: '',

    }    apellido: '',

    email: '',

    if (formData.password !== formData.confirmPassword) {    username: '',

      Alert.alert('Error', 'Las contraseñas no coinciden');    password: '',

      return;    confirmPassword: '',

    }  });



    try {  const [showPassword, setShowPassword] = useState(false);

      await register(formData, selectedRole);  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    } catch (error) {  const [loading, setLoading] = useState(false);

      Alert.alert('Error', error.message || 'Error al registrarse');  const [errors, setErrors] = useState({});

    }

  };  const validateForm = () => {

    const newErrors = {};

  return (

    <ScrollView style={styles.container} contentContainerStyle={styles.contentContainer}>    if (!formData.nombre.trim()) newErrors.nombre = 'El nombre es requerido';

      <Text style={styles.title}>Crear Cuenta</Text>    if (!formData.apellido.trim()) newErrors.apellido = 'El apellido es requerido';

      <Text style={styles.subtitle}>Como {selectedRole}</Text>    if (!formData.email.trim()) newErrors.email = 'El correo es requerido';

          if (!formData.email.includes('@')) newErrors.email = 'Correo inválido';

      <TextInput    if (!formData.username.trim()) newErrors.username = 'El usuario es requerido';

        style={styles.input}    if (formData.username.length < 3) newErrors.username = 'El usuario debe tener al menos 3 caracteres';

        placeholder="Nombre *"    if (!formData.password) newErrors.password = 'La contraseña es requerida';

        value={formData.nombre}    if (formData.password.length < 6) newErrors.password = 'La contraseña debe tener al menos 6 caracteres';

        onChangeText={(text) => setFormData({ ...formData, nombre: text })}    if (formData.password !== formData.confirmPassword) newErrors.confirmPassword = 'Las contraseñas no coinciden';

      />

          setErrors(newErrors);

      <TextInput    return Object.keys(newErrors).length === 0;

        style={styles.input}  };

        placeholder="Apellido"

        value={formData.apellido}  const handleRegister = async () => {

        onChangeText={(text) => setFormData({ ...formData, apellido: text })}    if (!validateForm()) return;

      />

          setLoading(true);

      <TextInput    const success = await register(

        style={styles.input}      formData.email,

        placeholder="Email *"      formData.password,

        value={formData.email}      formData.nombre,

        onChangeText={(text) => setFormData({ ...formData, email: text })}      formData.apellido,

        keyboardType="email-address"      formData.username,

        autoCapitalize="none"      tipo

      />    );

          setLoading(false);

      <TextInput

        style={styles.input}    // Si fue exitoso, AuthContext redireccionará automáticamente

        placeholder="Teléfono"    // Si falló, el error está en state.error

        value={formData.telefono}  };

        onChangeText={(text) => setFormData({ ...formData, telefono: text })}

        keyboardType="phone-pad"  const handleInputChange = (field, value) => {

      />    setFormData({ ...formData, [field]: value });

          if (errors[field]) {

      <TextInput      setErrors({ ...errors, [field]: '' });

        style={styles.input}    }

        placeholder="Contraseña *"  };

        value={formData.password}

        onChangeText={(text) => setFormData({ ...formData, password: text })}  return (

        secureTextEntry    <SafeAreaView style={styles.container}>

      />      <View style={styles.header}>

              <TouchableOpacity onPress={() => navigation.goBack()}>

      <TextInput          <Text style={styles.backArrow}>‹</Text>

        style={styles.input}        </TouchableOpacity>

        placeholder="Confirmar Contraseña *"        <Text style={styles.headerTitle}>Crear cuenta</Text>

        value={formData.confirmPassword}        <View style={{ width: 30 }} />

        onChangeText={(text) => setFormData({ ...formData, confirmPassword: text })}      </View>

        secureTextEntry

      />      <ScrollView style={styles.content} showsVerticalScrollIndicator={false}>

              {/* Tipo de usuario */}

      <TouchableOpacity style={styles.button} onPress={handleRegister}>        <View style={styles.roleSection}>

        <Text style={styles.buttonText}>Registrarse</Text>          <Text style={styles.roleLabel}>Registrándose como:</Text>

      </TouchableOpacity>          <Text style={styles.roleValue}>

            {tipo === 'CLIENTE' ? '👤 Usuario' : '🔧 Técnico'}

      <TouchableOpacity onPress={() => navigation.goBack()}>          </Text>

        <Text style={styles.linkText}>¿Ya tienes cuenta? Inicia sesión</Text>        </View>

      </TouchableOpacity>

    </ScrollView>        {/* Error Box */}

  );        {state.error && (

}          <View style={styles.errorBox}>

            <Text style={styles.errorText}>❌ {state.error}</Text>

const styles = StyleSheet.create({          </View>

  container: {        )}

    flex: 1,

    backgroundColor: '#f5f5f5',        {/* Nombre */}

  },        <View style={styles.inputGroup}>

  contentContainer: {          <Text style={styles.label}>Nombre</Text>

    padding: 20,          <View style={[styles.inputContainer, errors.nombre && styles.inputError]}>

  },            <TextInput

  title: {              style={styles.input}

    fontSize: 28,              placeholder="Tu nombre"

    fontWeight: 'bold',              placeholderTextColor="#9ca3af"

    marginBottom: 10,              value={formData.nombre}

    textAlign: 'center',              onChangeText={(text) => handleInputChange('nombre', text)}

  },              editable={!loading}

  subtitle: {            />

    fontSize: 16,          </View>

    color: '#666',          {errors.nombre && <Text style={styles.errorText}>{errors.nombre}</Text>}

    marginBottom: 30,        </View>

    textAlign: 'center',

  },        {/* Apellido */}

  input: {        <View style={styles.inputGroup}>

    backgroundColor: 'white',          <Text style={styles.label}>Apellido</Text>

    padding: 15,          <View style={[styles.inputContainer, errors.apellido && styles.inputError]}>

    borderRadius: 10,            <TextInput

    marginBottom: 15,              style={styles.input}

    borderWidth: 1,              placeholder="Tu apellido"

    borderColor: '#ddd',              placeholderTextColor="#9ca3af"

  },              value={formData.apellido}

  button: {              onChangeText={(text) => handleInputChange('apellido', text)}

    backgroundColor: '#007AFF',              editable={!loading}

    padding: 15,            />

    borderRadius: 10,          </View>

    marginTop: 10,          {errors.apellido && <Text style={styles.errorText}>{errors.apellido}</Text>}

  },        </View>

  buttonText: {

    color: 'white',        {/* Email */}

    fontSize: 18,        <View style={styles.inputGroup}>

    fontWeight: 'bold',          <Text style={styles.label}>Correo electrónico</Text>

    textAlign: 'center',          <View style={[styles.inputContainer, errors.email && styles.inputError]}>

  },            <TextInput

  linkText: {              style={styles.input}

    color: '#007AFF',              placeholder="usuario@ejemplo.com"

    textAlign: 'center',              placeholderTextColor="#9ca3af"

    marginTop: 15,              value={formData.email}

  },              onChangeText={(text) => handleInputChange('email', text)}

});              editable={!loading}

              keyboardType="email-address"
              autoCapitalize="none"
            />
          </View>
          {errors.email && <Text style={styles.errorText}>{errors.email}</Text>}
        </View>

        {/* Usuario */}
        <View style={styles.inputGroup}>
          <Text style={styles.label}>Nombre de usuario</Text>
          <View style={[styles.inputContainer, errors.username && styles.inputError]}>
            <TextInput
              style={styles.input}
              placeholder="usuario123"
              placeholderTextColor="#9ca3af"
              value={formData.username}
              onChangeText={(text) => handleInputChange('username', text)}
              editable={!loading}
              autoCapitalize="none"
            />
          </View>
          {errors.username && <Text style={styles.errorText}>{errors.username}</Text>}
        </View>

        {/* Contraseña */}
        <View style={styles.inputGroup}>
          <Text style={styles.label}>Contraseña</Text>
          <View style={[styles.inputContainer, errors.password && styles.inputError]}>
            <TextInput
              style={styles.input}
              placeholder="Mínimo 6 caracteres"
              placeholderTextColor="#9ca3af"
              value={formData.password}
              onChangeText={(text) => handleInputChange('password', text)}
              secureTextEntry={!showPassword}
              editable={!loading}
            />
            <TouchableOpacity onPress={() => setShowPassword(!showPassword)}>
              <Text style={styles.eyeIcon}>{showPassword ? '👁️' : '👁️‍🗨️'}</Text>
            </TouchableOpacity>
          </View>
          {errors.password && <Text style={styles.errorText}>{errors.password}</Text>}
        </View>

        {/* Confirmar Contraseña */}
        <View style={styles.inputGroup}>
          <Text style={styles.label}>Confirmar contraseña</Text>
          <View style={[styles.inputContainer, errors.confirmPassword && styles.inputError]}>
            <TextInput
              style={styles.input}
              placeholder="Repite tu contraseña"
              placeholderTextColor="#9ca3af"
              value={formData.confirmPassword}
              onChangeText={(text) => handleInputChange('confirmPassword', text)}
              secureTextEntry={!showConfirmPassword}
              editable={!loading}
            />
            <TouchableOpacity onPress={() => setShowConfirmPassword(!showConfirmPassword)}>
              <Text style={styles.eyeIcon}>{showConfirmPassword ? '👁️' : '👁️‍🗨️'}</Text>
            </TouchableOpacity>
          </View>
          {errors.confirmPassword && <Text style={styles.errorText}>{errors.confirmPassword}</Text>}
        </View>

        {/* Register Button */}
        <TouchableOpacity
          style={[styles.registerButton, loading && styles.registerButtonDisabled]}
          onPress={handleRegister}
          disabled={loading}
        >
          {loading ? (
            <ActivityIndicator color="#fff" />
          ) : (
            <Text style={styles.registerButtonText}>Crear cuenta</Text>
          )}
        </TouchableOpacity>

        {/* Terms */}
        <Text style={styles.termsText}>
          Al registrarte, aceptas nuestros{' '}
          <Text style={styles.termsLink}>términos de servicio</Text> y{' '}
          <Text style={styles.termsLink}>política de privacidad</Text>
        </Text>

        {/* Login Link */}
        <View style={styles.loginContainer}>
          <Text style={styles.loginText}>¿Ya tienes cuenta? </Text>
          <TouchableOpacity onPress={() => navigation.navigate('Login')}>
            <Text style={styles.loginLink}>Inicia sesión</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
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

  content: { flex: 1, padding: 16 },

  roleSection: {
    backgroundColor: '#f0f4ff',
    borderRadius: 8,
    padding: 12,
    marginBottom: 20,
    borderLeftWidth: 4,
    borderLeftColor: '#2563eb',
  },
  roleLabel: { fontSize: 12, color: '#6b7280', fontWeight: '600' },
  roleValue: { fontSize: 16, fontWeight: '700', color: '#0f172a', marginTop: 4 },

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

  registerButton: {
    backgroundColor: '#2563eb',
    borderRadius: 8,
    paddingVertical: 12,
    alignItems: 'center',
    marginTop: 24,
  },
  registerButtonDisabled: { opacity: 0.6 },
  registerButtonText: { color: '#fff', fontWeight: '700', fontSize: 16 },

  termsText: { fontSize: 11, color: '#6b7280', textAlign: 'center', marginTop: 16, lineHeight: 16 },
  termsLink: { color: '#2563eb', fontWeight: '600' },

  loginContainer: { flexDirection: 'row', justifyContent: 'center', marginTop: 16, marginBottom: 20 },
  loginText: { color: '#6b7280', fontSize: 13 },
  loginLink: { color: '#2563eb', fontWeight: '700' },
});

export default RegisterScreen;
