import axios from 'src/utils/axios';
import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';

interface StateType {
    notifications: any[];
    sortBy: string;
    selectedNotification: any;
}

const initialState = {
    notifications: [],
    sortBy: 'newest',
    selectedNotification: null,
};

export const NotificationSlice = createSlice({
    name: 'Notification',
    initialState,
    reducers: {
        getNotifications: (state: StateType, action) => {
            state.notifications = action.payload;
        },
        getNotification: (state: StateType, action) => {
            state.selectedNotification = action.payload;
        },
    },
});

export const { getNotification, getNotifications } = NotificationSlice.actions;

export const fetchUserNotifications = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/users/' + id + '/notifications/inbox');
        dispatch(getNotifications(response.data));
    } catch (err) {
        throw new Error();
    }
};

// export const addUser= (teacher: TeacherType) => async (dispatch: AppDispatch) => {
//     try {
//         const data = JSON.stringify(teacher);
//         const response = await axios.post('/api/teachers/', { data });
//         dispatch(getUser(response.data.posts));
//     } catch (err: any) {
//         throw new Error(err);
//     }
// };

export default NotificationSlice.reducer;
