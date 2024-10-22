<script setup lang="ts">
import { onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { OnClickOutside } from '@vueuse/components'

import { useCourseStore } from '../stores/courses'
import { useDepStore } from '../stores/departments'
import { useDisplayModal } from '../stores/displayModal'
import { useErrorStore } from '@symfoititis-frontend-monorepo/stores'

import { useModal } from '../composables/modal'
import { useFetch } from '../composables/fetchService'

import Layout from '../components/Layout.vue'
import { Toasts } from '@symfoititis-frontend-monorepo/ui'
import Table from '../components/Table.vue'
import Course from '../components/Course.vue'
import Modal from '../components/Modal.vue'

const route = useRoute()

const courseStore = useCourseStore()
const depStore = useDepStore()
const modalStore = useDisplayModal()
const errorStore = useErrorStore()

const { modal, activateCourseAddModal, activateCourseEditModal, activateCourseRemoveModal } =
  useModal()

const { createCourse, updateCourse, deleteCourse } = useFetch()

onMounted(async () => {
  try {
    if (depStore.departments.length == 0) {
      await depStore.getDepartments()
    }
    await courseStore.getCourses(parseInt(route.params.dep_id.toString()))
  } catch (err: any) {
    errorStore.addError(JSON.parse(err.message))
  }
})
</script>

<template>
  <Toasts />
  <Layout :selected="1">
    <template v-slot:main-section>
      <button
        @click="activateCourseAddModal(parseInt(route.params.dep_id.toString()))"
        class="pf-v5-c-button pf-m-link"
        type="button"
      >
        Add Course
        <span class="pf-v5-c-button__icon pf-m-end">
          <i class="fa fa-plus-circle" aria-hidden="true"></i>
        </span>
      </button>
      <Table title="Courses">
        <template v-slot:table-body>
          <Course
            v-for="course in courseStore.courses"
            :course="course"
            @activate-edit-modal="activateCourseEditModal"
            @activate-remove-modal="activateCourseRemoveModal"
          >
            <td class="pf-v5-c-table__td" role="cell">
              {{
                depStore.departments.find((dep) => dep.dep_id == course.dep_id)?.dep_display_name
              }}
            </td>
          </Course>
        </template>
      </Table>
      <OnClickOutside @trigger="modalStore.setDisplay(false)">
        <Modal
          v-if="modalStore.displayModal"
          :content="modal"
          @hide="modalStore.setDisplay(false)"
          @create-course="createCourse"
          @update-course="updateCourse"
          @delete-course="deleteCourse"
        />
      </OnClickOutside>
    </template>
  </Layout>
</template>

<style scoped></style>
