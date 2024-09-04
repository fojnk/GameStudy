import { Typography } from '@mui/material';
import { useEffect } from 'react';
import PageContainer from 'src/components/container/PageContainer';
import { useDispatch, useSelector } from 'src/store/Store';
import { fetchCourseLessons, fetchLessons, getLesson } from 'src/store/apps/lessons/LessonSlice';
import { lessonType } from 'src/types/apps/lesson';
import LessonTable from '../../../views/tables/LessonTable';
import EditLessonTable from 'src/views/tables/EditLessonTable';
import { CourseProgressType, CourseType } from 'src/types/apps/course';

type Props = {
    id?: string;
    type: number;
    studentProgres?: CourseProgressType;
};

const Lessons = ({ id, type, studentProgres }: Props) => {
    const dispatch = useDispatch();

    useEffect(() => {
        if (id) {
            dispatch(fetchCourseLessons(id));
        } 
    }, [dispatch]);

    var lessons: lessonType[] = useSelector((state) => {
        return state.lessonReducer.lessons;
    });


    return lessons.length != 0 ? (
        type == 1 ? (
            (
                studentProgres ? <LessonTable progress={studentProgres} lessons={lessons} /> : <></>
            )
            
        ) : (
            <EditLessonTable courseId={id ? id : ''} lessons={lessons} />
        )
    ) : (
        <></>
    );
};

export default Lessons;
