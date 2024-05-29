<script setup lang="ts">
import { useSidebarStore } from '../stores/sidebar'

const props = defineProps(['selected'])

const store = useSidebarStore()

const navItems = [
  { name: 'Universities', to: 'universities', classes: ['pf-v5-c-nav__link'] },
  { name: 'Departments', to: 'departments', classes: ['pf-v5-c-nav__link'] }
]

if (props?.selected >= 0 && props?.selected < navItems.length) {
  navItems[props.selected].classes.push('pf-m-current')
}
</script>

<template>
  <div ref="sidebar" :class="store?.classes">
    <nav class="pf-v5-c-nav" aria-label="Global">
      <ul class="pf-v5-c-nav__list" role="list">
        <li v-for="item in navItems" class="pf-v5-c-nav__item">
          <RouterLink :to="{ name: item.to }" :class="item.classes">{{ item.name }}</RouterLink>
        </li>
      </ul>
    </nav>
  </div>
</template>

<style scoped></style>
