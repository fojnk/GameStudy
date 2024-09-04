import { configureStore } from '@reduxjs/toolkit';
import CustomizerReducer from './customizer/CustomizerSlice';
import { combineReducers } from 'redux';
import {
    useDispatch as useAppDispatch,
    useSelector as useAppSelector,
    TypedUseSelectorHook,
} from 'react-redux';
import CourseReducer from './apps/courses/CourseSlise';
import DiscipineReducer from './apps/disciplines/DisciplineSlice';
import TagReducer from './apps/tags/TagSlice';
import TeacherReducer from './apps/teachers/TeacherSlice';
import AchievementReducer from './apps/achievements/AchievementSlice';
import NotificationReducer from './apps/notifications/NotificationSlice';
import LessonReducer from './apps/lessons/LessonSlice';
import TaskReducer from './apps/tasks/TaskSlice';
import GroupReducer from './apps/groups/GroupSlice';
import StudentReducer from './apps/students/StudentSlice'

export const store = configureStore({
    reducer: {
        customizer: CustomizerReducer,
        courseReduser: CourseReducer,
        disciplineReducer: DiscipineReducer,
        tagReducer: TagReducer,
        teacherReducer: TeacherReducer,
        achievementReducer: AchievementReducer,
        notificationReducer: NotificationReducer,
        lessonReducer: LessonReducer,
        taskReducer: TaskReducer,
        groupReducer: GroupReducer,
        studentReducer: StudentReducer,
    },
});

const rootReducer = combineReducers({
    customizer: CustomizerReducer,
    courseReduser: CourseReducer,
    disciplineReducer: DiscipineReducer,
    tagReducer: TagReducer,
    teacherReducer: TeacherReducer,
    achievementReducer: AchievementReducer,
    notificationReducer: NotificationReducer,
    lessonReducer: LessonReducer,
    taskReducer: TaskReducer,
    groupReducer: GroupReducer,
    studentReducer: StudentReducer,
});

export type AppState = ReturnType<typeof rootReducer>;
export type AppDispatch = typeof store.dispatch;
export const { dispatch } = store;
export const useDispatch = () => useAppDispatch<AppDispatch>();
export const useSelector: TypedUseSelectorHook<AppState> = useAppSelector;

export default store;
