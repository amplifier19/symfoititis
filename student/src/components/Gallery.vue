<script setup lang="ts">
import type { Course } from '../interfaces/Course'
import Card from './Card.vue'

const props = defineProps<{ uniqueSemesters: number[]; filteredCourses: Course[] }>()

const alphabet = 'ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ'

const semCourses = (semester: number) => {
  return props.filteredCourses.filter((course: Course) => course.semester == semester)
}
</script>

<template>
  <div v-for="semester in uniqueSemesters" :key="semester" class="gallery-container">
    <h2>{{ alphabet[semester - 1] }}' Εξάμηνο</h2>
    <div class="gallery">
      <div v-for="course in semCourses(semester)" :key="course.semester" class="card-container">
        <Card v-if="course.semester === semester" :course="course" />
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
