// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import {
    Backdrop,
    Box,
    Button,
    ButtonBase,
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

import { AddCircleOutline, DeleteOutline } from '@mui/icons-material';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { IconEdit } from '@tabler/icons-react';
import toast from 'react-hot-toast';
import LessonEdit from 'src/components/apps/lessons/details/LessonEdit';
import TaskEdit from 'src/components/apps/tasks/details/TaskEdit';
import TaskInfo from 'src/components/apps/tasks/details/TaskInfo';
import { useDispatch } from 'src/store/Store';
import { deleteLessonFromCourse, fetchCourseLessons } from 'src/store/apps/lessons/LessonSlice';
import { deleteTaskFromLesson } from 'src/store/apps/tasks/TaskSlice';
import { CourseLesson, lessonType } from 'src/types/apps/lesson';
import { TaskType } from 'src/types/apps/task';
import LessonInfo from '../../components/apps/lessons/details/LessonInfo';
import CreateTask from 'src/components/apps/tasks/details/TaskCreate';

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

function TaskRow(props: { row: TaskType; id: number; courseId: string; lessonId: string }) {
    const { row, id } = props;
    const dispatch = useDispatch();
    const [openTask, setTaskOpen] = React.useState(false);
    const [editTask, setEditTask] = React.useState(false);

    async function handleTaskDeletion(){
        try {
            await dispatch(deleteTaskFromLesson(props.lessonId, row.id ? row.id.toString() : ''));
            await dispatch(fetchCourseLessons(props.courseId));
            toast.success('Задача успешно удалена.');
        } catch (err) {
            toast.error('Задача не была удалена.');
        }
    };

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
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={editTask}
                onClose={() => setEditTask(false)}
                closeAfterTransition
                slots={{ backdrop: Backdrop }}
            >
                <Fade in={editTask}>
                    <Box sx={style}>
                        <TaskEdit courseId={props.courseId} type={2} task={row} />
                    </Box>
                </Fade>
            </Modal>
            <TableRow key={row.id + props.lessonId}>
                <TableCell>
                    <Typography fontWeight="600">{'Задача ' + (id + 1)}</Typography>
                </TableCell>
                <TableCell>
                    <Button onClick={() => setTaskOpen(true)}>
                        <Typography fontWeight="400">{row.title}</Typography>
                    </Button>
                </TableCell>
                <TableCell>
                    <ButtonBase onClick={() => setEditTask(true)}>
                        <IconEdit />
                    </ButtonBase>
                </TableCell>
                <TableCell>
                    <ButtonBase onClick={() => handleTaskDeletion()}>
                        <DeleteOutline />
                    </ButtonBase>
                </TableCell>
            </TableRow>
        </>
    );
}

function Row(props: { row: CourseLesson; id: number; courseId: string }) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const [openLesson, setLessonOpen] = React.useState(false);
    const [editLesson, setEditLesson] = React.useState(false);
    const [createTask, setCreateTask] = React.useState(false);
    const dispatch = useDispatch();


    const handleLessonDeletion = () => {
        try {
            dispatch(deleteLessonFromCourse(props.courseId, row.id ? row.id.toString() : ''));
            dispatch(fetchCourseLessons(props.courseId));
            toast.success('Урок был успешно удалён.');
        } catch (err) {
            toast.error('Урок не был удален.');
        }
    };

    return (
        <>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={createTask}
                onClose={() => setCreateTask(false)}
                closeAfterTransition
                slots={{ backdrop: Backdrop }}
            >
                <Fade in={createTask}>
                    <Box sx={style}>
                        <CreateTask
                            courseId={props.courseId}
                            type={1}
                            key={row.id ? row.id : 0 + 1}
                            id={row.id ? row.id.toString() : ''}
                        />
                    </Box>
                </Fade>
            </Modal>
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
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={editLesson}
                onClose={() => setEditLesson(false)}
                closeAfterTransition
                slots={{ backdrop: Backdrop }}
            >
                <Fade in={editLesson}>
                    <Box sx={style}>
                        <LessonEdit courseId={props.courseId} lesson={row} />
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
                    <ButtonBase onClick={() => setEditLesson(true)}>
                        <IconEdit />
                    </ButtonBase>
                </TableCell>
                <TableCell>
                    <ButtonBase onClick={() => handleLessonDeletion()}>
                        <DeleteOutline />
                    </ButtonBase>
                </TableCell>
            </TableRow>
            <TableRow>
                <TableCell sx={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box margin={1}>
                            <Table size="small" aria-label="purchases">
                                <TableBody>
                                    <TableRow>
                                        <ButtonBase onClick={() => setCreateTask(true)}>
                                            <Stack direction="row" spacing={1}>
                                                <Typography>Добавить задачу</Typography>
                                                <AddCircleOutline />
                                            </Stack>
                                        </ButtonBase>
                                    </TableRow>
                                    {row.tasks?.map((taskRow: any, index) => (
                                        <TaskRow
                                            lessonId={row.id ? row.id.toString() : ''}
                                            courseId={props.courseId}
                                            row={taskRow}
                                            id={index}
                                        />
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

const EditLessonTable = ({ lessons, courseId }: { lessons: lessonType[]; courseId: string }) => (
    <PageContainer title="Уроки" description="this is Collapsible Table page">
        <BlankCard>
            <TableContainer
                component={Paper}
                sx={{
                    maxHeight: 500,
                }}
            >
                <Table aria-label="collapsible sticky table" sx={{}}>
                    <TableHead>
                        <TableRow
                            sx={{
                                '& th': {
                                    backgroundColor: 'background.default',
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
                            <Row courseId={courseId} id={index + 1} key={row.title} row={row} />
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </BlankCard>
    </PageContainer>
);

export default EditLessonTable;
