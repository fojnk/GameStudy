import axios from 'src/utils/axios';
import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';

interface StateType {
    boards: any[];
    sortBy: string;
    selectedBoard: any;
}

const initialState = {
    boards: [],
    sortBy: 'newest',
    selectedBoard: null,
};

export const BoardSlice = createSlice({
    name: 'Board',
    initialState,
    reducers: {
        getBoards: (state: StateType, action) => {
            state.boards = action.payload;
        },
        getBoard: (state: StateType, action) => {
            state.selectedBoard = action.payload;
        },
    },
});

export const { getBoard, getBoards } = BoardSlice.actions;

// export const fetchCourseBoards = (id: string) => async (dispatch: AppDispatch) => {
//     try {
//         const response = await axios.get('/api/v1/users/' + id + '/notifications/inbox');
//         dispatch(getBoards(response.data));
//     } catch (err) {
//         throw new Error();
//     }
// };

// export const addUser= (teacher: TeacherType) => async (dispatch: AppDispatch) => {
//     try {
//         const data = JSON.stringify(teacher);
//         const response = await axios.post('/api/teachers/', { data });
//         dispatch(getUser(response.data.posts));
//     } catch (err: any) {
//         throw new Error(err);
//     }
// };

export default BoardSlice.reducer;
