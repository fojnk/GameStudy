export interface CourseType {
    id?: number;
    title?: string;
    description?: string;
    creator_uid?: number;
    cost?: number;
    rating?: number;
    amt_passed_users?: number;
    image_path?: string;
    tag_ids?: number[];
    teacher_ids?: number[];
    student_ids?: number[];
    lesson_ids?: number[];
    task_ids?: number[];
    board_ids?: number[];
    created_at?: string;
    endDate?: string;
}

export interface CourseProgressType {
    id?: number;
    reached_exp?: number;
    course_id?: number;
    student_id?: number;
    passed_task_ids: number[];
    passed_lesson_ids: number[];
}