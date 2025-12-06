export class PurchaseApiService {
  private static instance = new PurchaseApiService();
  private readonly API_BASE_URL = import.meta.env.VITE_API_BASE;

  public constructor() {}

  public static getPurchaseApiFactory() {
    return this.instance;
  }

  public getPurchaseProducts = () => {
    return fetch(
      `${this.API_BASE_URL}/tutoring/purchase/products`,
      {
        method: "GET",
      },
    );
  };

  public getPurchaseProduct = (id: number) => {
    return fetch(
      `${this.API_BASE_URL}/tutoring/purchase/products/${id}`,
      {
        method: "GET",
      },
    );
  };
  
  public getStudentBalance = () => {
    return fetch(
      `${this.API_BASE_URL}/tutoring/purchase/student-balance`,
      {
        method: "GET",
      },
    );
  };

  public createPaymentIntent = (id: number) => {
    return fetch(
      `${this.API_BASE_URL}/tutoring/purchase/payment-intent/product/${id}`,
      {
        method: "GET",
      },
    );
  };
}