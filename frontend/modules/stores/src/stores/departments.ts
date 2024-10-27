import { ref } from 'vue'
import { defineStore } from 'pinia'

import type { Department } from '@symfoitititis-frontend-monorepo/interfaces'

export const useDepartmentStore = defineStore('department', () => {
  const department = ref<Department>({
    dep_id: -100,
    uni_id: -100,
    dep_display_name: 'default',
    dep_alt_name: 'default'
  })

  const getDepartment = async () => {
    await fetch(`${import.meta.env.VITE_API_BASE}/institutions/department`, {
      method: 'GET'
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        department.value = data.data
      })
      .catch((error: Error) => {
        throw new Error(error.message)
      })
  }

  return { department, getDepartment }
})
