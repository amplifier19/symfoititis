import type { University } from "@symfoititis-frontend-monorepo/interfaces";

export class UniversitiesApiService {
  private static instance = new UniversitiesApiService();
  private readonly API_BASE = import.meta.env.VITE_API_BASE;

  public constructor() {}

  public static getUniversitiesApiFactory() {
    return this.instance;
  }

  public getUniversities(): Promise<Response> {
    return fetch(`${this.API_BASE}/institutions/universities`, {
      method: "GET",
    });
  }

  public createUniversity(uni: University): Promise<Response> {
    return fetch(`${this.API_BASE}/institutions/university`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(uni),
    });
  }

  public updateUniversity(uni: University): Promise<Response> {
    return fetch(`${this.API_BASE}/institutions/university`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(uni),
    });
  }

  public deleteUniversity(uni_id: number): Promise<Response> {
    return fetch(`${this.API_BASE}/institutions/university/${uni_id}`, {
      method: "DELETE",
    });
  }
}
