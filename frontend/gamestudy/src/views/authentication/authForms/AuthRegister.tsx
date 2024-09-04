// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Alert, Box, Button, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

import { Stack } from '@mui/system';
import { Form, FormikProvider, useFormik } from 'formik';
import useAuth from 'src/guards/authGuard/UseAuth';
import useMounted from 'src/guards/authGuard/UseMounted';
import { registerType } from 'src/types/auth/auth';
import * as Yup from 'yup';
import CustomTextField from '../../../components/forms/theme-elements/CustomTextField';

const ButtonSx = {
    color: 'primary.contrastText',
    backgroundColor: 'primary.main',
    '&:hover': {
        color: 'secondary.contrastText',
        backgroundColor: 'secondary.main',
    },
};

const AuthRegister = ({ subtitle }: registerType) => {
    const mounted = useMounted();
    const { signup, loginWithGoogle } = useAuth();
    const navigate = useNavigate();

    const handleLoginGoogle = async () => {
        try {
            await loginWithGoogle();
        } catch (error) {
            console.error(error);
        }
    };

    const registerSchema = Yup.object().shape({
        email: Yup.string().email('Не корректная почта.').required('Почта обязательна.'),
        password: Yup.string()
            .min(6, 'Пароль должен содержать как минимум 6 символов.')
            .required('Пароль обязателен'),
    });

    const formik = useFormik({
        initialValues: {
            email: '',
            password: '',
            policy: true,
            submit: null,
        },

        validationSchema: registerSchema,

        onSubmit: async (values, { setErrors, setStatus, setSubmitting }) => {
            try {
                await signup(values.email, values.password);
                console.log('signup request was sent');
                navigate('/auth/login');
                if (mounted.current) {
                    setStatus({ success: true });
                    setSubmitting(true);
                }
            } catch (err: any) {
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
                    textAlign={'center'}
                >
                    РЕГИСТРАЦИЯ
                </Typography>
            </Box>
            <Box>
                {errors.submit && (
                    <Box mt={2}>
                        <Alert severity="error">{errors.submit}</Alert>
                    </Box>
                )}
                <FormikProvider value={formik}>
                    <Form onSubmit={handleSubmit}>
                        <Stack mb={2}>
                            <Box mb={2}>
                                <CustomTextField
                                    id="email"
                                    variant="outlined"
                                    placeholder="Эл. почта"
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
                        </Stack>
                        <Button
                            sx={ButtonSx}
                            size="large"
                            fullWidth
                            type="submit"
                            disabled={isSubmitting}
                        >
                            Зарегестрироваться
                        </Button>
                        <Box mt={2}>
                            <Button
                                sx={ButtonSx}
                                size="large"
                                fullWidth
                                onClick={handleLoginGoogle}
                            >
                                Зарегестрироваться с помощью Google
                            </Button>
                        </Box>
                    </Form>
                </FormikProvider>
            </Box>
            {subtitle}
        </>
    );
};

export default AuthRegister;
