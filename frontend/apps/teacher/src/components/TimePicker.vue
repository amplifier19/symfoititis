<script setup lang="ts">
import { useRoute } from 'vue-router'
import { ref, computed, watch } from 'vue'

import TimeSlot from './TimeSlot.vue'

import { Day } from '@symfoititis-frontend-monorepo/interfaces'

import { useAvailabilityStore } from '../stores/availability'

import { AvailabilityDataService } from '../core/services/availability/availability-data.service'

const props = defineProps<{
  selectedDay: Day
}>()

const route = useRoute()

const availabilityStore = useAvailabilityStore()

const availabilityDataService = AvailabilityDataService.getAvailabilityDataFactory()

const cid = ref<number>(parseInt(route.params.c_id as string))
const weekDays = ['Κυριακή', 'Δευτέρα', 'Τρίτη', 'Τετάρτη', 'Πέμπτη', 'Παρασκευή', 'Σάββατο']

watch(props, (newDate, oldDate) => {
  availabilityStore.handleDateChange(props.selectedDay)
  availabilityStore.getAvailabilityPreferences(props.selectedDay.weekDay)
})

watch(route, async (oldRoute, newRoute) => {
  cid.value = parseInt(route.params.c_id as string)
})
</script>

<template>
  <div v-if="!props.selectedDay.date" class="time-picker time-picker-prompt">
    <span>Επελεξε μια ημερομηνια </span>
  </div>

  <div v-else-if="availabilityStore.availabilityPreferences.length > 0" class="time-picker preferences-container">
    <span class="date-prompt pf-v5-c-title pf-m-lg">
      {{ weekDays[props.selectedDay.weekDay] }}, {{ props.selectedDay.date }}
    </span>
    <section class="preferences-prompt-container">
      <span class="">Αυτή την ημέρα, συνήθως προτιμάς τις παρακάτω ώρες.</span>
      <ul class="preference-slot-list">
        <li v-for="(el, idx) in availabilityStore.availabilityPreferences" :key="idx"> {{ el.start_time }}:00 - {{
          el.start_time + 1 }}:00
        </li>
      </ul>
      <span class="">Θα ήθελες να ξεκινήσεις τον προγραμματισμό της ημέρας με εκείνες τις ώρες;</span>
    </section>
    <section class="preference-btn-container">
      <button @click="availabilityStore.declinePreferences" class="pf-v5-c-button pf-m-link preference-btn"
        id="preference-no-btn" type="button"> Όχι
        <span class="pf-v5-c-button__icon pf-m-end">
          <i class="fa fa-times" aria-hidden="true"></i>
        </span>
      </button>
      <button @click="availabilityStore.applyPreferences" class="pf-v5-c-button pf-m-link preference-btn"
        id="preference-yes-btn" type="button">
        Ναι
        <span class="pf-v5-c-button__icon pf-m-end">
          <i class="fa fa-check" aria-hidden="true"></i>
        </span>
      </button>
    </section>
  </div>

  <ul v-else ref="timeSlotList" class="time-picker">
    <li class="date-prompt pf-v5-c-title pf-m-lg">
      {{ weekDays[props.selectedDay.weekDay] }}, {{ props.selectedDay.date }}
    </li>
    <li v-for="(slot, idx) in availabilityStore.filteredByDateAvailabilitySlots" class="time-slot">
      <TimeSlot @update-updatable-slot="availabilityStore.updateUpdatableSlot"
        @cancel-updatable-slot="availabilityStore.cancelUpdatableSlot(slot.av_id!)" :slotKey="slot.av_id!"
        :startTime="slot.start_time" :state="slot.state" updateEvent="update-updatable-slot" removeEvent="cancel-updatable-slot" />
    </li>
    <li v-for="(slot, idx) in availabilityStore.insertableAvailabilitySlots" class="time-slot">
      <TimeSlot @update-insertable-slot="availabilityStore.updateInsertableSlot"
        @remove-insertable-slot="availabilityStore.removeInsertableSlot(idx)" :slotKey="idx"
        :startTime="slot.start_time" updateEvent="update-insertable-slot" removeEvent="remove-insertable-slot" />
    </li>
    <li>
      <button @click="availabilityStore.addInsertableSlot(cid)"
        class="pf-v5-c-button pf-m-link add-time-slot-btn" type="button">
        Προσθήκη χρονοθυρίδας
        <span class="pf-v5-c-button__icon pf-m-end">
          <i class="fa fa-plus-circle" aria-hidden="true"></i>
        </span>
      </button>
    </li>

    <section class="btn-container">
      <div v-if="
        availabilityStore.filteredInsertableAvailabilitySlots.length > 0 ||
        availabilityStore.updatableAvailabilitySlots.length > 0 ||
        availabilityStore.cancelableAvailabilitySlotIds.length > 0
      "
       @click="availabilityDataService.saveAvailabilityChanges(cid)"
        class="booking-btn" id="book-btn">
        <span>ΑΠΟΘΗΚΕΥΣΗ</span>
      </div>
      <div v-else class="booking-btn" id="choose-time-btn">
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
  margin: 16px 24px;
  color: var(--orange);
  border: 2.5px dashed var(--orange);
  width: 450px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  text-transform: uppercase;
  padding: 32px;
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
}
</style>
