import { useTeacherStore } from "../../../stores/teachers"
import { TeachersApiService } from "./teachers-api.service"
import { useErrorStore } from "@symfoititis-frontend-monorepo/stores"

export class TeachersDataService {
    private static instance: TeachersDataService = new TeachersDataService()
    private teachersApiService: TeachersApiService = TeachersApiService.getTeachersApiFactory()

    private errorStore = useErrorStore()
    private teacherStore = useTeacherStore()

    private constructor() { }

    public static getTeachersDataFactory() {
        return this.instance
    }

    public getTeachers = async (cid: number) => {
        try {
            const response = await this.teachersApiService.getTeachers(cid)
            const data = await response.json()
            if (!!data.error) {
                this.errorStore.addError(data)
                return
            }
            this.teacherStore.teachers = data.data
        } catch (err) {
            this.errorStore.addError(err)
        }
    }
}