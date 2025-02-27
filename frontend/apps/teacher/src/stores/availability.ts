import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

import { useUserStore } from '@symfoititis-frontend-monorepo/stores'

import { AvailabilitySlot, Day } from '@symfoititis-frontend-monorepo/interfaces'

export const useAvailabilityStore = defineStore("availabilityStore", () => {
  const userStore = useUserStore()
  const selectedDay = ref<Day>()
  const selectedDate = ref<string>('')
  const availabilitySlots = ref<AvailabilitySlot[]>([])
  const cancelableAvailabilitySlotIds = ref<number[]>([])
  const availabilityPreferences = ref<AvailabilitySlot[]>([])
  const updatableAvailabilitySlots = ref<AvailabilitySlot[]>([])
  const insertableAvailabilitySlots = ref<AvailabilitySlot[]>([])

  const filteredByDateAvailabilitySlots = computed(() => {
    return availabilitySlots.value.filter(slot => {
      return (slot.date === selectedDate.value && !cancelableAvailabilitySlotIds.value.includes(slot.av_id!))
    })
  })
  const filteredInsertableAvailabilitySlots = computed(() => {
    return insertableAvailabilitySlots.value.filter(slot => slot.start_time > 0)
  })

  const updateUpdatableSlot = (slotKey: number, startTime: number) => {
    const i = availabilitySlots.value.findIndex((slot) => slot.av_id === slotKey)
    if (i < 0) return
    const j = updatableAvailabilitySlots.value.findIndex((slot) => slot.av_id === slotKey)
    const modifiedAndNotInUpdatableList = availabilitySlots.value[i].start_time != startTime && j < 0
    const modifiedAndExistsInUpdatableList = availabilitySlots.value[i].start_time != startTime && j >= 0
    const notModifiedAndExistsInUpdatableList = availabilitySlots.value[i].start_time === startTime && j >= 0
    if (modifiedAndNotInUpdatableList) {
      const slot = availabilitySlots.value[i]
      slot.start_time = startTime
      updatableAvailabilitySlots.value.push(slot)
    } else if (modifiedAndExistsInUpdatableList) {
      updatableAvailabilitySlots.value[j].start_time = startTime
    } else if (notModifiedAndExistsInUpdatableList) {
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
      t_id: userStore.profile.id!,
      c_id: c_id,
      dep_id: userStore.department.dep_id,
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

  return {
    availabilitySlots,
    filteredByDateAvailabilitySlots,
    insertableAvailabilitySlots,
    filteredInsertableAvailabilitySlots,
    updatableAvailabilitySlots,
    cancelableAvailabilitySlotIds,
    availabilityPreferences,
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
