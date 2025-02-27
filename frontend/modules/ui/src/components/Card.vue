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
    <div class="pf-v5-c-card pf-m-hoverable-raised">
      <slot name="card-header"></slot>
      <span class="pf-v5-c-card__body">
        {{ formattedCourseName  }}
      </span>
      <slot name="card-footer"></slot>
    </div>
  </RouterLink>
  <span class="pf-v5-c-card__title">
    <h2>{{ props.course.c_display_name }}</h2>
  </span>
</template>

<style scoped>
.pf-v5-c-card{
    position: relative;
    /* justify-content: space-around !important;*/
}
.pf-v5-c-card__body{
    padding: 0rem;
}
.pf-v5-c-card.pf-m-hoverable-raised::before,
.pf-v5-c-card.pf-m-selectable-raised::before,
.pf-v5-c-card.pf-m-non-selectable-raised::before {
  background-color: transparent;
}

.pf-v5-c-card.pf-m-hoverable-raised:hover {
  background-image: url('/svg/hover_course_card.svg');
}

.card-link {
  display: block;
  width: max-content;
  height: auto;
  margin: 0 auto;
}

.pf-v5-c-card {
  background-color: var(--white);
  box-shadow: none;
  background-image: url('/svg/course-icon.svg');
  background-repeat: no-repeat;
  color: var(--orange) !important;
  width: 6vw;
  height: 6vw;
  max-width: 8.5rem;
  max-height: 8.5rem;
  min-width: 7.5rem;
  min-height: 7.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: auto;
}

.pf-v5-c-card__body {
  color: var(--orange);
  font-size: 1.5rem;
  flex-grow: 0;
}

.pf-v5-c-card__title {
  width: 8.5rem;
  display: flex;
  justify-content: center;
  text-align: center;
  margin: 1rem auto;
  font-size: 0.95rem;
}

.pf-v5-c-card__title > h2 {
  font-weight: 500;
}

@media screen and (max-width: 1800px) {
  .pf-v5-c-card {
    width: 7vw;
    height: 7vw;
    min-width: 6.5rem;
    min-height: 6.5rem;
  }

  .pf-v5-c-card__title {
    width: 6.5rem;
    margin: 0.8rem auto 1rem auto;
  }

  .pf-v5-c-card__body {
    font-size: 1.1rem;
  }

  .pf-v5-c-card__title {
    font-size: 0.85rem;
  }
}

@media screen and (max-width: 1300px) {
  .pf-v5-c-card {
    min-width: 5.5rem;
    min-height: 5.5rem;
  }

  .pf-v5-c-card__title {
    width: 5.5rem;
    margin: 0.6rem auto 1rem auto;
  }

  .pf-v5-c-card__title {
    font-size: 0.8rem;
  }
}

@media screen and (max-width: 800px) {
  .pf-v5-c-card {
    min-width: 5rem;
    min-height: 5rem;
  }

  .pf-v5-c-card__title {
    width: 5rem;
    margin: 0.6rem auto 1rem auto;
  }

  .pf-v5-c-card__title {
    font-size: 0.8rem;
  }
}
</style>
