<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'

import { useBookingsDataService } from '@symfoititis-frontend-monorepo/core/services'
import { useCoursesDataService, useChatDataService } from '@symfoititis-frontend-monorepo/core/services'

import { useBookingStore } from '../../../../modules/stores/src/stores/bookings.store'

import { Page } from '@symfoititis-frontend-monorepo/ui'
import { Toasts } from '@symfoititis-frontend-monorepo/ui'
import { Masterhead } from '@symfoititis-frontend-monorepo/ui'
import { SearchHeader } from '@symfoititis-frontend-monorepo/ui'
import { BookingsGallery } from '@symfoititis-frontend-monorepo/ui'
import BookingProgram from 'modules/ui/src/components/BookingProgram.vue'
import BuyHours from '../components/BuyHours.vue'

const { getCourses } = useCoursesDataService()
const { getBookings } = useBookingsDataService()
const { connectToStompServer, getChatStats } = useChatDataService()

const bookingStore = useBookingStore()
const { bookings, upcomingBookings, pastBookings } = storeToRefs(bookingStore)

onMounted(async () => {
  getChatStats()
  await getCourses()
  await getBookings()
  connectToStompServer()
})
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="2" />
    </template>
    <template v-slot:main>
      <SearchHeader title="Χαρτοφυλάκιο" :display-search="false" />
      <div class="main-container">
        <BuyHours />
        <BookingsGallery :bookings="upcomingBookings" header="Προγραμματισμένα Μαθήματα" />
        <BookingProgram :bookings="bookings" />
        <BookingsGallery :bookings="pastBookings" header="Περασμένη δραστηριότητα" />
      </div>
    </template>
  </Page>
</template>

<style scoped>
.main-container {
  width: 100%;
  height: 100%;
}
</style>
