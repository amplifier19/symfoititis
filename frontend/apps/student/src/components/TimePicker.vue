<script setup lang="ts">
import { useRoute } from 'vue-router'
import { ref, computed, watch } from 'vue'

import { Day } from '@symfoititis-frontend-monorepo/interfaces'

import { useBookingsDataService } from '@symfoititis-frontend-monorepo/core/services'
import { AvailabilityDataService } from '../core/services/availability/availability-data.service'

import { useAvailabilityStore } from '../stores/availability'

import RefreshButton from 'modules/ui/src/components/RefreshButton.vue'

const route = useRoute()

const props = defineProps<{
  selectedDay: Day
  teacherId: string
}>()

const { addBookings } = useBookingsDataService()
const availabilityDataService = AvailabilityDataService.getAvailabilityDataFactory()

const availabilityStore = useAvailabilityStore()

const filteredAvailabilitySlots = computed(() => {
  return availabilityStore.availabilitySlots.filter((s) => s.date === selectedDate.value)
})

const timeSlotList = ref<HTMLUListElement>()
const selectedDay = ref<Day>(props.selectedDay)
const selectedAvailabilitySlotIds = ref<number[]>([])
const selectedDate = ref<string>(props.selectedDay.date)
const c_id = ref<number>(parseInt(route.params.c_id as string))
const weekDays = ['Κυριακή', 'Δευτέρα', 'Τρίτη', 'Τετάρτη', 'Πέμπτη', 'Παρασκευή', 'Σάββατο']

const handleRefresh = async () => {
  await availabilityDataService.getAvailabilitySlots(c_id.value, props.teacherId, true)
}

const addTimeSlot = (event: MouseEvent, av_id: number) => {
  (event.target as HTMLElement).classList.toggle('active-btn')
  const idx = selectedAvailabilitySlotIds.value.findIndex((id) => av_id === id)
  if (idx >= 0) {
    selectedAvailabilitySlotIds.value.splice(idx, 1)
    return
  }
  selectedAvailabilitySlotIds.value.push(av_id)
}

const clearStyles = () => {
  if (!!timeSlotList.value) {
    timeSlotList.value.childNodes.forEach((li) => {
      li.childNodes.forEach((span: ChildNode) => {
        if (!!(span as HTMLElement).classList) {
          (span as HTMLElement).classList.remove('active-btn')
        }
      })
    })
  }
}

const bookSlots = async () => {
  await addBookings(selectedAvailabilitySlotIds.value)
  clearStyles()
  selectedAvailabilitySlotIds.value = []
  await availabilityDataService.getAvailabilitySlots(c_id.value, props.teacherId, true)
}

watch(props, (newDate, oldDate) => {
  selectedDate.value = props.selectedDay.date
  selectedAvailabilitySlotIds.value = []
  clearStyles()
})

watch(route, (newRoute, oldRoute) => {
  c_id.value = parseInt(route.params.c_id as string)
}) 
</script>

<template>
  <ul ref="timeSlotList" v-if="filteredAvailabilitySlots.length > 0" class="time-picker">
    <li class="regular-text date-prompt">
      <span>{{ weekDays[props.selectedDay.weekDay] }}, {{ props.selectedDay.date }}</span>
      <RefreshButton @refresh="handleRefresh" />
    </li>
    <li v-for="slot in filteredAvailabilitySlots" @click="addTimeSlot($event, slot.av_id!)">
      <span class="pf-v5-c-button pf-m-tertiary time-slot" type="button">
        {{ slot.start_time }}:00 - {{ slot.start_time + 1 }}:00
      </span>
    </li>
    <div v-if="selectedAvailabilitySlotIds.length > 0" @click="bookSlots" class="regular-text booking-btn"
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
  justify-content: space-between;
  align-items: center;
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
