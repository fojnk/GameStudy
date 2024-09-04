import axios from 'src/utils/axios';
import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';
import { TagType } from 'src/types/apps/tags';

interface StateType {
    tags: any[];
    activeTags: any[];
    tagSearch: string;
    sortBy: string;
    selectedTag: any;
}

const initialState = {
    tags: [],
    activeTags: [],
    tagSearch: '',
    sortBy: 'newest',
    selectedTag: null,
};

export const TagSlice = createSlice({
    name: 'Tag',
    initialState,
    reducers: {
        getTags: (state: StateType, action) => {
            state.tags = action.payload;
        },
        getTag: (state: StateType, action) => {
            state.selectedTag = action.payload;
        },
        clear: (state: StateType) => {
            state.tags = [];
        },
    },
});

export const { clear, getTags, getTag } = TagSlice.actions;

export const fetchTags = () => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/management/course-tags');
        dispatch(getTags(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const clearTagsState = () => async (dispatch: AppDispatch) => {
    try {
        dispatch(clear());
    } catch (err) {
        throw new Error();
    }
};

export const fetchCourseTags = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/courses/' + id + '/tags');
        dispatch(getTags(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const addTag = (tag: TagType) => async (dispatch: AppDispatch) => {
    try {
        const data = JSON.stringify(tag);
        const response = await axios.post('/api/v1/management/tags/', { data });
        dispatch(getTag(response.data.posts));
    } catch (err: any) {
        throw new Error(err);
    }
};

export default TagSlice.reducer;
