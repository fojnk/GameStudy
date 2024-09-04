// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import React from 'react';
import { Button, Stack } from '@mui/material';
import { Link } from 'react-router-dom';

import CustomTextField from '../../../components/forms/theme-elements/CustomTextField';

const ButtonSx = {
    color: 'primary.contrastText',
    backgroundColor: 'primary.main',
    '&:hover': {
        color: 'secondary.contrastText',
        backgroundColor: 'secondary.main',
    },
};

const AuthForgotPassword = () => (
    <>
        <Stack mt={4} spacing={2}>
            <CustomTextField
                id="reset-email"
                placeholder="Эл. почта"
                variant="outlined"
                fullWidth
            />

            <Button sx={ButtonSx} size="large" fullWidth component={Link} to="/">
                Отправить ссылку
            </Button>
            <Button sx={ButtonSx} size="large" fullWidth component={Link} to="/auth/login">
                Обратно к входу в личный кабинет
            </Button>
        </Stack>
    </>
);

export default AuthForgotPassword;
