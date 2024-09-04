import { BlogType } from "./blog";
import { UserType } from "./user";

export interface TeacherType {
    id?: number;
    user?: UserType;
    organisation?: string;
    qualification?: string;
    blog?: BlogType;
    age?: number;
    birth_date?: string;
    course_ids?: number[];
    image_path?: string;
}