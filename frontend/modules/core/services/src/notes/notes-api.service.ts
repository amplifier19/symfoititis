export class NotesApiService {
    private static instance = new NotesApiService()
    private readonly API_BASE_URL = import.meta.env.VITE_API_BASE

    public constructor() { }

    public static getNotesApiFactory() {
        return this.instance
    }

    public getNotes = (cid: number) => {
        return fetch(
            `${this.API_BASE_URL}/education/notes/${cid}`,
            {
                method: 'GET'
            }
        )
    }
}