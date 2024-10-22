<script setup lang="ts">
import { ref } from 'vue'

import { useFileStore } from '../stores/files'

const props = defineProps<{ c_id: number }>()
const rootUrl = import.meta.env.VITE_ROOT_URL
const fileStore = useFileStore()
const fileInput = ref<HTMLInputElement | null>(null)
const drag = ref(false)

const handleDrag = (e: Event) => {
  e.preventDefault()
  drag.value = true
}
const dragStopped = () => {
  drag.value = false
}
const handleFileChange = () => {
  if (!fileInput.value) return
  const fileListAsArry = Array.from(fileInput.value!.files!)
  fileStore.setAttachments(fileListAsArry)
}
const handleDrop = (e: DragEvent) => {
  e.preventDefault()
  if (!!fileInput.value) return
  fileInput.value!.files = e.dataTransfer!.files
  handleFileChange()
  drag.value = false
}
</script>

<template>
  <form class="file-upload-form" @submit.prevent="$emit('upload-note')">
    <label
      class="upload-container"
      @dragover="handleDrag"
      @dragleave="dragStopped"
      @drop="handleDrop"
    >
      <input
        ref="fileInput"
        type="file"
        name="file"
        id="file"
        @change="handleFileChange"
        hidden
        multiple
      />
      <div class="img-container">
        <img :src="`${rootUrl}/svg/upload-icon.png`" alt="upload-icon" />
        <section v-if="fileStore.attachments.length" class="file-name">
          <p v-for="f in fileStore.attachments">
            {{ f?.name }}
          </p>
        </section>
        <section v-else-if="drag" class="description">
          <p>Drop files Here</p>
        </section>
        <section v-else class="description">
          <p>Drag and drop or Click here, to upload mutiple file</p>
        </section>
      </div>
    </label>
    <button v-if="fileStore.attachments.length > 0" class="submit-btn" type="submit">Upload</button>
  </form>
</template>

<style scoped>
.file-upload-form {
  margin: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.upload-container {
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-items: center;
  align-items: center;
  max-width: max-content;
}
.img-container {
  padding: 20px;
  width: 460px;
  border-radius: 20px;
  border: 2px dashed #cccccc;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-items: center;
}
.img-container img {
  width: 30%;
  height: auto;
}
.img-container .description,
.file-name {
  display: flex;
  flex-direction: column;
  justify-items: center;
  align-items: center;
  margin-top: 10px;
}
.img-container .description p {
  color: #999999;
}
.submit-btn {
  width: max-content;
  margin-top: 10px;
  background-color: #3399db;
  border: none;
  color: white;
  font-size: 20px;
  font-weight: 500;
  padding: 8px 16px;
  border-radius: 10px;
  cursor: pointer;
  margin-top: 25px;
  transition: all;
}
</style>
