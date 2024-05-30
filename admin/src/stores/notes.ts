import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Note } from '../interfaces/Note'

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
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/notes/${c_id}`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
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

  const createNote = async (note: Note) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/note`, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(note)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  const updateNote = async (note: Note) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/note`, {
      method: 'PUT',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(note)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  const deleteNote = async (note_id: number) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/note/${note_id}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
      }
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error)
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
