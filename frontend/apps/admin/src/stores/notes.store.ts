import { ref } from "vue";
import { defineStore } from "pinia";
import type { Note } from "@symfoititis-frontend-monorepo/interfaces";

export const useNoteStore = defineStore("notes", () => {
  const notes = ref<Note[]>([]);
  const currentNote = ref<Note | null>(null);

  const setNotes = (list: Note[]) => {
    notes.value = list;
  };

  const setCurrentNote = (note: Note) => {
    currentNote.value = note;
  };

  return {
    notes,
    currentNote,
    setNotes,
    setCurrentNote,
  };
});
