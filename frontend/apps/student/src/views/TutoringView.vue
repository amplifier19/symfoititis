<script setup lang="ts">
import { ref, onMounted, computed, watch } from 'vue'
import { SearchHeader } from '@symfoititis-frontend-monorepo/ui'
import { Page } from '@symfoititis-frontend-monorepo/ui'
import { Masterhead } from '@symfoititis-frontend-monorepo/ui'
import { History } from '@symfoititis-frontend-monorepo/ui'
import { Recents } from '@symfoititis-frontend-monorepo/ui'
import { Gallery } from '@symfoititis-frontend-monorepo/ui'
import { Toasts } from '@symfoititis-frontend-monorepo/ui'
import { useCourseStore, useDepartmentStore } from '@symfoititis-frontend-monorepo/stores'
import { useRecents } from '@symfoititis-frontend-monorepo/composables'
import { useHistory } from '@symfoititis-frontend-monorepo/composables'
import { useFetch } from '@symfoititis-frontend-monorepo/composables'

const search = ref<string>('')
const courseStore = useCourseStore()
const departmentStore = useDepartmentStore();
const { getCourses, getAvailableTutoringCourses, getDepartment } = useFetch();
const { history, getHistoryFromStorage, deleteCourseFromStorage } = useHistory('bookings_history')
const { recents, getRecFromStorage } = useRecents('bookings_recent')
history.value = getHistoryFromStorage()
recents.value = getRecFromStorage()
const uniqueSemesters = ref<number[]>([])
const filteredCourses = computed(() => {
  return courseStore.availableTutoringCourses.filter((course) =>
    course.c_display_name.toLowerCase().includes(search.value.toLowerCase())
  )
})

const clearSearch = () => {
  search.value = ''
}

const handleDelete = (index: number) => {
  const cid = deleteCourseFromStorage(index);
  history.value = getHistoryFromStorage();
};

const initSemesters = () => {
  uniqueSemesters.value = [
    ...new Set(courseStore.availableTutoringCourses.map((c) => c.semester))
  ]
}
onMounted(async () => {
  await getDepartment()
  await getCourses()
  await getAvailableTutoringCourses(departmentStore.department.dep_id)
  initSemesters()
})
</script>

<template>
  <Toasts/>
  <Page>
    <template v-slot:header>
      <Masterhead :selected="1" />
      <History to="availability" :cid="-100" :history="history" @delete-course="handleDelete" />
    </template>
    <template v-slot:main>
      <SearchHeader title="Ιδιαίτερα" :search="search" @clear-search="clearSearch">
        <input v-model="search" type="text" class="regular-text search-input" placeholder="  Αναζήτησε ένα μάθημα" />
      </SearchHeader>
      <Recents v-if="search.length == 0 && recents?.length > 0" link="availability" :recentCourses="recents" />
      <Gallery :uniqueSemesters="uniqueSemesters" :filteredCourses="filteredCourses" link="availability" />
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
