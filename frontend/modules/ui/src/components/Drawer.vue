<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { useUserStore } from '@symfoititis-frontend-monorepo/stores';
import { AuthAdapterService } from '@symfoititis-frontend-monorepo/core/services';

const authAdapterService = AuthAdapterService.getAuthAdapterFactory()
const { profile, university, department } = storeToRefs(useUserStore())

const signOut = () => {
  authAdapterService.logout()
}
</script>

<template>
  <div class="drawer">
    <div class="profile-container">
      <div class="icon-container">
        <img src="/svg/profile-icon-orange.svg" alt="profile-icon" class="profile-icon" />
      </div>
      <div class="item-container">
        <section class="drawer-item" id="username">
          {{ profile.username }}
        </section>
        <section class="drawer-item" id="uni-dep-info">
          <span>
            {{ university.uni_display_name }}
          </span>
          <span>
            {{ department.dep_display_name }}
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

@media screen and (max-width: 1800px) {
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

@media screen and (max-width: 1300px) {
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
