import { useCourseStore } from '../../../stores/courses.store'
import { CoursesApiService } from './courses-api.service'
import { useErrorStore, useResponseStore } from '@symfoititis-frontend-monorepo/stores'
import type { Course } from '@symfoititis-frontend-monorepo/interfaces'
import { useDisplayModal } from '../../../stores/displayModal'

export class CoursesDataService {
  private static instance = new CoursesDataService()
  private apiService = CoursesApiService.getCoursesApiFactory()
  private courseStore = useCourseStore()
  private responseStore = useResponseStore()
  private errorStore = useErrorStore()
  private modalStore = useDisplayModal()

  public constructor() { }

  public static getCoursesDataFactory() {
    return this.instance
  }

  public getCourses = async (dep_id: number, force?: boolean) => {
    try {
      const response = await this.apiService.getCourses(dep_id)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.courseStore.setCourses(data.data)
      }
    } catch (err) {
      this.errorStore.addError(err)
    }
  }

  public getCourse = async (c_id: number) => {
    try {
      const response = await this.apiService.getCourse(c_id)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.courseStore.setCurrentCourse(data.data)
      }
    } catch (err) {
      this.errorStore.addError(err)
    }
  }

  public createCourse = async (course: Course) => {
    try {
      const response = await this.apiService.createCourse(course)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.responseStore.addResponse(data)
        this.getCourses(course.dep_id, true)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
    }
  }

  public updateCourse = async (course: Course) => {
    try {
      const response = await this.apiService.updateCourse(course)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.responseStore.addResponse(data)
        this.getCourses(course.dep_id, true)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
    }
  }

  public deleteCourse = async (c_id: number) => {
    try {
      if (c_id !== this.courseStore.currentCourse?.c_id) {
        throw "Wrong Course Id"
      }
      const response = await this.apiService.deleteCourse(c_id)
      const data = await response.json()
      if (!!data?.error) {
        this.errorStore.addError(data.error)
      } else {
        this.responseStore.addResponse(data)
        this.getCourses(this.courseStore.currentCourse.dep_id, true)
      }
    } catch (err) {
      this.errorStore.addError(err)
    } finally {
      this.modalStore.setDisplay(false)
    }
  }
}

