<script setup lang="ts">
import { ref, watch, onBeforeMount } from 'vue';
import { useRoute } from 'vue-router';
import TimePicker from './TimePicker.vue';
import { Calendar } from '@symfoititis-frontend-monorepo/ui';
import  { type Teacher, type Day } from "@symfoititis-frontend-monorepo/interfaces"
import { useAvailabilityStore } from '../stores/availability'
import { useStudentFetch } from '../composables/fetch'

const props = defineProps<{
  teacher: Teacher
}>()
const route = useRoute();
const selectedDay = ref<Day>({
  monthDay: -1,
  weekDay: -1,
  date: '',
  av_id: -100,
  cell_classes: [],
  btn_classes: []
});
const { getAvailabilitySlots } = useStudentFetch()
const availabilityStore = useAvailabilityStore()

const selectDate = (day: Day) => {
  selectedDay.value = day;
};

onBeforeMount(async () => {
  await getAvailabilitySlots(parseInt(route.params.c_id as string), props.teacher.t_id);
});

watch(route, async (newRoute, oldRoute) => {
  await getAvailabilitySlots(parseInt(route.params.c_id as string), props.teacher.t_id);
});

watch(props, async (newProps, oldProps) => {
  await getAvailabilitySlots(parseInt(route.params.c_id as string), props.teacher.t_id);
});
</script>

<template>
  <div class="date-time-pick-container">
    <Calendar @select-date="selectDate" :availabilitySlots="availabilityStore.availabilitySlots"/>
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
