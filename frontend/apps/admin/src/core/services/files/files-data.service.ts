import { useFileStore } from "../../../stores/files.store"
import { useDisplayModal } from "../../../stores/displayModal"
import { useErrorStore, useResponseStore } from "@symfoititis-frontend-monorepo/stores"

import { FileApiService } from "./files-api.service"

export class FileDataService {
  private static instance = new FileDataService()
  private apiService = FileApiService.getFilesApiFactory()
  private fileStore = useFileStore()
  private errorStore = useErrorStore()
  private responseStore = useResponseStore()
  private modalStore = useDisplayModal()

  public constructor() { }

  public static getFilesDataFactory() {
    return this.instance
  }

  public generatePresignedUrl = async (c_id: number, filename: string) => {
    try {
      const response = await this.apiService.generatePresignedUrl(c_id, filename)
      const data = await response.json()
      if (!!data.error) {
        this.errorStore.addError(data.error)
      } else {
        return data.data
      }
    } catch (err) {
      this.errorStore.addError(err)
    }
  }

  public getFiles = async (c_id: number) => {
    try {
      const response = await this.apiService.getFiles(c_id)
      const data = await response.json()
      if (!!data.error) {
        this.errorStore.addError(data.error)
      } else {
        this.fileStore.filenames = data.data
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
    }
  }

  public uploadFiles = async (c_id: number) => {
    try {
      let formData = new FormData()
      for (const file of this.fileStore.attachments) {
        formData.append("files", file)
      }
      const response = await this.apiService.uploadFiles(c_id, formData)
      const data = await response.json()
      if (!!data.error) {
        this.errorStore.addError(data.error)
      } else {
        this.responseStore.addResponse(data)
        this.getFiles(c_id)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
      this.fileStore.setAttachments([])
    }
  }

  public deleteFile = async (c_id: number, filename: string) => {
    try {
      if (!this.fileStore.filenames.includes(filename)) {
        throw "Wrong filename"
      }
      const response = await this.apiService.deleteFile(c_id, filename)
      const data = await response.json()
      if (!!data.error) {
        this.errorStore.addError(data.error)
      } else {
        this.responseStore.addResponse(data)
        this.getFiles(c_id)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
    }
  }
}
