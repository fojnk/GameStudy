import React from 'react';
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
import { GroupType } from 'src/types/apps/group';
import { StudentType } from 'src/types/apps/student';
import useAuth from 'src/guards/authGuard/UseAuth';

interface columnType {
    id: string;
    label: string;
    minWidth: number;
}
const columns: columnType[] = [
    { id: 'pname', label: 'Название группы', minWidth: 170 },
    { id: 'exp', label: 'Опыт', minWidth: 170 }
];

const ASC = 'ascending';
const DSC = 'descending';

const StudentTable = ({ group }: { group?: GroupType }) => {
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
                                            {group?.title}
                                        </Typography>
                                    </TableCell>
                                    <TableCell >
                                        <Typography
                                            variant="h6"
                                            fontWeight="500"
                                            color="primary.light"
                                        >
                                            Опыт
                                        </Typography>
                                    </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            { group?.students?.map((row) => {
                                return (
                                        <TableRow>
                                    
                                        <TableCell>
                                            <Stack spacing={2} direction="row" alignItems="center">
                                                <Box>
                                                    <Typography>
                                                        {row.user?.surname + ' '}
                                                        {row.user?.name + ' '}
                                                        {row.user?.fathers_name + ' '}
                                                    </Typography>
                                                </Box>
                                            </Stack>
                                        </TableCell>
                                        <TableCell>
                                            <Box>
                                                <Typography>{row.experience} баллов</Typography>
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

export default StudentTable;
