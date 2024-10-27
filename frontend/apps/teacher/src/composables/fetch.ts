import { useErrorStore } from '@symfoititis-frontend-monorepo/stores'
import { useAvailabilityStore } from '../stores/availability'

export const useTeacherFetch = () => {
  const availabilityStore = useAvailabilityStore()
  const errorStore = useErrorStore()

  const getAvailabilitySlots = async (cid: number) => {
    try {
      await availabilityStore.getAvailabilitySlots(cid)
    } catch (err: any) {
      errorStore.addError(JSON.parse(err.message))
    }
  }

  const addAvailabilitySlots = async () => {
    try {
      await availabilityStore.addAvailabilitySlots()
    } catch (err: any) {
      errorStore.addError(JSON.parse(err.message))
    }
  }

  const updateAvailabilitySlots = async () => {
    try {
      await availabilityStore.updateAvailabilitySlots()
    } catch (err: any) {
      errorStore.addError(JSON.parse(err.message))
    }
  }

  const cancelAvailabilitySlots = async () => {
    try {
      await availabilityStore.cancelAvailabilitySlots()
    } catch (err: any) {
      errorStore.addError(JSON.parse(err.message))
    }
  }

  const saveAvailabilityChanges = async (cid: number) => {
    await Promise.all([
      addAvailabilitySlots(),
      updateAvailabilitySlots(),
      cancelAvailabilitySlots()
    ])
    await getAvailabilitySlots(cid)
  }


  return { getAvailabilitySlots, updateAvailabilitySlots, cancelAvailabilitySlots, saveAvailabilityChanges }
}
