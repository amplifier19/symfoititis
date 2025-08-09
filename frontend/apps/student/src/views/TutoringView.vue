<script setup lang="ts">
import { onMounted } from 'vue'
import { storeToRefs } from 'pinia'

import { Page } from '@symfoititis-frontend-monorepo/ui'
import { Toasts } from '@symfoititis-frontend-monorepo/ui'
import { History } from '@symfoititis-frontend-monorepo/ui'
import { Recents } from '@symfoititis-frontend-monorepo/ui'
import { Gallery } from '@symfoititis-frontend-monorepo/ui'
import { Masterhead } from '@symfoititis-frontend-monorepo/ui'
import { SearchHeader } from '@symfoititis-frontend-monorepo/ui'

import { useCourseStore } from '@symfoititis-frontend-monorepo/stores'

import { useChatDataService, useCoursesDataService } from '@symfoititis-frontend-monorepo/core/services'

import { useRecents } from '@symfoititis-frontend-monorepo/composables'
import { useHistory } from '@symfoititis-frontend-monorepo/composables'

const { getCourses, getAvailableTutoringCourseIds } = useCoursesDataService()

const courseStore = useCourseStore()
const { search, uniqueSemesters, filteredAvailableTutoringCourses } = storeToRefs(courseStore)

const { recents } = useRecents('bookings_recent')
const { history, removeCourseFromHistory } = useHistory('bookings_history')

const clearSearch = () => {
  search.value = ''
}

onMounted(async () => {
  await getCourses()
  await getAvailableTutoringCourseIds()
})
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="1" />
      <History to="availability" :cid="-100" :history="history" @delete-course="removeCourseFromHistory" />
    </template>
    <template v-slot:main>
      <SearchHeader title="Ιδιαίτερα" :display-search="true" :search="search" @clear-search="clearSearch">
        <input v-model="search" type="text" class="regular-text search-input" placeholder="  Αναζήτησε μάθημα" />
      </SearchHeader>
      <Recents v-if="!search && recents.length > 0" link="availability" :recentCourses="recents" />
      <Gallery :uniqueSemesters="uniqueSemesters" :filteredCourses="filteredAvailableTutoringCourses"
        link="availability" />
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
