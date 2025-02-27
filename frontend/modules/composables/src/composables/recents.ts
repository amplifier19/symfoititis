import { ref } from 'vue'
import type { Course } from "@symfoititis-frontend-monorepo/interfaces"

export const useRecents = (name: string) => {
  const itemName = name

  const getRecentsFromStorage = () => {
    return JSON.parse(localStorage.getItem(itemName)!) || []
  }

  const saveRecentsToStorage = () => {
    localStorage.setItem(itemName, JSON.stringify(recents.value))
  }
  const recents = ref<Course[]>(
    getRecentsFromStorage()
  )

  const addCourseToRecents = (course: Course) => {
    const courseDoesNotExist = !recents.value.some((c: Course) => c.c_id == course.c_id)
    if (courseDoesNotExist) {
      const recs = getRecentsFromStorage().slice(0, 6)
      recents.value = [course, ...recs]
      saveRecentsToStorage()
    }
  }

  return { recents, addCourseToRecents }
}
