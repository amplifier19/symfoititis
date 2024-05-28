<script setup lang="ts">
import type { Course } from '../interfaces/Course'

defineEmits(['delete-course'])

const props = defineProps<{ cid: number; history: Course[] }>()
</script>

<template>
  <nav class="pf-v5-c-nav pf-m-horizontal pf-m-scrollable" aria-labe l="Global">
    <ul class="pf-v5-c-nav__list" role="list">
      <li
        class="pf-v5-c-nav__item"
        v-for="(course, index) in props.history"
        :key="course.c_id"
        :class="course.c_id == props.cid ? 'current-nav-item' : ''"
      >
        <RouterLink class="history-link" :to="{ name: 'course', params: { c_id: course.c_id } }">
          {{ course.c_display_name }}
        </RouterLink>
        <i @click="$emit('delete-course', index)" class="fa fa-close"></i>
      </li>
    </ul>
  </nav>
</template>

<style scoped>
.pf-v5-c-nav {
  margin-top: 0.4rem;
  background-color: var(--white);
}

.pf-v5-c-nav__list {
  scroll-snap-type: none !important;
  scrollbar-width: auto !important;
  font-size: 0.95rem;
}

.pf-v5-c-nav__item {
  white-space: nowrap;
  margin: 0rem 0.4rem;
  border: var(--orange) 0.12rem solid;
  border-top-right-radius: 0.5rem;
}

.current-nav-item {
  background-color: var(--orange);
}

.current-nav-item a,
.current-nav-item i {
  color: var(--white) !important;
}

.history-link {
  padding: 0.5rem 0.9rem;
}

.fa-angle-left,
.fa-angle-right {
  color: black;
}

.fa-close {
  cursor: pointer;
  color: var(--orange);
  padding: 0.5rem 0.5rem 0rem 0rem;
}

.pf-v5-c-nav__scroll-button {
  padding: 0px !important;
}

.pf-v5-c-nav__scroll-button::before {
  border: none;
}

.pf-v5-c-nav__list {
  padding: 0.5rem 0;
}

@media screen and (max-width: 1024px) {
  .pf-v5-c-nav > ul {
    font-size: 0.85rem;
  }
}

@media screen and (max-width: 550px) {
  .pf-v5-c-nav > ul {
    font-size: 0.8rem;
  }
}
</style>
