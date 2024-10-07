<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { SearchHeader, Page, Masterhead, History, Recents, Gallery, Toasts } from '@symfoititis-frontend-monorepo/ui';
import { useCourseStore } from '@symfoititis-frontend-monorepo/stores';
import { useRecents } from '@symfoititis-frontend-monorepo/composables';
import { useHistory } from '@symfoititis-frontend-monorepo/composables';
import { useFetch } from '@symfoititis-frontend-monorepo/composables';

const search = ref<string>('');
const courseStore = useCourseStore();
const { getCourses } = useFetch();
const { recents, getRecFromStorage } = useRecents('bookings_recent');
recents.value = getRecFromStorage();
const { history, getHistoryFromStorage, deleteCourseFromStorage } = useHistory('bookings_history');
history.value = getHistoryFromStorage();
const uniqueSemesters = ref<number[]>([]);

const filteredCourses = computed(() => {
  return courseStore.courses.filter(course =>
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
  uniqueSemesters.value = [...new Set(courseStore.courses.map(c => c.semester))];
};

onMounted(async () => {
  await getCourses();
  initSemesters();
});
</script>

<template>
  <Toasts/>
  <Page>
    <template v-slot:header>
      <Masterhead :selected="1" />
      <History
        to="availability"
        :cid="-100"
        :history="history"
        @deleteCourse="handleDelete"
      />
    </template>
    <template v-slot:main>
      <SearchHeader
        title="Ιδιαίτερα"
        :search="search"
        @clearSearch="clearSearch"
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
        key="0"
        link="availability"
        :recentCourses="recents"
      />
      <Gallery
        :uniqueSemesters="uniqueSemesters"
        :filteredCourses="filteredCourses"
        link="availability"
      />
    </template>
  </Page>
</template>

<style scoped></style>
