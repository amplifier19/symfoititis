<script setup lang="ts">
import { computed } from 'vue'
import { Course } from '@symfoititis-frontend-monorepo/interfaces'

const props = defineProps<{
  course: Course
  link: string
  bookingId?: number
}>()

const formattedCourseName = computed(() => {
  return props.course.c_display_name
    .split(' ')
    .map((word: string) => {
      const firstLetter = word[0];
      return firstLetter === firstLetter.toUpperCase() ? firstLetter : ''
    })
   .join('')
})
</script>

<template>
  <RouterLink :to="{ name: props.link, params: { c_id: course.c_id, b_id: props.bookingId } }">
    <div class="card-container">
      <slot name="card-header"></slot>
      <span class="course-name">
        {{ formattedCourseName  }}
      </span>
      <slot name="card-footer"></slot>
    </div>
  </RouterLink>
  <span class="course-description">
    <h2>{{ props.course.c_display_name }}</h2>
  </span>
</template>

<style scoped>
.card-container:hover {
  background-image: url('/svg/hover_course_card.svg');
}

.card-container{
  height: clamp(7.5rem, 6vw, 8.5rem);
  width: clamp(7.5rem, 6vw, 8.5rem);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  background-color: var(--white);
  background-image: url('/svg/course-icon.svg');
  background-repeat: no-repeat;
  color: var(--orange); 

}

.course-name {
  color: var(--orange);
  font-size: 1.5rem;
  flex-grow: 0;
}

.course-description {
  width: 8.5rem;
  display: flex;
  justify-content: center;
  text-align: center;
  margin: 1rem auto;
  font-size: 0.95rem;
}

.course-description > h2 {
  font-weight: 500;
}

@media screen and (max-width: 1800px) {
  .card-container {
    height: clamp(6.5rem, 7vw, 8.5rem);
    width: clamp(6.5rem, 7vw, 8.5rem);
  }

  .course-description {
    width: 6.5rem;
    margin: 0.8rem auto 1rem auto;
  }

  .course-name {
    font-size: 1.1rem;
  }

  .course-description {
    font-size: 0.85rem;
  }
}

@media screen and (max-width: 1300px) {
  .card-container {
    height: clamp(5.5rem, 7vw, 8.5rem);
    width: clamp(5.5rem, 7vw, 8.5rem);
  }

  .course-description {
    width: 5.5rem;
    margin: 0.6rem auto 1rem auto;
  }

  .course-description {
    font-size: 0.8rem;
  }
}

@media screen and (max-width: 800px) {
  .pf-v5-c-card {
    min-width: 5rem;
    min-height: 5rem;
  }

  .course-description {
    width: 5rem;
    margin: 0.6rem auto 1rem auto;
  }

  .course-description {
    font-size: 0.8rem;
  }
}
</style>
