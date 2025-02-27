import { BookingsApiService } from './bookings-api.service'
import { useBookingStore } from '@symfoititis-frontend-monorepo/stores'
import { useResponseStore, useErrorStore } from '@symfoititis-frontend-monorepo/stores'

export const useBookingsDataService = () => {
    const bookingsApiService = BookingsApiService.getBookingsApiFactory()

    const errorStore = useErrorStore()
    const bookingStore = useBookingStore()
    const responseStore = useResponseStore()

    const getBookings = async () => {
        try {
            const response = await bookingsApiService.getBookings()
            const data = await response.json()
            if (!!data.error) {
                errorStore.addError(data)
                return
            }
            bookingStore.bookings = data.data
        } catch (err) {
            errorStore.addError(err)
        }
    }

    const addBookings = async (availabilityIds: number[]) => {
        try {
            const response = await bookingsApiService.addBookings(availabilityIds)
            const data = await response.json()
            if (data.error) {
                errorStore.addError(data)
                return
            }
            responseStore.addResponse(data)
        } catch (err) {
            errorStore.addError(err)
        }
    }

    const cancelBooking = async (b_id: number) => {
        try {
            const response = await bookingsApiService.cancelBooking(b_id)
            const data = await response.json()
            if (data.error) {
                errorStore.addError(data)
                return
            }
            responseStore.addResponse(data)
        } catch (err) {
            errorStore.addError(err)
        }
    }

    return { getBookings, addBookings, cancelBooking }
}
