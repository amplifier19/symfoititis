import { ref } from 'vue'
import { defineStore } from 'pinia'

import { type Course } from '@symfoititis-frontend-monorepo/interfaces'

export const useCourseStore = defineStore('courses', () => {
  const courses = ref<Course[]>([])
  const current_course = ref<Course>({
    c_id: -100,
    dep_id: -100,
    semester: -100,
    c_display_name: 'default',
    description: ''
  })

  const getCourses = async (dep_id: number) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/education/courses/${dep_id}`, {
      method: 'GET'
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        courses.value = data.data
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  const getCourse = async (c_id: number) => {
    await fetch(`${import.meta.env.VITE_API_BASE}/education/course/${c_id}`, {
      method: 'GET'
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        current_course.value = data.data
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  const createCourse = async (course: Course) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/education/course`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(course)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  const updateCourse = async (course: Course) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/education/course`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(course)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  const deleteCourse = async (c_id: number) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/education/course/${c_id}`, {
      method: 'DELETE'
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  return {
    courses,
    current_course,
    getCourses,
    getCourse,
    createCourse,
    updateCourse,
    deleteCourse
  }
})
