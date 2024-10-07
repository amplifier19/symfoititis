<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { useAvailabilityStore } from '@symfoititis-frontend-monorepo/stores'
import { type AvailabilitySlots } from '@symfoititis-frontend-monorepo/intefaces'
import { type Day } from '@symfoititis-frontend-monorepo/intefaces'

const props = defineProps<{
  selectedDay: Day
}>()
const availabilityStore = useAvailabilityStore()
const selectedDay = ref<Day>(props.selectedDay)
const selectedDate = ref<Day>(props.selectedDay.date)
const filteredAvailabilitySlots = computed(() => {
  return availabilityStore.availabilitySlots.filter((s) => s.date === selectedDate.value)
})
const selectedAvailabilitySlots = ref<AvailabilitySlots[]>([])
const timeSlotList = ref<HTMLElement>()
const weekDays = ['Κυριακή', 'Δευτέρα', 'Τρίτη', 'Τετάρτη', 'Πέμπτη', 'Παρασκευή', 'Σάββατο']

const addTimeSlot = (event: Event, slot: AvailabilitySlot) => {
  event.target.classList.toggle('active-btn')
  const idx = selectedAvailabilitySlots.value.findIndex((el) => slot.av_id === el.av_id)
  if (idx >= 0) {
    selectedAvailabilitySlots.value.splice(idx, 1)
    return
  }
  selectedAvailabilitySlots.value.push(slot)
}
const clearStyles = () => {
  if (!!timeSlotList.value) {
    timeSlotList.value.childNodes.forEach((li) => {
      li.childNodes.forEach((span) => {
        if (!!span.classList) {
          span.classList.remove('active-btn')
        }
      })
    })
  }
}
const clearSelectedTimeSlots = () => {
  selectedAvailabilitySlots.value = []
}
watch(props, (newDate, oldDate) => {
  selectedDate.value = props.selectedDay.date
  clearStyles()
  clearSelectedTimeSlots()
})
</script>

<template>
  <ul ref="timeSlotList" v-if="filteredAvailabilitySlots.length > 0" class="time-picker">
    <li class="regular-text date-prompt">
      {{ weekDays[props.selectedDay.weekDay] }}, {{ props.selectedDay.date }}
    </li>
    <li v-for="(slot, idx) in filteredAvailabilitySlots" @click="addTimeSlot($event, slot)">
      <span class="pf-v5-c-button pf-m-tertiary time-slot" type="button">
        {{ slot.start_time }}:00 - {{ slot.start_time + 1 }}:00
      </span>
    </li>
    <div v-if="selectedAvailabilitySlots.length > 0" @click="console.log(selectedAvailabilitySlots)" class="regular-text booking-btn"
      id="book-btn">
      <span>ΚΡΑΤΗΣΗ</span>
    </div>
    <div v-else class="regular-text booking-btn" id="choose-time-btn">
      <span>ΕΠΕΛΕΞΕ ΩΡΑ</span>
    </div>
  </ul>
  <div v-else-if="!selectedDay.date" class="time-picker time-picker-prompt">
    <span>Επελεξε μια διαθεσιμη ημερομηνια </span> <i class="fa fa-circle"></i>
  </div>
  <div v-else class="time-picker time-picker-prompt">
    <span>Απ' οτι φαινεται ο συμφοιτητης ειναι αρκετα busy. Επελεξε αλλη ημερομηνια</span>
  </div>
</template>

<style scoped>
.fa-circle {
  font-size: 1.2rem;
  margin-left: 10px;
  margin-top: 10px;
}

.time-picker {
  margin: 24px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 450px;
  min-height: 353px;
  height: max-content;
}

.time-picker>li {
  width: 100%;
}

.date-prompt {
  display: flex;
  margin-top: 0.6rem;
  margin-bottom: 1.9rem;
}

.time-picker>li:last-of-type {
  margin-bottom: 2.5rem;
}

.time-slot {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 60px;
  margin-bottom: 0.25rem;
}

.time-slot,
.time-slot:hover,
.time-slot::after {
  border-color: var(--orange);
  color: var(--black);
}

.pf-v5-c-button {
  width: 100%;
}

.booking-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  background-repeat: no-repeat;
  width: 220px;
  height: 60px;
  color: var(--orange);
  margin-top: auto;
}

#book-btn {
  background-image: url('/svg/booking-cta-btn.svg');
  cursor: pointer;
}

#book-btn>span {
  margin-bottom: 12px;
  font-family: 'Geologica-Medium';
}

#choose-time-btn {
  background-image: url('/svg/booking-dashed-cta-btn.svg');
}

.active-btn,
.active-btn:hover {
  background-color: var(--orange);
  color: white;
}

.time-picker-prompt {
  margin: 24px;
  color: var(--orange);
  border: 2.5px dashed var(--orange);
  width: 450px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  text-transform: uppercase;
  padding: 2rem;
}

@media screen and (max-width: 1300px) {
  .time-slot {
    font-size: 0.9rem;
    padding: 0;
  }

  .date-prompt {
    margin-bottom: 1.5rem;
  }
}

@media screen and (max-width: 590px) {
  .time-picker {
    min-height: 250px;
  }
}
</style>
