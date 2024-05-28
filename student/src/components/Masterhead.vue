<script setup lang="ts">
import { ref } from 'vue'
import { OnClickOutside } from '@vueuse/components'

import Drawer from './Drawer.vue'

const props = defineProps<{ selected: number }>()

const drawerNew = ref<boolean>(false)
const drawerOld = ref<boolean>(false)
const clickedOutside = ref<boolean>(false)

const navItems = [
  { name: 'Σημειώσεις', link: 'courses', class: 'notes' },
  { name: 'Ιδιαίτερα', link: '#', class: 'tutoring' }
]

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

navItems.forEach((item, idx) => {
  if (idx == props.selected) {
    item.class = `${item.class}-current`
  }
})
</script>

<template>
  <header>
    <div class="brand-container">
      <RouterLink class="logo" :to="{ name: 'courses' }">
        <img class="pf-v5-c-brand" src="../assets/svg/logo_hor_white.svg" alt="Symfoititis" />
      </RouterLink>
    </div>
    <nav>
      <ul class="nav-items-container">
        <RouterLink v-for="navItem in navItems" :key="navItem.name" :to="{ name: 'courses' }">
          <li class="nav-item" :class="navItem.class">
            {{ navItem.name }}
          </li>
        </RouterLink>
      </ul>
    </nav>
    <span class="icons-container">
      <img
        class="side-icon"
        id="logo-icon-white"
        src="../assets/svg/icon-white.svg"
        alt="logo-icon-white"
      />
      <img
        @click="toggleDrawer"
        class="side-icon"
        id="profile-icon"
        src="../assets/svg/profile-icon.svg"
        alt="profile-icon"
      />
    </span>
  </header>
  <OnClickOutside @trigger="closeDrawer">
    <Drawer v-if="drawerNew" />
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
  display: flex;
  flex-direction: row;
  transform: translateY(2px);
}

.nav-items-container > a {
  transform: translateX(-0.75rem);
}

.nav-item,
li {
  font-size: 1.15rem;
  color: var(--white);
}

.notes-current {
  padding: 0.9rem 3.5rem 0.9rem 5.5rem;
  background-color: var(--white);
  color: var(--orange);
  clip-path: polygon(
    100% 100%,
    0% 100%,
    0% 100%,
    0.804% 99.839%,
    1.597% 99.362%,
    2.373% 98.576%,
    3.13% 97.491%,
    3.862% 96.115%,
    4.566% 94.455%,
    5.238% 92.521%,
    5.873% 90.32%,
    6.469% 87.861%,
    7.02% 85.152%,
    19.59% 17.821%,
    19.59% 17.821%,
    20.252% 14.589%,
    20.976% 11.649%,
    21.755% 9.012%,
    22.585% 6.689%,
    23.46% 4.693%,
    24.374% 3.034%,
    25.321% 1.724%,
    26.297% 0.774%,
    27.296% 0.195%,
    28.311% 0%,
    89.774% 0%,
    89.774% 0%,
    90.793% 0.346%,
    91.761% 1.346%,
    92.663% 2.946%,
    93.487% 5.093%,
    94.22% 7.731%,
    94.849% 10.806%,
    95.36% 14.264%,
    95.742% 18.05%,
    95.98% 22.11%,
    96.063% 26.39%,
    96.063% 81.865%,
    96.063% 81.865%,
    96.114% 84.805%,
    96.263% 87.595%,
    96.502% 90.196%,
    96.822% 92.573%,
    97.216% 94.686%,
    97.674% 96.499%,
    98.19% 97.975%,
    98.755% 99.075%,
    99.361% 99.762%,
    100% 100%
  );
  transform: translateX(0.75rem);
}

.tutoring {
  padding: 0.9rem 3.5rem;
  border-top: 1px solid var(--white);
  border-right: 1px solid var(--white);
  border-top-right-radius: 14px;
  transform: translateX(-0.75rem);
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
  width: calc(53% - 1rem);
}

#profile-icon {
  width: calc(42% - 1rem);
}

@media screen and (max-width: 1024px) {
  .pf-v5-c-brand {
    width: 160px;
  }

  li {
    font-size: 1rem !important;
  }

  .notes-current {
    padding: 0.6rem 1.5rem 0.6rem 3.5rem;
  }

  .tutoring {
    padding: 0.6rem 1.5rem;
    transform: translateX(-0.2rem);
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
}

@media screen and (max-width: 590px) {
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

  .tutoring {
    padding: 0.4rem 1rem 0.4rem 1.5rem;
  }

  .logo {
    display: none;
  }

  .icons-container {
    min-width: 100px;
    margin: 0rem;
    margin-top: 0.4rem;
    margin-right: 0.8rem;
    gap: 1rem;
  }
}
</style>
