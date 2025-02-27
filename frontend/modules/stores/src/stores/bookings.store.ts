import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { Booking } from '@symfoititis-frontend-monorepo/interfaces'

export const useBookingStore = defineStore("bookingStore", () => {
  const bookings = ref<Booking[]>([])

  const upcomingBookings = computed(() => {
    const now = new Date()
    return bookings.value.filter((booking: Booking) => {
      const formattedHour = booking.start_time < 10 ? `0${booking.start_time}` : `${booking.start_time}`
      const bookingDate = new Date(`${booking.date}T${formattedHour}:00:00`)
      return bookingDate.getTime() >= now.getTime() - 2 * 60 * 60 * 1000
    })
  })

  const pastBookings = computed(() => {
    const now = new Date()
    return bookings.value.filter((booking: Booking) => {
      const formattedHour = booking.start_time < 10 ? `0${booking.start_time}` : `${booking.start_time}`
      const bookingDate = new Date(`${booking.date}T${formattedHour}:00:00`)
      return bookingDate.getTime() < now.getTime() - 2 * 60 * 60 * 1000
    })
  })

  return { bookings, upcomingBookings, pastBookings }
})
