import axios from 'src/utils/axios';
import { createSlice } from '@reduxjs/toolkit';
import { AppDispatch } from 'src/store/Store';
import { CourseProgressType, CourseType } from 'src/types/apps/course';

interface StateType {
    courses: any[];
    activeCourses: any[];
    studentCourses: any[];
    creatorCourses: any[];
    teacherCourses: any[];
    courseSearch: string;
    sortBy: string;
    selectedCourse: any;
    selectedCourseProgress: any;
    allSelectedCourseProgresses: any[];
}

const initialState = {
    courses: [],
    activeCourses: [],
    studentCourses: [],
    creatorCourses: [],
    teacherCourses: [],
    courseSearch: '',
    sortBy: 'newest',
    selectedCourse: undefined,
    selectedCourseProgress: undefined,
    allSelectedCourseProgresses: [],
};

export const CourseSlice = createSlice({
    name: 'Course',
    initialState,
    reducers: {
        getCourses: (state: StateType, action) => {
            state.courses = action.payload;
        },
        getStudentCourses: (state: StateType, action) => {
            state.studentCourses = action.payload;
        },
        getTeacherCourses: (state: StateType, action) => {
            state.teacherCourses = action.payload;
        },
        getCreatorCourses: (state: StateType, action) => {
            state.creatorCourses = action.payload;
        },
        getCourse: (state: StateType, action) => {
            state.selectedCourse = action.payload;
        },
        getAllCourseProgresses: (state: StateType, action) => {
            state.allSelectedCourseProgresses = action.payload;
        },
        getCourseProgress: (state: StateType, action) => {
            state.selectedCourseProgress = action.payload;
        },
        deleteCourse: (state: StateType, action) => {
            state.creatorCourses = state.creatorCourses.filter((item) => (item.id != action.payload.id));
        },
    },
});

export const { getAllCourseProgresses, getCourseProgress, getCreatorCourses, getCourses, getCourse, getStudentCourses, getTeacherCourses, deleteCourse } =
    CourseSlice.actions;

export const fetchCourses = () => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/courses/');
        dispatch(getCourses(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const fetchDisciplineCourses = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/management/disciplines/' + id +'/courses');
        dispatch(getCourses(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const fetchCreatorCourses = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/courses/creator/' + id);
        dispatch(getCreatorCourses(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const fetchStudentCourses = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/students/' + id + '/courses');
        dispatch(getStudentCourses(response.data));
    } catch (err) {
        throw new Error();
    }
};



export const fetchTeacherCourses = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/teachers/' + id + '/courses');
        dispatch(getTeacherCourses(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const fetchStudentCourseProgress = (id: string, studentId: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/course-progress/'+'?student-id='+studentId+'&course-id='+id);
        dispatch(getCourseProgress(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const fetchCourseProgress = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/course-progress/'+'?course-id='+id);
        dispatch(getAllCourseProgresses(response.data));
    } catch (err) {
        throw new Error();
    }
};

export const fetchCourse = (id: string) => async (dispatch: AppDispatch) => {
    try {
        const response = await axios.get('/api/v1/courses/' + id);
        dispatch(getCourse(response.data));
    } catch (err) {
        throw new Error();
    }
};


export const addCourse = (course: CourseType) => async (dispatch: AppDispatch) => {
    try {
        const data = JSON.stringify(course);
        const response = await axios.post('/api/v1/course/', { data });
        dispatch(getCourses(response.data.posts));
    } catch (err: any) {
        throw new Error(err);
    }
};

export const addCourseProgress = (progress: CourseProgressType) => async (dispatch: AppDispatch) => {
    try {
        const data = JSON.stringify(progress);
        const response = await axios.post('/api/v1/course-progress/', { data });
        //dispatch(getCourseProgress(response.data));
    } catch (err: any) {
        throw new Error(err);
    }
};

export const deleteCrs = (id: string) => async (dispatch: AppDispatch) => {
    try {
        await axios.delete('/api/v1/courses/'+ id);
        dispatch(deleteCourse({id: id}));
    } catch (err: any) {
        throw new Error(err);
    }
};

export default CourseSlice.reducer;
