import type { Department } from './Department'
import type { University } from './University'

export interface Input {
  label: string
  name: string
  value?: string | number
  placeholder?: string
  readonly?: boolean
  type: 'number' | 'text'
  required: boolean
}

export interface Select {
  name: string
  label: string
  options: string[] | University[] | Department[] | { type_name: string; type: string }[]
  optionKey?: string
  valueKey?: string
  selection?: string | number
  required: boolean
}

export interface Button {
  text: string
  classes: string[]
  type?: 'submit'
}

export interface Modal {
  title: string
  icon: string
  event: string
  inputs: Input[]
  selects?: Select[]
  buttons: Button[]
}
