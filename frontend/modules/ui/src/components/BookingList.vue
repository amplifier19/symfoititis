<script setup lang="ts">
import { type Day, type Booking as BookingType } from '@symfoititis-frontend-monorepo/interfaces'

import Booking from './Booking.vue';
import SearchHeader from './SearchHeader.vue';
import { useDate } from '@symfoititis-frontend-monorepo/composables';

const props = defineProps<{
    bookings: BookingType[]
    selectedDay: Day
}>()

const { formatDate } = useDate()
</script>

<template>
  <ul class="booking-list">
    <li class="booking-list-header-container ">
        <SearchHeader :title="props.selectedDay && props.selectedDay.date != '' ? formatDate(props.selectedDay.date, 'dd/MM/yyyy') : 'Επέλεξε ημερομηνία'" :displaySearch="false"/>
    </li>
    <li v-for="(booking, idx) in bookings" class="booking-item">
        <Booking :booking="booking" :card="false"/>
    </li>
    </ul>
</template>

<style>
.booking-list {
  width: clamp(350px, 50%, 50%);
  display: flex;
  flex-direction: column;
}

@media screen and (max-width: 590px) {
  .booking-list {
    min-height: 250px;
  }
}
</style>