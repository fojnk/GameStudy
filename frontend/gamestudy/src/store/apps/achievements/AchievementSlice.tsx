import axios from 'src/utils/axios';
import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';

interface StateType {
    allAchievements: any[];
    studentAchievements: any[];
}

const initialState = {
    allAchievements: [],
    studentAchievements: [],
};

export const AchievementSlice = createSlice({
    name: 'Achievement',
    initialState,
    reducers: {
        getAchievements: (state: StateType, action) => {
            state.allAchievements = action.payload;
        },
        getStudentAchievements: (state: StateType, action) => {
            state.studentAchievements = action.payload;
        },
    },
});

export const { getAchievements, getStudentAchievements } = AchievementSlice.actions;

export const fetchAllAchievements = () => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/management/achievements/');
        dispatch(getAchievements(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const fetchStudentAchievements = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/students/' + id + '/achievements/');
        dispatch(getStudentAchievements(response.data));
    } catch (err) {
        throw new Error();
    }
};

export default AchievementSlice.reducer;
