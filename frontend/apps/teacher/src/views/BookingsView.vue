<script setup lang="ts">
import { storeToRefs } from "pinia";
import { onMounted } from "vue";

import {
  useBookingsDataService,
  useChatDataService,
  useCoursesDataService,
} from "@symfoititis-frontend-monorepo/core/services";

import { useBookingStore } from "@symfoititis-frontend-monorepo/stores";

import { Page } from "@symfoititis-frontend-monorepo/ui";
import { Toasts } from "@symfoititis-frontend-monorepo/ui";
import { Masterhead } from "@symfoititis-frontend-monorepo/ui";
import { SearchHeader } from "@symfoititis-frontend-monorepo/ui";
import { BookingsGallery } from "@symfoititis-frontend-monorepo/ui";
import BookingProgram from "modules/ui/src/components/BookingProgram.vue";

const { getCourses } = useCoursesDataService();
const { getBookings } = useBookingsDataService();
const { connectToStompServer, getChatStats } = useChatDataService();

const bookingStore = useBookingStore();
const { bookings, upcomingBookings, pastBookings } = storeToRefs(bookingStore);

onMounted(async () => {
  getChatStats();
  await getCourses();
  await getBookings();
  connectToStompServer();
});
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="2" />
    </template>
    <template v-slot:main>
      <SearchHeader title="Μαθήματα" :display-search="false" />
      <div>
        <BookingsGallery
          :bookings="upcomingBookings"
          header="Προγραμματισμένα Μαθήματα"
        />
        <BookingProgram :bookings="bookings" />
        <BookingsGallery
          :bookings="pastBookings"
          header="Περασμένη δραστηριότητα"
        />
      </div>
    </template>
  </Page>
</template>
