import { useDepStore } from '../../../stores/departments.store'
import { DepartmentsApiService } from './departments-api.service'
import { useErrorStore, useResponseStore } from '@symfoititis-frontend-monorepo/stores'
import { useDisplayModal } from '../../../stores/displayModal'
import type { Department } from '@symfoititis-frontend-monorepo/interfaces'

export class DepartmentsDataService {
  private static instance = new DepartmentsDataService()
  private apiService = DepartmentsApiService.getDepartmentsApiFactory()
  private departmentStore = useDepStore()
  private responseStore = useResponseStore()
  private errorStore = useErrorStore()
  private modalStore = useDisplayModal()

  public constructor() { }

  public static getDepartmentsDataFactory() {
    return this.instance
  }

  public getDepartments = async (force?: boolean) => {
    if (this.departmentStore.departments.length > 0 && !force) {
      return
    }
    try {
      const response = await this.apiService.getDepartments()
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.departmentStore.setDepartments(data.data)
      }
    } catch (err) {
      this.errorStore.addError(err)
    }
  }

  public createDepartment = async (dep: Department) => {
    try {
      const response = await this.apiService.createDepartment(dep)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.responseStore.addResponse(data)
        this.getDepartments(true)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
    }
  }

  public updateDepartment = async (dep: Department) => {
    try {
      const response = await this.apiService.updateDepartment(dep)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.responseStore.addResponse(data)
        this.getDepartments(true)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
    }
  }

  public deleteDepartment = async (dep_id: number, dep_alt_name: string) => {
    try {
      if (!this.departmentStore.currentDepartment) {
        throw "Current department is not set"
      }
      if (dep_alt_name !== this.departmentStore.currentDepartment.dep_alt_name) {
        throw "Wrong department alt name"
      }
      const response = await this.apiService.deleteDepartment(dep_id)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.responseStore.addResponse(data)
        this.getDepartments(true)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
    }
  }
}

