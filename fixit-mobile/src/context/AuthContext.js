import React, { useReducer, useEffect, useCallback } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import * as SecureStore from 'expo-secure-store';
import { authService } from '../services/api';

export const AuthContext = React.createContext({});

const initialState = {
  isLoading: true,
  isSignedIn: false,
  user: null,
  selectedRole: null,
  error: null,
};

const reducer = (state, action) => {
  switch (action.type) {
    case 'RESTORE_TOKEN':
      return {
        ...state,
        isSignedIn: !!action.payload.token,
        user: action.payload.user,
        isLoading: false,
      };
    case 'SELECT_ROLE':
      return {
        ...state,
        selectedRole: action.payload,
      };
    case 'LOGIN_SUCCESS':
      return {
        ...state,
        isSignedIn: true,
        user: action.payload.user,
        selectedRole: null,
        error: null,
      };
    case 'LOGIN_FAILURE':
      return {
        ...state,
        isSignedIn: false,
        user: null,
        error: action.payload,
      };
    case 'LOGOUT':
      return {
        ...state,
        isSignedIn: false,
        user: null,
        selectedRole: null,
        error: null,
      };
    case 'SET_ERROR':
      return {
        ...state,
        error: action.payload,
      };
    default:
      return state;
  }
};

export const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  // Restaurar sesión al iniciar la app
  useEffect(() => {
    const bootstrapAsync = async () => {
      try {
        const token = await SecureStore.getItemAsync('authToken');
        let user = null;
        if (token) {
          const userData = await AsyncStorage.getItem('user');
          if (userData) {
            user = JSON.parse(userData);
          }
        }
        dispatch({ type: 'RESTORE_TOKEN', payload: { token, user } });
      } catch (e) {
        dispatch({ type: 'RESTORE_TOKEN', payload: { token: null, user: null } });
      }
    };
    bootstrapAsync();
  }, []);

  const selectRole = useCallback((role) => {
    dispatch({ type: 'SELECT_ROLE', payload: role });
  }, []);

  const loginWithEmail = useCallback(async (usernameOrEmail, password) => {
    try {
      const response = await authService.login(usernameOrEmail, password);
      const { token, rol, nombre, email } = response.data;

      // Guardar token y datos del usuario
      await SecureStore.setItemAsync('authToken', token);
      const userData = {
        nombre,
        email,
        rol,
        username: usernameOrEmail,
      };
      await AsyncStorage.setItem('user', JSON.stringify(userData));

      dispatch({ type: 'LOGIN_SUCCESS', payload: { user: userData } });
      return true;
    } catch (error) {
      const errorMsg = error.response?.data?.message || 'Error al iniciar sesión. Verifica tus credenciales.';
      dispatch({ type: 'LOGIN_FAILURE', payload: errorMsg });
      return false;
    }
  }, []);

  const loginWithOAuth = useCallback(async (googleToken) => {
    try {
      const response = await api.post('/auth/oauth/google', { token: googleToken });
      const { token, user } = response.data;

      await SecureStore.setItemAsync('authToken', token);
      await AsyncStorage.setItem('user', JSON.stringify(user));

      dispatch({ type: 'LOGIN_SUCCESS', payload: { user } });
      return true;
    } catch (error) {
      const errorMsg = error.response?.data?.message || 'Error al iniciar sesión con Google';
      dispatch({ type: 'LOGIN_FAILURE', payload: errorMsg });
      return false;
    }
  }, []);

  const register = useCallback(async (email, password, nombre, apellido, username, tipo) => {
    try {
      const response = await authService.register(email, password, nombre, apellido, username, tipo);
      const { token, rol, nombre: responseNombre, email: responseEmail } = response.data;

      // Guardar token y datos del usuario
      await SecureStore.setItemAsync('authToken', token);
      const userData = {
        nombre: responseNombre,
        email: responseEmail,
        rol,
        username,
      };
      await AsyncStorage.setItem('user', JSON.stringify(userData));

      dispatch({ type: 'LOGIN_SUCCESS', payload: { user: userData } });
      return true;
    } catch (error) {
      const errorMsg = error.response?.data?.message || 'Error al registrarse. Intenta nuevamente.';
      dispatch({ type: 'LOGIN_FAILURE', payload: errorMsg });
      return false;
    }
  }, []);

  const logout = useCallback(async () => {
    try {
      await SecureStore.deleteItemAsync('authToken');
      await AsyncStorage.removeItem('user');
      dispatch({ type: 'LOGOUT' });
    } catch (error) {
      console.log('Error al cerrar sesión:', error);
    }
  }, []);

  const value = {
    state,
    selectRole,
    loginWithEmail,
    loginWithOAuth,
    register,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};
