export interface Note {
  note_id?: number;
  c_id: number;
  type: "theory" | "lab";
  note_display_name: string;
  note_filename: string;
}
