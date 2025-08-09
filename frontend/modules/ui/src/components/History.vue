<script setup lang="ts">
import type { Course } from '@symfoititis-frontend-monorepo/interfaces'

const emit = defineEmits<{
  (e: 'delete-course', c_id: number): void
}>()

const props = defineProps<{
  to: string
  cid: number
  history: Course[]
}>()
</script>

<template>
  <section class="history-wrapper">
    <transition-group tag="ul" name="history-list" class="history-container" appear>
      <li class="history-item" v-for="(course, index) in props.history" 
        :key="course.c_id"
        :class="course.c_id == props.cid ? 'current-history-item' : ''" 
        :data-index="index">

        <RouterLink class="history-link" :to="{ name: props.to, params: { c_id: course.c_id } }">
          {{ course.c_display_name }}
        </RouterLink>

        <i @click="emit('delete-course', course.c_id!)" class="fa fa-close"></i>
      </li>
    </transition-group>
  </section>
</template>

<style scoped>
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

.history-wrapper {
  display: flex;
  align-items: center;
  position: relative;
  background-color: var(--white);
  min-height: clamp(50px, 3vw, 60px);
  overflow-x: auto;
}

.history-container {
  display: flex;
  flex-direction: row;
  font-size: .95rem;
}

.history-item {
  margin: 0rem 0.4rem;
  padding: 0.5rem 0.9rem;
  border: var(--main-border);
  border-top-right-radius: 14px;
  white-space: nowrap;
}

.current-history-item {
  background-color: var(--orange);
}

.current-history-item a,
.current-history-item i {
  color: var(--white) !important;
}

.history-link {
  color: var(--gray);
}

.fa-close {
  cursor: pointer;
  color: var(--orange);
  padding-left: .9rem;
}

@media screen and (max-width: 1800px) {
  .history-container {
    font-size: 0.85rem;
  }
}

@media screen and (max-width: 1300px) {
  .history-container {
    font-size: 0.8rem;
  }

  .history-item {
    padding: 0.3rem 0.7rem;
  }
}
</style>
