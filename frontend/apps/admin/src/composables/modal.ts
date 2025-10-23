import { ref } from "vue";

import { useUniStore } from "../stores/universities.store";
import { useDepStore } from "../stores/departments.store";
import { useCourseStore } from "../stores/courses.store";
import { useNoteStore } from "../stores/notes.store";
import { useFileStore } from "../stores/files.store";
import { useDisplayModal } from "../stores/displayModal";

import type { Modal } from "../interfaces/Modal";
import type { University } from "@symfoititis-frontend-monorepo/interfaces";
import type { Course } from "@symfoititis-frontend-monorepo/interfaces";
import type { Department } from "@symfoititis-frontend-monorepo/interfaces";
import type { Note } from "@symfoititis-frontend-monorepo/interfaces";

export const useModal = () => {
  const modal = ref<Modal>({
    title: "",
    icon: "",
    event: "",
    inputs: [],
    buttons: [],
  });

  const modalStore = useDisplayModal();
  const uniStore = useUniStore();
  const depStore = useDepStore();
  const courseStore = useCourseStore();
  const noteStore = useNoteStore();
  const fileStore = useFileStore();

  /*
   *
   * University Modal
   */
  const activateUniAddModal = () => {
    modal.value = {
      title: "Add University",
      icon: "fa fa-plus-circle",
      event: "create-university",
      inputs: [
        {
          label: "Display name",
          name: "uni_display_name",
          value: "",
          type: "text",
          required: true,
        },
        {
          label: "Alt name",
          name: "uni_alt_name",
          value: "",
          type: "text",
          required: true,
        },
      ],
      buttons: [{ text: "Add", classes: ["pf-v5-c-button", "pf-m-primary"] }],
    };
    modalStore.setDisplay(true);
  };

  const activateUniEditModal = (uni: University) => {
    modal.value = {
      title: "Edit University",
      icon: "fa fa-edit",
      event: "update-university",
      inputs: [
        {
          label: "University Id",
          name: "uni_id",
          value: uni.uni_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: "Display name",
          name: "uni_display_name",
          value: uni.uni_display_name,
          type: "text",
          required: true,
        },
        {
          label: "Alt name",
          name: "uni_alt_name",
          value: uni.uni_alt_name,
          type: "text",
          required: true,
        },
      ],
      buttons: [
        {
          text: "Edit",
          classes: ["pf-v5-c-button", "pf-m-primary"],
          type: "submit",
        },
      ],
    };
    modalStore.setDisplay(true);
  };

  const activateUniRemoveModal = (uni: University) => {
    modal.value = {
      title: "Remove University",
      icon: "fa fa-trash",
      event: "delete-university",
      inputs: [
        {
          label: "University Id",
          name: "uni_id",
          value: uni.uni_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: `Are you sure you want to remove  ${uni.uni_alt_name}  ?`,
          name: "uni_alt_name",
          value: "",
          placeholder: "Type the Alt name to delete",
          type: "text",
          required: true,
        },
      ],
      buttons: [
        { text: "Remove", classes: ["pf-v5-c-button", "pf-m-tritary"] },
      ],
    };
    uniStore.currentUniversity = uni;
    modalStore.setDisplay(true);
  };

  /*
   *
   * Department Modal
   */
  const activateDepAddModal = () => {
    modal.value = {
      title: "Add Department",
      icon: "fa fa-edit",
      event: "create-department",
      inputs: [
        {
          label: "Display name",
          name: "dep_display_name",
          value: "",
          type: "text",
          required: true,
        },
        {
          label: "Alt name",
          name: "dep_alt_name",
          value: "",
          type: "text",
          required: true,
        },
      ],
      selects: [
        {
          name: "uni_id",
          label: "University",
          options: uniStore.universities,
          optionKey: "uni_display_name",
          valueKey: "uni_id",
          required: true,
        },
      ],
      buttons: [{ text: "Add", classes: ["pf-v5-c-button", "pf-m-primary"] }],
    };
    modalStore.setDisplay(true);
  };

  const activateDepEditModal = (dep: Department) => {
    modal.value = {
      title: "Edit Department",
      icon: "fa fa-edit",
      event: "update-department",
      inputs: [
        {
          label: "Department Id",
          name: "dep_id",
          value: dep.dep_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: "Display name",
          name: "dep_display_name",
          value: dep.dep_display_name,
          type: "text",
          required: true,
        },
        {
          label: "Alt name",
          name: "dep_alt_name",
          value: dep.dep_alt_name,
          type: "text",
          required: true,
        },
      ],
      selects: [
        {
          name: "uni_id",
          label: "University",
          options: uniStore.universities,
          optionKey: "uni_display_name",
          valueKey: "uni_id",
          selection: dep.uni_id,
          required: true,
        },
      ],
      buttons: [{ text: "Edit", classes: ["pf-v5-c-button", "pf-m-primary"] }],
    };
    modalStore.setDisplay(true);
  };

  const activateDepRemoveModal = (dep: Department) => {
    modal.value = {
      title: "Remove Department",
      icon: "fa fa-trash",
      event: "delete-department",
      inputs: [
        {
          label: "Department Id",
          name: "dep_id",
          value: dep.dep_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: `Are you sure you want to remove  ${dep.dep_alt_name}  ?`,
          name: "dep_alt_name",
          value: "",
          placeholder: "Type the Alt name to delete",
          type: "text",
          required: true,
        },
      ],
      buttons: [
        { text: "Remove", classes: ["pf-v5-c-button", "pf-m-tritary"] },
      ],
    };
    depStore.currentDepartment = dep;
    modalStore.setDisplay(true);
  };

  /*
   *
   * Course Modal
   */
  const activateCourseAddModal = (dep_id: number) => {
    modal.value = {
      title: "Add Course",
      icon: "fa fa-edit",
      event: "create-course",
      inputs: [
        {
          label: "Department Id",
          name: "dep_id",
          value: dep_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: "Display name",
          name: "c_display_name",
          value: "",
          type: "text",
          required: true,
        },
        {
          label: "Semester",
          name: "semester",
          value: "",
          type: "number",
          required: true,
        },
        {
          label: "Description",
          name: "description",
          value: "",
          type: "text",
          required: false,
        },
      ],
      buttons: [{ text: "Add", classes: ["pf-v5-c-button", "pf-m-primary"] }],
    };
    modalStore.setDisplay(true);
  };

  const activateCourseEditModal = (course: Course) => {
    modal.value = {
      title: "Edit Course",
      icon: "fa fa-edit",
      event: "update-course",
      inputs: [
        {
          label: "Course Id",
          name: "c_id",
          value: course.c_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: "Display name",
          name: "c_display_name",
          value: course.c_display_name,
          type: "text",
          required: true,
        },
        {
          label: "Semester",
          name: "semester",
          value: course.semester,
          type: "number",
          required: true,
        },
        {
          label: "Description",
          name: "description",
          value: course.description,
          type: "text",
          required: false,
        },
      ],
      selects: [
        {
          name: "dep_id",
          label: "Department",
          options: depStore.departments,
          optionKey: "dep_display_name",
          valueKey: "dep_id",
          selection: course.dep_id,
          required: true,
        },
      ],
      buttons: [{ text: "Edit", classes: ["pf-v5-c-button", "pf-m-primary"] }],
    };
    modalStore.setDisplay(true);
  };

  const activateCourseRemoveModal = (course: Course) => {
    modal.value = {
      title: "Remove Course",
      icon: "fa fa-trash",
      event: "delete-course",
      inputs: [
        {
          label: "Course Id",
          name: "c_id",
          value: course.c_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: "Department Id",
          name: "dep_id",
          value: course.dep_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: `Are you sure you want to remove course  ${course.c_id}  ?`,
          name: "course_id",
          value: "",
          placeholder: "Type the course Id to delete",
          type: "number",
          required: true,
        },
      ],
      buttons: [
        { text: "Remove", classes: ["pf-v5-c-button", "pf-m-tritary"] },
      ],
    };
    courseStore.currentCourse = course;
    modalStore.setDisplay(true);
  };

  /*
   *
   * Note Modal
   */
  const activateNoteAddModal = (c_id: number) => {
    modal.value = {
      title: "Add Note",
      icon: "fa fa-edit",
      event: "create-note",
      inputs: [
        {
          label: "Course Id",
          name: "c_id",
          value: c_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: "Display name",
          name: "note_display_name",
          value: "",
          type: "text",
          required: true,
        },
      ],
      selects: [
        {
          name: "note_filename",
          label: "Note filename",
          options: fileStore.filenames,
          required: false,
        },
        {
          name: "type",
          label: "Note type",
          options: [
            { type_name: "Θεωρία", type: "theory" },
            { type_name: "Εργαστήριο", type: "lab" },
          ],
          optionKey: "type_name",
          valueKey: "type",
          required: true,
        },
      ],
      buttons: [{ text: "Add", classes: ["pf-v5-c-button", "pf-m-primary"] }],
    };
    modalStore.setDisplay(true);
  };

  const activateNoteEditModal = (note: Note) => {
    modal.value = {
      title: "Edit Note",
      icon: "fa fa-edit",
      event: "update-note",
      inputs: [
        {
          label: "Note Id",
          name: "note_id",
          value: note.note_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: "Course Id",
          name: "c_id",
          value: note.c_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: "Display name",
          name: "note_display_name",
          value: note.note_display_name,
          type: "text",
          required: true,
        },
      ],
      selects: [
        {
          name: "note_filename",
          label: "Note filename",
          options: fileStore.filenames,
          selection: note.note_filename,
          required: true,
        },
        {
          name: "type",
          label: "Note type",
          options: [
            { type_name: "Θεωρία", type: "theory" },
            { type_name: "Εργαστήριο", type: "lab" },
          ],
          optionKey: "type_name",
          valueKey: "type",
          selection: note.type,
          required: true,
        },
      ],
      buttons: [{ text: "Edit", classes: ["pf-v5-c-button", "pf-m-primary"] }],
    };
    modalStore.setDisplay(true);
  };

  const activateNoteRemoveModal = (note: Note) => {
    modal.value = {
      title: "Remove Note",
      icon: "fa fa-trash",
      event: "delete-note",
      inputs: [
        {
          label: "Note Id",
          name: "note_id",
          value: note.note_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: "Course Id",
          name: "c_id",
          value: note.c_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: `Are you sure you want to remove note  ${note.note_display_name}  ?`,
          name: "note_display_name",
          value: "",
          placeholder: "Type the note display name to delete",
          type: "text",
          required: true,
        },
      ],
      buttons: [
        { text: "Remove", classes: ["pf-v5-c-button", "pf-m-tritary"] },
      ],
    };
    noteStore.currentNote = note;
    modalStore.setDisplay(true);
  };

  /*
   *
   * File Modal
   */
  const activateFileRemoveModal = (data: {
    c_id: number;
    filename: string;
  }) => {
    modal.value = {
      title: "Remove Note",
      icon: "fa fa-trash",
      event: "delete-file",
      inputs: [
        {
          label: "Course Id",
          name: "c_id",
          value: data.c_id,
          readonly: true,
          type: "number",
          required: true,
        },
        {
          label: `Are you sure you want to remove  ${data.filename}  ?`,
          name: "filename",
          value: "",
          placeholder: "Type the filename to delete",
          type: "text",
          required: true,
        },
      ],
      buttons: [
        { text: "Remove", classes: ["pf-v5-c-button", "pf-m-tritary"] },
      ],
    };
    fileStore.current_file = data.filename;
    modalStore.setDisplay(true);
  };

  return {
    modal,
    activateUniAddModal,
    activateUniEditModal,
    activateUniRemoveModal,
    activateDepAddModal,
    activateDepEditModal,
    activateDepRemoveModal,
    activateCourseAddModal,
    activateCourseEditModal,
    activateCourseRemoveModal,
    activateNoteAddModal,
    activateNoteEditModal,
    activateNoteRemoveModal,
    activateFileRemoveModal,
  };
};
