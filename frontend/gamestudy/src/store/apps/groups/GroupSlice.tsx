import axios from 'src/utils/axios';
import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';

interface StateType {
    groups: any[];
    sortBy: string;
    selectedGroup: any;
}

const initialState = {
    groups: [],
    sortBy: 'newest',
    selectedGroup: null,
};

export const GroupSlice = createSlice({
    name: 'Group',
    initialState,
    reducers: {
        getGroups: (state: StateType, action) => {
            state.groups = action.payload;
        },
        getGroup: (state: StateType, action) => {
            state.selectedGroup = action.payload;
        },
        delGroup: (state: StateType, action) => {
            state.groups = state.groups.filter((item) => (item.id != action.payload.id));
        },
    },
});

export const { delGroup, getGroup, getGroups } = GroupSlice.actions;

export const fetchStudentGroups = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/students/' + id + '/groups');
        dispatch(getGroups(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const fetchCourseGroups = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/courses/' + id + '/groups');
        dispatch(getGroups(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const fetchCreatorGroups = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/students/groups' + '?creator-id='+id);
        dispatch(getGroups(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const deleteGroup = (id: string) => async (dispatch: AppDispatch) => {
    try {
        await axios.delete('/api/v1/students/groups/'+id);
        dispatch(delGroup({id: id}));
    } catch (err) {
        throw new Error();
    }
};

export const deleteGroupFromCourse = (id: string, groupId: string) => async (dispatch: AppDispatch) => {
    try {
        await axios.delete('/api/v1/courses/'+id +'/groups' +'?groupIds='+groupId);
        dispatch(delGroup({id: groupId}));
    } catch (err) {
        throw new Error();
    }
};

export default GroupSlice.reducer;
