<script setup lang="ts">
const props = defineProps<{ c_id: number; filename: string }>()
</script>

<template>
  <tr class="pf-v5-c-table__tr">
    <td class="pf-v5-c-table__td" role="cell" data-label="Course Id">
      {{ filename }}
    </td>
    <td class="pf-v5-c-table__td pf-v5-c-table__action" role="cell">
      <div class="pf-v5-c-overflow-menu__group pf-m-button-group">
        <div class="pf-v5-c-overflow-menu__item">
          <button
            @click="$emit('activate-remove-modal', { c_id: props.c_id, filename: props.filename })"
            class="pf-v5-c-button pf-m-primary"
            type="button"
          >
            <i class="fa fa-trash"></i>
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
