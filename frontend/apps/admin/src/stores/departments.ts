import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Department } from '@symfoititis-frontend-monorepo/interfaces'

export const useDepStore = defineStore('departments', () => {
  const departments = ref<Department[]>([])
  const current_dep = ref<Department>({
    dep_id: -100,
    uni_id: -100,
    dep_display_name: 'default',
    dep_alt_name: 'default'
  })

  const getDepartments = async () => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/institutions/departments`, {
      method: 'GET'
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        departments.value = data.data
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  const createDepartment = async (dep: Department) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/institutions/department`, {
      method: 'POST',
      headers: {
	"Content-Type": "application/json"
      },
      body: JSON.stringify(dep)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        return data
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  const updateDepartment = async (dep: Department) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/institutions/department`, {
      method: 'PUT',
      headers: {
	"Content-Type": "application/json"
      },
      body: JSON.stringify(dep)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        return data
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  const deleteDepartment = async (dep_id: number) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/institutions/department/${dep_id}`, {
      method: 'DELETE',
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        return data
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  return {
    departments,
    current_dep,
    getDepartments,
    createDepartment,
    updateDepartment,
    deleteDepartment
  }
})
