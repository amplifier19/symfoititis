import { ref } from 'vue'
import { defineStore } from 'pinia'
import  { type AvailabilitySlot } from '@symfoititis-frontend-monorepo/interfaces'

export const useAvailabilityStore = defineStore("availabilityStore", () => {
  const availabilitySlots = ref<AvailabilitySlot[]>([]);

  const getAvailabilitySlots = async (cid: number, tid: string) => {
    await fetch(`${import.meta.env.VITE_API_BASE}/tutoring/availability/${cid}/teacher/${tid}`, {
      method: 'GET'
    })
      .then(response => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        availabilitySlots.value = data.data
      })
      .catch((err: any) => {
        throw new Error(err.message)
      })
  }

  return { availabilitySlots, getAvailabilitySlots };
});
