import { ref } from 'vue'
import { defineStore } from 'pinia'

import Keycloak, { type KeycloakError, type KeycloakProfile } from 'keycloak-js'

export const useAuthStore = defineStore('auth', () => {
  const keycloak = ref<any>({})
  const profile = ref<KeycloakProfile>({})

  const keycloakCreate = () => {
    if (Object.keys(keycloak.value).length > 0) {
      return
    }
    keycloak.value = new Keycloak({
      url: import.meta.env.VITE_AUTH_API_URL,
      realm: import.meta.env.VITE_KC_REALM,
      clientId: import.meta.env.VITE_KC_CLIENT_ID
    })
  }

  const keycloakInit = async () => {
    await keycloak.value
      .init({
        token: localStorage.getItem('token') || '',
        refreshToken: localStorage.getItem('refreshToken') || ''
      })
      .then(async () => {
        await keycloakLogin()
        updateLocalStorage()
      })
      .catch(async () => {
        await keycloakLogin()
        updateLocalStorage()
      })
  }

  const keycloakLogin = async () => {
    if (keycloak.value.authenticated) return
    await keycloak.value.login()
  }

  const updateToken = async (sec: number) => {
    await keycloak.value
      .updateToken(sec)
      .then((refreshed: boolean) => {
        if (refreshed) {
          updateLocalStorage()
        }
      })
      .catch(async () => {
        await keycloakLogin()
        updateLocalStorage()
      })
  }

  const getProfile = async () => {
    await keycloak.value
      .loadUserProfile()
      .then((data: KeycloakProfile) => {
        profile.value = data
      })
      .catch((error: KeycloakError) => {
        throw new Error(error.error_description)
      })
  }

  const logout = () => {
    clearLocalStorage()
    keycloak.value.logout({
      redirectUri: import.meta.env.VITE_BASE_URL
    })
  }

  const updateLocalStorage = () => {
    localStorage.setItem('token', keycloak.value.token || '')
    localStorage.setItem('refreshToken', keycloak.value.refreshToken || '')
  }

  const clearLocalStorage = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('refreshToken')
  }

  return { keycloak, profile, keycloakCreate, keycloakInit, getProfile, updateToken, logout }
})
