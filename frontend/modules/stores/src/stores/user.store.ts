import { ref } from 'vue'
import { defineStore } from 'pinia'
import { Department, University } from '@symfoititis-frontend-monorepo/interfaces'
import { KeycloakProfile } from 'keycloak-js'

export const useUserStore = defineStore("profileStore", () => {
    const profile = ref<KeycloakProfile>({})

    const university = ref<University>({
        uni_id: -1, uni_alt_name: '', uni_display_name: ''
    })

    const department = ref<Department>({
        dep_id: -1, uni_id: -1, dep_alt_name: '', dep_display_name: ''
    })

    return { profile, university, department }
})