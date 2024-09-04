import { CheckCircleOutline } from '@mui/icons-material';
import { Box, ButtonBase, Grid, Paper, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import { Link } from 'react-router-dom';
import useAuth from 'src/guards/authGuard/UseAuth';
import img1 from '../../../../assets/images/products/right-arrow.png';

const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
});

const UserInfo = () => {
    const { user, role } = useAuth();

    return (
        <>
            <Paper
                sx={{
                    maxWidth: '90%',
                    margin: '50px auto 0 auto',
                    p: 2,
                    flexGrow: 1,
                    backgroundColor: (theme) =>
                        theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
                }}
            >
                {user.role == 'TEACHER' ? (
                    <Box m={2}>
                        <Grid container spacing={1}>
                            <Grid item>
                                <CheckCircleOutline />
                            </Grid>
                            <Grid item>
                                <Typography>
                                    Статус преподавателя подтвержден модератором
                                </Typography>
                            </Grid>
                        </Grid>
                    </Box>
                ) : (
                    <></>
                )}

                <Grid container spacing={1}>
                    <Grid item xs={7} container>
                        <Grid container direction="column" m={2}>
                            <Grid item xs>
                                <Box p={1} sx={{ backgroundColor: 'primary.main' }}>
                                    <Typography
                                        fontSize={15}
                                        variant="body2"
                                        color="primary.light"
                                    >
                                        {user.surname ? user.surname : 'Фамилия'}
                                    </Typography>
                                </Box>
                            </Grid>
                            <Grid item xs>
                                <Box p={1} sx={{ backgroundColor: 'primary.main' }}>
                                    <Typography
                                        fontSize={15}
                                        variant="body2"
                                        color="primary.light"
                                    >
                                        {user.name ? user.name : 'Имя'}
                                    </Typography>
                                </Box>
                            </Grid>
                            <Grid item xs>
                                <Box p={1} sx={{ backgroundColor: 'primary.main' }}>
                                    <Typography
                                        fontSize={15}
                                        variant="body2"
                                        color="primary.light"
                                    >
                                        {user.fathers_name ? user.fathers_name : 'Отчество'}
                                    </Typography>
                                </Box>
                            </Grid>
                            <Grid item xs>
                                <Box p={1} sx={{ backgroundColor: 'primary.main' }}>
                                    <Typography
                                        fontSize={15}
                                        variant="body2"
                                        color="primary.light"
                                    >
                                        {role.birth_date ? role.birth_date : 'X'}
                                    </Typography>
                                </Box>
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={4}>
                        <ButtonBase sx={{ width: 300, height: 250 }}>
                            <Img
                                src={role.image_path}
                                alt="https://michael.stapelberg.ch/posts/2023-01-15-gokrazy-instance-centric-config/gokrazy-logo.png"
                            />
                        </ButtonBase>
                    </Grid>
                    <Grid item xs={1}>
                        <ButtonBase
                            to="/profile/edit"
                            component={Link}
                            sx={{
                                width: 80,
                                height: 80,
                                margin: '80px auto auto auto',
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

export default UserInfo;
