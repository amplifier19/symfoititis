<script setup lang="ts">
import type { Course } from '@symfoititis-frontend-monorepo/interfaces'

const emit = defineEmits<{
  (e:'delete-course', index: number)
}>()

const props = defineProps<{ to: string; cid: number; history: Course[] }>()
</script>

<template>
  <nav class="pf-v5-c-nav pf-m-horizontal pf-m-scrollable" aria-labe l="Global">
    <transition-group tag="ul" name="history-list" class="pf-v5-c-nav__list" role="list" appear>
      <li class="pf-v5-c-nav__item" v-for="(course, index) in props.history" :key="course.c_id"
        :class="course.c_id == props.cid ? 'current-nav-item' : ''" :data-index="index">
        <RouterLink class="history-link" :to="{ name: props.to, params: { c_id: course.c_id } }">
          {{ course.c_display_name }}
        </RouterLink>
        <i @click="emit('delete-course', index)" class="fa fa-close"></i>
      </li>
    </transition-group>
  </nav>
</template>

<style scoped>
.pf-v5-c-nav {
  position: relative;
}

.history-list-leave-to {
  opacity: 0;
  transform: scale(0.5);
}

.history-list-leave-active {
  transition: all 0.4s ease;
  position: absolute;
}

.history-list-move {
  transition: all 0.3s ease;
}

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
  border: var(--main-border); 
  border-top-right-radius: 14px; 
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
  color: var(--gray);
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

@media screen and (max-width: 1800px) {
  .pf-v5-c-nav>ul {
    font-size: 0.85rem;
  }
}

@media screen and (max-width: 1300px) {
  .pf-v5-c-nav>ul {
    font-size: 0.8rem;
  }
}
</style>
