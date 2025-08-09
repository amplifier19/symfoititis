import type { Note } from '@symfoititis-frontend-monorepo/interfaces'

export class NotesApiService {
  private static instance = new NotesApiService()
  private readonly API_BASE = import.meta.env.VITE_API_BASE

  public constructor() {}

  public static getNotesApiFactory() {
    return this.instance
  }

  public getNotes(c_id: number): Promise<Response> {
    return fetch(`${this.API_BASE}/education/notes/${c_id}`, {
      method: 'GET',
    })
  }

  public createNote(note: Note): Promise<Response> {
    return fetch(`${this.API_BASE}/education/note`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(note),
    })
  }

  public updateNote(note: Note): Promise<Response> {
    return fetch(`${this.API_BASE}/education/note`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(note),
    })
  }

  public deleteNote(note_id: number): Promise<Response> {
    return fetch(`${this.API_BASE}/education/note/${note_id}`, {
      method: 'DELETE',
    })
  }
}

