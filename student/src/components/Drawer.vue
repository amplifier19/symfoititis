<script setup lang="ts">
import { onMounted } from 'vue'

import { useAuthStore } from '../stores/auth'
import { useUniversity } from '../stores/university'
import { useDepartment } from '../stores/department'

import { useFetch } from '../composables/fetchService'

const authStore = useAuthStore()
const universityStore = useUniversity()
const departmentStore = useDepartment()

const { getUserInfo } = useFetch()

const signOut = () => {
  authStore.logout()
}

onMounted(async () => {
  await getUserInfo()
})
</script>

<template>
  <div class="drawer">
    <div class="profile-container">
      <div class="icon-container">
        <!-- <RouterLink :to="{ name: 'courses' }"> -->
        <img src="../assets/svg/profile-icon-orange.svg" alt="profile-icon" class="profile-icon" />
        <!-- </RouterLink> -->
      </div>
      <div class="item-container">
        <section class="drawer-item" id="username">
          <!-- <RouterLink :to="{ name: 'courses' }"> </RouterLink> -->
          {{ authStore.profile.username }}
        </section>
        <section class="drawer-item" id="uni-dep-info">
          <span>
            {{ universityStore.university.uni_display_name }}
          </span>
          <span>
            {{ departmentStore.department.dep_display_name }}
          </span>
        </section>
      </div>
    </div>
    <span @click="signOut" class="drawer-item" id="sign-out">Έξοδος</span>
  </div>
</template>

<style scoped>
.drawer {
  z-index: 1;
  position: absolute;
  right: 0;
  top: 0;
  transform: translateY(64px);
  display: flex;
  flex-direction: column;
  background-color: white;
  border: 1.5px var(--orange) solid;
  width: min-content;
  margin-right: 1rem;
}

.profile-container {
  display: flex;
  flex-direction: row;
  border-bottom: 1.5px var(--orange) solid;
  align-items: center;
}

.icon-container {
  width: 3.5rem;
  height: auto;
  margin: 1rem;
}

.profile-icon {
  width: 100%;
}

.item-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  align-content: center;
}

.drawer-item {
  white-space: nowrap;
  padding-right: 0.5rem;
}

.drawer-item:first-child {
  border-bottom: 1.5px var(--orange) solid;
}

#username {
  text-transform: uppercase;
  font-size: 1.15rem;
}

#uni-dep-info {
  margin-top: 0.5rem;
  display: flex;
  flex-direction: column;
  font-size: 0.95rem;
}

#sign-out {
  cursor: pointer;
  padding: 0.7rem 0rem;
  margin-left: 5.5rem;
}

@media screen and (max-width: 1024px) {
  .drawer {
    transform: translateY(50px);
  }

  .icon-container {
    width: 3rem;
    margin: 0.8rem;
  }

  #username {
    font-size: 1.1rem;
  }

  #uni-dep-info {
    margin-top: 0.5rem;
    font-size: 0.9rem;
  }

  #sign-out {
    padding: 0.5rem 0rem;
    margin-left: 4.5rem;
    font-size: 0.9rem;
  }
}

@media screen and (max-width: 590px) {
  .drawer {
    transform: translateY(43px);
    margin: 0;
  }

  .icon-container {
    width: 2.5rem;
    margin: 0.6rem;
  }

  .item-container {
    margin: 0.3rem 0rem;
  }

  #username {
    font-size: 1rem;
  }

  #uni-dep-info {
    margin-top: 0.5rem;
    font-size: 0.85rem;
  }

  #sign-out {
    padding: 0.3rem 0rem;
    margin-left: 3.5rem;
    font-size: 0.85rem;
  }
}
</style>
