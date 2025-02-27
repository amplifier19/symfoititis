<script setup lang="ts">
import { storeToRefs } from 'pinia'

import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import { Course } from '@symfoititis-frontend-monorepo/interfaces'

import DateTimePicker from '../components/DateTimePicker.vue'
import { Page, Masterhead, History, NavHeader, Subheader, Toasts } from '@symfoititis-frontend-monorepo/ui'

import { useCoursesDataService } from '@symfoititis-frontend-monorepo/core/services'

import { useHistory, useRecents } from '@symfoititis-frontend-monorepo/composables'

import { useCourseStore, useErrorStore } from '@symfoititis-frontend-monorepo/stores'

const route = useRoute()
const router = useRouter()

const errorStore = useErrorStore()
const courseStore = useCourseStore()
const { courses } = storeToRefs(courseStore)

const { getCourses } = useCoursesDataService() 

const { addCourseToRecents } = useRecents('bookings_recent')
const { history, addCourseToHistory, removeCourseFromHistory } = useHistory('bookings_history')

const c_id = ref<number>(parseInt(route.params.c_id as string))
const course = ref<Course>({
  c_id: -1, dep_id: -1, c_display_name: '', semester: -1
})

const saveCourse = () => {
  const res = courses.value.find((c: Course) => c.c_id === c_id.value)
  if (!res) {
    errorStore.addError('Course not found')
    return
  }
  course.value = res
  addCourseToHistory(course.value);
  addCourseToRecents(course.value);
}

const handleDelete = (index: number) => {
  const cid = removeCourseFromHistory(index)
  if (cid === c_id.value) {
    router.push({ name: 'tutoring' })
  }
}

onMounted(async () => {
  await getCourses()
  c_id.value = parseInt(route.params.c_id as string)
  saveCourse()
})

watch(route, (newRoute, oldRoute) => {
  c_id.value = parseInt(route.params.c_id as string)
  saveCourse()
})
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="1" />
      <History to="availability" :cid="c_id" :history="history" @delete-course="handleDelete" />
    </template>

    <template v-slot:main>
      <NavHeader navigation="tutoring" storageItem="bookings_history" :course="course" />
      <div class="main-wrapper">
        <Subheader title="Διαθεσιμότητα" />
        <section class="main-container">
          <div class="date-time-pick-container">
            <DateTimePicker />
          </div>
        </section>
      </div>
    </template>
  </Page>
</template>

<style scoped>
.main-wrapper {
  margin-top: 6rem;
}

.main-container {
  margin: 0 auto;
  width: calc(100% - 9rem);
}

.date-time-pick-container {
  display: flex;
  justify-content: space-evenly;
  flex-wrap: wrap;
  margin-bottom: 1rem;
}

@media screen and (max-width: 1800px) {
  .main-container {
    width: calc(100% - 8rem);
  }
}

@media screen and (max-width: 550px) {
  .main-container {
    width: 100%;
  }
}
</style>
