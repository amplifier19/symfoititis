import { ref } from 'vue'
import { defineStore } from 'pinia'
import { type University } from '../interfaces/University'

export const useUniStore = defineStore('universities', () => {
  const universities = ref<University[]>([])
  const current_uni = ref<University>({
    uni_id: -100,
    uni_display_name: 'default',
    uni_alt_name: 'default'
  })

  const getUniversities = async () => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/universities`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
      .then((response) => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(data.error)
        }
        universities.value = data.data
      })
      .catch((error) => {
        throw new Error(error)
      })
  }

  const createUniversity = async (uni: University) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/university`, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(uni)
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

  const updateUniversity = async (uni: University) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/university`, {
      method: 'PUT',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(uni)
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

  const deleteUniversity = async (uni_id: number) => {
    return await fetch(`${import.meta.env.VITE_ADMIN_API_URL}/university/${uni_id}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
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
    universities,
    current_uni,
    getUniversities,
    createUniversity,
    updateUniversity,
    deleteUniversity
  }
})
