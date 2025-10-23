<script setup lang="ts">
import { onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useCourseStore } from "@symfoititis-frontend-monorepo/stores";
import {
  useRecents,
  useHistory,
} from "@symfoititis-frontend-monorepo/composables";
import { useCoursesDataService } from "@symfoititis-frontend-monorepo/core/services";

import Page from "../components/Page.vue";
import Toasts from "../components/Toasts.vue";
import History from "../components/History.vue";
import Recents from "../components/Recents.vue";
import Gallery from "../components/Gallery.vue";
import Masterhead from "../components/Masterhead.vue";
import SearchHeader from "../components/SearchHeader.vue";

const { getCourses } = useCoursesDataService();

const courseStore = useCourseStore();
const { search, uniqueSemesters, filteredCourses } = storeToRefs(courseStore);

const { recents } = useRecents("notes_recent");
const { history, removeCourseFromHistory } = useHistory("notes_history");

const clearSearch = () => {
  search.value = "";
};

onMounted(async () => {
  await getCourses();
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
        @delete-course="removeCourseFromHistory"
      />
    </template>

    <template v-slot:subheader>
      <SearchHeader
        title="Σημειώσεις"
        :display-search="true"
        :search="search"
        @clear-search="clearSearch"
      >
        <input
          v-model="search"
          type="text"
          class="search-input"
          placeholder="  Αναζήτησε μάθημα"
        />
      </SearchHeader>
    </template>

    <template v-slot:main>
      <Recents
        v-if="!search && recents.length > 0"
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
  font-size: 16px;
}
</style>
