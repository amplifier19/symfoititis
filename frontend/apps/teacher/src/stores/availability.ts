import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useAuthStore, useDepartmentStore, useResponseStore } from '@symfoititis-frontend-monorepo/stores'
import { type AvailabilitySlot, type Day } from '@symfoititis-frontend-monorepo/interfaces'

export const useAvailabilityStore = defineStore("availabilityStore", () => {
  const departmentStore = useDepartmentStore()
  const authStore = useAuthStore()
  const responseStore = useResponseStore()
  const selectedDay = ref<Day>()
  const selectedDate = ref<string>('')
  const availabilitySlots = ref<AvailabilitySlot[]>([])
  const cancelableAvailabilitySlotIds = ref<number[]>([])
  const filteredByDateAvailabilitySlots = computed (() => {
    return availabilitySlots.value.filter(slot => {
      return (slot.date === selectedDate.value && !cancelableAvailabilitySlotIds.value.includes(slot.av_id!))
  })
  })
  const filteredInsertableAvailabilitySlots = computed (() => {
    return insertableAvailabilitySlots.value.filter(slot => slot.start_time > 0)
  })
  const updatableAvailabilitySlots = ref<AvailabilitySlot[]>([])
  const insertableAvailabilitySlots = ref<AvailabilitySlot[]>([]);
  const availabilityPreferences = ref<AvailabilitySlot[]>([])

  const updateUpdatableSlot = (slotKey: number, startTime: number) => {
    const i = availabilitySlots.value.findIndex((slot) => slot.av_id === slotKey)
    if (i < 0) return
    const j = updatableAvailabilitySlots.value.findIndex((slot) => slot.av_id === slotKey)
    if (j < 0 && availabilitySlots.value[i].start_time != startTime) {
      updatableAvailabilitySlots.value.push(availabilitySlots.value[i]) 
    } else if (j >= 0 && availabilitySlots.value[i].start_time != startTime) {
      updatableAvailabilitySlots.value[j].start_time = startTime
    } else if (j >= 0 && availabilitySlots.value[i].start_time === startTime) {
      updatableAvailabilitySlots.value.splice(j, 1)
    }
  }

  const cancelUpdatableSlot = (av_id: number) => {
    const idx = availabilitySlots.value.findIndex((slot) => slot.av_id === av_id)
    if (idx < 0) return
    cancelableAvailabilitySlotIds.value = [...cancelableAvailabilitySlotIds.value, availabilitySlots.value[idx].av_id!]
  }

  const addInsertableSlot = async (c_id: number) => {
    insertableAvailabilitySlots.value = [...insertableAvailabilitySlots.value, {
      t_id: authStore.profile.id!,
      c_id: c_id,
      dep_id: departmentStore.department.dep_id,
      date: selectedDate.value,
      week_day: selectedDay.value!.weekDay,
      start_time: -1,
    }]
  }

  const updateInsertableSlot = (idx: number, startTime: number) => {
    if (idx < 0 || idx >= insertableAvailabilitySlots.value.length) return
    const slot = insertableAvailabilitySlots.value.splice(idx)[0]
    slot.start_time = startTime
    insertableAvailabilitySlots.value = [...insertableAvailabilitySlots.value, slot]
  }

  const removeInsertableSlot = (idx: number) => {
    if (idx < 0) return
    insertableAvailabilitySlots.value.splice(idx, 1)
  }

  const getAvailabilityPreferences = (day: number) => {
    if (filteredByDateAvailabilitySlots.value.length == 0) {
      const slot = availabilitySlots.value.findLast((el: AvailabilitySlot) => day === el.week_day && selectedDate.value !== el.date)
      if (!!slot) {
        availabilityPreferences.value = JSON.parse(JSON.stringify(
          availabilitySlots.value.filter((el) => day === el.week_day && slot.date === el.date)
        ))
        availabilityPreferences.value.forEach(slot => slot.date = selectedDate.value)
        return
      }
    }
    availabilityPreferences.value = []
    return
  }

  const declinePreferences = () => {
    availabilityPreferences.value = []
  }

  const applyPreferences = () => {
    insertableAvailabilitySlots.value = availabilityPreferences.value
    availabilityPreferences.value = []
  }

  const handleDateChange = (day: Day) => {
    selectedDay.value = day
    selectedDate.value = day.date
    insertableAvailabilitySlots.value = []
    updatableAvailabilitySlots.value = []
    cancelableAvailabilitySlotIds.value = []
  }

  const getAvailabilitySlots = async (cid: number) => {
    await fetch(`${import.meta.env.VITE_API_BASE}/tutoring/availability/${cid}`, {
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

  const addAvailabilitySlots = async () => {
    if (filteredInsertableAvailabilitySlots.value.length === 0) return
    await fetch(`${import.meta.env.VITE_API_BASE}/tutoring/availability`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json' },
      body: JSON.stringify(filteredInsertableAvailabilitySlots.value)
    })
      .then(response => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        insertableAvailabilitySlots.value = []
        responseStore.addResponse(data)
      })
      .catch((err: any) => {
        throw new Error(err.message)
      })
  }

  const updateAvailabilitySlots = async () => {
    if (updatableAvailabilitySlots.value.length === 0) return
    await fetch(`${import.meta.env.VITE_API_BASE}/tutoring/availability`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(updatableAvailabilitySlots.value)
    })
      .then(response => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        updatableAvailabilitySlots.value = []
        responseStore.addResponse(data)
      })
      .catch((err: any) => {
        throw new Error(err.message)
      })
  }

  const cancelAvailabilitySlots = async () => {
    if (cancelableAvailabilitySlotIds.value.length === 0) return
    await fetch(`${import.meta.env.VITE_API_BASE}/tutoring/availability`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(cancelableAvailabilitySlotIds.value)
    })
      .then(response => response.json())
      .then((data) => {
        if (!!data?.error) {
          throw new Error(JSON.stringify(data))
        }
        cancelableAvailabilitySlotIds.value = []
        responseStore.addResponse(data)
      })
      .catch((err: any) => {
        throw new Error(err.message)
      })
  }

  return {
    availabilitySlots,
    filteredByDateAvailabilitySlots,
    insertableAvailabilitySlots,
    filteredInsertableAvailabilitySlots,
    updatableAvailabilitySlots,
    cancelableAvailabilitySlotIds,
    availabilityPreferences,
    getAvailabilitySlots,
    addAvailabilitySlots,
    updateAvailabilitySlots,
    cancelAvailabilitySlots,
    updateUpdatableSlot,
    cancelUpdatableSlot,
    addInsertableSlot,
    updateInsertableSlot,
    removeInsertableSlot,
    getAvailabilityPreferences,
    declinePreferences,
    applyPreferences,
    handleDateChange
  };
})
