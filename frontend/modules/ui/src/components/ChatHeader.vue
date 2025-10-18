<script setup lang="ts">
import { Booking } from '@symfoititis-frontend-monorepo/interfaces';
import { useDate } from '@symfoititis-frontend-monorepo/composables';

const props = defineProps<{
  booking: Booking
  isTeacher: boolean
}>()

const { getWeekDay, formatDate } = useDate()


</script>

<template>
  <section class="chat-header-wrapper wrapper">
    <header class="chat-header">
      <div class="booking-info-container">
        <div class="date">{{ formatDate(booking.date, 'dd/MM') }}</div>
        <div class="name-day-time-container">
          <span v-if="props.isTeacher" class="name">{{ booking.student_name }}</span>
          <span v-else class="name">
            {{ booking.teacher_firstname }} {{ booking.teacher_lastname }}
          </span>
          <span class="day-time">
            {{ getWeekDay(booking.date) }} - {{ booking.start_time }}:00
          </span>
        </div>
      </div>
      <div class="meet-btn-container">
        <button class="meet-btn">Meet</button>
      </div>
    </header>
  </section>
</template>

<style scoped>
.chat-header-wrapper {
  background-color: var(--white);
  margin-top: 54px;
}

.chat-header {
  display: flex;
  flex-direction: row;
  width: 800px;
}

.date {
  width: 50px;
  height: 50px;
  background-color: var(--orange);
  border-radius: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  margin-right: 1rem;
}

.name-day-time-container {
  display: flex;
  flex-direction: column;
}

.day-time {
  color: var(--orange);
}

.meet-btn-container {
  display: flex;
  flex-grow: 1;
  justify-content: end;
}

@media screen and (max-width: 1300px) {
  .chat-header {
    width: clamp(350px, 70vw, 1500px);
  }
}

.booking-info-container {
  display: flex;
  flex-direction: row;
}
</style>
