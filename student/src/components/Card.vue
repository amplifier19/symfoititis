<script setup lang="ts">
import type { Course } from '../interfaces/Course'

const props = defineProps<{ course: Course }>()
</script>

<template>
  <RouterLink :to="{ name: 'course', params: { c_id: course.c_id } }">
    <div class="pf-v5-c-card pf-m-hoverable-raised">
      <span class="pf-v5-c-card__body">
        {{
          course.c_display_name
            .split(' ')
            .map((word: string) => {
              const firstLetter = word[0]
              return firstLetter === firstLetter.toUpperCase() ? firstLetter : ''
            })
            .join('')
        }}
      </span>
    </div>
  </RouterLink>
  <span class="pf-v5-c-card__title">
    <h2>{{ course.c_display_name }}</h2>
  </span>
</template>

<style scoped>
.pf-v5-c-card {
  background-color: var(--white);
  box-shadow: none;
  background-image: url(../assets/svg/course-icon.svg);
  background-repeat: no-repeat;
  color: var(--orange) !important;
  width: 9rem;
  height: 9rem;
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
  width: 9rem;
  display: flex;
  justify-content: center;
  text-align: center;
  margin: 1rem 0rem;
  font-size: 0.95rem;
}

.pf-v5-c-card__title > h2 {
  font-weight: 500;
}

@media screen and (max-width: 1024px) {
  .pf-v5-c-card {
    width: 7.7rem;
    height: 7.7rem;
  }

  .pf-v5-c-card__title {
    width: 7.7rem;
    margin: 0.8rem 0rem 1rem 0rem;
  }

  .pf-v5-c-card {
    height: 7.7rem;
  }

  .pf-v5-c-card__body {
    font-size: 1.3rem;
  }

  .pf-v5-c-card__title {
    font-size: 0.85rem;
  }
}

@media screen and (max-width: 590px) {
  .pf-v5-c-card {
    width: 5.5rem;
    height: 5.5rem;
  }

  .pf-v5-c-card__title {
    width: 5.5rem;
    margin: 0.6rem 0rem 1rem 0rem;
  }

  .pf-v5-c-card {
    height: 5.5rem;
  }

  .pf-v5-c-card__body {
    font-size: 1.1rem;
  }

  .pf-v5-c-card__title {
    font-size: 0.8rem;
  }
}
</style>
