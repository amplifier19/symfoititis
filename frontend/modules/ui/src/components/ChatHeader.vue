<script setup lang="ts">
import { Booking } from "@symfoititis-frontend-monorepo/interfaces";
import { useDate } from "@symfoititis-frontend-monorepo/composables";

const props = defineProps<{
  booking: Booking;
  isTeacher: boolean;
}>();

const { getDateDiffString } = useDate();

const { getWeekDay, formatDate } = useDate();
</script>

<template>
  <section class="chat-header-wrapper wrapper">
    <header class="chat-header">
      <div class="booking-info-container outline">
        <div class="date sm-font rg-fw">{{ formatDate(booking.date, "dd/MM") }}</div>
        <div class="name-day-time-container">
          <span v-if="props.isTeacher" class="name">{{
            booking.student_name
          }}</span>
          <span v-else class="name md-font rg-fw">
            {{ booking.teacher_firstname }} {{ booking.teacher_lastname }}
          </span>
          <span class="day-time sm-font rg-fw">
            {{ getWeekDay(booking.date) }} - {{ booking.start_time }}:00
          </span>
        </div>
      </div>
      <div class="meet-btn-container outline">
        <img class="meet-img" src="/svg/meet_icon_light-grey.svg" alt="">
        <span class="meet-hours md-font">{{ getDateDiffString(booking.date, booking.start_time) }}</span>
      </div>
    </header>
  </section>
</template>

<style scoped>
.chat-header-wrapper {
  height: 80px;
  background-color: var(--white);
  margin-top: 54px;
}

.chat-header {
  height: 100%;
  display: flex;
  flex-direction: row;
  width: 800px;
}

.date {
  width: 50px;
  height: 50px;
  margin-left: 12px;
  background-image: url("/svg/date_icon_for-chat.svg"); 
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

.name {
  text-align: left;
}

.day-time {
  color: var(--orange);
}

.meet-btn-container {
  border-color: var(--light-gray);
  display: flex;
  width: 80px;
  height: 80px;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.meet-btn-container::after {
  background-color: var(--light-gray); 
}

.meet-img {
  width: 38px;
  height: 38px; 
}

.meet-hours {
  line-height: 1;
  color: var(--light-gray);
}

.booking-info-container {
  display: flex;
  flex-grow: 1;
  flex-direction: row;
  align-items: center;
  margin-right: 8px;
}

@media screen and (max-width: 1400px) {
  .chat-header {
    width: clamp(350px, 70vw, 1500px);
  }

  .chat-header-wrapper {
    margin-top: 40px;
  }
}
</style>
