import { AvailabilityApiService } from "./availability-api.service";

import { useAvailabilityStore } from "../../../stores/availability";
import {
  useErrorStore,
  useResponseStore,
} from "@symfoititis-frontend-monorepo/stores";

export class AvailabilityDataService {
  private static instance = new AvailabilityDataService();

  private errorStore = useErrorStore();
  private responseStore = useResponseStore();
  private availabilityStore = useAvailabilityStore();
  private availabilityApiService =
    AvailabilityApiService.getAvailabilityApiFactory();

  public constructor() {}

  public static getAvailabilityDataFactory() {
    return this.instance;
  }

  public getAvailabilitySlots = async (cid: number) => {
    try {
      const response =
        await this.availabilityApiService.getAvailabilitySlots(cid);
      const data = await response.json();
      if (!!data.error) {
        this.errorStore.addError(data);
        return;
      }
      this.availabilityStore.availabilitySlots = data.data;
    } catch (err) {
      this.errorStore.addError(err);
    }
  };

  public addAvailabilitySlots = async () => {
    if (this.availabilityStore.filteredInsertableAvailabilitySlots.length === 0)
      return;
    try {
      const response = await this.availabilityApiService.addAvailabilitySlots();
      const data = await response.json();
      if (!!data.error) {
        this.errorStore.addError(data);
        return;
      }
      this.availabilityStore.insertableAvailabilitySlots = [];
      this.responseStore.addResponse(data);
    } catch (err) {
      this.errorStore.addError(err);
    }
  };

  public updateAvailabilitySlots = async () => {
    if (this.availabilityStore.updatableAvailabilitySlots.length === 0) return;
    try {
      const response =
        await this.availabilityApiService.updateAvailabilitySlots();
      const data = await response.json();

      if (!!data.error) {
        this.availabilityStore.updatableAvailabilitySlots = [];
        this.errorStore.addError(data);
        return;
      }
      this.availabilityStore.updatableAvailabilitySlots = [];
      this.responseStore.addResponse(data);
    } catch (err) {
      this.errorStore.addError(err);
    }
  };

  public cancelAvailabilitySlots = async () => {
    if (this.availabilityStore.cancelableAvailabilitySlotIds.length === 0)
      return;
    try {
      const response =
        await this.availabilityApiService.cancelAvailabilitySlots();
      const data = await response.json();
      if (!!data.error) {
        this.availabilityStore.cancelableAvailabilitySlotIds = [];
        this.errorStore.addError(data);
        return;
      }
      this.availabilityStore.cancelableAvailabilitySlotIds = [];
      this.responseStore.addResponse(data);
    } catch (err) {
      this.errorStore.addError(err);
    }
  };

  public saveAvailabilityChanges = async (cid: number) => {
    await Promise.all([
      this.cancelAvailabilitySlots(),
      this.updateAvailabilitySlots(),
      this.addAvailabilitySlots(),
    ]);
    await this.getAvailabilitySlots(cid);
  };
}
