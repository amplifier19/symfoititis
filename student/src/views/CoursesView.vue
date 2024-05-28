<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'

import { useCourseStore } from '../stores/courses'

import { useHistory } from '../composables/history'
import { useFavorites } from '../composables/favorites'
import { useFetch } from '../composables/fetchService'

import Toasts from '../components/Toasts.vue'
import Page from '../components/Page.vue'
import Masterhead from '../components/Masterhead.vue'
import History from '../components/History.vue'
import Search from '../components/Search.vue'
import Gallery from '../components/Gallery.vue'
import Favorites from '../components/Favorites.vue'

const courseStore = useCourseStore()

const { getCourses } = useFetch()

const uniqueSemesters = ref<number[]>([])
const search = ref('')

const { favorites, getFavFromStorage } = useFavorites()
favorites.value = getFavFromStorage()

const { history, getHistoryFromStorage, deleteCourseFromStorage } = useHistory()
history.value = getHistoryFromStorage()

const clearSearch = () => {
  search.value = ''
}

const handleDelete = (index: number) => {
  deleteCourseFromStorage(index)
  history.value = getHistoryFromStorage()
}

if (courseStore.courses?.length > 0) {
  uniqueSemesters.value = [...new Set(courseStore.courses.map((c) => c.semester))]
}

onMounted(async () => {
  await getCourses()
  uniqueSemesters.value = [...new Set(courseStore.courses.map((c) => c.semester))]
})

const filteredCourses = computed(() => {
  return courseStore.courses.filter((course) =>
    course.c_display_name.toLowerCase().includes(search.value.toLowerCase())
  )
})
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="0" />
      <History :cid="-100" :history="history" @delete-course="handleDelete" />
    </template>
    <template v-slot:main>
      <Search @clear-search="clearSearch">
        <input
          v-model="search"
          type="text"
          class="search-input"
          placeholder="  Αναζήτησε ένα μάθημα"
        />
      </Search>
      <Favorites v-if="search.length == 0 && favorites?.length > 0" :favoriteCourses="favorites" />
      <Gallery :uniqueSemesters="uniqueSemesters" :filteredCourses="filteredCourses" />
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
