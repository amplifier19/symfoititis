import { defineStore } from "pinia";
import { computed, ref } from "vue";
import { Booking } from "@symfoititis-frontend-monorepo/interfaces";

export const useBookingStore = defineStore("bookingStore", () => {
  const bookings = ref<Booking[]>([]);

  const upcomingBookings = computed(() => {
    return bookings.value.filter((booking: Booking) => {
      const HH = booking.start_time.toString().padStart(2, "0");
      return Date.parse(`${booking.date}T${HH}:00:00`) >= Date.now();
    });
  });

  const pastBookings = computed(() => {
    return bookings.value.filter((booking: Booking) => {
      const HH = booking.start_time.toString().padStart(2, "0");
      return Date.parse(`${booking.date}T${HH}:00:00`) < Date.now();
    });
  });

  return { bookings, upcomingBookings, pastBookings };
});
