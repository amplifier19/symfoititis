<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useAvailabilityStore } from '@symfoititis-frontend-monorepo/stores'
import { useAuthStore } from '@symfoititis-frontend-monorepo/stores'
import { AvailabilitySlot } from '@symfoititis-frontend-monorepo/interfaces'
import TimeSlot from './TimeSlot.vue'

const props = defineProps<{
  selectedDay: Day
}>()
const saveChanges = ref<boolean>(false)
const route = useRoute()
const availabilityStore = useAvailabilityStore()
const authStore = useAuthStore()
const selectedDay = ref<Day>(props.selectedDay)
const selectedDate = ref<Day>(props.selectedDay.date)
const showPreferences = ref<boolean>(false)
const availabilityPreferences = ref<AvailabilitySlot[]>([])
const filteredAvailabilitySlots = computed(() => {
  return availabilityStore.availabilitySlots.filter((s) => s.date === selectedDate.value)
})
const updatableAvailabilitySlots = computed(() => {
  return availabilityStore.availabilitySlots.filter((s) => s.date === selectedDate.value)
})
const insertableAvailabilitySlots = ref<AvailabilitySlot[]>([])
const weekDays = ['Κυριακή', 'Δευτέρα', 'Τρίτη', 'Τετάρτη', 'Πέμπτη', 'Παρασκευή', 'Σάββατο']

const updateUpdatableSlot = (slotKey: number, startTime: number) => {
  const idx = updatableAvailabilitySlots.value.findIndex((slot) => slot.av_id === slotKey)
  if (idx < 0) return
  updatableAvailabilitySlots.value[idx].start_time = startTime
  saveChanges.value = true
}
const updateInsertableSlot = (slotKey: number, startTime: number) => {
  if (slotKey < 0) return
  insertableAvailabilitySlots.value[slotKey].start_time = startTime
  saveChanges.value = true
}
const deleteInsertableSlot = (slotKey: number) => {
  if (slotKey < 0) return
  insertableAvailabilitySlots.value.splice(slotKey, 1)
}
const addInsertableSlot = () => {
  authStore.getProfile()
  insertableAvailabilitySlots.value.push({
    t_id: authStore.profile.id,
    c_id: route.params.c_id,
    date: selectedDate.value,
    week_day: selectedDay.value.weekDay,
    start_time: -1,
  })
}
const getAvailabilityPreferences = (day: number) => {
  if (updatableAvailabilitySlots.value.length == 0) {
    const slot: AvailabilitySlot = availabilityStore.availabilitySlots.findLast((el) => day == el.week_day)
    if (!!slot) {
      showPreferences.value = true
      availabilityPreferences.value = availabilityStore.availabilitySlots.filter((el) => day == el.week_day && slot.date == el.date)
      return
    }
  }
  showPreferences.value = false
  availabilityPreferences.value = []
  return
}
const declinePreferences = () => {
  showPreferences.value = false
}
const applyPreferences = () => {
  insertableAvailabilitySlots.value = availabilityPreferences.value
  availabilityPreferences.value = []
  showPreferences.value = false
}
watch(props, (newDate, oldDate) => {
  selectedDay.value = props.selectedDay
  selectedDate.value = props.selectedDay.date
  insertableAvailabilitySlots.value = []
  saveChanges.value = false
  getAvailabilityPreferences(selectedDay.value.weekDay)
})
</script>

<template>
  <div v-if="!selectedDay.date" class="time-picker time-picker-prompt">
    <span>Επελεξε μια ημερομηνια </span>
  </div>
  <div v-else-if="showPreferences" class="time-picker preferences-container">
    <span class="regular-text date-prompt pf-v5-c-title pf-m-lg">
      {{ weekDays[props.selectedDay.weekDay] }}, {{ props.selectedDay.date }}
    </span>
    <section class="preferences-prompt-container">
    <span class="regular-text">Αυτή την ημέρα, συνήθως προτιμάς τις παρακάτω ώρες.</span>
    <ul class="preference-slot-list">
      <li v-for="(el, idx) in availabilityPreferences" :key="idx"> {{ el.start_time }}:00 - {{ el.start_time + 1 }}:00
      </li>
    </ul>
    <span class="regular-text">Θα ήθελες να ξεκινήσεις τον προγραμματισμό της ημέρας με εκείνες τις ώρες;</span>
    </section>
    <section class="preference-btn-container">
      <button @click="declinePreferences" class="pf-v5-c-button pf-m-link preference-btn" id="preference-no-btn"
        type="button">
        Όχι
        <span class="pf-v5-c-button__icon pf-m-end">
          <i class="fa fa-times" aria-hidden="true"></i>
        </span>
      </button>
      <button @click="applyPreferences" class="pf-v5-c-button pf-m-link preference-btn" id="preference-yes-btn"
        type="button">
        Ναι
        <span class="pf-v5-c-button__icon pf-m-end">
          <i class="fa fa-check" aria-hidden="true"></i>
        </span>
      </button>
    </section>

  </div>
  <ul v-else ref="timeSlotList" class="time-picker">
    <li class="regular-text date-prompt pf-v5-c-title pf-m-lg">
      {{ weekDays[props.selectedDay.weekDay] }}, {{ props.selectedDay.date }}
    </li>
    <li v-for="slot in updatableAvailabilitySlots" class="time-slot">
      <TimeSlot @update-updatable-slot="updateUpdatableSlot" :slotKey="slot.av_id" :startTime="slot.start_time"
        event="update-updatable-slot" />
    </li>
    <li v-for="(slot, idx) in insertableAvailabilitySlots" class="time-slot">
      <TimeSlot @update-insertable-slot="updateInsertableSlot" :slotKey="idx" :startTime="slot.start_time"
        event="update-insertable-slot">
        <span class="trash-icon" @click="deleteInsertableSlot(idx)"><i class="fa fa-trash"></i></span>
      </TimeSlot>
    </li>
    <li>
      <button @click="addInsertableSlot" class="pf-v5-c-button pf-m-link add-time-slot-btn" type="button">
        Προσθήκη χρονοθυρίδας
        <span class="pf-v5-c-button__icon pf-m-end">
          <i class="fa fa-plus-circle" aria-hidden="true"></i>
        </span>
      </button>
    </li>

    <section class="btn-container">
      <div v-if="saveChanges" @click="" class="regular-text booking-btn" id="book-btn">
        <span>ΑΠΟΘΗΚΕΥΣΗ</span>
      </div>
      <div v-else class="regular-text booking-btn" id="choose-time-btn">
        <span>ΚΑΝΕ ΑΛΛΑΓΕΣ</span>
      </div>
    </section>
  </ul>
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
  width: 450px;
  min-height: 353px;
  height: max-content;
}

.pf-v5-c-title {
  font-family: Geologica-Regular;
}

.time-slot {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.preferences-prompt-container {
  margin-top: 24px;
}
.preference-slot-list {
  margin: 24px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.preference-slot-list>li {
  color: black;
}

.preference-btn-container {
  display: flex;
  justify-content: space-evenly;
  margin-top: 24px;
}

.preference-btn {
  max-width: fit-content;
}

#preference-no-btn {
  color: black;
}

#preference-yes-btn {
  color: var(--orange);
}

.date-prompt {
  margin-top: 0.6rem;
  margin-bottom: 1rem;
}

.time-slot:last-of-type {
  margin-bottom: 2.5rem;
}

.trash-icon {
  position: absolute;
  right: 2.5rem;
  cursor: pointer;
}

.fa-trash {
  font-size: 1rem;
}

.btn-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: auto;
}

.pf-v5-c-button {
  width: 100%;
}

.add-time-slot-btn {
  margin: 1rem 0 2rem 0;
  color: var(--orange);
}

.booking-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  background-repeat: no-repeat;
  width: 220px;
  height: 60px;
  color: var(--orange);
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
  .date-prompt {
    margin-bottom: 1.5rem;
  }
}

@media screen and (max-width: 590px) {
  .time-picker {
    min-height: 250px;
  }

  .trash-icon {
    right: 1.5rem;
  }
}
</style>
