<script setup lang="ts">
import { useRouter } from 'vue-router'

import type { Course } from '../interfaces/Course'

import { useHistory } from '../composables/history'

const props = defineProps<{ course: Course }>()

const router = useRouter()

const alphabet = 'ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ'

const { getHistoryFromStorage, deleteCourseFromStorage } = useHistory()

const handleNavigation = () => {
  router.push({ name: 'courses' })
}

const handleDelete = () => {
  const history = getHistoryFromStorage()
  const idx = history.findIndex((c: Course) => c.c_id == props.course.c_id)
  deleteCourseFromStorage(idx)
  router.push({ name: 'courses' })
}
</script>

<template>
  <div class="header-container">
    <div class="title-container">
      <div @click="handleNavigation" class="icon-container">
        <img class="title-icon" src="../assets/svg/left-arrow.svg" alt="left-arrow" />
      </div>
      <span class="title" id="course-title">{{ props.course.c_display_name }}</span>
    </div>

    <div class="title-container">
      <span class="title" id="semester-title"
        >{{ alphabet[props.course.semester - 1] }}' Εξάμηνο</span
      >
      <div @click="handleDelete" class="icon-container">
        <img class="title-icon" src="../assets/svg/close-icon.svg" alt="close-icon" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.header-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border-bottom: 1.5px solid var(--orange);
  align-items: center;
}

.title-container {
  height: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

.title {
  padding: 0.5rem 0rem;
  color: var(--orange);
  font-size: 1.15rem;
}

#course-title {
  font-weight: 800;
  padding-left: 2rem;
  border-left: var(--orange) 1.5px solid;
}

#semester-title {
  padding-right: 2rem;
  border-right: var(--orange) 1.5px solid;
}

.icon-container {
  height: 100%;
  width: auto;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0.5rem;
  cursor: pointer;
}

.title-icon {
  height: auto;
  width: 1.4rem;
}

@media screen and (max-width: 1024px) {
  .title {
    font-size: 1.1rem;
  }

  .title-icon {
    width: 1.2rem;
  }

  #course-title {
    padding-left: 1.5rem;
  }

  #semester-title {
    padding-right: 1.5rem;
  }
}

@media screen and (max-width: 590px) {
  .title {
    font-size: 0.9rem;
  }

  .title-icon {
    width: 1rem;
  }

  #course-title {
    padding-left: 0.6rem;
  }

  #semester-title {
    padding-right: 0.6rem;
  }
}
</style>
