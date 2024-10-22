import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Course } from '@symfoitititis-frontend-monorepo/interfaces'

export const useCourseStore = defineStore('courseStore', () => {
  const courses = ref<Course[]>([])

  const getCourses = async () => {
    await fetch(`${import.meta.env.VITE_STUDENT_API_URL}/courses`, {
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

  return { courses, getCourses }
})
