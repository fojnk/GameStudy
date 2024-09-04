// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Box, Grid } from '@mui/material';
import RatingListing from 'src/components/apps/courses/listing/RatingListing';
import Groups from 'src/components/apps/groups/GroupListing';
import useAuth from 'src/guards/authGuard/UseAuth';
import CollapsibleCourseTable from 'src/views/tables/CollapsibleCourseTable';

const StudentRatings = () => {
    const { role } = useAuth();

    return (
        <Box m={5}>
            <Grid container spacing={6}>
                <Grid item xs={12}>
                <RatingListing title="Рейтинг активных курсов"/>
                </Grid>
                <Grid item xs={12}>
                    <Groups type={1} id={role.id.toString()} />
                </Grid>
            </Grid>
        </Box>
    );
};

export default StudentRatings;
