<script setup lang="ts">
import type { Course } from '../interfaces/Course'

import Card from './Card.vue'

const props = defineProps<{ favoriteCourses: Course[] }>()
</script>

<template>
  <div class="gallery-container">
    <h2>Αγαπημένα</h2>
    <div class="gallery">
      <div v-for="course in props.favoriteCourses" :key="course.semester" class="card-container">
        <Card :course="course" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.gallery-container {
  margin: 3rem;
}

.gallery-container > h2 {
  font-size: 1.15rem;
  font-weight: bolder;
  margin-bottom: 1.5rem;
  height: max-content;
}

.gallery {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 8%;
}

.card-container {
  width: max-content;
  height: auto;
}

@media screen and (max-width: 1024px) {
  .gallery-container {
    margin: 2rem;
  }

  .gallery-container > h2 {
    font-size: 1.1rem;
  }
}

@media screen and (max-width: 590px) {
  .gallery-container {
    margin: 1rem;
  }

  .gallery-container > h2 {
    font-size: 1rem;
  }
}
</style>
