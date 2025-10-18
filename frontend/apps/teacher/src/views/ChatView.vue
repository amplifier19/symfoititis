<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useRoute } from 'vue-router'
import { ref, onMounted, watch, computed } from 'vue'

import { Course, Booking } from '@symfoititis-frontend-monorepo/interfaces'

import { AvailabilityDataService } from '../core/services/availability/availability-data.service'
import { useBookingsDataService, useChatDataService, useCoursesDataService } from '@symfoititis-frontend-monorepo/core/services'

import { useBookingStore } from '@symfoititis-frontend-monorepo/stores'
import { useCourseStore } from '@symfoititis-frontend-monorepo/stores'

import { Page } from '@symfoititis-frontend-monorepo/ui'
import { Chat, ChatHeader } from '@symfoititis-frontend-monorepo/ui'
import { Toasts } from '@symfoititis-frontend-monorepo/ui'
import { NavHeader } from '@symfoititis-frontend-monorepo/ui'
import { Masterhead } from '@symfoititis-frontend-monorepo/ui'

const route = useRoute()

const { getCourses } = useCoursesDataService()
const { getBookings, cancelBooking } = useBookingsDataService()
const { connectToStompServer, getMessages, getChatStats } = useChatDataService()
const availabilityDataService = AvailabilityDataService.getAvailabilityDataFactory()

const courseStore = useCourseStore()
const bookingStore = useBookingStore()
const { courses } = storeToRefs(courseStore)
const { bookings } = storeToRefs(bookingStore)

const course = computed(() => {
  return courses.value.find((c: Course) => c.c_id === c_id.value) ||
    { dep_id: -1, semester: -1, c_display_name: '' }
})
const booking = computed(() => {
  return bookings.value.find((b: Booking) => b.b_id === b_id.value) ||
    { av_id: -1, c_id: -1, s_id: '', t_id: '', room: '', date: '', start_time: -1, state: '' }
})

const b_id = ref<number>(parseInt(route.params.b_id as string))
const c_id = ref<number>(parseInt(route.params.c_id as string))

const handleBookingCancelation = async () => {
  await cancelBooking(b_id.value)
  await availabilityDataService.getAvailabilitySlots(c_id.value)
}

onMounted(async () => {
  getChatStats()
  await getCourses()
  await getBookings()
  await getMessages(c_id.value, booking.value.s_id)
  connectToStompServer()
})

watch(route, () => {
  c_id.value = parseInt(route.params.c_id as string)
  b_id.value = parseInt(route.params.b_id as string)
})

</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="2" />
    </template>

    <template v-slot:subheader>
      <div class="sticky-cnt">
        <NavHeader navigation="bookings" storageItem="bookings_history" :course="course" />
        <ChatHeader :isTeacher="true" :booking="booking"/>
      </div>
    </template>

    <template v-slot:main>
      <Chat :booking="booking" />
    </template>
  </Page>
</template>

<style scoped>
.sticky-cnt {
  background-color: var(--white);
  position: sticky;
  top: 0;
}
</style>
