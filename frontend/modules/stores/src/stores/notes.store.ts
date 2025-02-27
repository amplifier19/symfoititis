import { ref } from 'vue'
import { defineStore } from 'pinia'
import { Note } from '@symfoititis-frontend-monorepo/interfaces'

export const useNoteStore = defineStore('notes', () => {
  const notes = ref<Note[]>([])

  return { notes }
})
