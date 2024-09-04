import { Box, Paper, Typography } from '@mui/material';
import Achievements from 'src/components/apps/achievements/AchievementsListing';
import useAuth from 'src/guards/authGuard/UseAuth';

const ActivityAchievements = () => {
    const { user } = useAuth();

    return (
        <Paper
            sx={{
                maxWidth: '90%',
                margin: '50px auto 0 auto',
                p: 2,
                width: '100%',
                flexGrow: 1,
                backgroundColor: (theme) => (theme.palette.mode === 'dark' ? '#1A2027' : '#fff'),
            }}
        >
            <Typography fontSize={15} mb={1} textAlign={'center'} fontWeight={600}>
                Достижения
            </Typography>
            <Box p={2}>
                <Achievements id={user.id} />
            </Box>
        </Paper>
    );
};

export default ActivityAchievements;
