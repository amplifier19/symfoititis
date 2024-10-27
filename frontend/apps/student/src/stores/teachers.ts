import { ref } from 'vue'
import { defineStore } from 'pinia'
import { type Teacher } from '@symfoititis-frontend-monorepo/interfaces'

export const useTeacherStore = defineStore("teacherStore", () => {
  const teachers = ref<Teacher[]>([])

  const getTeachers = async (cid: number) => {
    await fetch(`${import.meta.env.VITE_API_BASE}/tutoring/availability/${cid}/teachers`, {
      method: 'GET'
    })
      .then(response => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        teachers.value = data.data
      })
      .catch((err: any) => {
        throw new Error(err.message)
      })
  }

  return { teachers, getTeachers };
});
