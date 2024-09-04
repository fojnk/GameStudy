import axios from 'src/utils/axios';
import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';
import { TaskType } from 'src/types/apps/task';

interface StateType {
    tasks: any[];
    taskSearch: string;
    sortBy: string;
    selectedTask: any;
}

const initialState = {
    tasks: [],
    taskSearch: '',
    sortBy: 'newest',
    selectedTask: null,
};

export const TaskSlice = createSlice({
    name: 'Task',
    initialState,
    reducers: {
        getTasks: (state: StateType, action) => {
            state.tasks = action.payload;
        },
        getTask: (state: StateType, action) => {
            state.selectedTask = action.payload;
        },
        addTask: (state: StateType, action) => {
            state.tasks = state.tasks.concat([action.payload]);
        },
        deleteTask: (state: StateType, action) => {
            state.tasks = state.tasks.filter((item) => (item.id != action.payload.id));
        },
    },
});

export const { addTask, getTask, getTasks, deleteTask } = TaskSlice.actions;

export const fetchTasks = () => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/tasks/');
        dispatch(getTasks(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const addTaskToCourse = (id1: string, task: TaskType) => async (dispatch: AppDispatch) => {
    try {
        await axios.put('/api/v1/courses/' + id1 + '/tasks?task-id=' + task.id);
        dispatch(addTask(task))
    } catch (err) {
        throw new Error();
    }
};

export const fetchCourseTasks = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/courses/' + id + '/tasks');
        dispatch(getTasks(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const deleteTaskFromCourse = (id1: string, id2: string) => async (dispatch: AppDispatch) => {
    try {
        await axios.delete('/api/v1/courses/' + id1 + '/tasks?task-id=' + id2);
        dispatch(deleteTask({id: id2}))
    } catch (err) {
        throw new Error();
    }
};

export const deleteTaskFromLesson = (id1: string, id2: string) => async (dispatch: AppDispatch) => {
    try {
        await axios.delete('/api/v1/lessons/' + id1 + '/tasks?task-id=' + id2);
        dispatch(deleteTask({id: id2}))
    } catch (err) {
        throw new Error();
    }
};

export const fetchTask = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/tasks/' + id);
        dispatch(getTask(response.data));
    } catch (err) {
        throw new Error();
    }
};

export default TaskSlice.reducer;
