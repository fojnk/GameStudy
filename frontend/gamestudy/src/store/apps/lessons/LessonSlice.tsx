import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';
import { lessonType } from 'src/types/apps/lesson';
import { TaskType } from 'src/types/apps/task';
import axios from 'src/utils/axios';

interface StateType {
    lessons: any[];
    lessonSearch: string;
    sortBy: string;
    selectedLesson: any;
}

const initialState = {
    lessons: [],
    lessonSearch: '',
    sortBy: 'newest',
    selectedLesson: null,
};

export const LessonSlice = createSlice({
    name: 'Lesson',
    initialState,
    reducers: {
        getLessons: (state: StateType, action) => {
            state.lessons = action.payload;
        },
        addLesson: (state: StateType, action) => {
            state.lessons = state.lessons.concat([action.payload]);
        },

        getLesson: (state: StateType, action) => {
            state.selectedLesson = action.payload;
        },
        deleteLesson: (state: StateType, action) => {
            state.lessons = state.lessons.filter((item) => (item.id != action.payload.id));
        },
    },
});

export const { getLesson, getLessons, deleteLesson, addLesson } = LessonSlice.actions;

export const fetchLessons = () => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/lessons');
        dispatch(getLessons(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const addTaskToLesson = (id1: string, task: TaskType) => async (dispatch: AppDispatch) => {
    try {
        await axios.put('/api/v1/lessons/' + id1 + '/tasks?task-id=' + task.id);
    } catch (err) {
        throw new Error();
    }
};

export const fetchCourseLessons = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/courses/' + id + '/lessons');
        dispatch(getLessons(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const addLessonToCourse = (id1: string, lesson: lessonType) => async (dispatch: AppDispatch) => {
    try {
        await axios.put('/api/v1/courses/' + id1 + '/lessons?lesson-id=' + lesson.id);
        dispatch(addLesson(lesson))
    } catch (err) {
        throw new Error();
    }
};

export const deleteLessonFromCourse = (id1: string, id2: string) => async (dispatch: AppDispatch) => {
    try {
        await axios.delete('/api/v1/courses/' + id1 + '/lessons?lesson-id=' + id2);
        dispatch(deleteLesson({id: id2}))
    } catch (err) {
        throw new Error();
    }
};

export const fetchLesson = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/lessons/' + id);
        dispatch(getLesson(response.data));
    } catch (err) {
        throw new Error();
    }
};

export default LessonSlice.reducer;
