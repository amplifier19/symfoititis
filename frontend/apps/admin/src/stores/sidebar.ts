import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useSidebarStore = defineStore('sidebar', () => {
  const classes = ref(['pf-v5-c-page__sidebar', 'pf-m-expanded'])

  const toogleState = () => {
    let idx = classes.value.indexOf('pf-m-expanded')
    if (idx >= 0) {
      classes.value[idx] = 'pf-m-collapsed'
      return
    }
    idx = classes.value.indexOf('pf-m-collapsed')
    if (idx >= 0) {
      classes.value[idx] = 'pf-m-expanded'
      return
    }
    classes.value.push('pf-m-expanded')
  }
  return { classes, toogleState }
})
