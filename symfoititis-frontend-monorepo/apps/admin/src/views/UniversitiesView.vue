<script setup lang="ts">
import { onMounted } from 'vue'
import { OnClickOutside } from '@vueuse/components'

import Layout from '../components/Layout.vue'
import Section from '../components/Section.vue'
import Modal from '../components/Modal.vue'
import { Toasts } from '@symfoititis-frontend-monorepo/ui'
import { type Response } from '@symfoititis-frontend-monorepo/interfaces'

import { useUniStore } from '../stores/universities'
import { useDepStore } from '../stores/departments'
import { useDisplayModal } from '../stores/displayModal'
import { useErrorStore } from '@symfoititis-frontend-monorepo/stores'

import { useModal } from '../composables/modal'
import { useFetch } from '../composables/fetchService'

const uniStore = useUniStore()
const depStore = useDepStore()
const modalStore = useDisplayModal()
const errorStore = useErrorStore()

const { createUniversity, updateUniversity, deleteUniversity, updateDepartment, deleteDepartment } =
  useFetch()

const {
  modal,
  activateUniAddModal,
  activateUniEditModal,
  activateUniRemoveModal,
  activateDepEditModal,
  activateDepRemoveModal
} = useModal()

onMounted(async () => {
  try {
    if (uniStore.universities.length == 0) {
      await uniStore.getUniversities()
    }
    if (depStore.departments.length == 0) {
      await depStore.getDepartments()
    }
  } catch (err: string) {
    errorStore.addError(JSON.parse(err.message))
  }
})
</script>

<template>
  <Toasts />
  <Layout :selected="0">
    <template v-slot:main-section>
      <button @click="activateUniAddModal" class="pf-v5-c-button pf-m-link" type="button">
        Add University
        <span class="pf-v5-c-button__icon pf-m-end">
          <i class="fa fa-plus-circle" aria-hidden="true"></i>
        </span>
      </button>
      <Section
        v-for="uni in uniStore.universities"
        :university="uni"
        @activate-uni-edit-modal="activateUniEditModal(uni)"
        @activate-uni-remove-modal="activateUniRemoveModal(uni)"
        @activate-dep-edit-modal="activateDepEditModal"
        @activate-dep-remove-modal="activateDepRemoveModal"
      />
      <OnClickOutside @trigger="modalStore.setDisplay(false)">
        <Modal
          @hide="modalStore.setDisplay(false)"
          @create-university="createUniversity"
          @update-university="updateUniversity"
          @delete-university="deleteUniversity"
          @update-department="updateDepartment"
          @delete-department="deleteDepartment"
          v-if="modalStore.displayModal"
          :content="modal"
        />
      </OnClickOutside>
    </template>
  </Layout>
</template>

<style scoped></style>
