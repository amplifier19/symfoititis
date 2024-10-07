import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { University } from '@symfoitititis-frontend-monorepo/interfaces'

export const useUniversityStore = defineStore('university', () => {
  const university = ref<University>({
    uni_id: -100,
    uni_display_name: 'default',
    uni_alt_name: 'default'
  })

  const getUniversity = async () => {
    await fetch(`${import.meta.env.VITE_STUDENT_API_URL}/university`, {
      method: 'GET'
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        university.value = data.data
      })
      .catch((error: Error) => {
        throw new Error(error.message)
      })
  }

  return { university, getUniversity }
})
