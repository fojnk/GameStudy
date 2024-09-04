import { Battery3Bar } from '@mui/icons-material';
import { Box, ButtonBase, Grid, Paper, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import { Link } from 'react-router-dom';
import useAuth from 'src/guards/authGuard/UseAuth';
import img1 from '../../../../../assets/images/products/right-arrow.png';

const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
});

const styles = {
    largeIcon: {
        transform: 'scale(5) rotate(90deg)',
        margin: '50px 0 auto 37px',
    },
};

const ActivityInfo = () => {
    const { role } = useAuth();

    return (
        <>
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
                <Grid container spacing={1}>
                    <Grid item xs={11} container>
                        <Grid container m={2}>
                            <Grid item xs={3} container>
                                <Grid item xs={12}>
                                    <Typography
                                        fontSize={15}
                                        fontWeight={600}
                                        variant="body2"
                                        color="background.default"
                                    >
                                        Уровень
                                    </Typography>
                                </Grid>
                                <Grid item xs={12}>
                                    <Typography
                                        fontSize={30}
                                        variant="body2"
                                        color="background.default"
                                        ml={3}
                                        mt={4}
                                        mb={2}
                                    >
                                        {role.experience ? Math.floor(role.experience / 10) : 'X'}
                                    </Typography>
                                </Grid>
                            </Grid>
                            <Grid item xs={3} container>
                                <Grid item xs={12}>
                                    <Typography
                                        fontSize={15}
                                        fontWeight={600}
                                        variant="body2"
                                        color="background.default"
                                    >
                                        Активность
                                    </Typography>
                                </Grid>
                                <Grid item xs={12}>
                                    <Battery3Bar color="secondary" style={styles.largeIcon} />
                                </Grid>
                                <Grid item xs={12}>
                                    <Typography
                                        fontSize={14}
                                        fontWeight={600}
                                        sx={{
                                            marginTop: '20px',
                                        }}
                                        variant="body2"
                                        color="background.default"
                                    >
                                        Нормальная
                                    </Typography>
                                </Grid>
                            </Grid>
                            <Grid item xs={3}>
                                <Typography
                                    fontSize={15}
                                    fontWeight={600}
                                    variant="body2"
                                    color="background.default"
                                >
                                    Рейтинг
                                </Typography>
                                <Grid item xs={12} mr={2} mt={3}>
                                    <Box
                                        p={1}
                                        color="primary.dark"
                                        sx={{ boxShadow: 2, borderRadius: '9px' }}
                                    >
                                        <Typography>Курс1 место1</Typography>
                                    </Box>
                                </Grid>
                                <Grid item xs={12} mr={2} mt={1}>
                                    <Box
                                        p={1}
                                        color="primary.dark"
                                        sx={{ boxShadow: 2, borderRadius: '9px' }}
                                    >
                                        <Typography>Курс2 место1</Typography>
                                    </Box>
                                </Grid>
                            </Grid>
                            <Grid item xs={3}>
                                <Typography
                                    fontSize={15}
                                    fontWeight={600}
                                    variant="body2"
                                    color="background.default"
                                >
                                    Ударный режим
                                </Typography>
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={1}>
                        <ButtonBase
                            to="/profile/activity"
                            component={Link}
                            sx={{
                                width: 80,
                                height: 80,
                                margin: '40px auto auto auto',
                                borderRadius: '80px',
                            }}
                        >
                            <Img alt="complex" src={img1} />
                        </ButtonBase>
                    </Grid>
                </Grid>
            </Paper>
        </>
    );
};

export default ActivityInfo;
