import React, { useContext } from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { ActivityIndicator, View, Text } from 'react-native';

import { AuthContext } from '../context/AuthContext';

// Auth Screens
import RoleSelectionScreen from '../screens/auth/RoleSelectionScreen';
import LoginScreen from '../screens/auth/LoginScreen';
import RegisterScreen from '../screens/auth/RegisterScreen';

// Cliente Screens
import ClienteHomeScreen from '../screens/cliente/ClienteHomeScreen';
import MisSolicitudesScreen from '../screens/cliente/MisSolicitudesScreen';
import MetodosPagoScreen from '../screens/cliente/MetodosPagoScreen';
import GestionarTarjetasScreen from '../screens/cliente/GestionarTarjetasScreen';
import PantallaPagoScreen from '../screens/cliente/PantallaPagoScreen';
import ConfirmacionPagoScreen from '../screens/cliente/ConfirmacionPagoScreen';

// Tecnico Screens
import TecnicoHomeScreen from '../screens/tecnico/TecnicoHomeScreen';
import TecnicoHerramientasScreen from '../screens/tecnico/TecnicoHerramientasScreen';

// Common Screens
import PerfilScreen from '../screens/common/PerfilScreen';
import ChatScreen from '../screens/common/ChatScreen';
import NotificacionesScreen from '../screens/common/NotificacionesScreen';
import EditarPerfilScreen from '../screens/common/EditarPerfilScreen';
import CambiarPasswordScreen from '../screens/common/CambiarPasswordScreen';
import DireccionesScreen from '../screens/common/DireccionesScreen';
import PrivacidadScreen from '../screens/common/PrivacidadScreen';
import AccesibilidadScreen from '../screens/common/AccesibilidadScreen';
import NotificacionesConfigScreen from '../screens/common/NotificacionesConfigScreen';
import SoporteScreen from '../screens/common/SoporteScreen';
import CuentaBancariaScreen from '../screens/common/CuentaBancariaScreen';
import HistorialPagosScreen from '../screens/common/HistorialPagosScreen';

const Stack = createNativeStackNavigator();
const Tab = createBottomTabNavigator();

// Auth Navigator
const AuthNavigator = () => {
  return (
    <Stack.Navigator
      screenOptions={{
        headerShown: false,
        cardStyle: { backgroundColor: 'white' },
      }}
    >
      <Stack.Screen name="RoleSelection" component={RoleSelectionScreen} />
      <Stack.Screen name="Login" component={LoginScreen} />
      <Stack.Screen name="Register" component={RegisterScreen} />
    </Stack.Navigator>
  );
};

// Cliente Tab Navigator
const ClienteTabNavigator = () => {
  return (
    <Tab.Navigator
      screenOptions={{
        headerShown: false,
        tabBarActiveTintColor: '#2563eb',
        tabBarInactiveTintColor: '#9ca3af',
        tabBarStyle: {
          borderTopColor: '#e5e7eb',
          borderTopWidth: 1,
        },
      }}
    >
      <Tab.Screen
        name="ClienteHome"
        component={ClienteHomeScreen}
        options={{
          title: 'Inicio',
          tabBarLabel: 'Inicio',
          tabBarIcon: ({ color }) => <IconText style={{ color }}>🏠</IconText>,
        }}
      />
      <Tab.Screen
        name="MisSolicitudes"
        component={MisSolicitudesScreen}
        options={{
          title: 'Mis Solicitudes',
          tabBarLabel: 'Solicitudes',
          tabBarIcon: ({ color }) => <IconText style={{ color }}>📋</IconText>,
        }}
      />
      <Tab.Screen
        name="ClienteNotificaciones"
        component={NotificacionesScreen}
        options={{
          title: 'Notificaciones',
          tabBarLabel: 'Notificaciones',
          tabBarIcon: ({ color }) => <IconText style={{ color }}>�</IconText>,
        }}
      />
      <Tab.Screen
        name="ClientePerfil"
        component={PerfilScreen}
        options={{
          title: 'Perfil',
          tabBarLabel: 'Perfil',
          tabBarIcon: ({ color }) => <IconText style={{ color }}>👤</IconText>,
        }}
      />
    </Tab.Navigator>
  );
};

// Cliente Navigator con Stack
const ClienteNavigatorStack = () => {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="ClienteTabNavigator" component={ClienteTabNavigator} />
      <Stack.Screen name="Chat" component={ChatScreen} options={{ cardStyle: { backgroundColor: 'white' } }} />
      <Stack.Screen name="EditarPerfil" component={EditarPerfilScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="CambiarPassword" component={CambiarPasswordScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="Direcciones" component={DireccionesScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="MetodosPago" component={MetodosPagoScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="GestionarTarjetas" component={GestionarTarjetasScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="PantallaPago" component={PantallaPagoScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="ConfirmacionPago" component={ConfirmacionPagoScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="Privacidad" component={PrivacidadScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="Accesibilidad" component={AccesibilidadScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="NotificacionesConfig" component={NotificacionesConfigScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="Soporte" component={SoporteScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
    </Stack.Navigator>
  );
};

// Tecnico Tab Navigator
const TecnicoTabNavigator = () => {
  return (
    <Tab.Navigator
      screenOptions={{
        headerShown: false,
        tabBarActiveTintColor: '#2563eb',
        tabBarInactiveTintColor: '#9ca3af',
        tabBarStyle: {
          borderTopColor: '#e5e7eb',
          borderTopWidth: 1,
        },
      }}
    >
      <Tab.Screen
        name="TecnicoHome"
        component={TecnicoHomeScreen}
        options={{
          title: 'Inicio',
          tabBarLabel: 'Inicio',
          tabBarIcon: ({ color }) => <IconText style={{ color }}>🏠</IconText>,
        }}
      />
      <Tab.Screen
        name="TecnicoHerramientas"
        component={TecnicoHerramientasScreen}
        options={{
          title: 'Herramientas',
          tabBarLabel: 'Herramientas',
          tabBarIcon: ({ color }) => <IconText style={{ color }}>🛠️</IconText>,
        }}
      />
      <Tab.Screen
        name="TecnicoNotificaciones"
        component={NotificacionesScreen}
        options={{
          title: 'Notificaciones',
          tabBarLabel: 'Notificaciones',
          tabBarIcon: ({ color }) => <IconText style={{ color }}>🔔</IconText>,
        }}
      />
      <Tab.Screen
        name="TecnicoPerfil"
        component={PerfilScreen}
        options={{
          title: 'Perfil',
          tabBarLabel: 'Perfil',
          tabBarIcon: ({ color }) => <IconText style={{ color }}>👤</IconText>,
        }}
      />
    </Tab.Navigator>
  );
};

// Tecnico Navigator con Stack
const TecnicoNavigatorStack = () => {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="TecnicoTabNavigator" component={TecnicoTabNavigator} />
      <Stack.Screen name="Chat" component={ChatScreen} options={{ cardStyle: { backgroundColor: 'white' } }} />
      <Stack.Screen name="EditarPerfil" component={EditarPerfilScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="CambiarPassword" component={CambiarPasswordScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="Direcciones" component={DireccionesScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="Privacidad" component={PrivacidadScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="Accesibilidad" component={AccesibilidadScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="NotificacionesConfig" component={NotificacionesConfigScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="Soporte" component={SoporteScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="CuentaBancaria" component={CuentaBancariaScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
      <Stack.Screen name="HistorialPagos" component={HistorialPagosScreen} options={{ cardStyle: { backgroundColor: '#f9fafb' } }} />
    </Stack.Navigator>
  );
};

// Root Navigator
const RootNavigator = () => {
  const { state } = useContext(AuthContext);

  if (state.isLoading) {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <ActivityIndicator size="large" color="#2563eb" />
      </View>
    );
  }

  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      {state.isSignedIn ? (
        <Stack.Screen
          name="AppStack"
          component={state.user?.rol === 'TECNICO' ? TecnicoNavigatorStack : ClienteNavigatorStack}
        />
      ) : (
        <Stack.Screen name="AuthStack" component={AuthNavigator} />
      )}
    </Stack.Navigator>
  );
};

const IconText = ({ style, children }) => (
  <Text style={[{ fontSize: 20 }, style]}>{children}</Text>
);

export default RootNavigator;
