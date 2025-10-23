import type { Course } from "@symfoititis-frontend-monorepo/interfaces";

export class CoursesApiService {
  private static instance = new CoursesApiService();
  private readonly API_BASE = import.meta.env.VITE_API_BASE;

  public constructor() {}

  public static getCoursesApiFactory() {
    return this.instance;
  }

  public getCourses(dep_id: number) {
    return fetch(`${this.API_BASE}/education/courses/${dep_id}`, {
      method: "GET",
    });
  }

  public getCourse(c_id: number) {
    return fetch(`${this.API_BASE}/education/course/${c_id}`, {
      method: "GET",
    });
  }

  public createCourse(course: Course) {
    return fetch(`${this.API_BASE}/education/course`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(course),
    });
  }

  public updateCourse(course: Course) {
    return fetch(`${this.API_BASE}/education/course`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(course),
    });
  }

  public deleteCourse(c_id: number) {
    return fetch(`${this.API_BASE}/education/course/${c_id}`, {
      method: "DELETE",
    });
  }
}
