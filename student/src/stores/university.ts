import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { University } from '../interfaces/University'

export const useUniversity = defineStore('university', () => {
  const university = ref<University>({
    uni_id: -100,
    uni_display_name: 'default',
    uni_alt_name: 'default'
  })

  const getUniversity = async () => {
    await fetch(`${import.meta.env.VITE_STUDENT_API_URL}/university`, {
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
        university.value = data.data
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  return { university, getUniversity }
})
