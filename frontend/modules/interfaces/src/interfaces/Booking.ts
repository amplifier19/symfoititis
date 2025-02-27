export interface Booking {
    b_id?: number
    av_id: number
    c_id: number
    s_id: string
    room: string
    state: string
    t_id: string
    date: string
    start_time: number
    student_name?: string
    teacher_firstname?: string
    teacher_lastname?: string 
}