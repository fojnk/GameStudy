export interface TaskType {
    id?: number;
    title?: string;
    description?: string;
    time?: string;
    experience?: number;
    complexity?: number;
    answer?: string;
    discipline_id?: number;
    course_ids?: number[];
    lesson_ids?: number[];
    task_result_ids?: number[];
    game_method_id?: number;
    video_path?: string;
}