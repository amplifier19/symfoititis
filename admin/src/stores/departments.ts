import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Department } from '../interfaces/Department'

export const useDepStore = defineStore('departments', () => {
  const departments = ref<Department[]>([])
  const current_dep = ref<Department>({
    dep_id: -100,
    uni_id: -100,
    dep_display_name: 'default',
    dep_alt_name: 'default'
  })

  const getDepartments = async () => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/departments`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
      }
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        departments.value = data.data
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  const createDepartment = async (dep: Department) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/department`, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(dep)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  const updateDepartment = async (dep: Department) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/department`, {
      method: 'PUT',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(dep)
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  const deleteDepartment = async (dep_id: number) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/department/${dep_id}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
      }
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        return data.message
      })
      .catch((error) => {
        throw new Error(error)
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
