<script setup lang="ts">
const props = defineProps(["university"]);
</script>

<template>
  <div class="pf-v5-c-overflow-menu__content">
    <div class="pf-v5-c-overflow-menu__item">
      <button
        type="button"
        @click="$emit('toogle-expanded')"
        class="pf-v5-c-expandable-section__toggle"
        aria-expanded="true"
      >
        <span class="pf-v5-c-expandable-section__toggle-icon">
          <i class="fa fa-angle-right" aria-hidden="true"></i>
        </span>
        <span id="uni_id">
          {{ props.university.uni_id }}
        </span>
      </button>
    </div>
    <div class="pf-v5-c-overflow-menu__item">
      {{ props.university.uni_display_name }}
    </div>
    <div class="pf-v5-c-overflow-menu__item">
      {{ props.university.uni_alt_name }}
    </div>

    <div class="pf-v5-c-overflow-menu__group pf-m-button-group">
      <div class="pf-v5-c-overflow-menu__item">
        <button
          @click="$emit('activate-remove-modal')"
          class="pf-v5-c-button pf-m-primary"
          type="button"
        >
          <i class="fa fa-trash"></i>
        </button>
      </div>
      <div class="pf-v5-c-overflow-menu__item">
        <button
          class="pf-v5-c-button pf-m-secondary"
          @click="$emit('activate-edit-modal')"
          type="button"
        >
          Edit
        </button>
      </div>
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
  position: relative;
  justify-content: space-around;
  width: 100%;
}

#uni_id {
  margin-left: 1.5rem;
}

.pf-v5-c-expandable-section__content {
  width: 100%;
  max-width: none;
  padding-left: 10%;
}

.pf-m-primary {
  background-color: white;
}

.fa-trash {
  color: var(--pf-v5-global--danger-color--100);
}
</style>
