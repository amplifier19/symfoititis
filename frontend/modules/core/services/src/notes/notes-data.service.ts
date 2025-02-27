import { NotesApiService } from "./notes-api.service"
import { useErrorStore, useNoteStore } from "@symfoititis-frontend-monorepo/stores"

export const useNotesDataService = () => {
    const notesApiService = NotesApiService.getNotesApiFactory()
    const noteStore = useNoteStore()
    const errorStore = useErrorStore()

    const getNotes = async (cid: number) => {
        try {
            const response = await notesApiService.getNotes(cid)
            const data = await response.json()
            if (!!data.error) {
                errorStore.addError(data)
                return
            }
            noteStore.notes = data.data
        }
        catch (err) {
            errorStore.addError(err)
        }
    }

    return { getNotes }
}