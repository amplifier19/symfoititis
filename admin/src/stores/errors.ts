import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useErrors = defineStore('error', () => {
  const errors = ref<string[]>([])

  const addError = (err: string) => {
    errors.value.push(err)
  }

  const deleteError = (idx: number) => {
    errors.value.splice(idx, 1)
  }

  return { errors, addError, deleteError }
})
