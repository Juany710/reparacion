import React, { useContext } from 'react';import React, { useContext } from 'react';

import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';import {

import { AuthContext } from '../../context/AuthContext';  View,

  Text,

export default function RoleSelectionScreen({ navigation }) {  StyleSheet,

  const { selectRole } = useContext(AuthContext);  SafeAreaView,

  TouchableOpacity,

  const handleRoleSelect = (role) => {  Image,

    selectRole(role);} from 'react-native';

    navigation.navigate('Login');import { AuthContext } from '../../context/AuthContext';

  };

const RoleSelectionScreen = ({ navigation }) => {

  return (  const { selectRole } = useContext(AuthContext);

    <View style={styles.container}>

      <Text style={styles.title}>Bienvenido a FixIt</Text>  const handleRoleSelection = (role) => {

      <Text style={styles.subtitle}>¿Cómo deseas usar la aplicación?</Text>    selectRole(role);

          navigation.navigate('Login');

      <TouchableOpacity   };

        style={styles.roleButton}

        onPress={() => handleRoleSelect('cliente')}  return (

      >    <SafeAreaView style={styles.container}>

        <Text style={styles.roleButtonText}>Soy Cliente</Text>      <View style={styles.content}>

        <Text style={styles.roleDescription}>Necesito un servicio de reparación</Text>        {/* Logo */}

      </TouchableOpacity>        <View style={styles.logoSection}>

          <Text style={styles.logo}>🔧</Text>

      <TouchableOpacity           <Text style={styles.appName}>FIXIT</Text>

        style={styles.roleButton}          <Text style={styles.subtitle}>Soluciones de reparación al instante</Text>

        onPress={() => handleRoleSelect('tecnico')}        </View>

      >

        <Text style={styles.roleButtonText}>Soy Técnico</Text>        {/* Role Selection */}

        <Text style={styles.roleDescription}>Ofrezco servicios de reparación</Text>        <View style={styles.rolesContainer}>

      </TouchableOpacity>          <Text style={styles.sectionTitle}>¿Quién eres?</Text>

    </View>

  );          {/* Cliente Card */}

}          <TouchableOpacity

            style={styles.roleCard}

const styles = StyleSheet.create({            onPress={() => handleRoleSelection('CLIENTE')}

  container: {            activeOpacity={0.85}

    flex: 1,          >

    justifyContent: 'center',            <View style={[styles.roleIcon, { backgroundColor: '#dbeafe' }]}>

    alignItems: 'center',              <Text style={styles.roleIconText}>👤</Text>

    padding: 20,            </View>

    backgroundColor: '#f5f5f5',            <Text style={styles.roleTitle}>Usuario</Text>

  },            <Text style={styles.roleDescription}>Busco un técnico para reparar</Text>

  title: {          </TouchableOpacity>

    fontSize: 28,

    fontWeight: 'bold',          {/* Tecnico Card */}

    marginBottom: 10,          <TouchableOpacity

  },            style={styles.roleCard}

  subtitle: {            onPress={() => handleRoleSelection('TECNICO')}

    fontSize: 16,            activeOpacity={0.85}

    color: '#666',          >

    marginBottom: 40,            <View style={[styles.roleIcon, { backgroundColor: '#ddd6fe' }]}>

  },              <Text style={styles.roleIconText}>🔧</Text>

  roleButton: {            </View>

    width: '100%',            <Text style={styles.roleTitle}>Técnico</Text>

    backgroundColor: '#007AFF',            <Text style={styles.roleDescription}>Ofrezco servicios de reparación</Text>

    padding: 20,          </TouchableOpacity>

    borderRadius: 10,        </View>

    marginBottom: 15,

  },        {/* Footer */}

  roleButtonText: {        <View style={styles.footer}>

    color: 'white',          <Text style={styles.footerText}>

    fontSize: 20,            Al continuar, aceptas nuestros{' '}

    fontWeight: 'bold',            <Text style={styles.link}>términos y condiciones</Text>

    textAlign: 'center',          </Text>

  },        </View>

  roleDescription: {      </View>

    color: 'white',    </SafeAreaView>

    fontSize: 14,  );

    textAlign: 'center',};

    marginTop: 5,

  },const styles = StyleSheet.create({

});  container: {

    flex: 1,
    backgroundColor: '#f9fafb',
  },
  content: {
    flex: 1,
    paddingHorizontal: 16,
    justifyContent: 'space-between',
    paddingVertical: 20,
  },
  logoSection: {
    alignItems: 'center',
    marginVertical: 40,
  },
  logo: {
    fontSize: 64,
    marginBottom: 12,
  },
  appName: {
    fontSize: 32,
    fontWeight: '700',
    color: '#0f172a',
  },
  subtitle: {
    fontSize: 14,
    color: '#6b7280',
    marginTop: 8,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: '700',
    color: '#0f172a',
    marginBottom: 16,
    textAlign: 'center',
  },
  rolesContainer: {
    marginVertical: 20,
  },
  roleCard: {
    backgroundColor: '#fff',
    borderRadius: 12,
    paddingVertical: 24,
    paddingHorizontal: 16,
    marginBottom: 12,
    alignItems: 'center',
    borderWidth: 2,
    borderColor: '#e5e7eb',
  },
  roleIcon: {
    width: 72,
    height: 72,
    borderRadius: 36,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 12,
  },
  roleIconText: {
    fontSize: 36,
  },
  roleTitle: {
    fontSize: 18,
    fontWeight: '700',
    color: '#0f172a',
    marginBottom: 4,
  },
  roleDescription: {
    fontSize: 13,
    color: '#6b7280',
    textAlign: 'center',
  },
  footer: {
    paddingBottom: 20,
  },
  footerText: {
    fontSize: 12,
    color: '#6b7280',
    textAlign: 'center',
  },
  link: {
    color: '#2563eb',
    fontWeight: '600',
  },
});

export default RoleSelectionScreen;
