import { ref } from 'vue'
import type { Course } from '@symfoititis-frontend-monorepo/interfaces'

export const useHistory = (name: string) => {
  const itemName = name

  const getHistoryFromStorage = () => {
    const hist = JSON.parse(localStorage.getItem(itemName)!) || []
    const end = hist.length
    const start = end - 7 >= 0 ? end - 7 : 0
    return hist.slice(start, end)
  }

  const saveHistoryToStorage = () => {
    localStorage.setItem(itemName, JSON.stringify(history.value))
  }

  const history = ref<Course[]>(
    getHistoryFromStorage()
  )

  const addCourseToHistory = (course: Course) => {
    const courseDoesNotExists = !history.value.some((c: Course) => c.c_id == course.c_id)
    if (courseDoesNotExists) {
      history.value = [
        ...getHistoryFromStorage(),
        course 
      ]
      saveHistoryToStorage()
    }
  }

  const removeCourseFromHistory = (index: number) => {
    const course = history.value[index]
    if (!!course) {
      const left = history.value.slice(0, index)
      const right = history.value.slice(index + 1, history.value.length)
      history.value = [...left, ...right]
      saveHistoryToStorage()
      return course.c_id
    }
    return -1
  }

  return { history, getHistoryFromStorage, addCourseToHistory, removeCourseFromHistory }
}
