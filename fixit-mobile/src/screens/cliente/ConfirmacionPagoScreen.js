import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

export default function ConfirmacionPagoScreen() {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>ConfirmacionPagoScreen</Text>
      <Text>Pantalla en desarrollo</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, justifyContent: 'center', alignItems: 'center', padding: 20 },
  title: { fontSize: 24, fontWeight: 'bold', marginBottom: 20 },
});
