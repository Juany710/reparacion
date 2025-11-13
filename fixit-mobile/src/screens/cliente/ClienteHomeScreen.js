import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

export default function ClienteHomeScreen() {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Home Cliente</Text>
      <Text>Pantalla principal del cliente</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, justifyContent: 'center', alignItems: 'center', padding: 20 },
  title: { fontSize: 24, fontWeight: 'bold', marginBottom: 20 },
});
