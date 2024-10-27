import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import  { type Course } from '@symfoitititis-frontend-monorepo/interfaces'

export const useCourseStore = defineStore('courseStore', () => {
  const courses = ref<Course[]>([])
  const availableTutoringCourseIds = ref<number[]>([])
  const availableTutoringCourses = computed<Course[]>(() => {
    return courses.value.filter((c: Course) => availableTutoringCourseIds.value.includes(c.c_id))
  })

  const getCourses = async () => {
    await fetch(`${import.meta.env.VITE_API_BASE}/education/courses`, {
      method: 'GET'
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        courses.value = data.data
      })
      .catch((error: Error) => {
        throw new Error(error.message)
      })
  }

  const getAvailableTutoringCourseIds = async (dep_id: number) => {
    await fetch(`${import.meta.env.VITE_API_BASE}/tutoring/availability/${dep_id}/courseIds`, {
      method: 'GET'
    })
    .then((response) => response.json())
    .then((data) => {
      if (!!data?.error) {
        throw new Error(JSON.stringify(data))
      }
      availableTutoringCourseIds.value = data.data
    })
    .catch((error: Error) => {
      throw new Error(error.message)
    })
  }

  return { courses, availableTutoringCourses, getCourses, getAvailableTutoringCourseIds }
})
