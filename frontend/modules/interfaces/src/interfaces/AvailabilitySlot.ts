export interface AvailabilitySlot {
  av_id?: number
  dep_id: number
  c_id: number
  t_id: string
  date: string
  week_day: number
  start_time: number
  state?: string
}
