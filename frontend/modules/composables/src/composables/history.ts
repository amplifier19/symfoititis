import { ref } from "vue";
import type { Course } from "@symfoititis-frontend-monorepo/interfaces";

export const useHistory = (name: string) => {
  const itemName = name;

  const getHistoryFromStorage = () => {
    const hist = JSON.parse(localStorage.getItem(itemName)!) || [];
    return hist;
  };

  const getHistorySize = (itemName: string) => {
    const hist = JSON.parse(localStorage.getItem(itemName)!) || [];
    return hist.length;
  };

  const saveHistoryToStorage = () => {
    localStorage.setItem(itemName, JSON.stringify(history.value));
  };

  const history = ref<Course[]>(getHistoryFromStorage());

  const addCourseToHistory = (course: Course) => {
    const courseDoesNotExists = !history.value.some(
      (c: Course) => c.c_id == course.c_id,
    );
    if (courseDoesNotExists) {
      history.value = getHistoryFromStorage().toSpliced(6, Infinity, course);
      saveHistoryToStorage();
    }
  };

  const removeCourseFromHistory = (c_id: number) => {
    const copy = getHistoryFromStorage();
    const index = copy.findIndex((c: Course) => c.c_id == c_id);
    const course = copy[index];
    if (!!course) {
      history.value = copy.toSpliced(index, 1);
      saveHistoryToStorage();
      return course.c_id;
    }
    return -1;
  };

  return {
    history,
    getHistoryFromStorage,
    getHistorySize,
    addCourseToHistory,
    removeCourseFromHistory,
  };
};
