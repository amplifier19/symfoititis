<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRecents, useFetch, useHistory } from '@symfoititis-frontend-monorepo/composables'
import { useCourseStore } from  '@symfoititis-frontend-monorepo/stores'
import { Course } from  '@symfoititis-frontend-monorepo/interfaces'
import Toasts from '../components/Toasts.vue'
import Page from '../components/Page.vue'
import Masterhead from '../components/Masterhead.vue'
import History from '../components/History.vue'
import SearchHeader from '../components/SearchHeader.vue'
import Recents from '../components/Recents.vue'
import Gallery from '../components/Gallery.vue'

const courseStore = useCourseStore();
const { getCourses, getUserInfo } = useFetch();
const uniqueSemesters = ref<number[]>([]);
const search = ref<string>('');

const { recents, getRecFromStorage } = useRecents('notes_recent');
recents.value = getRecFromStorage();

const { history, getHistoryFromStorage, deleteCourseFromStorage } = useHistory('notes_history');
history.value = getHistoryFromStorage();

const filteredCourses = computed(() => {
  return courseStore.courses.filter((course: Course) =>
    course.c_display_name.toLowerCase().includes(search.value.toLowerCase())
  );
});

const clearSearch = () => {
  search.value = '';
};

const handleDelete = (index: number) => {
  deleteCourseFromStorage(index);
  history.value = getHistoryFromStorage();
};

const initSemesters = () => {
  uniqueSemesters.value = [...new Set<number>(courseStore.courses.map((c: Course) => c.semester))];
};

onMounted(async () => {
  getUserInfo();
  await getCourses();
  initSemesters();
});
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="0" />
      <History
        to="notes"
        :cid="-100"
        :history="history"
        @delete-course="handleDelete"
      />
    </template>
    <template v-slot:main>
      <SearchHeader
        title="Σημειώσεις"
        :search="search"
        @clear-search="clearSearch"
      >
        <input
          v-model="search"
          type="text"
          class="regular-text search-input"
          placeholder="  Αναζήτησε ένα μάθημα"
        />
      </SearchHeader>

      <Recents
        v-if="search.length === 0 && recents.length > 0"
        :recentCourses="recents"
        link="notes"
      />

      <Gallery
        :uniqueSemesters="uniqueSemesters"
        :filteredCourses="filteredCourses"
        link="notes"
      />
    </template>
  </Page>
</template>
<style scoped>
.search-input {
  width: 100%;
  box-sizing: unset;
  border: none;
  outline: none;
  background-color: white;
  margin: 0px;
}

@media screen and (max-width: 590px) {
  .search-input {
    font-size: 0.9rem;
  }
}
</style>
