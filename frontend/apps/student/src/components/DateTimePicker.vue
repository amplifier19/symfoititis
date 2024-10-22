<script setup lang="ts">
import { ref, watch, onBeforeMount } from 'vue';
import { useRoute } from 'vue-router';
import TimePicker from './TimePicker.vue';
import { Calendar } from '@symfoititis-frontend-monorepo/ui';
import { useAvailabilityStore } from '@symfoititis-frontend-monorepo/stores'; 

const props = defineProps({
  teacher: { type: Object, required: true }
});

const route = useRoute();
const availabilityStore = useAvailabilityStore();
const selectedDay = ref({
  monthDay: -1,
  weekDay: -1,
  date: '',
  av_id: -100,
  cell_classes: [],
  btn_classes: []
});

const selectDate = (day: any) => {
  selectedDay.value = day;
};

onBeforeMount(() => {
  availabilityStore.getAvailability(props.teacher.id, parseInt(route.params.c_id as string));
});

watch(route, () => {
  availabilityStore.getAvailability(props.teacher.id, parseInt(route.params.c_id as string));
});

watch(props, () => {
  availabilityStore.getAvailability(props.teacher.id, parseInt(route.params.c_id as string));
});
</script>

<template>
  <div class="date-time-pick-container">
    <Calendar @select-date="selectDate" />
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
