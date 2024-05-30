import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useFileStore = defineStore('files', () => {
  const attachments = ref<File[]>([])
  const filenames = ref<string[]>([])
  const current_file = ref<string>('')

  const setAttachments = (a: File[]) => (attachments.value = a)

  const getFiles = async (c_id: number) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/files/${c_id}`, {
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
        filenames.value.splice(0, filenames.value.length)
        filenames.value.unshift(...data.data)
      })
      .catch((error) => {
        filenames.value = []
        throw new Error(error)
      })
  }

  const uploadFiles = async (c_id: number) => {
    if (attachments.value.length <= 0) return
    const formData = new FormData()
    for (const file of attachments.value) {
      formData.append('files', file)
    }
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/files/upload/${c_id}`, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
      },
      body: formData
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        setAttachments([])
        var message = data.message + ': '
        message += data.data.join(', ')
        return message
      })
      .catch((error) => {
        setAttachments([])
        throw new Error(error)
      })
  }

  const deleteFile = async (formData: { c_id: number; filename: string }) => {
    return await fetch(
      `${import.meta.env.VITE_ADMIN_API_URL}/files/${formData.c_id}/${formData.filename}`,
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
          throw new Error(data.error)
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  return { attachments, filenames, current_file, getFiles, uploadFiles, deleteFile, setAttachments }
})
