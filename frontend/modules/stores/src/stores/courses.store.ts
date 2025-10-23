import { computed, ref } from "vue";
import { defineStore } from "pinia";
import { Course } from "@symfoititis-frontend-monorepo/interfaces";

export const useCourseStore = defineStore("courseStore", () => {
  const courses = ref<Course[]>([]);
  const search = ref<string>("");
  const availableTutoringCourseIds = ref<number[]>([]);

  const filteredCourses = computed(() => {
    return courses.value.filter((course: Course) =>
      course.c_display_name.toLowerCase().includes(search.value.toLowerCase()),
    );
  });

  const uniqueSemesters = computed(() => {
    return [...new Set<number>(courses.value.map((c: Course) => c.semester))];
  });

  const availableTutoringCourses = computed<Course[]>(() => {
    return courses.value.filter((c: Course) =>
      availableTutoringCourseIds.value.includes(c.c_id!),
    );
  });

  const filteredAvailableTutoringCourses = computed(() => {
    return availableTutoringCourses.value.filter((course) =>
      course.c_display_name.toLowerCase().includes(search.value.toLowerCase()),
    );
  });

  const uniqueTutoringSemesters = computed(() => {
    return [
      ...new Set<number>(
        filteredAvailableTutoringCourses.value.map((c: Course) => c.semester),
      ),
    ];
  });

  return {
    courses,
    search,
    uniqueSemesters,
    filteredCourses,
    availableTutoringCourseIds,
    availableTutoringCourses,
    filteredAvailableTutoringCourses,
    uniqueTutoringSemesters,
  };
});
