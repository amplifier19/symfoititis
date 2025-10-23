import { useUniStore } from "../../../stores/universities.store";
import { useDisplayModal } from "../../../stores/displayModal";
import { UniversitiesApiService } from "./universities-api.service";
import {
  useErrorStore,
  useResponseStore,
} from "@symfoititis-frontend-monorepo/stores";
import type { University } from "@symfoititis-frontend-monorepo/interfaces";

export class UniversitiesDataService {
  private static instance = new UniversitiesDataService();
  private apiService = UniversitiesApiService.getUniversitiesApiFactory();
  private universityStore = useUniStore();
  private responseStore = useResponseStore();
  private errorStore = useErrorStore();
  private modalStore = useDisplayModal();

  public constructor() {}

  public static getUniversitiesDataFactory() {
    return this.instance;
  }

  public getUniversities = async (force?: boolean) => {
    if (this.universityStore.universities.length > 0 && !force) {
      return;
    }
    try {
      const response = await this.apiService.getUniversities();
      const data = await response.json();
      if (!!data?.error) {
        this.errorStore.addError(data.error);
      } else {
        this.universityStore.setUniversities(data.data);
      }
    } catch (err) {
      this.errorStore.addError(err);
    }
  };

  public createUniversity = async (uni: University) => {
    try {
      const response = await this.apiService.createUniversity(uni);
      const data = await response.json();
      if (!!data?.error) {
        this.errorStore.addError(data.error);
      } else {
        this.responseStore.addResponse(data);
        this.getUniversities(true);
      }
    } catch (err) {
      this.errorStore.addError(err);
    } finally {
      this.modalStore.setDisplay(false);
    }
  };

  public updateUniversity = async (uni: University) => {
    try {
      const response = await this.apiService.updateUniversity(uni);
      const data = await response.json();
      if (!!data?.error) {
        this.errorStore.addError(data.error);
      } else {
        this.responseStore.addResponse(data);
        this.getUniversities(true);
      }
    } catch (err) {
      this.errorStore.addError(err);
    } finally {
      this.modalStore.setDisplay(false);
    }
  };

  public deleteUniversity = async (uni_id: number, uni_alt_name: string) => {
    try {
      if (!this.universityStore.currentUniversity) {
        throw "Current university is not set";
      }
      if (
        this.universityStore.currentUniversity.uni_alt_name !== uni_alt_name
      ) {
        throw "Wrong university alt name";
      }
      const response = await this.apiService.deleteUniversity(uni_id);
      const data = await response.json();
      if (!!data?.error) {
        this.errorStore.addError(data.error);
      } else {
        this.responseStore.addResponse(data);
        this.getUniversities(true);
      }
    } catch (err) {
      this.errorStore.addError(err);
    } finally {
      this.modalStore.setDisplay(false);
    }
  };
}
