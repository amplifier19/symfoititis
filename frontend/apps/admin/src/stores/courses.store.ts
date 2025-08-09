import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Course } from '@symfoititis-frontend-monorepo/interfaces'

export const useCourseStore = defineStore('courses', () => {
  const courses = ref<Course[]>([])
  const currentCourse = ref<Course | null>(null)

  const setCourses = (newCourses: Course[]) => {
    courses.value = newCourses
  }

  const setCurrentCourse = (course: Course) => {
    currentCourse.value = course
  }

  return {
    courses,
    currentCourse,
    setCourses,
    setCurrentCourse,
  }
})
