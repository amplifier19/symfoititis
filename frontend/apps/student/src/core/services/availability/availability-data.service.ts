import { useAvailabilityStore } from "../../../stores/availability"
import { AvailabilityApiService } from "./availability-api.service"
import { useErrorStore } from "@symfoititis-frontend-monorepo/stores"

export class AvailabilityDataService {
    private static instance = new AvailabilityDataService()
    private availabilityApiService = AvailabilityApiService.getAvailabilityApiFactory()
    private availabilityStore = useAvailabilityStore()
    private errorStore = useErrorStore()
    private teacherId = ""
    private courseId = -1

    public constructor() { }

    public static getAvailabilityDataFactory() {
        return this.instance
    }

    public getAvailabilitySlots = async (cid: number, tid: string, refresh: boolean) => {
        if (!refresh && tid === this.teacherId && cid === this.courseId) return
        try {
            const response = await this.availabilityApiService.getAvailabilitySlots(cid, tid)
            const data = await response.json()
            if (!!data?.error) {
                this.errorStore.addError(data);
                return
            }
            this.courseId = cid
            this.teacherId = tid
            this.availabilityStore.availabilitySlots = data.data
        } catch (err) {
            this.errorStore.addError(err)
        }
    }
}