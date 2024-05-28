import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Course } from '../interfaces/Course'

export const useCourseStore = defineStore('courseStore', () => {
  const courses = ref<Course[]>([])

  const getCourses = async () => {
    await fetch(`${import.meta.env.VITE_STUDENT_API_URL}/courses`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        courses.value = data.data
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  return { courses, getCourses }
})
