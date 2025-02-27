<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useRoute } from 'vue-router'
import { ref, watch, onBeforeMount } from 'vue'

import { type Teacher, type Day } from "@symfoititis-frontend-monorepo/interfaces"

import { useAvailabilityStore } from '../stores/availability'
import { AvailabilityDataService } from '../core/services/availability/availability-data.service'

import TimePicker from './TimePicker.vue'
import { Calendar } from '@symfoititis-frontend-monorepo/ui'

const route = useRoute();

const props = defineProps<{
  teacher: Teacher
}>()

const availabilityStore = useAvailabilityStore()
const { availabilitySlots } = storeToRefs(availabilityStore)

const availabilityDataService = AvailabilityDataService.getAvailabilityDataFactory()

const selectedDay = ref<Day>({
  monthDay: -1,
  weekDay: -1,
  date: '',
  av_id: -100,
  cell_classes: [],
  btn_classes: []
})
const c_id = ref<number>(parseInt(route.params.c_id as string))

const selectDate = (day: Day) => {
  selectedDay.value = day
}

onBeforeMount(async () => {
  await availabilityDataService.getAvailabilitySlots(c_id.value, props.teacher.t_id, false)
})

watch(props, async (newProps, oldProps) => {
  await availabilityDataService.getAvailabilitySlots(c_id.value, props.teacher.t_id, false)
})

watch(route, (newRoute, oldRoute) => {
  c_id.value = parseInt(route.params.c_id as string)
})
</script>

<template>
  <div class="date-time-pick-container">
    <Calendar @select-date="selectDate" :availabilitySlots="availabilitySlots" />
    <TimePicker :selectedDay="selectedDay" :teacherId="props.teacher.t_id" />
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
