import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Note } from '../interfaces/Note'

export const useNoteStore = defineStore('notes', () => {
  const notes = ref<Note[]>([])

  const getNotes = async (c_id: number) => {
    return await fetch(`${import.meta.env.VITE_STUDENT_API_URL}/notes/${c_id}`, {
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
        notes.value = data.data
      })
      .catch((error) => {
        throw new Error(error)
      })
  }
  return { notes, getNotes }
})
