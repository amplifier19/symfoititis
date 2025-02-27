<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { ref, onMounted } from 'vue'

import { useBookingsDataService, useChatDataService, useCoursesDataService } from '@symfoititis-frontend-monorepo/core/services'

import { useBookingStore } from '@symfoititis-frontend-monorepo/stores'

import { Page } from '@symfoititis-frontend-monorepo/ui'
import { Toasts } from '@symfoititis-frontend-monorepo/ui'
import { Skeleton } from '@symfoititis-frontend-monorepo/ui'
import { Masterhead } from '@symfoititis-frontend-monorepo/ui'
import { SearchHeader } from '@symfoititis-frontend-monorepo/ui'
import { BookingsGallery } from '@symfoititis-frontend-monorepo/ui'
import BookingProgram from 'modules/ui/src/components/BookingProgram.vue'

const { getCourses } = useCoursesDataService()
const { getBookings } = useBookingsDataService()
const { connectToStompServer } = useChatDataService()

const bookingStore = useBookingStore()
const { bookings, upcomingBookings, pastBookings } = storeToRefs(bookingStore)

const displaySkeleton = ref<boolean>(false)

onMounted(async () => {
  displaySkeleton.value = true
  await getCourses()
  await getBookings()
  connectToStompServer()
  displaySkeleton.value = false
})
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="1" />
    </template>
    <template v-slot:main>
      <SearchHeader title="Μαθήματα" :display-search="false" />
      <div v-if="!displaySkeleton">
        <BookingsGallery :bookings="upcomingBookings" header="Προγραμματισμένα Μαθήματα" />
        <BookingProgram :bookings="bookings" />
        <BookingsGallery :bookings="pastBookings" header="Περασμένη δραστηριότητα" />
      </div>
      <section v-else class="main-container">
        <Skeleton />
      </section>
    </template>
  </Page>
</template>