export class FileApiService {
  private static instance = new FileApiService()
  private readonly API_BASE = import.meta.env.VITE_API_BASE

  public constructor() { }

  public static getFilesApiFactory() {
    return this.instance
  }

  public generatePresignedUrl = (c_id: number, filename: string) => {
    return fetch(`${this.API_BASE}/education/object/${c_id}/${filename}/generateUrl`, {
      method: 'GET'
    })
  }

  public getFiles = async (c_id: number) => {
    return fetch(`${this.API_BASE}/education/objects/${c_id}`, {
      method: 'GET'
    })
  }

  public uploadFiles = (c_id: number, formData: FormData) => {
    return fetch(`${this.API_BASE}/education/objects/${c_id}`, {
      method: 'POST',
      body: formData
    })
  }

  public deleteFile = (c_id: number, filename: string) => {
    return fetch(
      `${this.API_BASE}/education/object/${c_id}/${filename}`,
      {
        method: 'DELETE',
      }
    )
  }
}

