<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue';

const props = defineProps<{
  slotKey: number 
  startTime: number
  event: string
}>()

const emit = defineEmits<{
  (e: 'update-updatable-slot', slotKey: number, startTime: number)
  (e: 'update-insertable-slot', slotKey: number, startTime: number)
}>()

const startTime = ref(props.startTime);
const times = [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22];

watch(props, (newProps) => {
  startTime.value = props.startTime;
});

watch(startTime, (newTime, oldTime) => {
  if (oldTime === newTime || startTime.value < 0 || startTime.value === props.startTime) return;
  emit(props.event, props.slotKey, startTime.value);
});
</script>

<template>
  <div class="time-picker-container">
    <select v-model="startTime" class="time-select" name="start-time" id="start-time">
      <option disabled value="-1">--:-- - --:--</option>
      <option v-for="time in times" :key="time" :value="time" :selected="time === startTime">
        {{ time }}:00 - {{ time + 1 }}:00
      </option>
    </select>
    <slot></slot>
  </div>
  <div class="border"></div>
</template>

<style scoped>
.time-picker-container {
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
  margin: 1rem 0;
}

.label-container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
}

.time-select {
  background-color: white;
  text-align: center;
  width: 50%;
}

.trash-icon {
  margin-left: 1rem;
  font-size: 1.3rem;
}

.border {
  width: 100%;
  border-top: var(--main-border);
}
</style>
