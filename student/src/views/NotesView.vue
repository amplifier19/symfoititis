<script setup lang="ts">
import { ref, onMounted, onBeforeUpdate, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import type { Course } from '../interfaces/Course'

import Page from '../components/Page.vue'
import Masterhead from '../components/Masterhead.vue'
import History from '../components/History.vue'
import NotesHeader from '../components/NotesHeader.vue'

import { useCourseStore } from '../stores/courses'
import { useNoteStore } from '../stores/notes'
import { useErrorStore } from '../stores/errors'

import { useHistory } from '../composables/history'
import { useFetch } from '../composables/fetchService'

const route = useRoute()
const router = useRouter()

const documents_url = import.meta.env.VITE_DOCUMENTS_API_URL

const c_id = ref<number>(parseInt(route.params.c_id as string))
const course = ref<Course>({
  c_id: -100,
  dep_id: -100,
  semester: -100,
  c_display_name: 'default',
  description: ''
})

const courseStore = useCourseStore()
const noteStore = useNoteStore()
const errorStore = useErrorStore()

const { history, getHistoryFromStorage, addCourseToStorage, deleteCourseFromStorage } = useHistory()
const { getCourses, getNotes } = useFetch()

const handleDelete = (index: number) => {
  const cid = deleteCourseFromStorage(index)
  if (!c_id || c_id == null) {
    return
  }
  history.value = getHistoryFromStorage()
  if (cid == c_id.value) {
    router.push({ name: 'courses' })
  }
}

const saveCourse = () => {
  course.value = courseStore.courses.find(
    (c) => c.c_id == parseInt(route.params.c_id as string)
  ) || { c_id: -100, dep_id: -100, semester: -100, c_display_name: 'default' }
  if (course.value.c_id > 0) {
    addCourseToStorage(course.value)
    history.value = getHistoryFromStorage()
  } else {
    errorStore.addError('Course not Found')
  }
}

onMounted(async () => {
  await getCourses()
  saveCourse()
  await getNotes(route.params.c_id as string)
})

onBeforeUpdate(() => {
  c_id.value = parseInt(route.params.c_id as string)
})

watch(c_id, async (newCid, oldCid) => {
  if (newCid != oldCid) {
    saveCourse()
    await getNotes(route.params.c_id as string)
  }
})
</script>

<template>
  <Page>
    <template v-slot:header>
      <Masterhead :selected="0" />
      <History :cid="c_id" :history="history" @delete-course="handleDelete" />
    </template>
    <template v-slot:main>
      <NotesHeader :course="course" />
      <div class="details-container">
        <section class="description-container">
          <h3 class="title" id="description-title">Περιγραφή:</h3>
          <p v-if="!!course?.description" class="description">{{ course.description }}</p>
        </section>
        <ul class="notes-list" id="theory-list">
          <li class="title">Θεωρία</li>
          <li class="notes" v-for="note in noteStore.notes.filter((el) => el.type === `theory`)">
            <a
              class="note-link"
              :href="`${documents_url}/${c_id}/${note.note_filename}`"
              target="_blank"
              >{{ note.note_display_name }}</a
            >
          </li>
        </ul>
        <ul class="notes-list" id="lab-list">
          <li class="title">Εργαστήριο</li>
          <li class="notes" v-for="note in noteStore.notes.filter((el) => el.type === `lab`)">
            <a
              class="note-link"
              :href="`${documents_url}/${c_id}/${note.note_filename}`"
              target="_blank"
              >{{ note.note_display_name }}</a
            >
          </li>
        </ul>
      </div>
    </template>
  </Page>
</template>

<style scoped>
.notes-list {
  margin: 0rem 2rem 4rem 2rem;
  border: none;
}

.description-container {
  padding: 1.5rem 4.5rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-bottom: 1.5px var(--orange) solid;
}

.title {
  margin: 4rem 0rem;
  font-size: 1.15rem;
  font-weight: 800;
}

#description-title {
  margin: 0rem 1rem 0rem 0rem;
}

.notes,
.description {
  cursor: pointer;
  font-size: 1rem;
  width: 30%;
  border-bottom: 1.5px var(--orange) solid;
  margin-bottom: 1rem;
  transition: all 0.4s ease;
}

.description {
  cursor: none;
  border: none;
  padding: 0rem;
  margin: 0rem;
}

.notes:hover {
  width: 100%;
}

.notes:last-child {
  margin-bottom: 0rem;
}

.note-link {
  display: block;
  padding: 0rem 1rem 0.5rem 1rem;
}

@media screen and (max-width: 1024px) {
  .description-container {
    padding: 1.3rem 3.7rem;
  }

  .notes-list {
    margin: 0rem 1.5rem 3.5rem 1.5rem;
  }

  .title {
    margin: 3rem 0rem;
    font-size: 1.1rem;
  }

  .notes {
    font-size: 0.9rem;
    width: 60%;
    margin-bottom: 0.8rem;
  }

  .note-link {
    padding: 0rem 0.8rem 0.5rem 0.8rem;
  }
}

@media screen and (max-width: 590px) {
  .description-container {
    padding: 1rem 2.7rem;
  }

  .notes-list {
    margin: 0rem 1rem 3rem 1rem;
  }

  .title {
    margin: 2rem 0rem;
    font-size: 1rem;
  }

  .notes {
    font-size: 0.9rem;
    width: 80%;
    margin-bottom: 0.5rem;
  }

  .note-link {
    padding: 0rem 0.5rem 0.5rem 0.5rem;
  }
}
</style>
