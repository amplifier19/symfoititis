<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue';

const props = defineProps<{
  slotKey: number
  startTime: number
  updateEvent: 'update-updatable-slot' | 'update-insertable-slot'
  removeEvent: 'cancel-updatable-slot' | 'remove-insertable-slot'
}>()

const emit = defineEmits<{
  (e: 'update-updatable-slot' | 'update-insertable-slot', slotKey: number, startTime: number):void
  (e: 'cancel-updatable-slot' | 'remove-insertable-slot'):void
}>()

const startTime = ref<number>(props.startTime);
const times = [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22];

watch(props, (newProps, oldProps) => { 
  startTime.value = props.startTime;
});

watch(startTime, (newTime, oldTime) => {
  emit(props.updateEvent, props.slotKey, startTime.value);
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
    <span class="trash-icon" @click="emit(props.removeEvent)"><i class="fa fa-trash"></i></span>
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

.trash-icon {
  position: absolute;
  right: 2.5rem;
  cursor: pointer;
}

.fa-trash {
  font-size: 1rem;
}
@media screen and (max-width: 590px) {
  .trash-icon {
    right: 1.5rem;
  }
}

</style>
