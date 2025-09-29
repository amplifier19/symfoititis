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
  <section class="wrapper">

<div class="program-container content-width">
  <Subheader title="Το Πρόγραμμα μου" />
  <div class="date-time-pick-container">
    <Calendar @select-date="selectDate" :booking="true" :bookings="props.bookings" />
    <BookingList :bookings="bookingsFilteredByDate" :selectedDay="selectedDay" />
  </div>
</div>
  </section>
</template>

<style scoped>
.date-time-pick-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly; 
}
</style>
