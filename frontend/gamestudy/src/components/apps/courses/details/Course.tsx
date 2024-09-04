import { Box, Grid, Typography } from '@mui/material';
import PageContainer from 'src/components/container/PageContainer';
import { CourseProgressType, CourseType } from 'src/types/apps/course';
import Lessons from '../../lessons/LessonListing';
import Tasks from '../../tasks/TaskListing';
import CourseMainRating from 'src/views/tables/CourseMainRating';
import { useEffect } from 'react';
import { fetchCourseProgress, fetchStudentCourseProgress } from 'src/store/apps/courses/CourseSlise';
import { useDispatch, useSelector } from 'src/store/Store';
import useAuth from 'src/guards/authGuard/UseAuth';

const Course = ({ course }: { course: CourseType | undefined }) => {
    const dispatch = useDispatch();
    const { role } = useAuth();
    const progresses : CourseProgressType[] | undefined = useSelector((state) =>
        { return state.courseReduser.allSelectedCourseProgresses }
        )
    const studentProgress : CourseProgressType | undefined = useSelector((state) =>
        { return state.courseReduser.selectedCourseProgress }
    )

    if (!course) {
        return <Typography>Курс не найден</Typography>;
    }

    useEffect(() => {
        dispatch(fetchCourseProgress(course.id ? course.id.toString() : ''));
        dispatch(fetchStudentCourseProgress(course.id ? course.id.toString() : '', role.id))
    }, [dispatch, progresses, studentProgress]);

    console.log(studentProgress);

    return (
        <PageContainer title="Courses" description="this is course page">
            <Box p={3} m={4}>
            <Grid container spacing={3}>
                <Grid item xs={8}>
                    <Box sx={{ backgroundColor: 'white' }} p={3}>
                        <Box mb={15} sx={{ maxWidth: '70%' }}>
                            <Typography
                                fontSize={30}
                                gutterBottom
                                variant="subtitle1"
                                component="div"
                            >
                                {course?.title}
                            </Typography>

                            <Typography variant="body2" fontSize={15} gutterBottom>
                                {course?.description}
                            </Typography>
                            <Typography variant="body2" color="text.secondary">
                                ID:{course?.id}
                            </Typography>
                        </Box>
                        <Box mt={3}>
                            <Lessons studentProgres={studentProgress} type={1} id={course.id?.toString()} />
                        </Box>
                        <Box mt={3}>
                            <Tasks type={1} id={course.id?.toString()} />
                        </Box>
                    </Box>
                </Grid>
                <Grid item xs={4}>
                    <CourseMainRating courseProgresses={progresses ? progresses: []}/>
                </Grid>
                
            </Grid>
            </Box>
        </PageContainer>
    );
};

export default Course;
