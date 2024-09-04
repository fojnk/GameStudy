// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import {
    Alert,
    Box,
    Button,
    FormControlLabel,
    FormGroup,
    Stack,
    Typography
} from '@mui/material';
import { Link } from 'react-router-dom';

import { Form, FormikProvider, useFormik } from 'formik';
import CustomCheckbox from 'src/components/forms/theme-elements/CustomCheckbox';
import CustomTextField from 'src/components/forms/theme-elements/CustomTextField';
import useAuth from 'src/guards/authGuard/UseAuth';
import useMounted from 'src/guards/authGuard/UseMounted';
import { loginType } from 'src/types/auth/auth';
import * as Yup from 'yup';
import toast from 'react-hot-toast';

const ButtonSx = {
    color: 'primary.contrastText',
    backgroundColor: 'primary.main',
    '&:hover': {
        color: 'secondary.contrastText',
        backgroundColor: 'secondary.main',
    },
};

const AuthLogin = ({ subtitle }: loginType) => {
    const mounted = useMounted();
    const { signin } = useAuth();
    const { loginWithGoogle } = useAuth();

    const handleLoginGoogle = async () => {
        try {
            await loginWithGoogle();
        } catch (error) {
            console.error(error);
        }
    };

    const LoginSchema = Yup.object().shape({
        email: Yup.string().email('Некорректная почта.').required('Почта обязательна.'),
        password: Yup.string()
            .min(6, 'Пароль должен содержать как минимум 6 символов.')
            .required('Пароль обязателен'),
    });

    const formik = useFormik({
        initialValues: {
            email: '',
            password: '',
            submit: null,
        },

        validationSchema: LoginSchema,

        onSubmit: async (values, { setErrors, setStatus, setSubmitting }) => {
            try {
                await signin(values.email, values.password);
                toast("Добро пожаловать")
                if (mounted.current) {
                    setStatus({ success: true });
                    setSubmitting(true);
                }
            } catch (err: any) {
                toast.error("Пользователь не найден.")
                if (mounted.current) {
                    setStatus({ success: false });
                    setErrors({ submit: err.message });
                    setSubmitting(false);
                }
            }
        },
    });
    const { errors, touched, handleSubmit, isSubmitting, getFieldProps } = formik;

    return (
        <>
            <Box
                sx={{
                    margin: '0 auto 40px auto',
                }}
            >
                <Typography
                    component="span"
                    color="black"
                    variant="h6"
                    fontWeight="100"
                    position="relative"
                    fontSize={25}
                    justifyContent="center"
                >
                    ВХОД В ЛИЧНЫЙ КАБИНЕТ
                </Typography>
            </Box>
            <FormikProvider value={formik}>
                <form onSubmit={handleSubmit}>
                    <Stack mb={2}>
                        <Box mb={2}>
                            <CustomTextField
                                id="email"
                                variant="outlined"
                                placeholder="Эл. почта"
                                color="primary"
                                fullWidth
                                {...getFieldProps('email')}
                                error={Boolean(touched.email && errors.email)}
                                helperText={touched.email && errors.email}
                            />
                        </Box>
                        <Box>
                            <CustomTextField
                                id="password"
                                type="password"
                                variant="outlined"
                                placeholder="Пароль"
                                fullWidth
                                {...getFieldProps('password')}
                                error={Boolean(touched.password && errors.password)}
                                helperText={touched.password && errors.password}
                            />
                        </Box>
                        <Stack
                            justifyContent="space-between"
                            direction="row"
                            alignItems="center"
                            my={1}
                        >
                            <FormGroup>
                                <FormControlLabel
                                    control={<CustomCheckbox defaultChecked />}
                                    label="Запомнить меня"
                                />
                            </FormGroup>
                            <Typography
                                component={Link}
                                to="/auth/forgot"
                                fontWeight="500"
                                sx={{
                                    textDecoration: 'none',
                                    color: 'black',
                                }}
                            >
                                Забыли пароль?
                            </Typography>
                        </Stack>
                    </Stack>
                    <Box>
                        <Button
                            sx={ButtonSx}
                            size="large"
                            fullWidth
                            type="submit"
                            disabled={isSubmitting}
                        >
                            Войти
                        </Button>
                    </Box>
                    <Box mt={2}>
                        <Button sx={ButtonSx} size="large" fullWidth onClick={handleLoginGoogle}>
                            Войти с помощью Google
                        </Button>
                    </Box>

                    {errors.submit && (
                        <Box mt={2}>
                            <Alert severity="error">{errors.submit}</Alert>
                        </Box>
                    )}
                </form>
            </FormikProvider>
            {subtitle}
        </>
    );
};

export default AuthLogin;
