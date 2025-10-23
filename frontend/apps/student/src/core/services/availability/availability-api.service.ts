export class AvailabilityApiService {
  private static instance = new AvailabilityApiService();
  private readonly API_BASE_URL = import.meta.env.VITE_API_BASE;

  public constructor() {}

  public static getAvailabilityApiFactory() {
    return this.instance;
  }

  public getAvailabilitySlots = async (cid: number, tid: string) => {
    return await fetch(
      `${this.API_BASE_URL}/tutoring/availability/${cid}/teacher/${tid}`,
      {
        method: "GET",
      },
    );
  };
}
