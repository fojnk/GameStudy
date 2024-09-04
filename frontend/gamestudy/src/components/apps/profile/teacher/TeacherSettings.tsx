
import { Grid } from '@mui/material';
import { Form, FormikProvider, useFormik } from 'formik';
import CustomTextField from 'src/components/forms/theme-elements/CustomTextField';
import useAuth from 'src/guards/authGuard/UseAuth';
import useMounted from 'src/guards/authGuard/UseMounted';
import * as Yup from 'yup';


const TeacherSettings = () => {
    const mounted = useMounted();
    const { role } = useAuth();

    const LoginSchema = Yup.object().shape({
        email: Yup.string().email('Некорректная почта.'),
        password: Yup.string()
            .min(6, 'Пароль должен содержать как минимум 6 символов.')
            .required('Пароль обязателен'),
    });

    const formik = useFormik({
        initialValues: {
            organisation: role.organisation ? role.organisation : '',
            qualification: role.qualification ? role.qualification : '',
            submit: null,
        },

        validationSchema: LoginSchema,

        onSubmit: async (_, { setErrors, setStatus, setSubmitting }) => {
            try {
                //await signin(values.email, values.password);
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
            <FormikProvider value={formik}>
                <form onSubmit={handleSubmit}>
                        <Grid container direction="column">
                            <Grid item xs mb={2}>
                                    <CustomTextField
                                        id="organisation"
                                        variant="outlined"
                                        placeholder="Организация"
                                        color="primary"
                                        fullWidth
                                        {...getFieldProps('organisation')}
                                        error={Boolean(touched.organisation && errors.organisation)}
                                        helperText={touched.organisation && errors.organisation}
                                    />
                            </Grid>
                            <Grid item xs>
                                    <CustomTextField
                                        id="qualification"
                                        variant="outlined"
                                        placeholder="Квалификация"
                                        color="primary"
                                        fullWidth
                                        {...getFieldProps('qualification')}
                                        error={Boolean(
                                            touched.qualification && errors.qualification,
                                        )}
                                        helperText={touched.qualification && errors.qualification}
                                    />
                            </Grid>
                        </Grid>
                </form>
            </FormikProvider>
        </>
    );
};

export default TeacherSettings;
