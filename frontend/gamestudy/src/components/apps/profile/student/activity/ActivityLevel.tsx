
import { Grid, LinearProgress, Paper, Typography } from '@mui/material';
import useAuth from 'src/guards/authGuard/UseAuth';

const normalise = (value: number) => ((value - 0) * 100) / (10 - 0);

const ActivityLevel = () => {
    const { role }= useAuth();

    const level = Math.floor(role.experience / 10);
    const points = role.experience % 10;

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
            <Typography fontSize={15} mb={3} fontWeight={600}>
                Уровень {level}
            </Typography>
            <LinearProgress variant="determinate" value={normalise(points)} />
            <Grid container>
                <Grid item xs={10}>
                    <Typography fontSize={15} mt={2}>
                        Для перехода на новый уровень нужно набрать 10 баллов.
                    </Typography>
                    <Typography fontSize={15} mb={1}>
                        Баллы можно получить за решение задач и просмотр уроков.
                    </Typography>
                </Grid>
                <Grid item xs={2}>
                    <Typography fontSize={15} mt={2} mb={1}>
                        {points}/10 баллов
                    </Typography>
                </Grid>
            </Grid>
        </Paper>
    );
};

export default ActivityLevel;
