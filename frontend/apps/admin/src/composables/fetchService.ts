import { useUniStore } from '../stores/universities'
import { useDepStore } from '../stores/departments'
import { useCourseStore } from '../stores/courses'
import { useNoteStore } from '../stores/notes'
import { useFileStore } from '../stores/files'
import { useDisplayModal } from '../stores/displayModal'
import { useErrorStore } from '@symfoititis-frontend-monorepo/stores'
import { useResponseStore } from '@symfoititis-frontend-monorepo/stores'
import type { University } from '@symfoititis-frontend-monorepo/interfaces'
import type { Department } from '@symfoititis-frontend-monorepo/interfaces'
import type { Course } from '@symfoititis-frontend-monorepo/interfaces'
import type { Note } from '@symfoititis-frontend-monorepo/interfaces'

export const useFetch = () => {
  const uniStore = useUniStore()
  const depStore = useDepStore()
  const courseStore = useCourseStore()
  const noteStore = useNoteStore()
  const fileStore = useFileStore()
  const modalStore = useDisplayModal()
  const errorStore = useErrorStore()
  const responseStore = useResponseStore()

  /*
   *
   * University Service
   */
  const createUniversity = async (event: Event) => {
    const { uni_display_name, uni_alt_name } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    const university: University = {
      uni_display_name: uni_display_name.toString(),
      uni_alt_name: uni_alt_name.toString()
    }
    if (uni_display_name.toString().trim() == '' || uni_alt_name.toString().trim() == '') {
      errorStore.addError({ status: 400, error: 'Bad values'})
      modalStore.setDisplay(false)
      return
    }
    try {
      const response = await uniStore.createUniversity(university)
      responseStore.addResponse(response)
      await uniStore.getUniversities()
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  const updateUniversity = async (event: Event) => {
    const { uni_id, uni_alt_name, uni_display_name } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    if (
      uni_id.toString().trim() == '' ||
      uni_display_name.toString().trim() == '' ||
      uni_alt_name.toString().trim() == ''
    ) {
      errorStore.addError({ status: 400, error: 'Bad values'})
      return
    }
    const university: University = {
      uni_id: parseInt(uni_id.toString()),
      uni_display_name: uni_display_name.toString(),
      uni_alt_name: uni_alt_name.toString()
    }
    try {
      const response = await uniStore.updateUniversity(university)
      responseStore.addResponse(response)
      await uniStore.getUniversities()
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  const deleteUniversity = async (event: Event) => {
    const { uni_id, uni_alt_name } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    if (uni_id.toString().trim() == '') {
      errorStore.addError({ status: 400, error: 'Bad values'})
      return
    }
    if (uni_alt_name.toString() != uniStore.current_uni.uni_alt_name) {
      errorStore.addError({ status: 400, error: 'Bad values'})
      return
    }
    try {
      const response = await uniStore.deleteUniversity(parseInt(uni_id.toString()))
      responseStore.addResponse(response)
      await uniStore.getUniversities()
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  /*
   *
   * Department Service
   */
  const createDepartment = async (event: Event) => {
    const { uni_id, dep_display_name, dep_alt_name } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    const department: Department = {
      uni_id: parseInt(uni_id.toString()),
      dep_display_name: dep_display_name.toString(),
      dep_alt_name: dep_alt_name.toString()
    }

    if (
      uni_id.toString() == '' ||
      dep_display_name.toString().trim() == '' ||
      dep_alt_name.toString().trim() == ''
    ) {
      errorStore.addError({ status: 400, error: "Bad values" })
      return
    }
    try {
      const response = await depStore.createDepartment(department)
      responseStore.addResponse(response)
      await depStore.getDepartments()
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  const updateDepartment = async (event: Event) => {
    const { dep_id, uni_id, dep_alt_name, dep_display_name } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    if (
      dep_id.toString() == '' ||
      uni_id.toString() == '' ||
      dep_display_name.toString().trim() == '' ||
      dep_alt_name.toString().trim() == ''
    ) {
      errorStore.addError({ status: 400, error: "Bad values" })
      return
    }
    const department: Department = {
      dep_id: parseInt(dep_id.toString()),
      uni_id: parseInt(uni_id.toString()),
      dep_display_name: dep_display_name.toString(),
      dep_alt_name: dep_alt_name.toString()
    }
    try {
      const response = await depStore.updateDepartment(department)
      responseStore.addResponse(response)
      await depStore.getDepartments()
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  const deleteDepartment = async (event: Event) => {
    const { dep_id, dep_alt_name } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    if (dep_id.toString() == '') {
      errorStore.addError({ status: 400, error: "Bad values" })
      return
    }
    if (dep_alt_name != depStore.current_dep?.dep_alt_name) {
      errorStore.addError({ status: 400, error: "Bad values" })
      return
    }
    try {
      const response = await depStore.deleteDepartment(parseInt(dep_id.toString()))
      responseStore.addResponse(response)
      await depStore.getDepartments()
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  /*
   *
   * Course Service
   */
  const createCourse = async (event: Event) => {
    const { dep_id, semester, c_display_name, description } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    if (
      dep_id.toString() == '' ||
      semester.toString() == '' ||
      c_display_name.toString().trim() == ''
    ) {
      errorStore.addError({ status: 400, error: "Bad values" })
      modalStore.setDisplay(false)
      return
    }
    const course: Course = {
      dep_id: parseInt(dep_id.toString()),
      semester: parseInt(semester.toString()),
      c_display_name: c_display_name.toString(),
      description: description.toString().trim() || undefined
    }
    try {
      const response = await courseStore.createCourse(course)
      responseStore.addResponse(response)
      await courseStore.getCourses(parseInt(dep_id.toString()))
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  const updateCourse = async (event: Event) => {
    const { c_id, dep_id, semester, c_display_name, description } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    if (
      c_id.toString().trim() == '' ||
      dep_id.toString().trim() == '' ||
      semester.toString().trim() == '' ||
      c_display_name.toString().trim() == ''
    ) {
      errorStore.addError({ status: 400, error: "Bad values" })
    }
    const course: Course = {
      c_id: parseInt(c_id.toString()),
      dep_id: parseInt(dep_id.toString()),
      semester: parseInt(semester.toString()),
      c_display_name: c_display_name.toString(),
      description: description.toString().trim() || undefined
    }
    try {
      const response = await courseStore.updateCourse(course)
      responseStore.addResponse(response)
      await courseStore.getCourses(parseInt(dep_id.toString()))
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  const deleteCourse = async (event: Event) => {
    const { c_id, dep_id, course_id } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    if (c_id.toString().trim() == '') {
      errorStore.addError({ status: 400, error: "Bad values" })
      return
    }
    if (parseInt(course_id.toString()) != courseStore.current_course?.c_id) {
      errorStore.addError({ status: 400, error: "Bad values" })
      return
    }
    try {
      const response = await courseStore.deleteCourse(parseInt(course_id.toString()))
      responseStore.addResponse(response)
      await courseStore.getCourses(parseInt(dep_id.toString()))
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  /*
   *
   * Note Service
   */
  const createNote = async (event: Event) => {
    const { c_id, type, note_display_name, note_filename } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    if (
      c_id.toString().trim() == '' ||
      (type.toString().trim() != 'theory' && type.toString().trim() != 'lab') ||
      note_display_name.toString().trim() == '' ||
      note_filename.toString().trim() == ''
    ) {
      errorStore.addError({ status: 400, error: "Bad values" })
      return
    }
    const note: Note = {
      c_id: parseInt(c_id.toString()),
      type: type.toString(),
      note_display_name: note_display_name.toString(),
      note_filename: note_filename.toString().trim()
    }
    try {
      const response = await noteStore.createNote(note)
      responseStore.addResponse(response)
      await noteStore.getNotes(parseInt(c_id.toString()))
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  const updateNote = async (event: Event) => {
    const { note_id, c_id, type, note_display_name, note_filename } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    if (
      note_id.toString().trim() == '' ||
      c_id.toString().trim() == '' ||
      type.toString().trim() == '' ||
      note_display_name.toString().trim() == '' ||
      note_filename.toString().trim() == ''
    ) {
      errorStore.addError({ status: 400, error: "Bad values" })
      modalStore.setDisplay(false)
      return
    }
    const note: Note = {
      note_id: parseInt(note_id.toString()),
      c_id: parseInt(c_id.toString()),
      type: type.toString(),
      note_display_name: note_display_name.toString(),
      note_filename: note_filename.toString().trim()
    }
    try {
      const response = await noteStore.updateNote(note)
      responseStore.addResponse(response)
      await noteStore.getNotes(parseInt(c_id.toString()))
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  const deleteNote = async (event: Event) => {
    const { note_id, c_id, note_display_name } = Object.fromEntries(
      new FormData(event.target as HTMLFormElement)
    )
    if (note_id.toString().trim() == '') {
      errorStore.addError({ status: 400, error: "Bad values" })
      modalStore.setDisplay(false)
      return
    }
    if (c_id.toString().trim() == '') {
      errorStore.addError({ status: 400, error: "Bad values" })
      modalStore.setDisplay(false)
      return
    }
    if (
      note_display_name.toString().trim() == '' ||
      note_display_name.toString() != noteStore.current_note?.note_display_name.toString()
    ) {
      errorStore.addError({ status: 400, error: "Bad values" })
      modalStore.setDisplay(false)
      return
    }
    try {
      const response = await noteStore.deleteNote(parseInt(note_id.toString()))
      responseStore.addResponse(response)
      await noteStore.getNotes(parseInt(c_id.toString()))
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  /*
   *
   * File Service
   */
  const uploadFile = async (c_id: number) => {
    try {
      const response = await fileStore.uploadFiles(c_id)
      responseStore.addResponse(response!)
      await fileStore.getFiles(c_id)
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    }
  }

  const deleteFile = async (event: Event) => {
    const { c_id, filename } = Object.fromEntries(new FormData(event.target as HTMLFormElement))
    if (c_id.toString().trim() == '') {
      errorStore.addError({ status: 400, error: "Bad values" })
      modalStore.setDisplay(false)
      return
    }
    if (filename != fileStore.current_file) {
      errorStore.addError({ status: 400, error: "Bad values" })
      modalStore.setDisplay(false)
      return
    }
    const formData = {
      c_id: parseInt(c_id.toString()),
      filename: filename.toString()
    }
    try {
      const response = await fileStore.deleteFile(formData)
      responseStore.addResponse(response)
      await fileStore.getFiles(parseInt(c_id.toString()))
    } catch (err) {
      errorStore.addError(JSON.parse(err.message))
    } finally {
      modalStore.setDisplay(false)
    }
  }

  return {
    createUniversity,
    updateUniversity,
    deleteUniversity,
    createDepartment,
    updateDepartment,
    deleteDepartment,
    createCourse,
    updateCourse,
    deleteCourse,
    createNote,
    updateNote,
    deleteNote,
    uploadFile,
    deleteFile
  }
}
