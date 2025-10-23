import { defineStore } from "pinia";
import { ref } from "vue";
import { type Response } from "@symfoititis-frontend-monorepo/interfaces";

export const useErrorStore = defineStore("error", () => {
  const errors = ref<any[]>([]);

  const addError = (err: any) => {
    errors.value.push(err);
  };

  const deleteError = (idx: number) => {
    errors.value.splice(idx, 1);
  };

  return { errors, addError, deleteError };
});
