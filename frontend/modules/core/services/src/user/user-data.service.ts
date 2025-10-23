import { KeycloakProfile } from "keycloak-js";
import {
  AuthAdapterService,
  DepartmentsApiService,
  UniversitiesApiService,
} from "@symfoititis-frontend-monorepo/core/services";
import {
  useErrorStore,
  useUserStore,
} from "@symfoititis-frontend-monorepo/stores";
import { storeToRefs } from "pinia";

export const useUserDataService = () => {
  const authAdapterService = AuthAdapterService.getAuthAdapterFactory();
  const universitiesApiService =
    UniversitiesApiService.getUniversitiesApiFactory();
  const departmentsApiService =
    DepartmentsApiService.getDepartmentsApiFactory();

  const { profile, university, department } = storeToRefs(useUserStore());
  const errorStore = useErrorStore();

  const initAuthAdapter = async () => {
    try {
      await authAdapterService.keycloakInit();
    } catch (err) {
      if (!err) err = "Error while attempting to initialize auth adapter";
      errorStore.addError(err);
    }
  };

  const loadUserProfile = () => {
    if (Object.keys(profile.value).length > 0) return;
    authAdapterService
      .loadUserProfile()
      .then((prof: KeycloakProfile) => {
        profile.value = prof;
      })
      .catch((err) => {
        if (!err) err = "Error while attempting to load user profile";
        errorStore.addError(err);
      });
  };

  const loadUserUniversity = async () => {
    if (!!university.value.uni_alt_name) return;
    try {
      const response = await universitiesApiService.getUniversity();
      const data = await response.json();
      if (!!data.error) {
        errorStore.addError(data);
        return;
      }
      university.value = data.data;
    } catch (err) {
      if (!err) err = "Error while attempting to load user's university";
      errorStore.addError(err);
    }
  };

  const loadUserDepartment = async () => {
    if (!!department.value.dep_alt_name) return;
    try {
      const response = await departmentsApiService.getDepartment();
      const data = await response.json();
      if (!!data.error) {
        errorStore.addError(data);
        return;
      }
      department.value = data.data;
    } catch (err) {
      if (!err) err = "Error while attempting to load user's department";
      errorStore.addError(err);
    }
  };

  const logout = () => {
    authAdapterService.logout();
  };

  const loadUser = () => {
    loadUserProfile();
    loadUserDepartment();
    loadUserUniversity();
  };

  return { initAuthAdapter, loadUser, logout };
};
