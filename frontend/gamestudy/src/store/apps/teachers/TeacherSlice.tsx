import axios from 'src/utils/axios';
import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';
import { TeacherType } from 'src/types/apps/teacher';

interface StateType {
    teachers: any[];
    teacherSearch: string;
    sortBy: string;
    selectedTeacher: any;
}

const initialState = {
    teachers: [],
    teacherSearch: '',
    sortBy: 'newest',
    selectedTeacher: null,
};

export const TeacherSlice = createSlice({
    name: 'Teacher',
    initialState,
    reducers: {
        getTeachers: (state: StateType, action) => {
            state.teachers = action.payload;
        },
        getTeacher: (state: StateType, action) => {
            state.selectedTeacher = action.payload;
        },
    },
});

export const { getTeachers, getTeacher } = TeacherSlice.actions;

export const fetchTeachers = () => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/teachers/');
        dispatch(getTeachers(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const fetchCourseTeachers = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/courses/' + id + '/teachers');
        dispatch(getTeachers(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const addTeacher = (teacher: TeacherType) => async (dispatch: AppDispatch) => {
    try {
        const data = JSON.stringify(teacher);
        const response = await axios.post('/api/v1/teachers/', { data });
        dispatch(getTeacher(response.data.posts));
    } catch (err: any) {
        throw new Error(err);
    }
};

export default TeacherSlice.reducer;
