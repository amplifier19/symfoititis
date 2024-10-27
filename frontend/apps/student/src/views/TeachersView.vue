<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { type Course, type Teacher } from '@symfoititis-frontend-monorepo/interfaces'
import { Page } from '@symfoititis-frontend-monorepo/ui'
import { Skeleton } from '@symfoititis-frontend-monorepo/ui'
import { Masterhead } from '@symfoititis-frontend-monorepo/ui'
import { History } from '@symfoititis-frontend-monorepo/ui'
import { NavHeader } from '@symfoititis-frontend-monorepo/ui'
import { Toasts } from '@symfoititis-frontend-monorepo/ui'
import TeacherCard from '../components/TeacherCard.vue'
import { useFetch} from '@symfoititis-frontend-monorepo/composables'
import { useHistory } from '@symfoititis-frontend-monorepo/composables'
import { useRecents } from '@symfoititis-frontend-monorepo/composables'
import { useStudentFetch } from '../composables/fetch'
import { useCourseStore } from '@symfoititis-frontend-monorepo/stores'
import { useDepartmentStore } from '@symfoititis-frontend-monorepo/stores'
import { useTeacherStore } from '../stores/teachers'

const router = useRouter()
const route = useRoute()
const displaySkeleton = ref<boolean>(true)
const c_id = ref<number>(parseInt(route.params.c_id as string))
const course = ref<Course>({
  c_id: 0,
  dep_id: 0,
  semester: 0,
  c_display_name: ''
})
const teacherSection = ref<Element>()
const selectedTeacher = ref<Teacher>({ t_id: "", firstname: "", lastname: "" })
const { history, getHistoryFromStorage, addCourseToStorage, deleteCourseFromStorage } = useHistory('bookings_history')
const { addRecToStorage } = useRecents('bookings_recent')
history.value = getHistoryFromStorage()
const courseStore = useCourseStore()
const teacherStore = useTeacherStore()
const departmentStore = useDepartmentStore()
const { getDepartment, getAvailableTutoringCourses } = useFetch()
const { getTeachers } = useStudentFetch()

const selectTeacher = (teacher: Teacher, e: Event) => {
  if (!!teacherSection.value) {
    teacherSection.value.classList.remove('pf-m-expanded')
  }
  if (selectedTeacher.value.t_id === teacher.t_id) {
    selectedTeacher.value = { t_id: "", firstname: "", lastname: "" }
    return
  }
  const target = e.target as HTMLElement
  teacherSection.value = target.offsetParent as Element
  teacherSection.value.classList.add('pf-m-expanded')
  selectedTeacher.value = teacher
}

const saveCourse = () => {
  course.value = courseStore.courses.find(
    (c: Course) => c.c_id == parseInt(route.params.c_id as string)
  ) || { c_id: 0, dep_id: 0, semester: 0, c_display_name: '' }
  if (course.value.c_id! > 0) {
    addCourseToStorage(course.value)
    history.value = getHistoryFromStorage()
    addRecToStorage(course.value);
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
  displaySkeleton.value = true
  c_id.value = parseInt(route.params.c_id as string)
  await getDepartment()
  await getAvailableTutoringCourses(departmentStore.department.dep_id)
  await getTeachers(c_id.value)
  saveCourse()
  displaySkeleton.value = false 
})

watch(route, async (oldRoute, newRoute) => {
  displaySkeleton.value = true 
  const cid = parseInt(route.params.c_id as string)  
  if (c_id.value != cid) {
    c_id.value = cid
    saveCourse()
    await getTeachers(c_id.value)
  }
  displaySkeleton.value = false 
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
      <section v-if="!displaySkeleton" class="main-container">
        <TeacherCard v-for="teacher in teacherStore.teachers" :teacher="teacher" :selectedTeacherId="selectedTeacher.t_id"
          @select-teacher="selectTeacher" :key="teacher.t_id" />
      </section>
      <section v-else class="main-container">
        <Skeleton />
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
