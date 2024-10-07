<script setup lang="ts">
import { ref, defineAsyncComponent, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { type Course, type Teacher } from '@symfoititis-frontend-monorepo/interfaces'

import { Page } from '@symfoititis-frontend-monorepo/ui' 
import { Masterhead } from '@symfoititis-frontend-monorepo/ui' 
import { History } from '@symfoititis-frontend-monorepo/ui' 
import { NavHeader } from '@symfoititis-frontend-monorepo/ui' 
import { Toasts } from '@symfoititis-frontend-monorepo/ui' 
import TeacherCard from '../components/TeacherCard.vue'

import { useHistory } from '@symfoititis-frontend-monorepo/composables'
import { useRecents } from '@symfoititis-frontend-monorepo/composables'
import { useFetch } from '@symfoititis-frontend-monorepo/composables'

import { useTutoringStore } from '@symfoititis-frontend-monorepo/stores' 
import { useErrorStore } from '@symfoititis-frontend-monorepo/stores' 

const router = useRouter()
const route = useRoute()
const c_id = ref<number>(parseInt(route.params.c_id))
const course = ref<Course>({
  c_id: 0,
  dep_id: 0,
  semester: 0,
  c_display_name: ''
})
const teachers: Teacher[] = [
  { id: 1, first_name: "John", last_name: "Doe" },
  { id: 2, first_name: "Jane", last_name: "Smith" },
  { id: 3, first_name: "Emily", last_name: "Johnson" },
  { id: 4, first_name: "Michael", last_name: "Brown" }
];
const teacherSection = ref<HTMLElement>()
const selectedTeacher = ref<Teacher>({ id: -100, first_name: "", last_name: "" })
const { history, getHistoryFromStorage, addCourseToStorage, deleteCourseFromStorage } = useHistory('bookings_history')
const { addRecToStorage } = useRecents('bookings_recent')
history.value = getHistoryFromStorage()
const tutoringStore = useTutoringStore()
const errorStore = useErrorStore()
const { getTutoringCourses } = useFetch()

const selectTeacher = (teacher: Teacher, e: MouseEvent) => {
  if (!!teacherSection.value) {
    teacherSection.value.classList.remove('pf-m-expanded')
  }
  if (selectedTeacher.value.id === teacher.id) {
    selectedTeacher.value = { id: -100, first_name: "", last_name: "" }
    return
  }
  teacherSection.value = e.target.offsetParent
  teacherSection.value.classList.add('pf-m-expanded')
  selectedTeacher.value = teacher
}
const saveCourse = () => {
  course.value = tutoringStore.courses.find(
    (c) => c.c_id == parseInt(route.params.c_id)
  ) || { c_id: 0, dep_id: 0, semester: 0, c_display_name: '' }
  if (course.value.c_id > 0) {
    addCourseToStorage(course.value)
    history.value = getHistoryFromStorage()
    addRecToStorage(course.value);
  } else {
    errorStore.addError({status: 404, error: 'Course not Found'})
  }
}
const handleDelete = (index: number) => {
  const cid = deleteCourseFromStorage(index)
  if (cid < 0) {
    return
  }
  if (cid === c_id.value) {
    router.push({ name: 'tutoring' })
  }
  history.value = getHistoryFromStorage()
}
onMounted(async () => {
  await getTutoringCourses()
  c_id.value = parseInt(route.params.c_id)
  saveCourse()
})
watch(route, (oldRoute, newRoute) => {
  const cid = parseInt(route.params.c_id)
  if (c_id.value != cid) {
    c_id.value = cid
    saveCourse()
  }
})
</script>

<template>
  <Toasts/>
  <Page>
    <template v-slot:header>
      <Masterhead :selected="1" />
      <History to="availability" :cid="c_id" :history="history" @delete-course="handleDelete" />
    </template>
    <template v-slot:main>
      <NavHeader navigation="tutoring" storageItem="bookings_history" :course="course" />
      <section class="main-container">
        <TeacherCard v-for="teacher in teachers" :teacher="teacher" :selectedTeacherId="selectedTeacher.id"
          @select-teacher="selectTeacher" :key="teacher.id" />
      </section>
    </template>
  </Page>
</template>

<style scoped>
.main-container {
  margin: 6rem auto;
  width: calc(100% - 9rem);
}

@media screen and (max-width: 1800px) {
  .main-container {
    width: calc(100% - 8rem);
  }
}

@media screen and (max-width: 1300px) {
  .main-container {
    width: 100%;
  }
}
</style>
