// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { ChevronRight } from '@mui/icons-material';
import {
    Button,
    Stack,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from '@mui/material';
import { Box } from '@mui/system';
import { Link } from 'react-router-dom';

import { CourseType } from 'src/types/apps/course';

interface columnType {
    id: string;
    label: string;
    minWidth: number;
}

const columns: columnType[] = [
    { id: 'title', label: 'Название курса', minWidth: 25 },
    { id: 'amt_lessons', label: 'Уроки', minWidth: 10 },
    { id: 'amt_tasks', label: 'Задачи', minWidth: 10 },
];

function Row(props: { row: CourseType }) {
    const { row } = props;
    return (
        <>
            <TableRow key={row.id}>
                <TableCell>
                    <Typography>{row.title}</Typography>
                </TableCell>
                <TableCell>
                    <Typography textAlign={'center'}>{row.lesson_ids?.length}</Typography>
                </TableCell>
                <TableCell>
                    <Typography textAlign={'center'}>{row.task_ids?.length}</Typography>
                </TableCell>
            </TableRow>
        </>
    );
}

const FixCourseTable = ({ courses, title }: { courses?: CourseType[]; title: string }) => {
    return (
        <>
            <Box>
                <Box p={2}>
                    <Button to="/courses/" component={Link}>
                        <Stack direction="row" spacing={1}>
                            <Typography fontSize={20} fontWeight={600} textAlign={'center'}>
                                {title}
                            </Typography>
                            <ChevronRight />
                        </Stack>
                    </Button>
                </Box>
                <Box
                    pt={1}
                    pb={1}
                    sx={{
                        border: 1,
                        borderRadius: '7px',
                    }}
                >
                    <TableContainer
                        sx={{
                            maxHeight: 210,
                        }}
                    >
                        <Table stickyHeader aria-label="sticky table">
                            <TableHead color="primary.main">
                                <TableRow
                                    sx={{
                                        '& th': {
                                            backgroundColor: 'primary.light',
                                        },
                                    }}
                                >
                                    {columns.map((column) => (
                                        <TableCell
                                            key={column.id}
                                            style={{ minWidth: column.minWidth }}
                                        >
                                            <Typography variant="h6" fontWeight="600" color="black">
                                                {column.label}
                                            </Typography>
                                        </TableCell>
                                    ))}
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {courses?.map((row) => {
                                    return <Row row={row} />;
                                })}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Box>
            </Box>
        </>
    );
};

export default FixCourseTable;
