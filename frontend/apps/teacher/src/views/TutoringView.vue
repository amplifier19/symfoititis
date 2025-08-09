<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { storeToRefs } from 'pinia'

import { SearchHeader, Page, Masterhead, History, Recents, Gallery, Toasts } from '@symfoititis-frontend-monorepo/ui'

import { useCourseStore } from '@symfoititis-frontend-monorepo/stores'

import { useCoursesDataService } from '@symfoititis-frontend-monorepo/core/services'

import { useRecents } from '@symfoititis-frontend-monorepo/composables'
import { useHistory } from '@symfoititis-frontend-monorepo/composables'

const courseStore = useCourseStore()
const { search, uniqueSemesters, filteredCourses } = storeToRefs(courseStore)

const { getCourses } = useCoursesDataService()

const { recents } = useRecents('bookings_recent')
const { history, removeCourseFromHistory } = useHistory('bookings_history')

const clearSearch = () => {
  search.value = ''
}

onMounted(async () => {
  await getCourses()
})
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
        @delete-course="removeCourseFromHistory"
      />
    </template>
    <template v-slot:main>
      <SearchHeader
        title="Ιδιαίτερα"
        :display-search="true"
        :search="search"
        @clear-search="clearSearch"
      >
        <input
          v-model="search"
          type="text"
          class="regular-text search-input"
          placeholder="  Αναζήτησε μάθημα"
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
