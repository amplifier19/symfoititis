import { ref } from 'vue';

export const useHistory = (name) => {
  const itemName = name;
  const history = ref([]);
  const getHistoryFromStorage = () => {
    return JSON.parse(localStorage.getItem(itemName)) || [];
  };
  const getHistoryLength = () => {
    return JSON.parse(localStorage.getItem(itemName))?.length || 0;
  };
  const addCourseToStorage = (course) => {
    const hist = getHistoryFromStorage();
    if (!hist.some((c) => c.c_id == course.c_id)) {
      hist.push({
        c_id: course.c_id,
        c_display_name: course.c_display_name
      });
      localStorage.setItem(itemName, JSON.stringify(hist));
      if (hist.length > 15) {
        deleteCourseFromStorage(0);
      }
    }
  };
  const deleteCourseFromStorage = (index) => {
    const hist = getHistoryFromStorage();
    if (index < 0 || index >= hist.length) return -1;
    const c_id = hist[index]?.c_id;
    hist.splice(index, 1);
    localStorage.setItem(itemName, JSON.stringify(hist));
    return c_id;
  };
  return { history, getHistoryFromStorage, getHistoryLength, addCourseToStorage, deleteCourseFromStorage };
};
