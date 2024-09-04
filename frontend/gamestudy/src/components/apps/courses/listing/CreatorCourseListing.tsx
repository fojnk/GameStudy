import { Box, Typography } from '@mui/material';
import { orderBy } from 'lodash';
import { useEffect } from 'react';
import BlankCard from 'src/components/shared/BlankCard';
import ParentCard from 'src/components/shared/ParentCard';
import useAuth from 'src/guards/authGuard/UseAuth';
import { useDispatch, useSelector } from 'src/store/Store';
import { fetchCreatorCourses } from 'src/store/apps/courses/CourseSlise';
import { CourseType } from 'src/types/apps/course';
import PaginationCourseTable from 'src/views/tables/PaginationCourseTable';

const CreatorCourseListing = () => {
    const dispatch = useDispatch();
    const { user } = useAuth();

    if (user.id) {
        useEffect(() => {
            dispatch(fetchCreatorCourses(user.id));
        }, [dispatch]);
    }

    const filterCourses = (courses: CourseType[], sortBy: string, _cSearch: string) => {
        // SORT BY

        if (sortBy === 'cost') {
            courses = orderBy(courses, ['cost'], ['desc']);
        }
        if (sortBy === 'cheapest') {
            courses = orderBy(courses, ['cost'], ['asc']);
        }
        if (sortBy === 'popular') {
            courses = orderBy(courses, ['rating'], ['desc']);
        }
        // if (courses) {
        //   return (courses = courses.filter((t) => t.featured === false));
        // }

        return courses;
    };

    const courses = useSelector((state) =>
        filterCourses(
            state.courseReduser.creatorCourses,
            state.courseReduser.sortBy,
            state.courseReduser.courseSearch,
        ),
    );

    if (courses.length == 0) {
        return (
            <Box sx={{ width: '90%', margin: '5% auto 0 auto' }}>
                <ParentCard title="Мои курсы">
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
            <Box sx={{ width: '90%', margin: '5% auto 0 auto' }}>
                <PaginationCourseTable
                    title={'Созданные курсы'}
                    courses={courses}
                    type={2}
                    linkPath={'/courses/'}
                />
            </Box>
        </>
    );
};

export default CreatorCourseListing;
