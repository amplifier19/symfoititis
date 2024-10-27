import { useAuthStore } from '@symfoititis-frontend-monorepo/stores'
import { useUniversityStore } from '@symfoititis-frontend-monorepo/stores'
import { useDepartmentStore } from '@symfoititis-frontend-monorepo/stores'
import { useCourseStore } from '@symfoititis-frontend-monorepo/stores'
import { useErrorStore } from '@symfoititis-frontend-monorepo/stores'
import { useNoteStore } from '@symfoititis-frontend-monorepo/stores'

export const useFetch = () => {
  const authStore = useAuthStore()
  const universityStore = useUniversityStore()
  const departmentStore = useDepartmentStore()
  const courseStore = useCourseStore()
  const noteStore = useNoteStore()
  const errorStore = useErrorStore()

  const getUserInfo = async () => {
    try {
      await authStore.updateToken(420);
      await Promise.all([
        authStore.getProfile(),
        getUniversity(),
        getDepartment(),
      ])
    } catch (error: any) {
      errorStore.addError(JSON.parse(error.message))
      return
    }
  }

  const getUniversity = async () => {
    if (universityStore.university.uni_id > 0) return
    try {
      await universityStore.getUniversity()
    } catch (error: any) {
      errorStore.addError(JSON.parse(error.message))
      return
    }
  }

  const getDepartment = async () => {
    if (departmentStore.department.dep_id > 0) return
    try {
      await departmentStore.getDepartment()
    } catch (error: any) {
      errorStore.addError(JSON.parse(error.message))
      return
    }
  }

  const getCourses = async () => {
    if (courseStore.courses.length > 0) return
    try {
      await courseStore.getCourses()
    } catch (error: any) {
      errorStore.addError(JSON.parse(error.message))
      return
    }
  }

  const getAvailableTutoringCourses = async (dep_id: number) => {
    if (courseStore.availableTutoringCourses.length > 0) return
    try {
      await courseStore.getAvailableTutoringCourseIds(dep_id)
    } catch (error: any) {
      errorStore.addError(JSON.parse(error.message))
      return
    }
  }

  const getNotes = async (c_id: string) => {
    let cid
    try {
      cid = parseInt(c_id)
    } catch (error: any) {
      errorStore.addError({ status: 400, error: error.message })
      return
    }
    if (cid <= 0) {
      errorStore.addError({ status: 400, error: 'Course not found' })
      return
    }
    try {
      await noteStore.getNotes(cid)
    } catch (error: any) {
      errorStore.addError(JSON.parse(error.message))
      return
    }
  }

  return { getUserInfo, getUniversity, getDepartment, getCourses, getAvailableTutoringCourses, getNotes }
}
