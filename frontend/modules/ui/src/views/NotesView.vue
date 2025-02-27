<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import { type Course, type Note } from '@symfoititis-frontend-monorepo/interfaces'

import { useCoursesDataService, useNotesDataService } from '@symfoititis-frontend-monorepo/core/services';

import { useCourseStore, useNoteStore, useErrorStore } from '@symfoititis-frontend-monorepo/stores'

import { useHistory, useRecents } from '@symfoititis-frontend-monorepo/composables'

import Page from '../components/Page.vue'
import Toasts from '../components/Toasts.vue'
import History from '../components/History.vue'
import NavHeader from '../components/NavHeader.vue'
import Subheader from '../components/Subheader.vue'
import Masterhead from '../components/Masterhead.vue'

const documents_url = import.meta.env.VITE_DOCUMENTS_API_URL;

const route = useRoute();
const router = useRouter();

const { addCourseToRecents } = useRecents('notes_recent');
const { history, addCourseToHistory, removeCourseFromHistory } = useHistory('notes_history');

const noteStore = useNoteStore();
const errorStore = useErrorStore()
const courseStore = useCourseStore();

const { getNotes } = useNotesDataService()
const { getCourses } = useCoursesDataService()

const { notes } = storeToRefs(noteStore)
const { courses } = storeToRefs(courseStore)

const course = ref<Course>({
  c_id: -1, dep_id: -1, c_display_name: '', semester: -1
})
const c_id = ref<number>(parseInt(route.params.c_id as string));

const handleDelete = (index: number) => {
  const cid = removeCourseFromHistory(index);
  if (cid === c_id.value) {
    router.push({ name: 'courses' })
  }
}

const saveCourse = () => {
  const res = courses.value.find((c: Course) => c.c_id === c_id.value)
  if (!res) {
    errorStore.addError('Course not found')
    return
  }
  course.value = res
  addCourseToHistory(course.value);
  addCourseToRecents(course.value);
}

onMounted(async () => {
  await getCourses()
  await getNotes(c_id.value)
  saveCourse()
})

watch(route, async (newRoute, oldRoute) => {
  c_id.value = parseInt(route.params.c_id as string)
  await getNotes(c_id.value)
  saveCourse()
})
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="0" />
      <History to="notes" :cid="c_id" :history="history" @delete-course="handleDelete" />
    </template>

    <template v-slot:main>
      <NavHeader navigation="courses" storageItem="notes_history" :course="course" />
      <div class="notes-container">
        <transition-group tag="ul" name="notes-list" class="notes-list" id="theory-list" appear>
          <Subheader key="subheader" title="Θεωρία" transitionGroupKey="theory" />
          <li v-for="note in notes.filter((el: Note) => el.type === 'theory')" :key="note.note_id" class="notes">
            <a class="note-link" :href="`${documents_url}/${c_id}/${note.note_filename}`" target="_blank">
              {{ note.note_display_name }}
            </a>
          </li>
        </transition-group>
        <transition-group tag="ul" name="notes-list" class="notes-list" id="lab-list" appear>
          <Subheader title="Εργαστήριο" key="lab" />
          <li v-for="note in notes.filter((el: Note) => el.type === 'lab')" :key="note.note_id" class="notes">
            <a class="note-link" :href="`${documents_url}/${c_id}/${note.note_filename}`" target="_blank">
              {{ note.note_display_name }}
            </a>
          </li>
        </transition-group>
      </div>
    </template>
  </Page>
</template>

<style scoped>
.notes-list {
  margin: 6rem 3rem 0rem 3rem;
  border: none;
  list-style: none;
  position: relative;
}

.notes-list-enter-from {
  opacity: 0;
  transform: scale(0.4);
}

.notes-list-enter-to {
  opacity: 1;
  transform: scale(1);
}

.notes-list-enter-active {
  transition: all 0.3s ease;
}

.notes-list-leave-from {
  opacity: 1;
  transform: scale(1);
}

.notes-list-leave-to {
  opacity: 0;
  transform: scale(0.4);
}

.notes-list-leave-active {
  transition: all 0.3s ease;
  position: absolute;
}

.notes-list-move {
  transition: all 0.3s ease;
}

.notes {
  cursor: pointer;
  font-size: 1rem;
  width: 80%;
  border-bottom: var(--main-border);
  margin-bottom: 1rem;
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

@media screen and (max-width: 1800px) {
  .notes {
    font-size: 0.9rem;
    margin-bottom: 0.8rem;
  }

  .note-link {
    padding: 0rem 0.8rem 0.5rem 0.8rem;
  }
}

@media screen and (max-width: 1300px) {
  .notes-list {
    margin: 5rem 2rem 0 2rem;
  }

  .notes {
    font-size: 0.9rem;
    margin-bottom: 0.5rem;
  }

  .note-link {
    padding: 0rem 0.5rem 0.5rem 0.5rem;
  }
}
</style>
