import { ref } from "vue";
import { defineStore } from "pinia";
import { type Teacher } from "@symfoititis-frontend-monorepo/interfaces";

export const useTeacherStore = defineStore("teacherStore", () => {
  const teachers = ref<Teacher[]>([]);

  return { teachers };
});
