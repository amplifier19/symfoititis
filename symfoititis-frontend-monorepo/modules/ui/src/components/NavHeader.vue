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

const { getHistoryFromStorage, deleteCourseFromStorage } = useHistory(props.storageItem)

const handleNavigation = () => {
  router.push({ name: props.navigation })
}

const handleDelete = () => {
  const history = getHistoryFromStorage()
  const idx = history.findIndex((c: Course) => c.c_id == props.course.c_id)
  deleteCourseFromStorage(idx)
  router.push({ name: props.navigation })
}
</script>

<template>
  <div class="header-container">
    <div class="title-container">
      <div @click="handleNavigation" class="icon-container">
        <img class="title-icon" src="/svg/arrow_white.svg" alt="left-arrow" />
      </div>
      <span class="title" id="course-title">{{ props.course.c_display_name }}</span>
    </div>

    <div class="title-container">
      <span class="title" id="semester-title">{{ alphabet[props.course.semester - 1] }}' Εξάμηνο</span>
      <div @click="handleDelete" class="icon-container">
        <img class="title-icon" src="/svg/close_white.svg" alt="close-icon" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.header-container {
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
  font-size: 1.15rem;
}

#course-title {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-weight: 800;
  padding-left: 2rem;
}

#semester-title {
  font-family: 'Geologica-Light';
  padding-right: 2rem;
}

.icon-container {
  background-color: var(--orange);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0.5rem;
  cursor: pointer;
}

.title-icon {
  height: 1.4rem;
  width: 1.4rem;
}

@media screen and (max-width: 1800px) {
  .title-container {
    max-width: calc(100% - 160px);
  }

  .title {
    font-size: 1rem;
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
  .title-container {
    max-width: calc(100% - 130px);
  }

  .title {
    font-size: 0.9rem;
  }

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
