export class TeachersApiService {
  private static instance: TeachersApiService = new TeachersApiService();
  private readonly API_BASE_URL = import.meta.env.VITE_API_BASE;

  private constructor() {}

  public static getTeachersApiFactory() {
    return this.instance;
  }

  public getTeachers = async (cid: number) => {
    return await fetch(
      `${this.API_BASE_URL}/tutoring/availability/${cid}/teachers`,
      {
        method: "GET",
      },
    );
  };
}
