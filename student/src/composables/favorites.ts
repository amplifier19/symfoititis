import { ref } from 'vue'

import type { Course } from '../interfaces/Course'

export const useFavorites = () => {
  const favorites = ref<Course[]>([])

  const getFavFromStorage = () => {
    return JSON.parse(localStorage.getItem('favorites')!) || []
  }

  const addFavToStorage = (fav: Course) => {
    const favorites = getFavFromStorage()
    if (favorites.some((fa: Course) => fa.c_id == fav.c_id)) return
    favorites.push({
      c_id: fav.c_id,
      c_display_name: fav.c_display_name
    })
    localStorage.setItem('favorites', JSON.stringify(favorites))
  }

  const deleteFavFromStorage = (index: number) => {
    const favorites = getFavFromStorage()
    if (index < 0 || index >= favorites.length) return
    favorites.splice(index, 1)
    localStorage.setItem('favorites', JSON.stringify(favorites))
  }

  return { favorites, getFavFromStorage, addFavToStorage, deleteFavFromStorage }
}
