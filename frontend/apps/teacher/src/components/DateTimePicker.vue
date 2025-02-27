<script setup lang="ts">
import { useRoute } from 'vue-router'
import { ref, onMounted, watch } from 'vue'
import { storeToRefs } from 'pinia'

import TimePicker from './TimePicker.vue'
import { Calendar } from '@symfoititis-frontend-monorepo/ui'

import { Day } from '@symfoititis-frontend-monorepo/interfaces'

import { AvailabilityDataService } from '../core/services/availability/availability-data.service'

import { useAvailabilityStore } from '../stores/availability'

const route = useRoute()

const availabilityDataService = AvailabilityDataService.getAvailabilityDataFactory()

const availabilityStore = useAvailabilityStore()
const { availabilitySlots } = storeToRefs(availabilityStore)

const cid = ref<number>(parseInt(route.params.c_id as string))
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

onMounted(async () => {
  cid.value = parseInt(route.params.c_id as string)
  await availabilityDataService.getAvailabilitySlots(cid.value)
})

watch(route, async (oldRoute, newRoute) => {
  cid.value = parseInt(route.params.c_id as string)
  await availabilityDataService.getAvailabilitySlots(cid.value)
})
</script>

<template>
  <div class="date-time-pick-container">
    <Calendar @select-date="selectDate" :availabilitySlots="availabilitySlots" />
    <TimePicker :selectedDay="selectedDay" />
  </div>
</template>

<style scoped>
.date-time-pick-container {
  display: flex;
  justify-content: space-evenly;
  flex-wrap: wrap;
  margin-bottom: 1rem;
}
</style>
