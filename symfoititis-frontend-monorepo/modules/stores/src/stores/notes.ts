import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Note } from '@symfoitititis-frontend-monorepo/interfaces'

export const useNoteStore = defineStore('notes', () => {
  const notes = ref<Note[]>([])

  const getNotes = async (c_id: number) => {
    return await fetch(`${import.meta.env.VITE_STUDENT_API_URL}/notes/${c_id}`, {
      method: 'GET'
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        notes.value = data.data
      })
      .catch((error: Error) => {
        throw new Error(error.message)
      })
  }
  return { notes, getNotes }
})
