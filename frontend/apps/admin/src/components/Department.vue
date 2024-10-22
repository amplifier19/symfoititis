<script setup lang="ts">
import type { Department } from '@symfoititis-frontend-monorepo/interfaces'
import { useDepStore } from '../stores/departments'

const props = defineProps<{ department: Department }>()

const depStore = useDepStore()
</script>

<template>
  <tr class="pf-v5-c-table__tr">
    <td class="pf-v5-c-table__td" role="cell" data-label="Repository name">
      {{ props.department.dep_id }}
    </td>
    <td class="pf-v5-c-table__td" role="cell" data-label="Branches">
      {{ props.department.dep_display_name }}
    </td>
    <td class="pf-v5-c-table__td" role="cell" data-label="Pull requests">
      {{ props.department.dep_alt_name }}
    </td>
    <slot></slot>
    <td class="pf-v5-c-table__td" role="cell" data-label="Pull requests">
      <RouterLink
        :to="{ name: 'courses', params: { dep_id: props.department.dep_id } }"
        class="pf-v5-c-button pf-m-inline pf-m-link pf-m-small"
        type="button"
        >Courses</RouterLink
      >
    </td>
    <td class="pf-v5-c-table__td pf-v5-c-table__action" role="cell">
      <div class="pf-v5-c-overflow-menu__group pf-m-button-group">
        <div class="pf-v5-c-overflow-menu__item">
          <button
            @click="$emit('activate-remove-modal', props.department)"
            class="pf-v5-c-button pf-m-primary"
            type="button"
          >
            <i class="fa fa-trash"></i>
          </button>
        </div>
        <div class="pf-v5-c-overflow-menu__item">
          <button
            class="pf-v5-c-button pf-m-secondary"
            @click="$emit('activate-edit-modal', props.department)"
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
