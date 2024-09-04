import axios from 'src/utils/axios';
import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';
import { TeacherType } from 'src/types/apps/teacher';

interface StateType {
    users: any[];
    userNotifications: any[];
    sortBy: string;
    selectedUser: any;
}

const initialState = {
    users: [],
    userNotifications: [],
    sortBy: 'newest',
    selectedUser: null,
};

export const UserSlice = createSlice({
    name: 'User',
    initialState,
    reducers: {
        getUsers: (state: StateType, action) => {
            state.users = action.payload;
        },
        getUser: (state: StateType, action) => {
            state.selectedUser = action.payload;
        },
    },
});

export const { getUsers, getUser } = UserSlice.actions;

export const fetchUsers = () => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/users/');
        dispatch(getUsers(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const addUser = (teacher: TeacherType) => async (dispatch: AppDispatch) => {
    try {
        const data = JSON.stringify(teacher);
        const response = await axios.post('/api/v1/teachers/', { data });
        dispatch(getUser(response.data.posts));
    } catch (err: any) {
        throw new Error(err);
    }
};

export default UserSlice.reducer;
