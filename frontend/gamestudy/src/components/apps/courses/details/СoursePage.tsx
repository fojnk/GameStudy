import { useEffect, useState } from 'react';

import { Typography } from '@mui/material';
import { useParams } from 'react-router';
import { fetchCourse } from 'src/store/apps/courses/CourseSlise';
import { useDispatch, useSelector } from 'src/store/Store';
import { CourseType } from 'src/types/apps/course';
import Course from './Course';
import CourseInfo from './CourseInfo';

const CoursePage = () => {
    const dispatch = useDispatch();
    const { id } = useParams<{ id: string }>();
    const [isWorking, setIsWorking] = useState(false);

    if (id) {
        useEffect(() => {
            dispatch(fetchCourse(id));
        }, [dispatch]);
    } else {
        return <Typography>Id не получен</Typography>;
    }

    const course: CourseType | undefined = useSelector((state) => {
        return state.courseReduser.selectedCourse;
    });

    return !isWorking ? (
        <CourseInfo key={id} course={course} setIsWorking={setIsWorking} />
    ) : (
        <Course course={course} />
    );
};

export default CoursePage;
