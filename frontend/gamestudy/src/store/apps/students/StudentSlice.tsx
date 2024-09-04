import axios from 'src/utils/axios';
import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';
import { TeacherType } from 'src/types/apps/teacher';

interface StateType {
    students: any[];
    studentSearch: string;
    sortBy: string;
    selectedStudent: any;
}

const initialState = {
    students: [],
    studentSearch: '',
    sortBy: 'newest',
    selectedStudent: null,
};

export const StudentSlice = createSlice({
    name: 'Students',
    initialState,
    reducers: {
        getStudents: (state: StateType, action) => {
            state.students = action.payload;
        },
        getStudent: (state: StateType, action) => {
            state.selectedStudent = action.payload;
        },
    },
});

export const { getStudents, getStudent } = StudentSlice.actions;

export const fetchStudents = () => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/students/');
        dispatch(getStudents(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const addStudent = (teacher: TeacherType) => async (dispatch: AppDispatch) => {
    try {
        const data = JSON.stringify(teacher);
        const response = await axios.post('/api/v1/students/', { data });
        dispatch(getStudent(response.data.posts));
    } catch (err: any) {
        throw new Error(err);
    }
};

export default StudentSlice.reducer;
