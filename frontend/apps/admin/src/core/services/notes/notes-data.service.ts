import { useNoteStore } from '../../../stores/notes.store'
import { useDisplayModal } from '../../../stores/displayModal'
import { NotesApiService } from './notes-api.service'
import { useErrorStore, useResponseStore } from '@symfoititis-frontend-monorepo/stores'
import type { Note } from '@symfoititis-frontend-monorepo/interfaces'

export class NotesDataService {
  private static instance = new NotesDataService()
  private apiService = NotesApiService.getNotesApiFactory()
  private noteStore = useNoteStore()
  private responseStore = useResponseStore()
  private errorStore = useErrorStore()
  private modalStore = useDisplayModal()

  public constructor() { }

  public static getNotesDataFactory() {
    return this.instance
  }

  public getNotes = async (c_id: number) => {
    try {
      const response = await this.apiService.getNotes(c_id)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.noteStore.setNotes(data.data)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
    }
  }

  public createNote = async (note: Note) => {
    try {
      const response = await this.apiService.createNote(note)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.responseStore.addResponse(data)
        this.getNotes(note.c_id)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
    }
  }

  public updateNote = async (note: Note) => {
    try {
      const response = await this.apiService.updateNote(note)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.responseStore.addResponse(data)
        this.getNotes(note.c_id)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
    }
  }

  public deleteNote = async (note_id: number, note_display_name: string) => {
    try {
      if (note_display_name !== this.noteStore.currentNote?.note_display_name) {
        throw "Wrong Note name"
      }
      const response = await this.apiService.deleteNote(note_id)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.responseStore.addResponse(data)
        this.getNotes(this.noteStore.currentNote.c_id)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
    }
  }
}
