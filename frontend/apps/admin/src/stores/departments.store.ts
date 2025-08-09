import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Department } from '@symfoititis-frontend-monorepo/interfaces'

export const useDepStore = defineStore('departments', () => {
  const departments = ref<Department[]>([])
  const currentDepartment = ref<Department | null>(null)

  const setDepartments = (list: Department[]) => {
    departments.value = list
  }

  const setCurrentDepartment = (dep: Department) => {
    currentDepartment.value = dep
  }

  return {
    departments,
    currentDepartment,
    setDepartments,
    setCurrentDepartment,
  }
})

