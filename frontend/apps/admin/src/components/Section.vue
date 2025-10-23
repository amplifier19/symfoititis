<script setup lang="ts">
import { computed, ref } from "vue";

import { useDepStore } from "../stores/departments.store";

import type { University as UniversityInterface } from "@symfoititis-frontend-monorepo/interfaces";

import Table from "./Table.vue";
import Department from "./Department.vue";
import University from "./University.vue";

const props = defineProps<{ university: UniversityInterface }>();

const depStore = useDepStore();

const expanded = ref(false);

const toogleExpanded = () => {
  expanded.value = !expanded.value;
};

const filtered = computed(() => {
  return depStore.departments.filter(
    (dep) => dep.uni_id === props.university.uni_id,
  );
});
</script>

<template>
  <div
    class="pf-v5-c-expandable-section pf-m-expanded pf-m-display-lg pf-m-limit-width"
  >
    <div class="pf-v5-c-overflow-menu" id="overflow-menu-simple-expanded">
      <University
        @activate-edit-modal="$emit('activate-uni-edit-modal')"
        @activate-remove-modal="$emit('activate-uni-remove-modal')"
        @toogle-expanded="toogleExpanded"
        :university="props.university"
      />
    </div>

    <div v-if="expanded" class="pf-v5-c-expandable-section__content">
      <Table title="Departments">
        <template v-slot:table-body>
          <Department
            v-for="dep in filtered"
            :department="dep"
            @activate-edit-modal="$emit('activate-dep-edit-modal', dep)"
            @activate-remove-modal="$emit('activate-dep-remove-modal', dep)"
          />
        </template>
      </Table>
    </div>
  </div>
</template>

<style scoped>
.pf-v5-c-expandable-section {
  background-color: white;
  margin: 1rem;
}

.pf-v5-c-overflow-menu {
  width: 100%;
}

.pf-v5-c-overflow-menu__content {
  justify-content: space-around;
  width: 100%;
}

#uni_id {
  margin-left: 1.5rem;
}

.pf-m-primary {
  background-color: white;
}

.fa-trash {
  color: var(--pf-v5-global--danger-color--100);
}

.pf-v5-c-expandable-section__content {
  width: 100%;
  max-width: none;
}
</style>
