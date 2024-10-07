<script setup lang="ts">
import { onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import { useNoteStore } from '../stores/notes'
import { useFileStore } from '../stores/files'
import { useErrorStore } from '@symfoititis-frontend-monorepo/stores'
import { useDisplayModal } from '../stores/displayModal'

import { useFetch } from '../composables/fetchService'
import { useModal } from '../composables/modal'

import { Toasts } from '@symfoititis-frontend-monorepo/ui'
import Layout from '../components/Layout.vue'
import FileUpload from '../components/FileUpload.vue'
import Table from '../components/Table.vue'
import FileEntry from '../components/FileEntry.vue'
import Modal from '../components/Modal.vue'
import Note from '../components/Note.vue'

import { OnClickOutside } from '@vueuse/components'

const route = useRoute()
const router = useRouter()

const noteStore = useNoteStore()
const fileStore = useFileStore()
const errorStore = useErrorStore()
const modalStore = useDisplayModal()

const { uploadFile, deleteFile, createNote, updateNote, deleteNote } = useFetch()
const {
  modal,
  activateNoteAddModal,
  activateNoteEditModal,
  activateNoteRemoveModal,
  activateFileRemoveModal
} = useModal()

onMounted(async () => {
  try {
    await noteStore.getNotes(parseInt(route.params.c_id.toString()))
    await fileStore.getFiles(parseInt(route.params.c_id.toString()))
  } catch (error: any) {
    errorStore.addError(error)
  }
})
</script>

<template>
  <Toasts />

  <Layout :selected="1">
    <template v-slot:main-section>
      <button @click="router.go(-1)" class="pf-v5-c-button pf-m-link" type="button">
        Back to Courses
        <span class="pf-v5-c-button__icon pf-m-end">
          <i sclass="fa fa-plus-circle" aria-hidden="true"></i>
        </span>
      </button>

      <FileUpload
        :c_id="parseInt(route.params.c_id.toString())"
        @upload-note="uploadFile(parseInt(route.params.c_id.toString()))"
      />

      <Table title="Files">
        <template v-slot:table-body>
          <FileEntry
            v-for="fname in fileStore.filenames"
            :c_id="parseInt(route.params.c_id.toString())"
            :filename="fname"
            @activate-remove-modal="activateFileRemoveModal"
          />
        </template>
      </Table>
      <button
        @click="activateNoteAddModal(parseInt(route.params.c_id.toString()))"
        class="pf-v5-c-button pf-m-link"
        type="button"
      >
        Add Note
        <span class="pf-v5-c-button__icon pf-m-end">
          <i class="fa fa-plus-circle" aria-hidden="true"></i>
        </span>
      </button>

      <Table title="Notes">
        <template v-slot:table-body>
          <div v-if="noteStore.notes.length > 0">
            <Note
              v-for="note in noteStore.notes"
              :note="note"
              @activate-edit-modal="activateNoteEditModal(note)"
              @activate-remove-modal="activateNoteRemoveModal(note)"
            />
          </div>
        </template>
      </Table>

      <OnClickOutside @trigger="modalStore.setDisplay(false)">
        <Modal
          v-if="modalStore.displayModal"
          :content="modal"
          @hide="modalStore.setDisplay(false)"
          @create-note="createNote"
          @update-note="updateNote"
          @delete-note="deleteNote"
          @delete-file="deleteFile"
        />
      </OnClickOutside>
    </template>
  </Layout>
</template>

<style scoped></style>
