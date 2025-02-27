import { useUserStore } from "@symfoititis-frontend-monorepo/stores"
import { useAvailabilityStore } from "../../../stores/availability"

export class AvailabilityApiService {
    private static instance = new AvailabilityApiService()
    private readonly API_BASE_URL = import.meta.env.VITE_API_BASE
    private userStore = useUserStore()
    private availabilityStore = useAvailabilityStore()

    public constructor() { }

    public static getAvailabilityApiFactory() {
        return this.instance
    }

    public getAvailabilitySlots = async (cid: number) => {
        return await fetch(`${this.API_BASE_URL}/tutoring/availability/${cid}/teacher/${this.userStore.profile.id}`, {
            method: 'GET'
        })
    }

    public addAvailabilitySlots = async () => {
        return await fetch(`${import.meta.env.VITE_API_BASE}/tutoring/availability`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.availabilityStore.filteredInsertableAvailabilitySlots)
        })
    }

    public updateAvailabilitySlots = async () => {
        return await fetch(`${this.API_BASE_URL}/tutoring/availability`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.availabilityStore.updatableAvailabilitySlots)
        })
    }

    public cancelAvailabilitySlots = async () => {
        return await fetch(`${this.API_BASE_URL}/tutoring/availability`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.availabilityStore.cancelableAvailabilitySlotIds)
        })
    }
}