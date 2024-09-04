// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import React from 'react';
import { Box, Typography } from '@mui/material';
import PageContainer from 'src/components/container/PageContainer';

import AuthForgotPassword from '../authForms/AuthForgotPassword';

const ForgotPassword = () => (
    <PageContainer title="Смена пароля" description="this is Forgot Password page">
        <Box
            p={24}
            sx={{
                backgroundColor: '#FFFFFF',
                height: '650px',
                borderRadius: 0,
                paddingTop: '100px',
            }}
        >
            <Typography
                component="span"
                color="black"
                variant="h6"
                fontWeight="100"
                position="relative"
                fontSize={20}
                justifyContent="center"
            >
                Забыли пароль?
            </Typography>

            <Typography color="textSecondary" variant="subtitle2" fontWeight="400" mt={1}>
                Пожалуйста введите эл. почту, указанную в вашем аккунте, мы отправим на неё ссылку
                для смены пароля.
            </Typography>
            <AuthForgotPassword />
        </Box>
    </PageContainer>
);

export default ForgotPassword;
