import { ref } from 'vue'
import { defineStore } from 'pinia'

import type { Department } from '../interfaces/Department'

export const useDepartment = defineStore('department', () => {
  const department = ref<Department>({
    dep_id: -100,
    uni_id: -100,
    dep_display_name: 'default',
    dep_alt_name: 'default'
  })

  const getDepartment = async () => {
    await fetch(`${import.meta.env.VITE_STUDENT_API_URL}/department`, {
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
        department.value = data.data
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  return { department, getDepartment }
})
