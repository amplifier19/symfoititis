<script setup lang="ts">
import { ref, watch, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { Page, Masterhead, History, NavHeader, Subheader, Toasts } from '@symfoititis-frontend-monorepo/ui';
import DateTimePicker from '../components/DateTimePicker.vue';
import { useRecents } from '@symfoititis-frontend-monorepo/composables';
import { useHistory } from '@symfoititis-frontend-monorepo/composables';
import { useFetch } from '@symfoititis-frontend-monorepo/composables';
import { useCourseStore, useErrorStore } from '@symfoititis-frontend-monorepo/stores';
import { type Course } from '@symfoititis-frontend-monorepo/interfaces';

const router = useRouter();
const route = useRoute();
const c_id = ref<number>(parseInt(route.params.c_id as string));
const course = ref<Course>({
  c_id: -100,
  dep_id: -100,
  semester: -100,
  c_display_name: "",
  description: ""
});
const { history, getHistoryFromStorage, addCourseToStorage, deleteCourseFromStorage } = useHistory('bookings_history');
history.value = getHistoryFromStorage();
const { addRecToStorage } = useRecents('bookings_recent');
const courseStore = useCourseStore();
const errorStore = useErrorStore();
const { getCourses, getUserInfo } = useFetch();

const saveCourse = () => {
  course.value = courseStore.courses.find((c: Course) => c.c_id == parseInt(route.params.c_id as string)) || {
    c_id: -100,
    dep_id: -100,
    semester: -100,
    c_display_name: ""
  };
  if (course.value.c_id! > 0) {
    addCourseToStorage(course.value);
    history.value = getHistoryFromStorage();
    addRecToStorage(course.value);
  } else {
    errorStore.addError({ status: 404, error: 'Course not Found' })
  }
};
const handleDelete = (index: number) => {
  const cid = deleteCourseFromStorage(index);
  if (cid < 0) return;
  if (cid === c_id.value) {
    router.push({ name: 'tutoring' });
  }
  history.value = getHistoryFromStorage();
};
onMounted(async () => {
  await getCourses();
  c_id.value = parseInt(route.params.c_id as string);
  saveCourse();
  await getUserInfo()
});
watch(route, async (newRoute, oldRoute) => {
  const cid = parseInt(route.params.c_id as string);
  if (c_id.value !== cid) {
    c_id.value = cid;
    saveCourse();
    await getUserInfo()
  }
});
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="1" />
      <History to="availability" :cid="c_id" :history="history" @delete-course="handleDelete" />
    </template>

    <template v-slot:main>
      <NavHeader navigation="tutoring" storageItem="bookings_history" :course="course" />
      <div class="main-wrapper">
        <Subheader title="Διαθεσιμότητα" />
        <section class="main-container">
          <div class="date-time-pick-container">
            <DateTimePicker />
          </div>
        </section>
      </div>
    </template>
  </Page>
</template>

<style scoped>
.main-wrapper {
  margin-top: 6rem;
}

.main-container {
  margin: 0 auto;
  width: calc(100% - 9rem);
}

.date-time-pick-container {
  display: flex;
  justify-content: space-evenly;
  flex-wrap: wrap;
  margin-bottom: 1rem;
}

@media screen and (max-width: 1800px) {
  .main-container {
    width: calc(100% - 8rem);
  }
}

@media screen and (max-width: 550px) {
  .main-container {
    width: 100%;
  }
}
</style>
