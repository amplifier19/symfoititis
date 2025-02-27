<script setup lang="ts">
import { type Day, type Booking as BookingType } from '@symfoititis-frontend-monorepo/interfaces'

import Booking from './Booking.vue';
import SearchHeader from './SearchHeader.vue';

const props = defineProps<{
    bookings: BookingType[]
    selectedDay: Day
}>()
</script>

<template>
  <ul class="time-picker">
    <li class="regular-text booking-list-header-container">
        <SearchHeader :title="props.selectedDay.date || 'Επέλεξε ημερομηνία'" :displaySearch="false"/>
    </li>
    <li v-for="(booking, idx) in bookings" class="booking-item">
        <Booking :booking="booking" :card="false"/>
    </li>
    </ul>
</template>

<style>
.booking-list-header-container {
    margin-bottom: 2rem;
}

li>div>span {
  color: var(--white);
  font-family: 'Geologica-Medium' !important;
  font-size: 1rem !important;
}

.time-picker {
  margin: 24px;
  display: flex;
  flex-direction: column;
  width: 600px;
  min-height: 353px;
  height: max-content;
}

@media screen and (max-width: 590px) {
  .time-picker {
    min-height: 250px;
  }
}
</style>