export interface BoardType {
    id?: number;
    title?: string;
    score_ids?: number[];
    course_id?: number[];
}

export interface ScoreType {
    id?: number;
    experience?: number;
    time?: string;
    student_id?: number;
}