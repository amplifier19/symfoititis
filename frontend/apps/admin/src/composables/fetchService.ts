import { useErrorStore, useResponseStore } from '@symfoititis-frontend-monorepo/stores'

import type { University, Department, Course, Note } from '@symfoititis-frontend-monorepo/interfaces'

import { UniversitiesDataService } from '../core/services/universities/universities-data.service'
import { DepartmentsDataService } from '../core/services/departments/departments-data.service'
import { CoursesDataService } from '../core/services/courses/courses-data.service'
import { NotesDataService } from '../core/services/notes/notes-data.service'
import { FileDataService } from '../core/services/files/files-data.service'

export const useFetch = () => {
  const errorStore = useErrorStore()

  const universitiesDataService = UniversitiesDataService.getUniversitiesDataFactory()
  const departmentsDataService = DepartmentsDataService.getDepartmentsDataFactory()
  const coursesDataService = CoursesDataService.getCoursesDataFactory()
  const notesDataService = NotesDataService.getNotesDataFactory()
  const fileDataService = FileDataService.getFilesDataFactory()


  // Universities
  const getUniversities = async () => {
    await universitiesDataService.getUniversities()
  }

  const createUniversity = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const uni_display_name = formData.get("uni_display_name")?.toString().trim() || ""
    const uni_alt_name = formData.get("uni_alt_name")?.toString().trim() || ""
    const university: University = { uni_display_name, uni_alt_name }

    await universitiesDataService.createUniversity(university)
  }

  const updateUniversity = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const uni_id = parseInt(formData.get("uni_id")?.toString().trim() || "-1")
    const uni_display_name = formData.get("uni_display_name")?.toString().trim() || ""
    const uni_alt_name = formData.get("uni_alt_name")?.toString().trim() || ""

    const university: University = {
      uni_id,
      uni_display_name,
      uni_alt_name
    }
    await universitiesDataService.updateUniversity(university)
    await universitiesDataService.getUniversities(true)
  }

  const deleteUniversity = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const uni_id = formData.get("uni_id")?.toString() || ""
    const uni_alt_name = formData.get("uni_alt_name")?.toString().trim() || ""

    await universitiesDataService.deleteUniversity(parseInt(uni_id), uni_alt_name)
  }


  // Departments
  const getDepartments = async () => {
    await departmentsDataService.getDepartments()
  }

  const createDepartment = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const uni_id = formData.get("uni_id")?.toString() || ""
    const dep_display_name = formData.get("dep_display_name")?.toString().trim() || ""
    const dep_alt_name = formData.get("dep_alt_name")?.toString().trim() || ""

    const department: Department = {
      uni_id: parseInt(uni_id),
      dep_display_name,
      dep_alt_name
    }

    await departmentsDataService.createDepartment(department)
  }

  const updateDepartment = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const dep_id = formData.get("dep_id")?.toString() || ""
    const uni_id = formData.get("uni_id")?.toString() || ""
    const dep_display_name = formData.get("dep_display_name")?.toString().trim() || ""
    const dep_alt_name = formData.get("dep_alt_name")?.toString().trim() || ""

    const department: Department = {
      dep_id: parseInt(dep_id),
      uni_id: parseInt(uni_id),
      dep_display_name,
      dep_alt_name
    }

    await departmentsDataService.updateDepartment(department)
  }

  const deleteDepartment = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const dep_id = formData.get("dep_id")?.toString() || ""
    const dep_alt_name = formData.get("dep_alt_name")?.toString() || ""

    await departmentsDataService.deleteDepartment(parseInt(dep_id), dep_alt_name)
  }


  // Courses
  const getCourses = async (dep_id: number) => {
    await coursesDataService.getCourses(dep_id)
  }

  const getCourse = async (c_id: number) => {
    await coursesDataService.getCourse(c_id)
  }

  const createCourse = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const dep_id = formData.get("dep_id")?.toString() || ""
    const semester = formData.get("semester")?.toString() || ""
    const c_display_name = formData.get("c_display_name")?.toString().trim() || ""
    const description = formData.get("description")?.toString().trim() || ""

    const course: Course = {
      dep_id: parseInt(dep_id.toString()),
      semester: parseInt(semester.toString()),
      c_display_name,
      description
    }

    await coursesDataService.createCourse(course)
  }

  const updateCourse = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const c_id = formData.get("c_id")?.toString() || ""
    const dep_id = formData.get("dep_id")?.toString() || ""
    const semester = formData.get("semester")?.toString() || ""
    const c_display_name = formData.get("c_display_name")?.toString()?.trim() || ""
    const description = formData.get("description")?.toString() || ""

    const course: Course = {
      c_id: parseInt(c_id),
      dep_id: parseInt(dep_id),
      semester: parseInt(semester),
      c_display_name,
      description
    }
    await coursesDataService.updateCourse(course)
  }

  const deleteCourse = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const course_id = formData.get("course_id")?.toString() || ""
    await coursesDataService.deleteCourse(parseInt(course_id))
  }


  // Notes
  const getNotes = async (c_id: number) => {
    await notesDataService.getNotes(c_id)
  }

  const createNote = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const c_id = formData.get("c_id")?.toString() || ""
    const type = formData.get("type")?.toString().trim()
    const note_display_name = formData.get("note_display_name")?.toString().trim() || ""
    const note_filename = formData.get("note_filename")?.toString().trim() || ""

    if (type !== "lab" && type !== "theory") {
      errorStore.addError("Wrong Note type")
      return
    }

    const note: Note = {
      c_id: parseInt(c_id),
      type,
      note_display_name,
      note_filename
    }

    await notesDataService.createNote(note)
  }

  const updateNote = async (event: Event) => {
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const note_id = formData.get("note_id")?.toString() || ""
    const c_id = formData.get("c_id")?.toString() || ""
    const type = formData.get("type")?.toString().trim()
    const note_display_name = formData.get("note_display_name")?.toString().trim() || ""
    const note_filename = formData.get("note_filename")?.toString().trim() || ""

    if (type !== "lab" && type != "theory") {
      errorStore.addError("Wrong Type value")
      return
    }

    const note: Note = {
      note_id: parseInt(note_id),
      c_id: parseInt(c_id),
      type,
      note_display_name,
      note_filename
    }

    await notesDataService.updateNote(note)
  }

  const deleteNote = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const note_id = formData.get("note_id")?.toString() || ""
    const note_display_name = formData.get("note_display_name")?.toString().trim() || ""

    await notesDataService.deleteNote(parseInt(note_id), note_display_name)
  }

  // Files 
  const generatePresignedUrl = async (c_id: number, filename: string) => {
    const attachmentUrl = await fileDataService.generatePresignedUrl(c_id, filename)
    return attachmentUrl
  }

  const getFiles = async (c_id: number) => {
    await fileDataService.getFiles(c_id)
  }

  const uploadFiles = async (c_id: number) => {
    await fileDataService.uploadFiles(c_id)
  }

  const deleteFile = async (event: Event) => {
    event.preventDefault()
    const form = event.target as HTMLFormElement
    const formData = new FormData(form)

    const c_id = formData.get("c_id")?.toString() || ""
    const filename = formData.get("filename")?.toString().trim() || ""

    await fileDataService.deleteFile(parseInt(c_id), filename)
  }

  return {
    getUniversities,
    createUniversity,
    updateUniversity,
    deleteUniversity,
    getDepartments,
    createDepartment,
    updateDepartment,
    deleteDepartment,
    getCourses,
    getCourse,
    createCourse,
    updateCourse,
    deleteCourse,
    getNotes,
    createNote,
    updateNote,
    deleteNote,
    generatePresignedUrl,
    getFiles,
    uploadFiles,
    deleteFile
  }
}
