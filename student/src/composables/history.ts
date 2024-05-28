import { ref } from 'vue'

import type { Course } from '../interfaces/Course'

export const useHistory = () => {
  const history = ref<Course[]>([])

  const getHistoryFromStorage = () => {
    return JSON.parse(localStorage.getItem('history')!) || []
  }

  const addCourseToStorage = (course: Course) => {
    const hist = getHistoryFromStorage()
    if (!hist.some((c: Course) => c.c_id == course.c_id)) {
      hist.push({
        c_id: course.c_id,
        c_display_name: course.c_display_name
      })
      localStorage.setItem('history', JSON.stringify(hist))
      if (hist.length > 15) {
        deleteCourseFromStorage(0)
      }
    }
  }

  const deleteCourseFromStorage = (index: number) => {
    const hist = getHistoryFromStorage()
    if (index < 0 || index >= hist.length) return
    const c_id = hist[index]?.c_id
    hist.splice(index, 1)
    localStorage.setItem('history', JSON.stringify(hist))
    return c_id
  }

  return { history, getHistoryFromStorage, addCourseToStorage, deleteCourseFromStorage }
}
