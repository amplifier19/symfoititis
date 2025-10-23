<script setup lang="ts">
import { storeToRefs } from "pinia";
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

import {
  type Course,
  type Note as NoteInterface,
} from "@symfoititis-frontend-monorepo/interfaces";

import {
  useCoursesDataService,
  useNotesDataService,
} from "@symfoititis-frontend-monorepo/core/services";

import {
  useCourseStore,
  useNoteStore,
  useErrorStore,
} from "@symfoititis-frontend-monorepo/stores";

import {
  useHistory,
  useRecents,
} from "@symfoititis-frontend-monorepo/composables";

import Page from "../components/Page.vue";
import Note from "../components/Note.vue";
import Toasts from "../components/Toasts.vue";
import History from "../components/History.vue";
import NavHeader from "../components/NavHeader.vue";
import Subheader from "../components/Subheader.vue";
import Masterhead from "../components/Masterhead.vue";

const route = useRoute();
const router = useRouter();

const { addCourseToRecents } = useRecents("notes_recent");
const { history, addCourseToHistory, removeCourseFromHistory } =
  useHistory("notes_history");

const noteStore = useNoteStore();
const errorStore = useErrorStore();
const courseStore = useCourseStore();

const { getNotes } = useNotesDataService();
const { getCourses } = useCoursesDataService();

const { notes } = storeToRefs(noteStore);
const { courses } = storeToRefs(courseStore);

const course = ref<Course>({
  c_id: -1,
  dep_id: -1,
  c_display_name: "",
  semester: -1,
});
const c_id = ref<number>(parseInt(route.params.c_id as string));

const handleDelete = (courseId: number) => {
  const cid = removeCourseFromHistory(courseId);
  if (cid === c_id.value) {
    router.push({ name: "courses" });
  }
};

const saveCourse = () => {
  const res = courses.value.find((c: Course) => c.c_id === c_id.value);
  if (!res) {
    errorStore.addError("Course not found");
    return;
  }
  course.value = res;
  addCourseToHistory(course.value);
  addCourseToRecents(course.value);
};

onMounted(async () => {
  await getCourses();
  await getNotes(c_id.value);
  saveCourse();
});

watch(route, async (newRoute, oldRoute) => {
  c_id.value = parseInt(route.params.c_id as string);
  await getNotes(c_id.value);
  saveCourse();
});
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="0" />
      <History
        to="notes"
        :cid="c_id"
        :history="history"
        @delete-course="handleDelete"
      />
    </template>

    <template v-slot:main>
      <NavHeader
        navigation="courses"
        storageItem="notes_history"
        :course="course"
      />
      <section class="notes-wrapper wrapper">
        <div class="notes-container content-width">
          <transition-group
            tag="ul"
            name="notes-list"
            class="notes-list"
            id="theory-list"
            appear
          >
            <Subheader key="subheader" title="Θεωρία" />
            <Note
              v-for="note in notes.filter(
                (el: NoteInterface) => el.type === 'theory',
              )"
              :key="note.note_id"
              :note="note"
            />
          </transition-group>
          <transition-group
            tag="ul"
            name="notes-list"
            class="notes-list"
            id="lab-list"
            appear
          >
            <Subheader title="Εργαστήριο" key="lab" />
            <Note
              v-for="note in notes.filter(
                (el: NoteInterface) => el.type === 'lab',
              )"
              :key="note.note_id"
              :note="note"
            />
          </transition-group>
        </div>
      </section>
    </template>
  </Page>
</template>

<style scoped>
.notes-list {
  margin-bottom: clamp(50px, 5vw, 75px);
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

.notes:last-child {
  margin-bottom: 0rem;
}
</style>
