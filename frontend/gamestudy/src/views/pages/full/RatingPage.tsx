// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Box, Grid } from '@mui/material';
import Groups from 'src/components/apps/groups/GroupListing';
import useAuth from 'src/guards/authGuard/UseAuth';
import CollapsibleCourseTable from 'src/views/tables/CollapsibleCourseTable';
import StudentRatings from './StudentRatings';
import TeacherRatings from './TeacherRating';

const RatingPage = () => {
    const { user } = useAuth();

    return (
        (user.role=="STUDENT" ?
            <StudentRatings />
        : 
            <TeacherRatings />
        )
    );
};

export default RatingPage;
