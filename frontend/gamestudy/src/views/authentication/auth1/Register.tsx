// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import React from 'react';
import { Link } from 'react-router-dom';
import { Box, Typography, Stack } from '@mui/material';

import PageContainer from 'src/components/container/PageContainer';

import AuthRegister from '../authForms/AuthRegister';

const Register = () => (
    <PageContainer title="Регистрация" description="this is Register page">
        <Box
            p={24}
            sx={{
                backgroundColor: '#FFFFFF',
                height: '650px',
                borderRadius: 0,
                paddingTop: '100px',
            }}
        >
            <AuthRegister
                title="Welcome to Modernize"
                subtitle={
                    <Stack direction="row" spacing={1} justifyContent="center" mt={3}>
                        <Typography color="textSecondary" variant="h6" fontWeight="400">
                            Уже есть аккаунт?
                        </Typography>
                        <Typography
                            component={Link}
                            to="/auth/login"
                            fontWeight="500"
                            sx={{
                                textDecoration: 'none',
                                color: 'secondary.main',
                            }}
                        >
                            Вход в личный кабинет
                        </Typography>
                    </Stack>
                }
            />
        </Box>
    </PageContainer>
);

export default Register;
