import { ref } from "vue";
import { defineStore } from "pinia";
import { type AvailabilitySlot } from "@symfoititis-frontend-monorepo/interfaces";

export const useAvailabilityStore = defineStore("availabilityStore", () => {
  const availabilitySlots = ref<AvailabilitySlot[]>([]);

  return { availabilitySlots };
});
