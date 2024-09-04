// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import React from 'react';
import { Link } from 'react-router-dom';
import {Box, Stack, Typography } from '@mui/material';
import PageContainer from 'src/components/container/PageContainer';
import AuthLogin from '../authForms/AuthLogin';

const Login = () => (
    <PageContainer title="Вход" description="this is Login page">
        <Box
            p={24}
            sx={{
                backgroundColor: '#FFFFFF',
                borderRadius: 0,
                paddingTop: '100px',
            }}
        >
            <AuthLogin
                subtitle={
                    <Stack direction="row" spacing={1} justifyContent="center" mt={3}>
                        <Typography color="textSecondary" variant="h6" fontWeight="500">
                            Нет аккаунта?
                        </Typography>
                        <Typography
                            component={Link}
                            to="/auth/register"
                            fontWeight="500"
                            sx={{
                                textDecoration: 'none',
                                color: 'secondary.main',
                            }}
                        >
                            Зарегестрироваться
                        </Typography>
                    </Stack>
                }
            />
        </Box>
    </PageContainer>
);

export default Login;
