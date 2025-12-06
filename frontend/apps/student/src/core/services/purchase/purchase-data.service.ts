import { useErrorStore, useUserStore } from "@symfoititis-frontend-monorepo/stores";
import { usePurchaseStore } from "../../../stores/purchase";
import { PurchaseApiService } from "./purchase-api.service";

export class PurchaseDataService {
  private static instance = new PurchaseDataService();
  private purchaseApiService = PurchaseApiService.getPurchaseApiFactory();

  private purchaseStore = usePurchaseStore();
  private errorStore = useErrorStore();
  private userStore = useUserStore();

  public constructor() { }

  public static getPurchaseDataFactory() {
    return this.instance;
  }

  public getPurchaseProducts = async () => {
    try {
      if (this.purchaseStore.purchaseProducts.length > 0) return;
      const response = await this.purchaseApiService.getPurchaseProducts();
      const data = await response.json();
      if (!!data?.error) {
        this.errorStore.addError(data);
        return;
      }
      this.purchaseStore.purchaseProducts = data.data;
    } catch (err) {
      this.errorStore.addError(err);
    }
  };

  public getPurchaseProduct = async (id: number) => {
    try {
      const response = await this.purchaseApiService.getPurchaseProduct(id);
      const data = await response.json();
      if (!!data?.error) {
        this.errorStore.addError(data);
        return;
      }
      this.purchaseStore.purchaseProducts = data.data;
    } catch (err) {
      this.errorStore.addError(err);
    }
  };

  public getStudentBalance = async () => {
    try {
      const response = await this.purchaseApiService.getStudentBalance();
      const data = await response.json();
      if (!!data?.error) {
        this.errorStore.addError(data);
        return;
      }
      if (data.data != null) {
        this.purchaseStore.studentBalance = data.data;
      }
    } catch (err) {
      this.errorStore.addError(err);
    }
  };

  public createPaymentIntent = async (id: number) => {
    if (this.purchaseStore.clientSecrets.has(id)) {
      return;
    }
    try {
      const response = await this.purchaseApiService.createPaymentIntent(id);
      const data = await response.json();
      console.log(data);
      if (!!data?.error) {
        this.errorStore.addError(data);
        return;
      }
      if (data.data != null) {
        this.purchaseStore.clientSecrets.set(id, data.data);
      }
    } catch (err) {
      this.errorStore.addError(err);
    }
  };
}