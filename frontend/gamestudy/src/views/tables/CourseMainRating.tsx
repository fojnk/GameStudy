import React, { useEffect } from 'react';
import {
    Box,
    Stack,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography,
} from '@mui/material';

import PageContainer from 'src/components/container/PageContainer';

import BlankCard from 'src/components/shared/BlankCard';
import useAuth from 'src/guards/authGuard/UseAuth';
import { CourseProgressType } from 'src/types/apps/course';

const CourseMainRating = ({courseProgresses} : {courseProgresses: CourseProgressType[]}) => {
    const { user } = useAuth();

    return (
        <PageContainer title="Группа" description="this is Fixed Header Table page">
            <BlankCard>
                <TableContainer
                    sx={{
                        maxHeight: 440,
                    }}
                >
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead color="primary.main">
                            <TableRow
                                sx={{
                                    '& th': {
                                        backgroundColor: 'primary.main',
                                    },
                                }}
                            >
                                <TableCell >
                                        <Typography
                                            variant="h6"
                                            fontWeight="500"
                                            color="primary.light"
                                        >
                                            Общий рейтинг
                                        </Typography>
                                    </TableCell>
                                    <TableCell />
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            { courseProgresses?.map((row) => {
                                return (
                                        <TableRow>
                                    
                                        <TableCell>
                                            <Stack spacing={2} direction="row" alignItems="center">
                                                <Box>
                                                    <Typography>
                                                        {row.id}
                                                    </Typography>
                                                </Box>
                                            </Stack>
                                        </TableCell>
                                        <TableCell>
                                            <Box>
                                                <Typography>{row.reached_exp}</Typography>
                                            </Box>
                                        </TableCell>
                                    </TableRow>
                                );
                            })}
                        </TableBody>
                    </Table>
                </TableContainer>
            </BlankCard>
        </PageContainer>
    );
};

export default CourseMainRating;
