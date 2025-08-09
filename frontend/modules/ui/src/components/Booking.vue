<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { ref, computed } from 'vue'

import { Booking, Course, ChatStats } from '@symfoititis-frontend-monorepo/interfaces'

import { useChatStore, useCourseStore } from '@symfoititis-frontend-monorepo/stores'

import Card from './Card.vue'

const props = defineProps<{
    booking: Booking
    card: boolean
}>()

const courseStore = useCourseStore()
const chatStore = useChatStore()

const { courses } = storeToRefs(courseStore)
const { chatStats } = storeToRefs(chatStore)

const course = computed(() => {
    return courses.value.find((c: Course) => c.c_id === props.booking.c_id) ||
        { c_id: -1, dep_id: -1, c_display_name: '', semester: -1 }
})

const cardFooter = ref<HTMLElement>()
const alphabet = 'ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ'
const isStudent = import.meta.env.VITE_KC_REALM === 'student' 

const getRemainingDays = () => {
    const MINUTE_MILLISEC = 1000 * 60
    const HOUR_MILLISEC = 1000 * 60 * 60
    const DAY_MILLISEC = 1000 * 60 * 60 * 24
    const formattedHour = props.booking.start_time < 10 ? `0${props.booking.start_time}` : `${props.booking.start_time}`
    const bookingDate = new Date(`${props.booking.date}T${formattedHour}:00:00`)
    const currentDate = new Date()
    const difference = bookingDate.getTime() - currentDate.getTime()
    const days = Math.floor(Math.abs(difference) / DAY_MILLISEC)
    const hours = Math.floor((Math.abs(difference) - (days * DAY_MILLISEC)) / HOUR_MILLISEC)
    const minutes = Math.floor((Math.abs(difference) - (days * DAY_MILLISEC) - (hours * HOUR_MILLISEC)) / MINUTE_MILLISEC)
    if (days > 0) {
        cardFooter.value?.classList.add('not-immediate-booking-footer-title')
        return difference < 0 ? `Πριν ${days} μέρες` : `Σε ${days} μέρες`
    } else if (hours > 0) {
        cardFooter.value?.classList.add('not-so-immediate-booking-footer-title')
        return difference < 0 ? `Πριν ${hours} ώρες` : `Σε ${hours} ώρες`
    } else if (minutes > 0) {
        cardFooter.value?.classList.add('immediate-booking-footer-title')
        return difference < 0 ? `Πριν ${minutes} λεπτά` : `Σε ${minutes} λεπτά`
    }
    cardFooter.value?.classList.add('immediate-booking-footer-title')
    return 'Τώρα'
}
</script>

<template>
    <Card v-if="props.card" :course="course" link="booking" :bookingId="booking.b_id">
        <template v-if="booking.state == 'CANCELED'" v-slot:card-header>
            <div class="canceled-badge">
                <h2 class="canceled-text card-badge-text">Ακυρώθηκε</h2>
            </div>
        </template>
        <template v-else-if="chatStats.some((s) => s.room == props.booking.room && s.myUnreadCount > 0)" v-slot:card-header>
            <div class="unread-message-badge">
                <p class="unread-message-text card-top-text" > {{ chatStats.find((s) => s.room == props.booking.room)?.myUnreadCount }}</p>
            </div>
        </template>
        <template v-slot:card-footer>
            <h2 ref="cardFooter" class="card-footer-text">{{ getRemainingDays() }}</h2>
        </template>
    </Card>

    <section v-else class="booking-row">
        <div id="course-details" class="details">
            <span class="course-title booking-field">{{ course.c_display_name }}</span>
            <span class="semester booking-field">{{ alphabet[course.semester - 1] }}' Εξάμηνο</span>
        </div>
        <div id="booking-details" class="details">
            <span class="time booking-field">{{ booking.start_time }}:00 - {{ booking.start_time + 1 }}:00</span>
            <span v-if="isStudent" class="name booking-field">
                {{ booking.teacher_firstname }} {{ booking.teacher_lastname }}
            </span>
            <span v-else class="name booking-field">{{ booking.student_name }}</span>
        </div>
    </section>
</template>

<style>
.unread-message-text{
    color: white;
    padding: 0 .5rem;
    padding-bottom: .15rem;
}

.card-top-text {
    font-size: 1rem;
    text-align: center;
}

.card-footer-text {
    text-align: center;
    max-height: fit-content;
}

.booking-field {
  color: var(--white);
  font-family: 'Geologica-Medium';
}

.unread-message-badge {
    position: absolute;
    top: -.5rem;
    left: -.5rem;
    background-color: var(--orange);
    border-radius: 14px;
}

.card-badge-text {
    text-align: center;
    font-family: 'Geologica-SemiBold';
}

.card-footer-text {
    position: absolute;
    bottom: 0;
    font-family: 'Geologica-SemiBold';
}

.not-immediate-booking-footer-title {
    font-family: 'Geologica-Regular';
}

.not-so-immediate-booking-footer-title {
    font-family: 'Geologica-Semibold';
}

.immediate-booking-footer-title {
    font-family: 'Geologica-Bold';
}

.booking-row {
    width: 100%;
    display: flex;
    flex-direction: column;
    margin-bottom: 2rem;
}

.details {
    width: 100%;
    padding: 0.3rem 2rem;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

#course-details {
    border: 1.5px solid var(--orange);
    border-radius: 5px;
    color: var(--orange);
    margin-bottom: 0.5rem;
}

#booking-details {
    color: var(--black);
}

.course-title {
    font-family: 'Geologica-Medium';
}

.semester {
    font-family: 'Geologica-Light';
}

.name {
    text-transform: uppercase;
}

@media screen and (max-width: 1300px) {
    .card-top-text{
        color: white;
        padding: 0 .4rem;
        padding-top: .1rem;
        font-size: .8rem;
    }
}
</style>