<script setup lang="ts">
import { onMounted } from 'vue'
import { OnClickOutside } from '@vueuse/components'

import { Toasts } from '@symfoititis-frontend-monorepo/ui'
import Layout from '../components/Layout.vue'
import Table from '../components/Table.vue'
import Department from '../components/Department.vue'
import Modal from '../components/Modal.vue'

import { useDepStore } from '../stores/departments'
import { useUniStore } from '../stores/universities'
import { useErrorStore } from '@symfoititis-frontend-monorepo/stores'
import { useDisplayModal } from '../stores/displayModal'

import { useModal } from '../composables/modal'
import { useFetch } from '../composables/fetchService'

const depStore = useDepStore()
const uniStore = useUniStore()
const modalStore = useDisplayModal()
const errorStore = useErrorStore()

const { modal, activateDepAddModal, activateDepEditModal, activateDepRemoveModal } = useModal()
const { createDepartment, updateDepartment, deleteDepartment } = useFetch()

onMounted(async () => {
  try {
    if (uniStore.universities.length == 0) {
      await uniStore.getUniversities()
    }
    if (depStore.departments.length == 0) {
      await depStore.getDepartments()
    }
  } catch (err: any) {
    errorStore.addError(JSON.parse(err.message))
  }
})
</script>

<template>
  <Toasts />
  <Layout :selected="1">
    <template v-slot:main-section>
      <button @click="activateDepAddModal" class="pf-v5-c-button pf-m-link" type="button">
        Add Department
        <span class="pf-v5-c-button__icon pf-m-end">
          <i class="fa fa-plus-circle" aria-hidden="true"></i>
        </span>
      </button>
      <Table>
        <template v-slot:table-body>
          <Department
            v-for="dep in depStore.departments"
            :department="dep"
            @activate-edit-modal="activateDepEditModal"
            @activate-remove-modal="activateDepRemoveModal"
          >
            <td v-if="uniStore.universities.length > 0" class="pf-v5-c-table__td" role="cell">
              {{ uniStore.universities.find((uni) => uni.uni_id == dep.uni_id)?.uni_display_name }}
            </td>
          </Department>
        </template>
      </Table>
      <OnClickOutside @trigger="modalStore.setDisplay(false)">
        <Modal
          v-if="modalStore.displayModal"
          :content="modal"
          @hide="modalStore.setDisplay(false)"
          @create-department="createDepartment"
          @update-department="updateDepartment"
          @delete-department="deleteDepartment"
        />
      </OnClickOutside>
    </template>
  </Layout>
</template>

<style scoped></style>
