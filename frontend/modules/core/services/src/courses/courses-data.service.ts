import { storeToRefs } from "pinia";
import { CoursesApiService } from "./courses-api.service";
import {
  useCourseStore,
  useErrorStore,
  useUserStore,
} from "@symfoititis-frontend-monorepo/stores";

export const useCoursesDataService = () => {
  const coursesApiService = CoursesApiService.getCoursesApiFactory();
  const courseStore = useCourseStore();
  const userStore = useUserStore();
  const { department } = storeToRefs(userStore);
  const errorStore = useErrorStore();

  const getCourses = async () => {
    try {
      if (courseStore.courses.length > 0) return;
      const response = await coursesApiService.getCourses();
      const data = await response.json();
      if (!!data.error) {
        errorStore.addError(data);
      } else {
        courseStore.courses = data.data;
      }
    } catch (err) {
      errorStore.addError(err);
    }
  };

  const getAvailableTutoringCourseIds = async () => {
    try {
      const response = await coursesApiService.getAvailableTutoringCourseIds(
        department.value.dep_id,
      );
      const data = await response.json();
      if (!!data.error) {
        errorStore.addError(data);
        return;
      }
      courseStore.availableTutoringCourseIds = data.data;
    } catch (err) {
      errorStore.addError(err);
    }
  };

  return { getCourses, getAvailableTutoringCourseIds };
};
