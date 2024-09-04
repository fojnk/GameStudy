import axios from 'src/utils/axios';
import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';
import { DisciplineType } from 'src/types/apps/discipline';

interface StateType {
    disciplines: any[];
    activeDisciplines: any[];
    disciplineSearch: string;
    sortBy: string;
    selectedDiscipline: any;
}

const initialState = {
    disciplines: [],
    activeDisciplines: [],
    disciplineSearch: '',
    sortBy: 'newest',
    selectedDiscipline: null,
};

export const DisciplineSlice = createSlice({
    name: 'Discipline',
    initialState,
    reducers: {
        getDisciplines: (state: StateType, action) => {
            state.disciplines = action.payload;
        },
        getDiscipline: (state: StateType, action) => {
            state.selectedDiscipline = action.payload;
        },
    },
});

export const { getDisciplines, getDiscipline } = DisciplineSlice.actions;

export const fetchDisciplines = () => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/management/disciplines');
        dispatch(getDisciplines(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const fetchCourseDisciplines = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/courses/' + id + '/disciplines');
        dispatch(getDisciplines(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const addDiscipline = (discipline: DisciplineType) => async (dispatch: AppDispatch) => {
    try {
        const data = JSON.stringify(discipline);
        const response = await axios.post('/api/v1/management/disciplines', { data });
        dispatch(getDiscipline(response.data.posts));
    } catch (err: any) {
        throw new Error(err);
    }
};

export default DisciplineSlice.reducer;
