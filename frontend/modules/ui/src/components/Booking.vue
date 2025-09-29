<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { ref, computed } from 'vue'

import { Booking, Course, ChatStats } from '@symfoititis-frontend-monorepo/interfaces'

import { useChatStore, useCourseStore } from '@symfoititis-frontend-monorepo/stores'

import Card from './Card.vue'
import { useDate } from '@symfoititis-frontend-monorepo/composables';

const props = defineProps<{
    booking: Booking
    card: boolean
}>()

const courseStore = useCourseStore()
const chatStore = useChatStore()
const { formatDate } = useDate()

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
        cardFooter.value?.classList.add('rg-fw')
        return difference < 0 ? formatDate(props.booking.date, "dd/MM") : `Σε ${days} μέρες`
    } else if (hours > 0) {
        cardFooter.value?.classList.add('sb-fw')
        return difference < 0 ? formatDate(props.booking.date, "dd/MM") : `Σε ${hours} ώρες`
    } else if (minutes > 0) {
        cardFooter.value?.classList.add('b-fw')
        return difference < 0 ? formatDate(props.booking.date, "dd/MM")  : `Σε ${minutes} λεπτά`
    }
    cardFooter.value?.classList.add('b-fw')
    return 'Τώρα'
}

const getUnreadMessages = () => {
    const unread = chatStats.value.find((s) => s.room == props.booking.room)?.myUnreadCount 
    if (!!unread) return unread
    return 0
}
</script>

<template>
    <Card class="test" v-if="props.card" :course="course" link="booking" :bookingId="booking.b_id" context="booking">
        <template v-if="booking.state == 'CANCELED'" v-slot:card-header>
            <div class="canceled-badge">
                <h2 class="canceled-text card-badge-text">Ακυρώθηκε</h2>
            </div>
        </template>
        <template v-else-if="chatStats.some((s) => s.room == props.booking.room && getUnreadMessages() > 0)" v-slot:card-header>
            <div class="unread-message-badge">
                <span class="sm-font unread-message-text card-top-text">{{ getUnreadMessages() > 9 ? "9+": getUnreadMessages() }}</span>
            </div>
        </template>
        <template v-slot:card-footer>
            <span ref="cardFooter" class="card-footer-text sm-font">{{ getRemainingDays() }}</span>
        </template>
    </Card>

    <section v-else class="booking-row">
        <div id="course-details" class="details">
            <span class="course-title booking-field lg-font md-fw">{{ course.c_display_name }}</span>
                <span class="semester booking-field lg-font lt-fw">{{ alphabet[course.semester - 1] }}' Εξάμηνο</span>
        </div>
        <div id="booking-details" class="details">
            <span class="time booking-field md-font sb-fw">{{ booking.start_time }}:00 - {{ booking.start_time + 1 }}:00</span>
            <span v-if="isStudent" class="name booking-field md-font lt-fw">
                {{ booking.teacher_firstname }} {{ booking.teacher_lastname }}
            </span>
            <span v-else class="name booking-field md-font lt-fw">{{ booking.student_name }}</span>
        </div>
    </section>
</template>

<style>
.unread-message-badge {
    width: 28px;
    height: 28px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.unread-message-text{
    color: white;
}

.card-top-text {
    text-align: center;
}

.card-footer-text {
    text-align: center;
    max-height: fit-content;
}

.unread-message-badge {
    position: absolute;
    top: -10px;
    left: -10px;
    background-color: var(--orange);
    border-radius: 14px;
}

.card-badge-text {
    text-align: center;
}

.card-footer-text {
    position: absolute;
    bottom: 0;
}

.booking-row {
    width: 100%;
    display: flex;
    flex-direction: column;
    margin-bottom: 2rem;
}

.semester {
    white-space: nowrap ;
}

.time {
    white-space: nowrap;
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
    color: var(--orange);
}

#booking-details {
    color: var(--black);
}

.name-cnt {
    display: flex;
    justify-content: flex-end;
}

.name {
    text-transform: uppercase;
    display: block;
    text-align: right;
}

@media screen and (max-width: 1000px) {
    .details {
        padding: 0.3rem 1rem;
    } 
    .unread-message-badge {
        width: 22px;
        height: 22px;
    }


}
</style>