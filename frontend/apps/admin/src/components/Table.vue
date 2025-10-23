<script setup lang="ts">
const props = defineProps(["title"]);
</script>

<template>
  <table
    class="pf-v5-c-table"
    aria-label="This is a simple table example"
    id="table-with-expanded-overflow-menu"
  >
    <thead class="pf-v5-c-table__thead">
      <tr class="pf-v5-c-table__tr pf-m-striped">
        <th class="pf-v5-c-table__th" colspan="7" scope="col">
          {{ props.title }}
        </th>
      </tr>
    </thead>

    <tbody class="pf-v5-c-table__tbody">
      <slot name="table-body"></slot>
    </tbody>
  </table>
</template>

<style scoped>
.pf-v5-c-table {
  width: 85%;
  margin: 0rem auto 3rem auto;
  border: 1.5px var(--pf-v5-global--BorderColor--100) solid;
}
</style>
