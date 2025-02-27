import Keycloak from 'keycloak-js'
import Cookies from 'universal-cookie'

export class AuthAdapterService {
  private readonly MODE = import.meta.env.MODE
  private readonly AUTH_BASE_URL = import.meta.env.VITE_AUTH_BASE
  private readonly API_BASE_URL = import.meta.env.VITE_API_BASE
  private readonly REALM = import.meta.env.VITE_KC_REALM
  private readonly CLIENT_ID = import.meta.env.VITE_KC_CLIENT_ID
  private readonly TOKEN_NAME = import.meta.env.VITE_TOKEN_NAME

  private static instance = new AuthAdapterService()
  private keycloak = new Keycloak(
    {
      url: this.AUTH_BASE_URL,
      realm: this.REALM,
      clientId: this.CLIENT_ID
    }
  )

  private cookieRest
  private cookieDocs

  public constructor() {
    if (this.MODE === "development") {
      this.cookieRest = new Cookies("Cookie", { path: `/rest/${this.REALM}`, sameSite: "lax" })
      this.cookieDocs = new Cookies("Cookie", { path: `/${this.REALM}/documents`, sameSite: "lax" })
    } else {
      this.cookieRest = new Cookies("Cookie", { path: `/rest/${this.REALM}`, secure: true, sameSite: "lax" })
      this.cookieDocs = new Cookies("Cookie", { path: `/${this.REALM}/documents`, secure: true, sameSite: "lax" })
    }
    this.initAuthCallbacks()
  }

  public static getAuthAdapterFactory() {
    return this.instance
  }

  public keycloakInit = async () => {
    if (!!this.keycloak.authenticated) return
    return await this.keycloak.init(
      {
        onLoad: 'login-required'
      }
    )
  }

  public loadUserProfile() {
    return this.keycloak.loadUserProfile()
  }

  public logout() {
    this.keycloak.clearToken()
    this.keycloak.logout(
      {
        redirectUri: this.API_BASE_URL
      }
    )
  }

  private initAuthCallbacks() {
    this.keycloak.onReady = () => {
      this.setCookies()
    }
    this.keycloak.onAuthSuccess = () => {
      this.setCookies()
    }
    this.keycloak.onAuthRefreshSuccess = () => {
      this.setCookies()
    }
    this.keycloak.onAuthRefreshError = () => {
      this.keycloak.login()
    }
    this.keycloak.onTokenExpired = () => {
      this.keycloak.updateToken()
    }
    this.keycloak.onAuthLogout = () => {
      this.removeCookies()
    }
  }

  private setCookies() {
    this.cookieRest.set(this.TOKEN_NAME, this.keycloak.token || "")
    this.cookieDocs.set(this.TOKEN_NAME, this.keycloak.token || "")
  }

  private removeCookies() {
    this.cookieRest.remove(this.TOKEN_NAME)
    this.cookieDocs.remove(this.TOKEN_NAME)
  }
}