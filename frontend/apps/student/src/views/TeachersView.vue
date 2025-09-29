<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import { Course, Teacher } from '@symfoititis-frontend-monorepo/interfaces'

import { useChatDataService, useCoursesDataService } from '@symfoititis-frontend-monorepo/core/services'
import { TeachersDataService } from '../core/services/teachers/teachers-data.service'

import { useTeacherStore } from '../stores/teachers'
import { useCourseStore, useErrorStore } from '@symfoititis-frontend-monorepo/stores'

import { useHistory } from '@symfoititis-frontend-monorepo/composables'
import { useRecents } from '@symfoititis-frontend-monorepo/composables'

import TeacherCard from '../components/TeacherCard.vue'
import { Page } from '@symfoititis-frontend-monorepo/ui'
import { Toasts } from '@symfoititis-frontend-monorepo/ui'
import { History } from '@symfoititis-frontend-monorepo/ui'
import { NavHeader } from '@symfoititis-frontend-monorepo/ui'
import { Masterhead } from '@symfoititis-frontend-monorepo/ui'
import { Subheader } from '@symfoititis-frontend-monorepo/ui'

const route = useRoute()
const router = useRouter()

const { addCourseToRecents } = useRecents('bookings_recent')
const { history, addCourseToHistory, removeCourseFromHistory } = useHistory('bookings_history')

const errorStore = useErrorStore()
const courseStore = useCourseStore()
const teacherStore = useTeacherStore()
const { courses } = storeToRefs(courseStore)
const { teachers } = storeToRefs(teacherStore)

const teacherDataService = TeachersDataService.getTeachersDataFactory()
const { getCourses, getAvailableTutoringCourseIds } = useCoursesDataService()

const course = ref<Course>({
  c_id: -1, dep_id: -1, c_display_name: '', semester: -1
})
const teacherSection = ref<Element>()
const c_id = ref<number>(parseInt(route.params.c_id as string))
const selectedTeacher = ref<Teacher>({ t_id: "", firstname: "", lastname: "" })

const collapseTeacher = () => {
  if (!!teacherSection.value) {
    teacherSection.value.classList.remove('pf-m-expanded')
  }
}

const selectTeacher = (teacher: Teacher, e: Event) => {
  collapseTeacher()
  if (selectedTeacher.value.t_id === teacher.t_id) {
    selectedTeacher.value = { t_id: "", firstname: "", lastname: "" }
    return
  }
  const target = e.target as HTMLElement
  teacherSection.value = target.offsetParent as Element
  teacherSection.value.classList.add('pf-m-expanded')
  selectedTeacher.value = teacher
}

const handleDelete = (courseId: number) => {
  const cid = removeCourseFromHistory(courseId);
  if (cid === c_id.value) {
    router.push({ name: 'tutoring' })
  }
}

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

onMounted(async () => {
  c_id.value = parseInt(route.params.c_id as string)
  await getCourses()
  await getAvailableTutoringCourseIds()
  await teacherDataService.getTeachers(c_id.value)
  saveCourse()
})

watch(route, async (oldRoute, newRoute) => {
  collapseTeacher()
  c_id.value = parseInt(route.params.c_id as string)
  selectedTeacher.value = { t_id: "", firstname: "", lastname: "" }
  await teacherDataService.getTeachers(c_id.value)
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

    <template v-slot:subheader>
      <NavHeader navigation="tutoring" storageItem="bookings_history" :course="course" />
    </template>

    <template v-slot:main>
      <section class="teachers-wrapper wrapper">
        <div class="teacher-container content-width">
          <Subheader title="Συμφοιτητές" />
          <TeacherCard v-for="teacher in teachers" :teacher="teacher" 
            :selectedTeacherId="selectedTeacher.t_id"
            @select-teacher="selectTeacher" :key="teacher.t_id" 
          />
        </div>
      </section>
    </template>
  </Page>
</template>

<style scoped>
</style>
