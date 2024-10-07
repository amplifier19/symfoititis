import { defineStore } from 'pinia'
import { ref } from 'vue'
import { type Response } from '@symfoititis-frontend-monorepo/interfaces'

export const useResponseStore = defineStore('responses', () => {
  const responses = ref<Reponse[]>([])

  const addResponse = (resp: Response) => {
    responses.value.push(resp)
  }
  const deleteResponse = (idx: number) => {
    responses.value.splice(idx, 1)
  }

  return { responses, addResponse, deleteResponse }
})
