import { defineStore } from 'pinia'
import { ref } from 'vue'
import { Response } from '@symfoititis-frontend-monorepo/interfaces'

export const useResponseStore = defineStore('responses', () => {
  const responses = ref<(Response | string)[]>([])

  const addResponse = (resp: Response | string) => {
    responses.value = [...responses.value, resp] 
  }

  const deleteResponse = (idx: number) => {
    responses.value = responses.value.toSpliced(idx, 1)
  }

  return { responses, addResponse, deleteResponse }
})
