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
    Typography,
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
    { id: 'amt_groups', label: 'Группы', minWidth: 10 },
    { id: 'date_start', label: 'Дата создания', minWidth: 10 },
    { id: 'date_end', label: 'Дата окончания', minWidth: 10 },
    { id: 'rating', label: 'Рейтинг', minWidth: 10 },
];

function Row(props: { row: CourseType }) {
    const { row } = props;
    return (
        <>
            <TableRow key={row.id}>
                <TableCell>
                    <Typography textAlign={'center'}>{row.title}</Typography>
                </TableCell>
                <TableCell>
                    <Typography textAlign={'center'}>{row.lesson_ids?.length}</Typography>
                </TableCell>
                <TableCell>
                    <Typography textAlign={'center'}>{row.task_ids?.length}</Typography>
                </TableCell>
                <TableCell>
                    <Typography textAlign={'center'}>X</Typography>
                </TableCell>
                <TableCell>
                    <Typography textAlign={'center'}>{row.created_at ? row.created_at : "X"}</Typography>
                </TableCell>
                <TableCell>
                    <Typography textAlign={'center'}>{row.endDate ? row.endDate : "X"}</Typography>
                </TableCell>
                <TableCell>
                    <Typography textAlign={'center'}>{row.rating}</Typography>
                </TableCell>
            </TableRow>
        </>
    );
}

const CreatorCourseTable = ({ courses, title }: { courses?: CourseType[]; title: string }) => {
    return (
        <>
            <Box p={2}>
                <Box mb={2}>
                    <Button to="/courses/" component={Link}>
                        <Stack direction="row" spacing={1}>
                            <Typography fontSize={20} fontWeight={600} textAlign={'center'}>
                                Последние действия
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
                            maxHeight: 300,
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
                                            <Typography
                                                textAlign={'center'}
                                                fontWeight="600"
                                                color="black"
                                            >
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

export default CreatorCourseTable;
