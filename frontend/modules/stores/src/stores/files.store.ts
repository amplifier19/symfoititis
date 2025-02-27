import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useFileStore = defineStore('fileStore', () => {
  const attachments = ref<File[]>([])
  const filenames = ref<string[]>([])
  const current_file = ref<string>('')

  const setAttachments = (a: File[]) => (attachments.value = a)

  return { attachments, filenames, current_file,setAttachments }
})
