import { ref } from 'vue';
import type { Course } from "@symfoititis-frontend-monorepo/interfaces"

export const useRecents = (name: string) => {
  const itemName = name;
  const recents = ref<Course[]>([]);
  const getRecFromStorage = () => {
    return JSON.parse(localStorage.getItem(itemName)!) || [];
  };
  const addRecToStorage = (course: Course) => {
    const recs = getRecFromStorage();
    if (recs.some((c: Course) => c.c_id == course.c_id)) return;
    recs.unshift({
      c_id: course.c_id,
      c_display_name: course.c_display_name
    });
    if (recs.length > 6) {
      recs.splice(6, recs.length - 6);
    }
    localStorage.setItem(itemName, JSON.stringify(recs));
  };
  return { recents, getRecFromStorage, addRecToStorage };
};
