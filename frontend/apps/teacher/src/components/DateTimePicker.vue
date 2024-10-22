<script setup lang="ts">
import { ref, onBeforeMount, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useAvailabilityStore, useAuthStore } from '@symfoititis-frontend-monorepo/stores';
import type { Day } from '@symfoititis-frontend-monorepo/interfaces';
import { Calendar } from '@symfoititis-frontend-monorepo/ui';
import TimePicker from './TimePicker.vue';

const route = useRoute();
const availabilityStore = useAvailabilityStore();
const authStore = useAuthStore();

const selectedDay = ref<Day>({
  monthDay: -1,
  weekDay: -1,
  date: "",
  av_id: -100,
  cell_classes: [],
  btn_classes: []
});

const selectDate = (day) => {
  selectedDay.value = day;
};

onBeforeMount(() => {
  availabilityStore.getAvailability(3, parseInt(route.params.c_id));
});

watch(route, () => {
  availabilityStore.getAvailability(3, parseInt(route.params.c_id));
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

