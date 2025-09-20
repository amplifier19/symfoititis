<script setup lang="ts">
import { useRouter } from 'vue-router'

import type { Course } from '@symfoititis-frontend-monorepo/interfaces'

import { useHistory } from '@symfoititis-frontend-monorepo/composables'

const props = defineProps<{
  course: Course
  navigation: string
  storageItem: string
}>()

const router = useRouter()

const alphabet = 'ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ'

const { removeCourseFromHistory } = useHistory(props.storageItem)

const handleDelete = () => {
  removeCourseFromHistory(props.course.c_id!)
  router.push({ name: props.navigation })
}
</script>

<template>
  <section class="nav-wrapper wrapper" id="navbar">
    <nav class="nav-container content-width">
      <div class="title-container">
        <RouterLink :to="{name: props.navigation}" class="icon-container">
          <img class="title-icon" src="/svg/arrow_white.svg" alt="left-arrow" />
        </RouterLink>
        <span class="title lg-font md-fw" id="course-title">{{ props.course.c_display_name }}</span>
      </div>

      <div class="title-container">
        <span class="title lg-font lt-fw" id="semester-title">{{ alphabet[props.course.semester - 0] }}' Εξάμηνο</span>
        <div @click="handleDelete" class="icon-container">
          <img class="title-icon" src="/svg/close_white.svg" alt="close-icon" />
        </div>
      </div>
    </nav>
  </section>
</template>

<style scoped>
.nav-wrapper {
  background-color: var(--white);
  min-height: clamp(80px, 8vw, 220px);
}

.nav-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border: var(--main-border);
  align-items: center;
}

.title-container {
  height: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.title {
  color: var(--orange);
}

#course-title {
  min-width: 0;
  flex: 1 1 auto;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding-left: 2rem;
}

#semester-title {
  padding-right: 2rem;
}

.icon-container {
  background-color: var(--orange);
  flex: 0 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0.5rem;
  cursor: pointer;
}

.title-icon {
  flex: 0 0 auto;
  object-fit: fill;
  height: 1.4rem;
  width: 1.4rem;
}

@media screen and (max-width: 1800px) {
  .title-container {
    max-width: calc(100% - 160px);
  }

  .title-icon {
    height: 1.2rem;
    width: 1.2rem;
  }

  #course-title {
    padding-left: 1.5rem;
  }

  #semester-title {
    padding-right: 1.5rem;
  }
}

@media screen and (max-width: 1300px) {
  .title-icon {
    height: 1rem;
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