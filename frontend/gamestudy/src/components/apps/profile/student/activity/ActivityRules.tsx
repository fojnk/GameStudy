
import { BatteryChargingFull } from '@mui/icons-material';
import { Grid, Paper, Typography } from '@mui/material';

const styles = {
    largeIcon: {
        transform: 'scale(18)',
        margin: '190px 0 auto 120px',
    },
};

const ActivityRules = () => {
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
            <Grid container spacing={1}>
                <Grid item xs={4}>
                    <BatteryChargingFull color="primary" style={styles.largeIcon} />
                </Grid>
                <Grid item xs={8}>
                    <Typography fontSize={20} mt={2} mr={1}>
                        Активность зависит от количества решенных задач за последний месяц.
                    </Typography>
                    <Typography fontSize={15} fontWeight={600} mt={2}>
                        Высокая:
                    </Typography>
                    <Typography fontSize={15}>
                        1 задача / урок в день за последние 30 дней; посещение сайта ежедневно.
                    </Typography>
                    <Typography fontSize={15} fontWeight={600} mt={2}>
                        Интенсивная:
                    </Typography>
                    <Typography fontSize={15}>
                        в среднем 1 задача / урок в день за последние 30 дней.
                    </Typography>
                    <Typography fontSize={15} fontWeight={600} mt={2}>
                        Нормальная:
                    </Typography>
                    <Typography fontSize={15}>
                        в среднем 1 задача / урок раз в 2 дня за последние 30 дней.
                    </Typography>
                    <Typography fontSize={15} fontWeight={600} mt={2} mr={4}>
                        Низкая:
                    </Typography>
                    <Typography fontSize={15}>
                        решено или просмотрено от 7 до 15 задач / уроков за последние 30 дней.
                    </Typography>
                    <Typography fontSize={15} fontWeight={600} mt={2}>
                        Остутсвует:
                    </Typography>
                    <Typography fontSize={15} mb={7}>
                        решено или просмотрено менее 7 задач / уроков за последние 30 дней.
                    </Typography>
                </Grid>
            </Grid>
        </Paper>
    );
};

export default ActivityRules;
