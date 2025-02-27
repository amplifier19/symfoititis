import { defineStore } from 'pinia'
import { ref } from 'vue'
import { Response } from '@symfoititis-frontend-monorepo/interfaces'

export const useResponseStore = defineStore('responses', () => {
  const responses = ref<Response[]>([])

  const addResponse = (resp: Response) => {
    responses.value.push(resp)
  }
  const deleteResponse = (idx: number) => {
    responses.value.splice(idx, 1)
  }

  return { responses, addResponse, deleteResponse }
})
