// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import {
    Avatar,
    Box,
    Collapse,
    IconButton,
    Paper,
    Stack,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography,
} from '@mui/material';
import * as React from 'react';
import PageContainer from 'src/components/container/PageContainer';
import BlankCard from 'src/components/shared/BlankCard';

import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { useDispatch, useSelector } from 'src/store/Store';
import { fetchCourseProgress } from 'src/store/apps/courses/CourseSlise';
import { CourseProgressType, CourseType } from 'src/types/apps/course';

function Row(props: { row: CourseType }) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const dispatch = useDispatch();
    React.useEffect(() => {
        if (row.id) {
            dispatch(fetchCourseProgress(row.id.toString()));
        }
    }, [dispatch]);

    const res: CourseProgressType[] | undefined = useSelector((state) => {
        return state.courseReduser.allSelectedCourseProgresses;
    });

    var progresses = null
    if (res) {
        progresses = res
    }

    return (
        <>
            <TableRow
                sx={{
                    '& > *': { borderBottom: 'unset' },
                }}
            >
                <TableCell>
                    <Stack direction="row" spacing={2} alignItems="center">
                        <IconButton
                            aria-label="expand row"
                            size="small"
                            onClick={() => setOpen(!open)}
                        >
                            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                        </IconButton>
                        <Avatar
                            src={row.image_path}
                            alt={"bad format"}
                            sx={{
                                width: 70,
                                height: 50,
                                borderRadius: '10px',
                            }}
                        />
                        <Typography variant="h6" fontWeight="600">
                            {row.title}
                        </Typography>
                    </Stack>
                </TableCell>
            </TableRow>
            <TableRow>
                <TableCell sx={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box margin={1}>
                            <Typography
                                gutterBottom
                                variant="h5"
                                sx={{
                                    mt: 2,
                                    backgroundColor: (theme) => theme.palette.grey.A200,
                                    p: '5px 15px',
                                    color: (theme) =>
                                        `${
                                            theme.palette.mode === 'dark'
                                                ? theme.palette.grey.A200
                                                : 'rgba(0, 0, 0, 0.87)'
                                        }`,
                                }}
                            >
                                Рейтинг
                            </Typography>
                            <Table size="small" aria-label="purchases">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>
                                            <Typography variant="h6">ФИО</Typography>
                                        </TableCell>
                                        <TableCell>
                                            <Typography variant="h6">Дата</Typography>
                                        </TableCell>
                                        <TableCell>
                                            <Typography variant="h6">Баллы</Typography>
                                        </TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {progresses ? progresses.map((historyRow : CourseProgressType) => (
                                        <TableRow key={historyRow.id}>
                                            <TableCell>
                                                <Typography color="textSecondary" fontWeight="400">
                                                    {historyRow.student_id}
                                                </Typography>
                                            </TableCell>
                                            <TableCell>
                                                <Typography color="textSecondary" fontWeight="400">
                                                    {historyRow.course_id}
                                                </Typography>
                                            </TableCell>
                                            <TableCell>
                                                <Typography color="textSecondary" fontWeight="400">
                                                    {historyRow.reached_exp}
                                                </Typography>
                                            </TableCell>
                                        </TableRow>
                                    )) : <></>}
                                </TableBody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </>
    );
}


const CollapsibleCourseTable = ({ title, courses }: { title: string; courses: CourseType[] }) => (
    <PageContainer title="Рейтинги" description="this is Collapsible Table page">
        <BlankCard>
            <TableContainer
                component={Paper}
                sx={{
                    maxHeight: 800,
                }}
            >
                <Table aria-label="collapsible sticky table" sx={{}}>
                    <TableHead>
                        <TableRow
                            sx={{
                                '& th': {
                                    backgroundColor: 'primary.main',
                                },
                            }}
                        >
                            <TableCell>
                                <Typography variant="h6" textAlign={'left'} color="primary.light">
                                    {title}
                                </Typography>
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {courses.map((row) => (
                            <Row key={row.id} row={row} />
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </BlankCard>
    </PageContainer>
);

export default CollapsibleCourseTable;
