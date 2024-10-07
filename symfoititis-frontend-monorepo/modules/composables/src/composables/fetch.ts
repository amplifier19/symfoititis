import { useAuthStore } from '@symfoititis-frontend-monorepo/stores'
import { useUniversityStore } from '@symfoititis-frontend-monorepo/stores'
import { useDepartmentStore } from '@symfoititis-frontend-monorepo/stores'
import { useCourseStore } from '@symfoititis-frontend-monorepo/stores'
import { useErrorStore } from '@symfoititis-frontend-monorepo/stores'
import { useNoteStore } from '@symfoititis-frontend-monorepo/stores'
import { useTutoringStore } from '@symfoititis-frontend-monorepo/stores'

export const useFetch = () => {
  const authStore = useAuthStore()
  const universityStore = useUniversityStore()
  const departmentStore = useDepartmentStore()
  const courseStore = useCourseStore()
  const noteStore = useNoteStore()
  const tutoringStore = useTutoringStore()
  const errorStore = useErrorStore()

  const getUserInfo = async () => {
    try {
      await authStore.updateToken(420);
      await Promise.all([
        authStore.getProfile(),
        getUniversity(),
        getDepartment(),
      ])
    } catch (error: Error) {
      errorStore.addError(JSON.parse(error.message))
      return
    }
  }

  const getUniversity = async () => {
    if (universityStore.university.uni_id > 0) return
    try {
      await universityStore.getUniversity()
    } catch (error: Error) {
      errorStore.addError(JSON.parse(error.message))
      return
    }
  }

  const getDepartment = async () => {
    if (departmentStore.department.dep_id > 0) return
    try {
      await departmentStore.getDepartment()
    } catch (error: Error) {
      errorStore.addError(JSON.parse(error.message))
      return
    }
  }

  const getCourses = async () => {
    if (courseStore.courses.length > 0) return
    try {
      await courseStore.getCourses()
    } catch (error: Error) {
      errorStore.addError(JSON.parse(error.message))
      return
    }
  }

  const getNotes = async (c_id: string) => {
    let cid
    try {
      cid = parseInt(c_id)
    } catch (error: Error) {
        errorStore.addError({status: 400, error: error.message})
	return
    }
    if (cid <= 0) {
      errorStore.addError({status: 400, error: 'Course not found'})
      return
    }
    try {
      await noteStore.getNotes(cid)
    } catch (error: Error) {
      errorStore.addError(JSON.parse(error.message))
      return
    }
  }

  const getTutoringCourses = async () => {
    if (tutoringStore.courses.length > 0) {
      return;
    }
    try {
      await tutoringStore.getCourses()
    } catch (error: Error) {
      errorStore.addError(JSON.parse(error.message))
    }
  }

  return { getUserInfo, getUniversity, getDepartment, getCourses, getNotes, getTutoringCourses }
}
