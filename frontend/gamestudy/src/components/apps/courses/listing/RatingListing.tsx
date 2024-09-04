import { Box, Grid, Typography } from '@mui/material';
import { orderBy } from 'lodash';
import { useEffect } from 'react';
import BlankCard from 'src/components/shared/BlankCard';
import ParentCard from 'src/components/shared/ParentCard';
import useAuth from 'src/guards/authGuard/UseAuth';
import { useDispatch, useSelector } from 'src/store/Store';
import { fetchCourses, fetchCreatorCourses, fetchStudentCourses, fetchTeacherCourses } from 'src/store/apps/courses/CourseSlise';
import { CourseType } from 'src/types/apps/course';
import CollapsibleCourseTable from 'src/views/tables/CollapsibleCourseTable';
import PaginationCourseTable from 'src/views/tables/PaginationCourseTable';

const RatingListing = ({ title }: { title: string }) => {
    const dispatch = useDispatch();
    const { user, role } = useAuth();

    useEffect(() => {
        if (user.role == "STUDENT") {
            dispatch(fetchStudentCourses(role.id));
        } else {
            dispatch(fetchTeacherCourses(role.id));
            dispatch(fetchCreatorCourses(user.id));
        }
    }, [dispatch]);


    const courses = useSelector((state) =>
        { return (user.role=="STUDENT" ? (
                 state.courseReduser.courses
            ) : (
                state.courseReduser.creatorCourses.concat(state.courseReduser.teacherCourses)
            ))
        }
    );

    if (courses.length == 0) {
        return (
            <Box sx={{ width: '90%', margin: '5% auto 0 auto' }}>
                <ParentCard title="Все курсы">
                    <BlankCard>
                        <Typography p={4} textAlign={'center'} color={'primary.main'} fontSize={40}>
                            Нет курсов
                        </Typography>
                    </BlankCard>
                </ParentCard>
            </Box>
        );
    }

    return (
        <>
            <Box>
            <Grid container spacing={6}>
                <Grid item xs={6}>
                    <CollapsibleCourseTable title={title} courses={courses} />
                </Grid>
                <Grid item xs={6}>
                    <CollapsibleCourseTable title="Рейтинги завершненных курсов" courses={courses}/>
                </Grid>
            </Grid>
        </Box>
        </>
    );
};

export default RatingListing;
