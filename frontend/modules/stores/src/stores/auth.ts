import { ref } from 'vue'
import { defineStore } from 'pinia'
import Cookies from 'universal-cookie'

import Keycloak, { type KeycloakError, type KeycloakProfile } from 'keycloak-js'

export const useAuthStore = defineStore("authStore", () => {
  let cookiesRestPath, cookiesDocsPath;
  if (import.meta.env.MODE === "development") {
    cookiesRestPath = new Cookies("Cookie", { path: `/rest/${import.meta.env.VITE_KC_REALM}`, sameSite: "lax" });
    cookiesDocsPath = new Cookies("Cookie", { path: `/${import.meta.env.VITE_KC_REALM}/documents`, sameSite: "lax" });
  } else {
    cookiesRestPath = new Cookies("Cookie", { path: `/rest/${import.meta.env.VITE_KC_REALM}`, secure: true, sameSite: "lax" });
    cookiesDocsPath = new Cookies("Cookie", { path: `/${import.meta.env.VITE_KC_REALM}/documents`, secure: true, sameSite: "lax" });
  }
  const keycloak = ref<any>({});
  const profile = ref<KeycloakProfile>({});
  const keycloakCreate = () => {
    if (Object.keys(keycloak.value).length > 0) {
      return;
    }
    keycloak.value = new Keycloak({
      url: import.meta.env.VITE_AUTH_BASE,
      realm: import.meta.env.VITE_KC_REALM,
      clientId: import.meta.env.VITE_KC_CLIENT_ID
    });
  };
  const keycloakInit = async () => {
    await keycloak.value.init({
      token: cookiesRestPath.get(import.meta.env.VITE_TOKEN_NAME) || "",
      refreshToken: localStorage.getItem(import.meta.env.VITE_REFRESH_TOKEN_NAME) || ""
    }).then(async () => {
      await keycloakLogin();
      setTokens();
    }).catch(async () => {
      await keycloakLogin();
      setTokens();
    });
  };
  const keycloakLogin = async () => {
    if (keycloak.value.authenticated) return;
    await keycloak.value.login();
  };
  const updateToken = async (sec: number) => {
    await keycloak.value.updateToken(sec).then((refreshed: boolean) => {
      if (refreshed) {
        setTokens()
      }
    }).catch(async () => {
      await keycloakLogin();
      setTokens();
    });
  };
  const getProfile = async () => {
    if (!!profile.value?.id) return;
    await keycloak.value.loadUserProfile().then((data: KeycloakProfile) => {
      profile.value = data;
    }).catch((error: KeycloakError) => {
      throw new Error(JSON.stringify({status: 500, error: error.error_description}));
    });
  };
  const logout = () => {
    removeTokens();
    keycloak.value.logout({
      redirectUri: import.meta.env.VITE_BASE
    });
  };
  const setTokens = () => {
    cookiesRestPath.set(import.meta.env.VITE_TOKEN_NAME, keycloak.value.token || "");
    cookiesDocsPath.set(import.meta.env.VITE_TOKEN_NAME, keycloak.value.token || "");
    localStorage.setItem(import.meta.env.VITE_REFRESH_TOKEN_NAME, keycloak.value.refreshToken || "");
  };
  const removeTokens = () => {
    cookiesRestPath.remove(import.meta.env.VITE_TOKEN_NAME);
    cookiesDocsPath.remove(import.meta.env.VITE_TOKEN_NAME);
    localStorage.removeItem(import.meta.env.VITE_REFRESH_TOKEN_NAME);
  };
  return { keycloak, profile, keycloakCreate, keycloakInit, getProfile, updateToken, logout };
});

