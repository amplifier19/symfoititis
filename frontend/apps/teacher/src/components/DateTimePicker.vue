<script setup lang="ts">
import { ref, onBeforeMount, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useTeacherFetch } from '../composables/fetch'
import { useAvailabilityStore } from '../stores/availability'
import type { Day } from '@symfoititis-frontend-monorepo/interfaces';
import { Calendar } from '@symfoititis-frontend-monorepo/ui';
import TimePicker from './TimePicker.vue';

const route = useRoute();
const { getAvailabilitySlots } = useTeacherFetch()
const availabilityStore = useAvailabilityStore ()
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

onBeforeMount(async () => {
  await getAvailabilitySlots(parseInt(route.params.c_id as string));
})

watch(route, async (oldRoute, newRoute) => {
  if (oldRoute.params.c_id === newRoute.params.c_id) return
  await getAvailabilitySlots(parseInt(route.params.c_id as string));
})
</script>

<template>
  <div class="date-time-pick-container">
    <Calendar @select-date="selectDate" :availabilitySlots="availabilityStore.availabilitySlots" />
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
