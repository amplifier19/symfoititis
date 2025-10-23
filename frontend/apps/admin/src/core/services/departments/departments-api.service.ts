import type { Department } from "@symfoititis-frontend-monorepo/interfaces";

export class DepartmentsApiService {
  private static instance = new DepartmentsApiService();
  private readonly API_BASE = import.meta.env.VITE_API_BASE;

  public constructor() {}

  public static getDepartmentsApiFactory() {
    return this.instance;
  }

  public getDepartments() {
    return fetch(`${this.API_BASE}/institutions/departments`, {
      method: "GET",
    });
  }

  public createDepartment(dep: Department) {
    return fetch(`${this.API_BASE}/institutions/department`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(dep),
    });
  }

  public updateDepartment(dep: Department) {
    return fetch(`${this.API_BASE}/institutions/department`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(dep),
    });
  }

  public deleteDepartment(dep_id: number) {
    return fetch(`${this.API_BASE}/institutions/department/${dep_id}`, {
      method: "DELETE",
    });
  }
}
