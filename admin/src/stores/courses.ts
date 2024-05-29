import { ref } from 'vue'
import { defineStore } from 'pinia'

import { type Course } from '../interfaces/Course'

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
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/courses/${dep_id}`, {
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

  const getCourse = async (c_id: number) => {
    await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/course/${c_id}`, {
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
        current_course.value = data.data
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  const createCourse = async (course: Course) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/course`, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(course)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  const updateCourse = async (course: Course) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/course`, {
      method: 'PUT',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(course)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  const deleteCourse = async (c_id: number) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/course/${c_id}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error)
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
