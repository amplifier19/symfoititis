<script setup lang="ts">
import { useCourseStore } from '../stores/courses'
import { type Course } from '@symfoititis-frontend-monorepo/interfaces'

const props = defineProps<{ course: Course }>()

const courseStore = useCourseStore()
</script>

<template>
  <tr class="pf-v5-c-table__tr">
    <td class="pf-v5-c-table__td" role="cell" data-label="Course Id">
      {{ props.course.c_id }}
    </td>
    <td class="pf-v5-c-table__td" role="cell" data-label="Course Id">
      {{ props.course.c_display_name }}
    </td>
    <td class="pf-v5-c-table__td" role="cell" data-label="Semester">
      {{ props.course.semester }}
    </td>
    <td
      v-if="props.course.description != null"
      class="pf-v5-c-table__td"
      role="cell"
      data-label="Description"
    >
      {{ props.course.description.slice(0, 20) }}...
    </td>
    <td v-else></td>
    <slot></slot>
    <td class="pf-v5-c-table__td" role="cell" data-label="Pull requests">
      <RouterLink
        @click="courseStore.current_course = props.course"
        :to="{ name: 'notes', params: { c_id: props.course.c_id } }"
        class="pf-v5-c-button pf-m-inline pf-m-link pf-m-small"
        type="button"
        >Notes</RouterLink
      >
    </td>
    <td class="pf-v5-c-table__td pf-v5-c-table__action" role="cell">
      <div class="pf-v5-c-overflow-menu__group pf-m-button-group">
        <div class="pf-v5-c-overflow-menu__item">
          <button
            @click="$emit('activate-remove-modal', props.course)"
            class="pf-v5-c-button pf-m-primary"
            type="button"
          >
            <i class="fa fa-trash"></i>
          </button>
        </div>
        <div class="pf-v5-c-overflow-menu__item">
          <button
            class="pf-v5-c-button pf-m-secondary"
            @click="$emit('activate-edit-modal', props.course)"
            type="button"
          >
            Edit
          </button>
        </div>
      </div>
    </td>
  </tr>
</template>

<style scoped>
.pf-v5-c-table__tr {
  width: 100%;
}

.pf-v5-c-table__td {
  padding: 1rem;
}

.fa-trash {
  color: var(--pf-v5-global--danger-color--100);
}

.pf-v5-c-button {
  margin-right: 1rem;
}

.pf-m-primary {
  background-color: white;
}
</style>
