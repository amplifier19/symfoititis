import { useAuthStore } from '../stores/auth'
import { useUniversity } from '../stores/university'
import { useDepartment } from '../stores/department'
import { useCourseStore } from '../stores/courses'
import { useErrorStore } from '../stores/errors'
import { useNoteStore } from '../stores/notes'

export const useFetch = () => {
  const authStore = useAuthStore()
  const universityStore = useUniversity()
  const departmentStore = useDepartment()
  const courseStore = useCourseStore()
  const noteStore = useNoteStore()
  const errorStore = useErrorStore()

  const getUserInfo = async () => {
    try {
      if (Object.keys(authStore.profile).length <= 0) {
        await authStore.getProfile()
      }
      if (universityStore.university.uni_id <= 0) {
        await getUniversity()
      }
      if (departmentStore.department.dep_id <= 0) {
        await getDepartment()
      }
    } catch (error: any) {
      errorStore.addError(error)
    }
  }

  const getUniversity = async () => {
    if (universityStore.university.uni_id > 0) return
    try {
      await universityStore.getUniversity()
    } catch (error: any) {
      errorStore.addError(error)
    }
  }

  const getDepartment = async () => {
    if (departmentStore.department.dep_id > 0) return
    try {
      await departmentStore.getDepartment()
    } catch (error: any) {
      errorStore.addError(error)
    }
  }

  const getCourses = async () => {
    if (courseStore.courses.length > 0) return
    try {
      await courseStore.getCourses()
    } catch (error: any) {
      errorStore.addError(error)
    }
  }

  const getNotes = async (c_id: string) => {
    if (parseInt(c_id) < 0) {
      errorStore.addError('Course not found')
      return
    }
    try {
      await noteStore.getNotes(parseInt(c_id))
    } catch (error: any) {
      errorStore.addError(error)
    }
  }

  return { getUserInfo, getUniversity, getDepartment, getCourses, getNotes }
}
