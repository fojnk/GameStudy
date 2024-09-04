import { Box, Button, ButtonBase, Grid, Paper, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import { FormikProvider, useFormik } from 'formik';
import CustomTextField from 'src/components/forms/theme-elements/CustomTextField';
import useAuth from 'src/guards/authGuard/UseAuth';
import * as Yup from 'yup';

import { ChevronRight } from '@mui/icons-material';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import ModalWindow from 'src/components/shared/ModalWindow';
import TeacherSettings from '../teacher/TeacherSettings';
import { UserType } from 'src/types/apps/user';
import toast from 'react-hot-toast';

const tomorrow = dayjs().add(1, 'day');

const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
});

function ProfileSettings() {
    const { user, role, update_student, update_teacher } = useAuth();

    const LoginSchema = Yup.object().shape({
        email: Yup.string().email('Некорректная почта.'),
        password: Yup.string()
            .min(6, 'Пароль должен содержать как минимум 6 символов.')
            .required('Пароль обязателен'),
    });


    const formik = useFormik({
        initialValues: {
            surname: user.surname ? user.surname : '',
            name: user.name ? user.name : '',
            fathers_name: user.fathers_name ? user.fathers_name : '',
            email: user.email ? user.email : '',
            phone_number: user.phone_number ? user.phone_number : '',
            birth_date:  role.birth_date? dayjs(role.birth_date, "DD.MM.YYYY") : dayjs().add(1, 'day'),
            blog: role.blog.text ? role.blog.text : '',
            submit: null,
        },

        // validationSchema: LoginSchema,

        onSubmit: async (values) => {
            try {
                const user_data: UserType = {
                    name: values.name,
                    surname: values.surname,
                    fathers_name: values.fathers_name,
                    phone_number: values.phone_number,
                    email: values.email,
                }


                var role_data;
                if (user.role == "STUDENT") {
                    role_data = {
                        "birth_date": values.birth_date ? values.birth_date : null,
                        "phone_number": values.phone_number ? values.phone_number : null,
                        "email": values.email ? values.email : null,
                    }
                } else {
                    role_data = {
                        "birth_date": values.birth_date ? values.birth_date : null,
                        "phone_number": values.phone_number ? values.phone_number : null,
                        "email": values.email ? values.email : null,
                        "qualification": null,
                        "organisation": null,
                    }
                }

                if (user.role=="STUDENT") {
                    update_student(role_data, user_data);
                } else {
                    update_teacher(role_data, user_data);
                }

                toast.success("Данные пользователя обновлены.")

            } catch (err: any) {
                toast.error("Ошибка обновления.")
            }
        },
    });
    const { errors, touched, handleSubmit } = formik;

    return (
        <>
            <FormikProvider value={formik}>
                <form onSubmit={handleSubmit}>
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
                        <Grid container spacing={1}>
                            <Grid item xs={7} container>
                                <Grid container direction="column" m={2}>
                                    <Grid item xs>
                                        <Box p={1}>
                                            <CustomTextField
                                                id="surname"
                                                variant="outlined"
                                                placeholder="Фамилия"
                                                color="primary"
                                                fullWidth
                                                onChange={formik.handleChange}
                                                value={formik.values.surname}
                                                error={Boolean(touched.surname && errors.surname)}
                                                helperText={touched.surname && errors.surname}
                                            />
                                        </Box>
                                    </Grid>
                                    <Grid item xs>
                                        <Box p={1}>
                                            <CustomTextField
                                                id="name"
                                                variant="outlined"
                                                placeholder="Имя"
                                                color="primary"
                                                fullWidth
                                                onChange={formik.handleChange}
                                                value={formik.values.name}
                                            />
                                        </Box>
                                    </Grid>
                                    <Grid item xs>
                                        <Box p={1}>
                                            <CustomTextField
                                                id="fathers_name"
                                                variant="outlined"
                                                placeholder="Отчество"
                                                color="primary"
                                                fullWidth
                                                onChange={formik.handleChange}
                                                value={formik.values.fathers_name}
                                            />
                                        </Box>
                                    </Grid>
                                    <Grid item xs>
                                        <Box p={1}>
                                            <LocalizationProvider dateAdapter={AdapterDayjs}>
                                                <DatePicker
                                                    name="birth_date"
                                                    defaultValue={tomorrow}
                                                    value={formik.initialValues.birth_date}
                                                    onChange={formik.handleChange}
                                                    sx={{
                                                        color: 'primary',
                                                        boxShadow: 1,
                                                        borderRadius: '9px',
                                                    }}
                                                    disableFuture
                                                    views={['year', 'month', 'day']}
                                                    format="DD.MM.YYYY"
                                                />
                                            </LocalizationProvider>
                                        </Box>
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Grid item xs={5}>
                                <ButtonBase sx={{ width: 300, height: 250 }}>
                                    <Img
                                        alt="complex"
                                        src="https://michael.stapelberg.ch/posts/2023-01-15-gokrazy-instance-centric-config/gokrazy-logo.png"
                                    />
                                </ButtonBase>
                            </Grid>
                            <Grid container p={1} m={2}>
                                <Grid item xs={12}>
                                    <Box p={1} mb={1}>
                                        <CustomTextField
                                            id="email"
                                            variant="outlined"
                                            placeholder="Эл. почта"
                                            color="primary"
                                            fullWidth
                                            onChange={formik.handleChange}
                                            value={formik.values.email}
                                        />
                                    </Box>
                                </Grid>
                                <Grid item xs={12}>
                                    <Box p={1} mb={3}>
                                        <CustomTextField
                                            id="phone_number"
                                            variant="outlined"
                                            placeholder="Номер телефона"
                                            color="primary"
                                            fullWidth
                                            onChange={formik.handleChange}
                                            value={formik.values.phone_number}
                                            error={Boolean(
                                                touched.phone_number && errors.phone_number,
                                            )}
                                            helperText={touched.phone_number && errors.phone_number}
                                        />
                                    </Box>
                                </Grid>
                                <Grid item xs={12}>
                                    <Box p={1} mb={1}>
                                        <CustomTextField
                                            id="blog"
                                            variant="outlined"
                                            placeholder="О себе"
                                            color="primary"
                                            multiline
                                            rows={4}
                                            fullWidth
                                            onChange={formik.handleChange}
                                            value={formik.values.blog}
                                            error={Boolean(touched.blog && errors.blog)}
                                            helperText={touched.blog && errors.blog}
                                        />
                                    </Box>
                                </Grid>
                                {user.role == 'TEACHER' ? (
                                    <Grid container item xs={12}>
                                        <ModalWindow
                                            button={
                                                <>
                                                    <Grid item>
                                                        <Typography>Я преподаватель</Typography>
                                                    </Grid>
                                                    <Grid item mt={1}>
                                                        <ChevronRight />
                                                    </Grid>
                                                </>
                                            }
                                            children={<TeacherSettings />}
                                        />
                                    </Grid>
                                ) : (
                                    <></>
                                )}
                            </Grid>
                        </Grid>
                        <Button type="submit" sx={{left:"85%", color:"white", backgroundColor: "black"}}>
                                Обновить
                        </Button>
                    </Paper>
                </form>
            </FormikProvider>
        </>
    );
};

export default ProfileSettings;
