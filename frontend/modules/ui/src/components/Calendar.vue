<script setup lang="ts">
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';

import { type Day, type AvailabilitySlot, type Booking } from '@symfoititis-frontend-monorepo/interfaces';

const route = useRoute();

const props = defineProps<{
  availabilitySlots?: AvailabilitySlot[]
  booking?: boolean
  bookings?: Booking[]
}>()

const emit = defineEmits<{
  (e: 'select-date', day: Day): void
  (e: 'month-change'): void
}>()

const date = new Date()
const weeks = ref<Day[][]>([[]]);
const month = ref<number>(date.getMonth());
const year = ref<number>(date.getFullYear());
const monthsDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
const months = [
  "Ιανουάριος", "Φεβρουάριος", "Μάρτιος", "Απρίλιος", "Μάιος", "Ιούνιος",
  "Ιούλιος", "Αύγουστος", "Σεπτέμβριος", "Οκτώβριος", "Νοέμβριος", "Δεκέμβριος"
]

const isLeap = () => {
  monthsDays[1] = (year.value % 4 === 0 && (year.value % 100 !== 0 || year.value % 400 === 0)) ? 29 : 28
}
isLeap()

const isAvailable = (dateString: string) => {
  if (!!props.booking) {
    return props.bookings!.some(slot => slot.date === dateString)
  }
  return props.availabilitySlots!.some(slot => slot.date === dateString)
}

const formatDate = (day: number) => {
  const mm = month.value + 1 > 9 ? month.value + 1 : `0${month.value + 1}`
  const dd = day > 9 ? day : `0${day}`
  return `${year.value}-${mm}-${dd}`
}

const initWeeks = () => {
  weeks.value = [[]]
  const first = new Date(year.value, month.value, 1)
  const now = new Date()
  let week = 0

  for (let i = first.getDay() - 1, j = 1; i >= 0; i--, j++) {
    const prev = month.value - 1 >= 0 ? month.value - 1 : 11
    weeks.value[week].push({
      monthDay: monthsDays[prev] - i,
      weekDay: weeks.value[week].length,
      date: formatDate(monthsDays[prev] - i),
      av_id: -100,
      cell_classes: ["pf-v5-c-calendar-month__dates-cell", "pf-m-adjacent-month"],
      btn_classes: ["pf-v5-c-calendar-month__date", "notallowed-btn"]
    })
  }

  for (let i = 1, j = first.getDay(); i <= monthsDays[month.value]; i++, j++) {
    if (j % 7 === 0) {
      week++
      weeks.value.push([])
    }
    const formatedDate = formatDate(i)
    weeks.value[week].push({
      monthDay: i,
      weekDay: weeks.value[week].length,
      date: formatedDate,
      cell_classes: ["pf-v5-c-calendar-month__dates-cell"],
      btn_classes: ["pf-v5-c-calendar-month__date"]
    })

    const isToday = now.getDate() === i && now.getMonth() === month.value && now.getFullYear() === year.value;
    if (isToday) {
      weeks.value[week][weeks.value[week].length - 1].cell_classes.push("pf-m-current");
    }

    const isPast = year.value < now.getFullYear() ||
      (year.value === now.getFullYear() && month.value < now.getMonth()) ||
      (year.value === now.getFullYear() && month.value === now.getMonth() && i < now.getDate());

    if (isPast) {
      weeks.value[week][weeks.value[week].length - 1].cell_classes.push("pf-m-adjacent-month");
      weeks.value[week][weeks.value[week].length - 1].btn_classes.push("notallowed-btn");
    }

    if (!isPast && isAvailable(formatedDate)) {
      weeks.value[week][weeks.value[week].length - 1].cell_classes.push("pf-m-selected");
    }
  }
}
initWeeks()

const nextMonth = () => {
  month.value = (month.value + 1) % 12
  if (month.value === 0) year.value++
  initWeeks()
}

const prevMonth = () => {
  month.value--
  if (month.value === -1) {
    month.value = 11
    year.value--
  }
  initWeeks()
}

const selectDate = (day: any) => {
  emit("select-date", day)
}

watch(year, (newYear, oldYear) => isLeap())
watch(month, (newMonth, oldMont) => {
  initWeeks()
  emit("select-date", {
    monthDay: -1,
    weekDay: -1,
    date: '',
    av_id: -100,
    cell_classes: [],
    btn_classes: []
  })
})
watch(route, (newRoute, oldRoute) => initWeeks())
watch(props, (newProps, oldProps) => initWeeks())
</script>

<template>
  <div class="pf-v5-c-calendar-month">
    <div class="pf-v5-c-calendar-month__header">
      <div class="pf-v5-c-calendar-month__header-nav-control pf-m-prev-month">
        <button @click="prevMonth" class="pf-v5-c-button pf-m-plain" type="button" aria-label="Previous month">
          <i class="fa fa-angle-left" aria-hidden="true"></i>
        </button>
      </div>
      <span class="regular-text">{{ months[month] }} {{ year }}</span>
      <div class="pf-v5-c-calendar-month__header-nav-control pf-m-next-month">
        <button @click="nextMonth" class="pf-v5-c-button pf-m-plain" type="button" aria-label="Next month">
          <i class="fa fa-angle-right" aria-hidden="true"></i>
        </button>
      </div>
    </div>

    <table class="pf-v5-c-calendar-month__calendar">
      <thead class="pf-v5-c-calendar-month__days" scope="col">
        <tr class="pf-v5-c-calendar-month__days-row">
          <th class="pf-v5-c-calendar-month__day">
            <span class="pf-v5-screen-reader">Monday</span>
            <span class="regular-text" aria-hidden="true">Κ</span>
          </th>
          <th class="pf-v5-c-calendar-month__day">
            <span class="pf-v5-screen-reader">Tuesday</span>
            <span class="regular-text" aria-hidden="true">Δ</span>
          </th>
          <th class="pf-v5-c-calendar-month__day">
            <span class="pf-v5-screen-reader">Wednesday</span>
            <span class="regular-text" aria-hidden="true">Τ</span>
          </th>
          <th class="pf-v5-c-calendar-month__day">
            <span class="pf-v5-screen-reader">Thursday</span>
            <span class="regular-text" aria-hidden="true">Τ</span>
          </th>
          <th class="pf-v5-c-calendar-month__day">
            <span class="pf-v5-screen-reader">Friday</span>
            <span class="regular-text" aria-hidden="true">Π</span>
          </th>
          <th class="pf-v5-c-calendar-month__day">
            <span class="pf-v5-screen-reader">Saturday</span>
            <span class="regular-text" aria-hidden="true">Π</span>
          </th>
          <th class="pf-v5-c-calendar-month__day">
            <span class="pf-v5-screen-reader">Sunday</span>
            <span class="regular-text" aria-hidden="true">Σ</span>
          </th>
        </tr>
      </thead>

      <tbody class="pf-v5-c-calendar-month__dates">
        <tr v-for="(week, wi) in weeks" :key="wi" class="pf-v5-c-calendar-month__dates-row">
          <td v-for="(day, di) in week" :key="di" :class="day.cell_classes">
            <button @click="selectDate(day)" :class="day.btn_classes" type="button">
              {{ day.monthDay }}
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.pf-v5-c-calendar-month {
  background-color: var(--white);
  width: 470px;
}

.pf-v5-c-calendar-month__header {
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.fa-angle-left,
.fa-angle-right {
  font-size: 30px;
}

.pf-v5-c-calendar-month__date:focus {
  --pf-v5-c-calendar-month__date--focus--after--BorderColor: var(--orange-shadow);
  background-color: transparent;
}

.pf-m-selected>.pf-v5-c-calendar-month__date {
  background-color: var(--orange);
}

.pf-m-selected>.pf-v5-c-calendar-month__date:hover {
  background-color: var(--orange-shadow);
}

.pf-m-selected>.pf-v5-c-calendar-month__date:focus {
  box-shadow: 0 0 0.3125rem var(--orange-shadow);
  background-color: var(--orange-shadow);
}

.pf-m-adjacent-month,
.elapsed-date {
  cursor: not-allowed;
}
.notallowed-btn {
  pointer-events: none;
}
</style>
