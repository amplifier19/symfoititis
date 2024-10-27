import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Note } from '@symfoititis-frontend-monorepo/interfaces'

export const useNoteStore = defineStore('notes', () => {
  const notes = ref<Note[]>([])
  const current_note = ref<Note>({
    note_id: -100,
    c_id: -100,
    type: 'theory',
    note_display_name: 'default',
    note_filename: 'default'
  })

  const getNotes = async (c_id: number) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/education/notes/${c_id}`, {
      method: 'GET'
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        notes.value = data.data
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  const createNote = async (note: Note) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/education/note`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(note)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  const updateNote = async (note: Note) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/education/note`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(note)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  const deleteNote = async (note_id: number) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/education/note/${note_id}`, {
      method: 'DELETE'
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  return {
    notes,
    current_note,
    getNotes,
    createNote,
    updateNote,
    deleteNote
  }
})
