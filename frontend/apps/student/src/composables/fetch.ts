import { useAvailabilityStore } from '../stores/availability'
import { useErrorStore } from '@symfoititis-frontend-monorepo/stores'
import { useTeacherStore } from '../stores/teachers'

export const useStudentFetch = () => {
  const errorStore = useErrorStore()
  const teacherStore = useTeacherStore()
  const availabilityStore = useAvailabilityStore()

  const getAvailabilitySlots = async (cid: number, tid: string) => {
    try {
      await availabilityStore.getAvailabilitySlots(cid, tid)
    } catch (err: any) {
      errorStore.addError(JSON.parse(err.message))
    }
  }

  const getTeachers = async (cid: number) => {
    try {
      await teacherStore.getTeachers(cid)
    } catch (err: any) {
      errorStore.addError(JSON.parse(err.message))
    }
  }

  return { getAvailabilitySlots, getTeachers }
}
