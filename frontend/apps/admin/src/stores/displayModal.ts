import { defineStore } from "pinia";
import { ref } from "vue";

export const useDisplayModal = defineStore("displayModal", () => {
  const displayModal = ref<boolean>(false);

  const setDisplay = (val: boolean) => {
    displayModal.value = val;
  };
  return { displayModal, setDisplay };
});
