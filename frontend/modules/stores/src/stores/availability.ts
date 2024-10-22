import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { AvailabilitySlot } from '@symfoititis-frontend-monorepo/interfaces'

export const useAvailabilityStore = defineStore("availabilityStore", () => {
  const availabilitySlots = ref<AvailabilitySlot[]>([]);
  const getAvailability = (tid: number, cid: number) => {
    const dummySlots = [
      {
        av_id: 1,
        t_id: 1,
        c_id: 8,
        date: "2024-10-08",
        week_day: 2,
        start_time: 9
      },
      {
        av_id: 2,
        t_id: 2,
        c_id: 8,
        date: "2024-10-09",
        week_day: 3,
        start_time: 10
      },
      {
        av_id: 3,
        t_id: 3,
        c_id: 8,
        date: "2024-10-12",
        week_day: 6,
        start_time: 13
      },
      {
        av_id: 4,
        t_id: 4,
        c_id: 8,
        date: "2024-10-04",
        week_day: 5,
        start_time: 15
      },
      {
        av_id: 5,
        t_id: 1,
        c_id: 8,
        date: "2024-10-05",
        week_day: 6,
        start_time: 8
      },
      {
        av_id: 6,
        t_id: 2,
        c_id: 8,
        date: "2024-10-06",
        week_day: 0,
        start_time: 14
      },
      {
        av_id: 7,
        t_id: 3,
        c_id: 8,
        date: "2024-10-07",
        week_day: 1,
        start_time: 9
      },
      {
        av_id: 8,
        t_id: 3,
        c_id: 8,
        date: "2024-10-07",
        week_day: 1,
        start_time: 14
      },
      {
        av_id: 9,
        t_id: 4,
        c_id: 8,
        date: "2024-10-08",
        week_day: 2,
        start_time: 12
      }
    ];
    availabilitySlots.value = dummySlots.filter((slot) => slot.t_id === tid && slot.c_id == cid);
  };
  return { availabilitySlots, getAvailability };
});
