import { ref } from 'vue';

export const useRecents = (name) => {
  const itemName = name;
  const recents = ref([]);
  const getRecFromStorage = () => {
    return JSON.parse(localStorage.getItem(itemName)) || [];
  };
  const addRecToStorage = (fav) => {
    const recs = getRecFromStorage();
    if (recs.some((fa) => fa.c_id == fav.c_id)) return;
    recs.unshift({
      c_id: fav.c_id,
      c_display_name: fav.c_display_name
    });
    if (recs.length > 6) {
      recs.splice(6, recs.length - 6);
    }
    localStorage.setItem(itemName, JSON.stringify(recs));
  };
  return { recents, getRecFromStorage, addRecToStorage };
};
