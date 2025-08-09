import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { University } from '@symfoititis-frontend-monorepo/interfaces'

export const useUniStore = defineStore('universities', () => {
  const universities = ref<University[]>([])
  const currentUniversity = ref<University | null>(null)

  const setUniversities = (list: University[]) => {
    universities.value = list
  }

  const setCurrentUniversity = (uni: University) => {
    currentUniversity.value = uni
  }

  return {
    universities,
    currentUniversity,
    setUniversities,
    setCurrentUniversity,
  }
})

