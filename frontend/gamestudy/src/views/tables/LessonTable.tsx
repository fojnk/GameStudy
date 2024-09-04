// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import {
    Backdrop,
    Box,
    Button,
    Collapse,
    Fade,
    IconButton,
    Modal,
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
import TaskInfo from 'src/components/apps/tasks/details/TaskInfo';
import { CourseLesson, lessonType } from 'src/types/apps/lesson';
import { TaskType } from 'src/types/apps/task';
import LessonInfo from '../../components/apps/lessons/details/LessonInfo';
import { CourseProgressType } from 'src/types/apps/course';

const style = {
    position: 'relative',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '85%',
    height: '85%',
    bgcolor: 'background.paper',
    boxShadow: 24,
};

function TaskRow(props: { row: TaskType; id: number }) {
    const { row, id } = props;
    const [openTask, setTaskOpen] = React.useState(false);
    return (
        <>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={openTask}
                onClose={() => setTaskOpen(false)}
                closeAfterTransition
                slots={{ backdrop: Backdrop }}
            >
                <Fade in={openTask}>
                    <Box sx={style}>
                        <TaskInfo setIsWorking={setTaskOpen} id={id + 1} task={row} />
                    </Box>
                </Fade>
            </Modal>
            <TableRow key={row.id}>
                <TableCell>
                    <Typography fontWeight="600">{'Задача ' + (id + 1)}</Typography>
                </TableCell>
                <TableCell>
                    <Button onClick={() => setTaskOpen(true)}>
                        <Typography fontWeight="400">{row.title}</Typography>
                    </Button>
                </TableCell>
                <TableCell>
                    <Typography fontWeight="400">{row.experience} баллов</Typography>
                </TableCell>
                <TableCell>
                    <Typography fontWeight="400">{'X'} валюты</Typography>
                </TableCell>
            </TableRow>
        </>
    );
}

function Row(props: { row: CourseLesson; id: number; progress: CourseProgressType }) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const [openLesson, setLessonOpen] = React.useState(false);

    return (
        <>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={openLesson}
                onClose={() => setLessonOpen(false)}
                closeAfterTransition
                slots={{ backdrop: Backdrop }}
            >
                <Fade in={openLesson}>
                    <Box sx={style}>
                        <LessonInfo setIsWorking={setLessonOpen} id={props.id} lesson={row} />
                    </Box>
                </Fade>
            </Modal>
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
                   
                        <Typography variant="h6" fontWeight="600">
                            {'Урок ' + props.id}
                        </Typography>
                    </Stack>
                </TableCell>
                <TableCell>
                    <Button onClick={() => setLessonOpen(true)}>
                        <Stack direction="row" spacing={2} alignItems="center">
                            <Typography variant="h6" fontWeight="600">
                                {row.title}
                            </Typography>
                        </Stack>
                    </Button>
                </TableCell>

                <TableCell>
                    <Stack direction="row" spacing={2} alignItems="center">
                        <Typography variant="h6" fontWeight="600">
                            {row.experience + ' баллов'}
                        </Typography>
                    </Stack>
                </TableCell>
                <TableCell>
                    <Stack direction="row" spacing={2} alignItems="center">
                        <Typography variant="h6" fontWeight="600">
                            {'X валюты'}
                        </Typography>
                    </Stack>
                </TableCell>
            </TableRow>
            <TableRow>
                <TableCell sx={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box margin={1}>
                            <Table size="small" aria-label="purchases">
                                <TableBody>
                                    {row.tasks?.map((taskRow: any, index) => (
                                        <TaskRow row={taskRow} id={index} />
                                    ))}
                                </TableBody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </>
    );
}

const LessonTable = ({ lessons, progress }: { lessons: lessonType[]; progress: CourseProgressType }) => (
    <PageContainer title="Уроки" description="this is Collapsible Table page">
        <BlankCard>
            <TableContainer
                component={Paper}
                sx={{
                    maxHeight: 5000,
                }}
            >
                <Table aria-label="collapsible sticky table" sx={{}}>
                    <TableHead>
                        <TableRow
                            sx={{
                                '& th': {
                                    backgroundColor: 'secondary.main',
                                },
                            }}
                        >
                            <TableCell>
                                <Typography variant="h6" textAlign={'left'} color="primary.light">
                                    Уроки
                                </Typography>
                            </TableCell>
                            <TableCell />
                            <TableCell />
                            <TableCell />
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {lessons.map((row, index) => (
                            <Row progress={progress} id={index + 1} key={row.title} row={row} />
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </BlankCard>
    </PageContainer>
);

export default LessonTable;
