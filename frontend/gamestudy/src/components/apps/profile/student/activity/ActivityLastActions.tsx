
import { Box, Grid, Paper, Typography } from '@mui/material';

const ActivityLastActions = () => {
    return (
        <Paper
        sx={{
            maxWidth: '90%',
            margin: '50px auto 0 auto',
            p: 2,
            width: '100%',
            flexGrow: 1,
            backgroundColor: (theme) =>
                theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
        }}
    >
        <Typography fontSize={15} mb={1} fontWeight={600}>
                    Последние действия
        </Typography>
        <Box p={2}>
        <Grid container>
            <Grid item xs={3}>
                <Typography fontSize={15} fontWeight={600}>
                    Курс
                </Typography>
            </Grid>
            <Grid item xs={4}>
                <Typography fontSize={15} fontWeight={600}>
                    Действие
                </Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography fontSize={15} fontWeight={600}>
                    Время
                </Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography fontSize={15} fontWeight={600}>
                    Дата
                </Typography>
            </Grid>
            <Grid item xs={1}>
                <Typography fontSize={15} fontWeight={600}>
                    Баллы
                </Typography>
            </Grid>
        </Grid>
        </Box>
    </Paper>
    )
}

export default ActivityLastActions