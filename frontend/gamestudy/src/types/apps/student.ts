import { BlogType } from "./blog";
import { UserType } from "./user";

export interface StudentType {
    id?: number;
    user?: UserType;
    wallet?: number;
    experience?: number;
    activity?: number;
    age?: number;
    blog?: BlogType;
    birth_date?: string;
    achievement_ids: number[];
    course_ids?: number[]
    image_path?: string;
    groups_ids?: number[];
}

export interface StudentPostType {
    id?: number;
    user?: number;
    wallet?: number;
    experience?: number;
    activity?: number;
    age?: number;
    blog?: number;
    birth_date?: string;
    achievement_ids: number[];
    course_ids?: number[]
}