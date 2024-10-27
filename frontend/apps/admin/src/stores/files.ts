import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useFileStore = defineStore('files', () => {
  const attachments = ref<File[]>([])
  const filenames = ref<string[]>([])
  const current_file = ref<string>('')

  const setAttachments = (a: File[]) => (attachments.value = a)

  const getFiles = async (c_id: number) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/education/files/${c_id}`, {
      method: 'GET'
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        filenames.value.splice(0, filenames.value.length)
        filenames.value.unshift(...data.data)
      })
      .catch((error) => {
        filenames.value = []
        throw new Error(error.message)
      })
  }

  const uploadFiles = async (c_id: number) => {
    if (attachments.value.length <= 0) return
    const formData = new FormData()
    for (const file of attachments.value) {
      formData.append('files', file)
    }
    return await fetch(`${import.meta.env.VITE_API_BASE}/education/files/upload/${c_id}`, {
      method: 'POST',
      body: formData
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        setAttachments([])
        var message = data.message + ': '
        message += data.data.join(', ')
        return message
      })
      .catch((error) => {
        setAttachments([])
        throw new Error(error.message)
      })
  }

  const deleteFile = async (formData: { c_id: number; filename: string }) => {
    return await fetch(
      `${import.meta.env.VITE_API_BASE}/education/files/${formData.c_id}/${formData.filename}`,
      {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${localStorage.getItem('admin_token')}`
        }
      }
    )
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

  return { attachments, filenames, current_file, getFiles, uploadFiles, deleteFile, setAttachments }
})
