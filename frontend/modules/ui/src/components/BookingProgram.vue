<script setup lang="ts">
import { computed, ref } from 'vue'

import { Day, Booking } from '@symfoititis-frontend-monorepo/interfaces'

import Calendar from './Calendar.vue'
import Subheader from './Subheader.vue'
import BookingList from './BookingList.vue'

const props = defineProps<{
    bookings: Booking[]
}>()

const bookingsFilteredByDate = computed(() => {
  return props.bookings.filter(booking => booking.date === selectedDay.value.date)
})

const selectedDay = ref<Day>({
  monthDay: -1,
  weekDay: -1,
  date: "",
  av_id: -100,
  cell_classes: [],
  btn_classes: []
})

const selectDate = (day: Day) => {
  selectedDay.value = day;
}
</script>

<template>
<div class="program-container">
  <Subheader title="Πρόγραμμα" />
  <section class="main-container">
  <div class="date-time-pick-container">
    <Calendar @select-date="selectDate" :booking="true" :bookings="props.bookings" />
    <BookingList :bookings="bookingsFilteredByDate" :selectedDay="selectedDay" />
  </div>
  </section>
</div>
</template>

<style scoped>
.program-container {
  margin-top: 6rem;
}

.date-time-pick-container {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  margin-bottom: 1rem;
}
</style>