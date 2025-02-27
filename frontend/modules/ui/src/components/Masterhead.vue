<script setup lang="ts">
import { ref } from 'vue'
import { OnClickOutside } from '@vueuse/components'
import Drawer from './Drawer.vue'
import { useHistory } from '@symfoititis-frontend-monorepo/composables'

const props = defineProps<{
  selected: number
}>()

const navItems = [
  { name: 'Σημειώσεις', link: 'courses', class: 'notes', sidebox: 'left', history: 'notes_history' },
  { name: 'Ιδιαίτερα', link: 'tutoring', class: 'tutoring', sidebox: 'right', history: 'bookings_history' }
]

const { history } =  useHistory(navItems[Math.abs(props.selected - 1)].history)

const sideboxContent = history.value.length
const drawerNew = ref<boolean>(false)
const drawerOld = ref<boolean>(false)
const clickedOutside = ref<boolean>(false)

const initNavItems = () => {
  navItems.forEach((item, idx) => {
    if (idx == props.selected) {
      item.class = `${item.class}-current`
    }
  })
}
initNavItems()

const toggleDrawer = () => {
  if (clickedOutside.value) {
    drawerNew.value = drawerOld.value
  }
  drawerOld.value = drawerNew.value
  drawerNew.value = !drawerNew.value
  clickedOutside.value = false
}

const closeDrawer = () => {
  drawerOld.value = drawerNew.value
  drawerNew.value = false
  clickedOutside.value = true
}
</script>

<template>
  <header>
    <div class="brand-container">
      <RouterLink class="logo" :to="{ name: 'courses' }">
        <img class="pf-v5-c-brand" src="/svg/logo_hor_white.svg" alt="Symfoititis" />
      </RouterLink>
    </div>
    <nav>
      <ul class="nav-items-container">
        <RouterLink v-for="(navItem, idx) in navItems" :key="navItem.name" :to="{ name: navItem.link }">
          <li v-if="props.selected === 1 && navItem.sidebox === 'left' && sideboxContent > 0" class="sidebox" id="left-sidebox">
            {{ sideboxContent }}
          </li>
          <li class="nav-item" :class="navItem.class">
            {{ navItem.name }}
          </li>
          <li v-if="props.selected === 0 && navItem.sidebox == 'right'&& sideboxContent > 0" class="sidebox" id="right-sidebox">
            {{ sideboxContent }}
          </li>
        </RouterLink>
      </ul>
    </nav>
    <span class="icons-container">
      <RouterLink :to="{ name: 'bookings'}">
        <img class="side-icon" id="logo-icon-white" src="/svg/icon-white.svg" alt="logo-icon-white" />
      </RouterLink>
      <img @click="toggleDrawer" class="side-icon" id="profile-icon" src="/svg/profile-icon.svg" alt="profile-icon" />
    </span>
  </header>
  <OnClickOutside @trigger="closeDrawer">
    <transition name="drawer">
      <Drawer v-if="drawerNew" />
    </transition>
  </OnClickOutside>
</template>

<style scoped>
header {
  display: flex;
  flex-direction: row;
  background-color: var(--orange);
}

.brand-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.pf-v5-c-brand {
  width: 200px;
  height: auto;
  margin: 1rem 0px 0.5rem 1rem;
}

nav {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: end;
  width: auto;
  flex-grow: 1;
}

.nav-items-container {
  font-family: 'Geologica-Medium';
  display: flex;
  flex-direction: row;
  transform: translateY(1px);
}

.nav-items-container>a {
  display: flex;
  flex-direction: row;
  align-items: end;
}

.sidebox {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 65%;
  width: 40px;
  border-top: 2px solid var(--white);
  color: var(--white);
}

#left-sidebox {
  border-left: 2px solid var(--white);
  border-top-left-radius: 14px;
}

#right-sidebox {
  border-right: 2px solid var(--white);
  border-top-right-radius: 14px;
}

.nav-item {
  font-size: 1.15rem;
  color: var(--white);
  display: flex;
  align-items: center;
}

.tutoring-current {
  padding: 0.9rem 5.5rem 0.9rem 3.5rem;
  background-color: var(--white);
  color: var(--orange);
  transform: translateX(-10px);
  clip-path: polygon(100% 100%, 0% 100%, 0% 100%, 0.639% 99.762%, 1.245% 99.075%, 1.81% 97.975%, 2.326% 96.499%, 2.784% 94.686%, 3.178% 92.573%, 3.498% 90.196%, 3.737% 87.595%, 3.886% 84.805%, 3.937% 81.865%, 3.937% 26.39%, 3.937% 26.39%, 4.02% 22.11%, 4.258% 18.05%, 4.64% 14.264%, 5.151% 10.806%, 5.78% 7.731%, 6.513% 5.093%, 7.337% 2.946%, 8.239% 1.346%, 9.207% 0.346%, 10.226% 0%, 71.689% 0%, 71.689% 0%, 72.704% 0.195%, 73.703% 0.774%, 74.679% 1.724%, 75.626% 3.034%, 76.54% 4.693%, 77.415% 6.689%, 78.245% 9.012%, 79.024% 11.649%, 79.748% 14.589%, 80.41% 17.821%, 92.98% 85.152%, 92.98% 85.152%, 93.531% 87.861%, 94.127% 90.32%, 94.762% 92.521%, 95.434% 94.455%, 96.138% 96.115%, 96.87% 97.491%, 97.627% 98.576%, 98.403% 99.362%, 99.196% 99.839%, 100% 100%);
}

.notes-current {
  padding: 0.9rem 3rem 0.9rem 5.5rem;
  background-color: var(--white);
  color: var(--orange);
  transform: translateX(10px);
  clip-path: polygon(100% 100%, 0% 100%, 0% 100%, 0.804% 99.839%, 1.597% 99.362%, 2.373% 98.576%, 3.13% 97.491%, 3.862% 96.115%, 4.566% 94.455%, 5.238% 92.521%, 5.873% 90.32%, 6.469% 87.861%, 7.02% 85.152%, 19.59% 17.821%, 19.59% 17.821%, 20.252% 14.589%, 20.976% 11.649%, 21.755% 9.012%, 22.585% 6.689%, 23.46% 4.693%, 24.374% 3.034%, 25.321% 1.724%, 26.297% 0.774%, 27.296% 0.195%, 28.311% 0%, 89.774% 0%, 89.774% 0%, 90.793% 0.346%, 91.761% 1.346%, 92.663% 2.946%, 93.487% 5.093%, 94.22% 7.731%, 94.849% 10.806%, 95.36% 14.264%, 95.742% 18.05%, 95.98% 22.11%, 96.063% 26.39%, 96.063% 81.865%, 96.063% 81.865%, 96.114% 84.805%, 96.263% 87.595%, 96.502% 90.196%, 96.822% 92.573%, 97.216% 94.686%, 97.674% 96.499%, 98.19% 97.975%, 98.755% 99.075%, 99.361% 99.762%, 100% 100%);
}

.notes,
.tutoring {
  height: calc(100% - 4px);
  padding: 0.9rem 3.5rem;
  border-top: 2px solid var(--white);
  border-left: 2px solid var(--white);
  border-right: 2px solid var(--white);
  border-top-left-radius: 14px;
  border-top-right-radius: 14px;
}

.notes {
  padding: 0.9rem 3rem;
}

.icons-container {
  display: flex;
  flex-direction: row;
  min-width: 120px;
  height: auto;
  margin: 0rem 1.3rem;
  gap: 2rem;
  justify-content: center;
  align-items: center;
}

.side-icon {
  cursor: pointer;
}

#logo-icon-white {
  width: 47.5px;
}

#profile-icon {
  width: 35px;
}

.drawer-enter-from {
  opacity: 0;
  transform: translateY(-60px);
}

.drawer-enter-to {
  opacity: 1;
  transform: translateY(64px);
}

.drawer-enter-active {
  transition: 0.2s all ease-in;
}

.drawer-leave-to {
  opacity: 0;
  transform: translateY(-60px);
}

.drawer-leave-active {
  transition: 0.2s all ease-out;
}

@media screen and (max-width: 1800px) {
  .pf-v5-c-brand {
    width: 160px;
  }

  li {
    font-size: 1rem !important;
  }

  .notes-current {
    padding: 0.6rem 1.5rem 0.6rem 3.5rem;
  }

  .tutoring-current {
    padding: 0.6rem 3.5rem 0.6rem 1.5rem;
  }

  .notes {
    padding: 0.6rem 1.5rem;
  }

  .tutoring {
    padding: 0.6rem 1.5rem;
  }

  .icons-container {
    min-width: 110px;
    margin: 0rem 0.8rem;
    gap: 1rem;
    margin-top: 0.6rem;
  }

  .pf-v5-c-brand {
    margin: 0.85rem 0px 0.2rem 1rem;
  }

  #logo-icon-white {
    width: 42.2px;
  }

  #profile-icon {
    width: 30.1px;
  }

  .drawer-enter-to {
    opacity: 1;
    transform: translateY(50px);
  }
}

@media screen and (max-width: 1300px) {
  .nav-items-container {
    margin: 0rem;
    margin-top: 0.4rem;
  }

  li {
    font-size: 1rem !important;
  }

  .notes-current {
    padding: 0.4rem 1.5rem 0.4rem 2.5rem;
  }

  .tutoring-current {
    padding: 0.4rem 2.5rem 0.4rem 1.5rem;
  }

  .tutoring {
    padding: 0.4rem 1rem 0.4rem 1.5rem;
  }

  .notes {
    padding: 0.4rem 1rem 0.4rem 1.5rem;
  }

  .icons-container {
    min-width: 100px;
    margin: 0rem;
    margin-top: 0.4rem;
    margin-right: 0.8rem;
    gap: 1rem;
  }

  #logo-icon-white {
    width: 37px;
  }

  #profile-icon {
    width: 26px;
  }

  .drawer-enter-to {
    opacity: 1;
    transform: translateY(43px);
  }
}


@media screen and (max-width: 590px) {
  .logo {
    display: none;
  }
}
</style>
