export class DepartmentsApiService {
  private static instance = new DepartmentsApiService();
  private readonly API_BASE_URL = import.meta.env.VITE_API_BASE;

  public constructor() {}

  public static getDepartmentsApiFactory() {
    return this.instance;
  }

  public getDepartment = () => {
    return fetch(`${this.API_BASE_URL}/institutions/department`, {
      method: "GET",
    });
  };
}
