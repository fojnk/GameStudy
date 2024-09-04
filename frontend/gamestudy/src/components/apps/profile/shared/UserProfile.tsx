import useAuth from 'src/guards/authGuard/UseAuth';
import ActivityInfo from '../student/activity/ActivityInfo';
import UserInfo from './UserInfo';
import CourseWidget from '../student/courses/CourseWidget';
import { Grid } from '@mui/material';
import { Box } from '@mui/system';

const UserProfile = () => {
    const { user } = useAuth();

    return (
        <>
            <UserInfo />
            {user.role == 'STUDENT' ? <ActivityInfo /> : <></>}
            <Box sx={{ width: '90%', margin: '6% auto 0 auto' }}>
                {user.role == 'STUDENT' ? (
                    <Grid container spacing={4}>
                        <Grid item xs={6}>
                            <CourseWidget />
                        </Grid>
                        <Grid item xs={6}>
                            <CourseWidget />
                        </Grid>
                    </Grid>
                ) : (
                    <CourseWidget />
                )}
            </Box>
        </>
    );
};

export default UserProfile;
