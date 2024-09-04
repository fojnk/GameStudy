import { StudentType } from "./student";

export interface GroupType {
    id?: number;
    title?: string;
    students?: StudentType[];
}