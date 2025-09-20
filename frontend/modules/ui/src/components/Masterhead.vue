<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { OnClickOutside } from '@vueuse/components'
import Drawer from './Drawer.vue'
import Sidebox from './Sidebox.vue'
import { useHistory } from '@symfoititis-frontend-monorepo/composables'

const props = defineProps<{
  selected: number
}>()

const { getHistorySize } =  useHistory('history')

const drawerNew = ref<boolean>(false)
const drawerOld = ref<boolean>(false)
const clickedOutside = ref<boolean>(false)

const notesBtn = ref<HTMLElement>()
const tutoringBtn = ref<HTMLElement>()
const dashboardBtn = ref<HTMLElement>()

const initNavItems = () => {
  if (!notesBtn.value || !tutoringBtn.value || !dashboardBtn.value) {
    return
  }
  switch (props.selected) {
    case 0:
      notesBtn.value?.classList.toggle('notes-current')
      tutoringBtn.value?.classList.toggle('tutoring')
      dashboardBtn.value?.classList.toggle('dashboard')
      break;
    case 1:
      notesBtn.value?.classList.toggle('notes')
      tutoringBtn.value?.classList.toggle('tutoring-current')
      dashboardBtn.value?.classList.toggle('dashboard')
      break;
    case 2:
      notesBtn.value?.classList.toggle('notes')
      tutoringBtn.value?.classList.toggle('tutoring')
      dashboardBtn.value?.classList.toggle('dashboard-current')
      break;
    default:
      notesBtn.value?.classList.toggle('notes')
      tutoringBtn.value?.classList.toggle('tutoring')
      dashboardBtn.value?.classList.toggle('dashboard')
  }
}

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

onMounted(() => {
  initNavItems()
})
</script>

<template>
  <header id="masterhead" >

    <div class="brand-container">
      <RouterLink class="logo" :to="{ name: 'courses' }">
        <img class="pf-v5-c-brand" src="/svg/logo_hor_white.svg" alt="Symfoititis" />
      </RouterLink>
    </div>

    <nav>
      <ul class="nav-items-container">
        <RouterLink :to="{ name: 'courses'}">
          <Sidebox v-if="props.selected == 1 || props.selected == 2" :content="getHistorySize('notes_history')" id="left-sidebox" />
          <li ref="notesBtn" class="nav-item lg-font md-fw">
            Σημειώσεις
          </li>
        </RouterLink>
        <RouterLink :to="{ name: 'tutoring'}">
          <li ref="tutoringBtn" class="nav-item lg-font md-fw">
            Ιδιαίτερα
          </li>
          <Sidebox v-if="props.selected == 0 || props.selected == 2" :content="getHistorySize('bookings_history')" id="right-sidebox" />
        </RouterLink>
      </ul>
    </nav>

    <div class="icons-container">
      <span ref="dashboardBtn" class="icon-container">
          <RouterLink :to="{ name: 'bookings'}">
            <img v-if="props.selected == 2" class="side-icon" id="logo-icon-white" src="/svg/icon.svg" alt="logo-icon-white" />
            <img v-else class="side-icon" id="logo-icon-white" src="/svg/icon-white.svg" alt="logo-icon-white" />
        </RouterLink>
      </span>
    </div>

  </header>
<!--
<img @click="toggleDrawer" class="side-icon" id="profile-icon" src="/svg/profile-icon.svg" alt="profile-icon" />
  <OnClickOutside @trigger="closeDrawer">
    <transition name="drawer">
      <Drawer v-if="drawerNew" />
    </transition>
  </OnClickOutside>
-->
</template>

<style scoped>
header {
  padding-top: env(safe-area-inset-top);
  min-height: clamp(45px, 4.5vw, 65px);
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
  width: clamp(100px, 10vw, 130px);
  height: auto;
  margin-left: 1.5rem;
}

a {
  display: flex;
  align-items: center;
  justify-content: center;
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
  transform: translateY(1px);
}

.nav-items-container>a {
  display: flex;
  flex-direction: row;
  align-items: end;
}

.nav-item {
  display: flex;
  justify-content: center;
  align-items: center;
  width: clamp(160px, 16vw, 235px);
  height: clamp(40px, 4vw, 55px);
  color: var(--white);
}

.tutoring-current {
  background-color: var(--white);
  padding-right: clamp(1rem, 2.5vw, 1.3rem);
  color: var(--orange);
  transform: translateX(-10px);
  clip-path: polygon(100% 100%, 0% 100%, 0% 100%, 0.639% 99.762%, 1.245% 99.075%, 1.81% 97.975%, 2.326% 96.499%, 2.784% 94.686%, 3.178% 92.573%, 3.498% 90.196%, 3.737% 87.595%, 3.886% 84.805%, 3.937% 81.865%, 3.937% 26.39%, 3.937% 26.39%, 4.02% 22.11%, 4.258% 18.05%, 4.64% 14.264%, 5.151% 10.806%, 5.78% 7.731%, 6.513% 5.093%, 7.337% 2.946%, 8.239% 1.346%, 9.207% 0.346%, 10.226% 0%, 71.689% 0%, 71.689% 0%, 72.704% 0.195%, 73.703% 0.774%, 74.679% 1.724%, 75.626% 3.034%, 76.54% 4.693%, 77.415% 6.689%, 78.245% 9.012%, 79.024% 11.649%, 79.748% 14.589%, 80.41% 17.821%, 92.98% 85.152%, 92.98% 85.152%, 93.531% 87.861%, 94.127% 90.32%, 94.762% 92.521%, 95.434% 94.455%, 96.138% 96.115%, 96.87% 97.491%, 97.627% 98.576%, 98.403% 99.362%, 99.196% 99.839%, 100% 100%);
}

.notes-current {
  background-color: var(--white);
  padding-left: clamp(1rem, 2.5vw, 1.3rem);
  color: var(--orange);
  color: var(--orange);
  transform: translateX(10px);
  clip-path: polygon(100% 100%, 0% 100%, 0% 100%, 0.804% 99.839%, 1.597% 99.362%, 2.373% 98.576%, 3.13% 97.491%, 3.862% 96.115%, 4.566% 94.455%, 5.238% 92.521%, 5.873% 90.32%, 6.469% 87.861%, 7.02% 85.152%, 19.59% 17.821%, 19.59% 17.821%, 20.252% 14.589%, 20.976% 11.649%, 21.755% 9.012%, 22.585% 6.689%, 23.46% 4.693%, 24.374% 3.034%, 25.321% 1.724%, 26.297% 0.774%, 27.296% 0.195%, 28.311% 0%, 89.774% 0%, 89.774% 0%, 90.793% 0.346%, 91.761% 1.346%, 92.663% 2.946%, 93.487% 5.093%, 94.22% 7.731%, 94.849% 10.806%, 95.36% 14.264%, 95.742% 18.05%, 95.98% 22.11%, 96.063% 26.39%, 96.063% 81.865%, 96.063% 81.865%, 96.114% 84.805%, 96.263% 87.595%, 96.502% 90.196%, 96.822% 92.573%, 97.216% 94.686%, 97.674% 96.499%, 98.19% 97.975%, 98.755% 99.075%, 99.361% 99.762%, 100% 100%);
}

.notes,
.tutoring {
  width: clamp(135px, 13vw, 195px);
  border-top: 1px solid var(--white);
  border-right: 1px solid var(--white);
  border-left: 1px solid var(--white);
  border-top-left-radius: 14px;
  border-top-right-radius: 14px;
}

.side-icon {
  cursor: pointer;
}

.icons-container {
  padding-right: clamp(0.5rem, 2vw, 1.5rem);
  display: flex;
  align-items: flex-end;
  justify-content: center;
}

.icon-container {
  height: 100%;
  width: clamp(50px, 5vw, 65px);
  display: flex;
  align-items: center; 
  justify-content: center;
  transform: translateY(1px);
}

.dashboard-current {
  height: clamp(40px, 4vw, 55px);
  clip-path: polygon( 100% 100%,0% 100%,0% 100%,1.591% 99.813%,3.099% 99.279%,4.506% 98.425%,5.791% 97.281%,6.934% 95.876%,7.914% 94.239%,8.712% 92.398%,9.308% 90.382%,9.681% 88.22%,9.811% 85.941%,9.811% 20.324%,9.811% 20.324%,9.997% 17.028%,10.534% 13.9%,11.394% 10.984%,12.548% 8.321%,13.966% 5.953%,15.618% 3.921%,17.477% 2.269%,19.512% 1.036%,21.694% 0.266%,23.995% 0%,76.044% 0%,76.044% 0%,78.345% 0.266%,80.527% 1.036%,82.562% 2.269%,84.421% 3.921%,86.074% 5.953%,87.491% 8.321%,88.645% 10.984%,89.505% 13.9%,90.042% 17.028%,90.228% 20.324%,90.228% 85.941%,90.228% 85.941%,90.357% 88.214%,90.728% 90.37%,91.321% 92.382%,92.115% 94.221%,93.091% 95.857%,94.229% 97.262%,95.509% 98.408%,96.911% 99.265%,98.414% 99.805%,100% 100% );
  background-color: var(--white);
}


#logo-icon-white {
  height: clamp(30px, 3vw, 40px);
  width: auto;
}

#profile-icon {
  width: 30px;
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
/*
@media screen and (max-width: 1800px) {
  .pf-v5-c-brand {
    width: 160px;
  }


  li {
    font-size: 1rem !important;
  }

  .notes-current {
    padding: 0.6rem 1.5rem 0.6rem 3.5rem;
  padding: 0.9rem 5.5rem 0.9rem 3.5rem;
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
    width: 30.1px;
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
    width: 26px;
  }

  #profile-icon {
    width: 26px;
  }

  .drawer-enter-to {
    opacity: 1;
    transform: translateY(43px);
  }
}
*/

@media screen and (max-width: 590px) {
  .tutoring {
    transform: translateX(-1px);
  }
  .notes-current {
    transform: translateX(7px);
  }
  .tutoring-current {
    transform: translateX(-7px);
  }
  .logo {
    display: none;
  }

  nav {
    justify-content: space-between;
    align-items: end;
    width: auto;
    flex-grow: 1;
  }
}
</style>
