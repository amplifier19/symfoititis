import { ref } from 'vue'
import { defineStore } from 'pinia'
import { type University, type Response } from '@symfoititis-frontend-monorepo/interfaces'

export const useUniStore = defineStore('universities', () => {
  const universities = ref<University[]>([])
  const current_uni = ref<University>({
    uni_id: -100,
    uni_display_name: 'default',
    uni_alt_name: 'default'
  })

  const getUniversities = async () => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/institutions/universities`, {
      method: 'GET',
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
	else {
          universities.value = data.data
	}
      })
      .catch((error) => {
        throw new Error(error.message)
      })
  }

  const createUniversity = async (uni: University) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/institutions/university`, {
      method: 'POST',
      headers: {
	"Content-Type": "application/json"
      },
      body: JSON.stringify(uni)
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

  const updateUniversity = async (uni: University) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/institutions/university`, {
      method: 'PUT',
      headers: {
	"Content-Type": "application/json"
      },
      body: JSON.stringify(uni)
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

  const deleteUniversity = async (uni_id: number) => {
    return await fetch(`${import.meta.env.VITE_API_BASE}/institutions/university/${uni_id}`, {
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
    universities,
    current_uni,
    getUniversities,
    createUniversity,
    updateUniversity,
    deleteUniversity
  }
})
