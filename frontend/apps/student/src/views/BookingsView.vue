<script setup lang="ts">
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue";
import { OnClickOutside } from "@vueuse/components";

import { useBookingsDataService } from "@symfoititis-frontend-monorepo/core/services";
import {
  useCoursesDataService,
  useChatDataService,
} from "@symfoititis-frontend-monorepo/core/services";

import { useBookingStore } from "@symfoititis-frontend-monorepo/stores";

import { Page } from "@symfoititis-frontend-monorepo/ui";
import { Toasts } from "@symfoititis-frontend-monorepo/ui";
import { Masterhead } from "@symfoititis-frontend-monorepo/ui";
import { SearchHeader } from "@symfoititis-frontend-monorepo/ui";
import { BookingsGallery } from "@symfoititis-frontend-monorepo/ui";
import BookingProgram from "modules/ui/src/components/BookingProgram.vue";
import BuyHours from "../components/BuyHours.vue";
import ProductsModal from "../components/ProductsModal.vue";

import { PurchaseDataService } from "../core/services/purchase/purchase-data.service";
import { usePurchaseStore } from "../stores/purchase";

const { getCourses } = useCoursesDataService();
const { getBookings } = useBookingsDataService();
const { connectToStompServer, getChatStats } = useChatDataService();
const purchaseDataService = PurchaseDataService.getPurchaseDataFactory();

const bookingStore = useBookingStore();
const purchaseStore = usePurchaseStore();
const { bookings, upcomingBookings, pastBookings } = storeToRefs(bookingStore);
const { currentProduct } = storeToRefs(purchaseStore)
const openModal = ref<boolean>(false);

const toggleProductsModal = (event: Event) => {
  openModal.value = !openModal.value;
}

const closeModal = () => {
  currentProduct.value = null
  openModal.value = false
}

onMounted(async () => {
  connectToStompServer();
  Promise.all([
    purchaseDataService.getPurchaseProducts(),
    purchaseDataService.getStudentBalance(),
    getChatStats()
  ]);
  await getCourses();
  await getBookings();
});
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="2" />
    </template>
    <template v-slot:main>
      <div class="history-fill"></div>
      <SearchHeader title="Χαρτοφυλάκιο" :display-search="false" />
      <BuyHours @toggle-products-modal="toggleProductsModal"  />
      <BookingsGallery
        :bookings="upcomingBookings"
        header="Προγραμματισμένα Μαθήματα"
      />
      <BookingProgram :bookings="bookings" />
      <BookingsGallery
        :bookings="pastBookings"
        header="Περασμένη δραστηριότητα"
      />
    </template>
  </Page>
    <div v-if="openModal" class="modal-backdrop" @click="openModal = false"></div>
    <OnClickOutside v-if="openModal" @trigger="closeModal">
      <ProductsModal @close-modal="closeModal" />
    </OnClickOutside>
</template>

<style scoped>
.main-container {
  display: flex;
}

.history-fill {
  min-height: clamp(50px, 3vw, 60px);
}

.modal-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,0.5);
  z-index: 1100;
}
</style>
