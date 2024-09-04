// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Box, Grid } from '@mui/material';
import RatingListing from 'src/components/apps/courses/listing/RatingListing';
import Groups from 'src/components/apps/groups/GroupListing';
import UserProfile from 'src/components/apps/profile/shared/UserProfile';
import useAuth from 'src/guards/authGuard/UseAuth';
import CollapsibleCourseTable from 'src/views/tables/CollapsibleCourseTable';

const TeacherRatings = () => {
    const { user } = useAuth();

    return (
        <Box m={5}>
            <Grid container spacing={6}>
                <Grid item xs={12}>
                    <RatingListing title="Рейтинг активных курсов"/>
                </Grid>
                <Grid item xs={12}>
                    <Groups type={2} id={user.id.toString()} />
                </Grid>
            </Grid>
        </Box>
    );
};

export default TeacherRatings;