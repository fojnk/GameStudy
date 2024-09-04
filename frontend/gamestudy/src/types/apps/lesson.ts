import { TaskType } from "./task";

export interface lessonType {
    id?: number;
    title?: string;
    description?: string;
    experience?: number;
    complexity?: number;
    video_path?: string;
    discipline_id?: number;
    course_ids?: number[];
    task_ids?: number[];
    image_path?: string;
}

export interface CourseLesson {
    id?: number;
    title?: string;
    description?: string;
    experience?: number;
    complexity?: number;
    video_path?: string;
    discipline_id?: number;
    course_ids?: number[];
    tasks?: TaskType[];
    image_path?: string;
}