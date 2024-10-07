<script setup lang="ts">
import Masterhead from './Masterhead.vue'
import Sidebar from './Sidebar.vue'

import { useSidebarStore } from '../stores/sidebar'

const props = defineProps<{ selected: number }>()

const store = useSidebarStore()
</script>

<template>
  <div class="pf-v5-c-page">
    <Masterhead @toogle-sidebar="store?.toogleState" />
    <Sidebar :selected="props.selected" />

    <main class="pf-v5-c-page__main" tabindex="-1">
      <section class="pf-v5-c-page__main-section">
        <slot name="main-section"></slot>
      </section>
    </main>
  </div>
</template>

<style scoped>
.pf-v5-c-page__main-section {
  position: relative;
}
</style>
