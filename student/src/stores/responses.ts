import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useResponseStore = defineStore('responses', () => {
  const responses = ref<string[]>([])

  const addResponse = (resp: string) => {
    responses.value.push(resp)
  }
  const deleteResponse = (idx: number) => {
    responses.value.splice(idx, 1)
  }

  return { responses, addResponse, deleteResponse }
})
