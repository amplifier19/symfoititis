export class CoursesApiService {
    private static instance = new CoursesApiService()
    private readonly API_BASE_URL = import.meta.env.VITE_API_BASE

    public constructor() { }

    public static getCoursesApiFactory() {
        return this.instance
    }

    public getCourses = () => {
        return fetch(
            `${this.API_BASE_URL}/education/courses`,
            {
                method: 'GET'
            }
        )
    }

    public getAvailableTutoringCourseIds = (dep_id: number) => {
        return fetch(
            `${this.API_BASE_URL}/tutoring/availability/${dep_id}/courseIds`,
            {
                method: 'GET'
            }
        )
    }
}