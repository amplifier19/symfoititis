export class UniversitiesApiService {
  private static instance = new UniversitiesApiService();
  private readonly API_BASE_URL = import.meta.env.VITE_API_BASE;

  public constructor() {}

  public static getUniversitiesApiFactory() {
    return this.instance;
  }

  public getUniversity = async () => {
    return await fetch(`${this.API_BASE_URL}/institutions/university`, {
      method: "GET",
    });
  };
}
