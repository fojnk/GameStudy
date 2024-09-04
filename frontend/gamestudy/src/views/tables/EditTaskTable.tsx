// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { DeleteOutline } from '@mui/icons-material';
import {
    Backdrop,
    Box,
    Button,
    ButtonBase,
    Fade,
    Modal,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography,
} from '@mui/material';
import { IconEdit } from '@tabler/icons-react';
import React from 'react';
import toast from 'react-hot-toast';
import TaskEdit from 'src/components/apps/tasks/details/TaskEdit';

import TaskInfo from 'src/components/apps/tasks/details/TaskInfo';
import PageContainer from 'src/components/container/PageContainer';
import BlankCard from 'src/components/shared/BlankCard';
import { deleteTaskFromCourse, fetchCourseTasks } from 'src/store/apps/tasks/TaskSlice';
import { useDispatch } from 'src/store/Store';
import { TaskType } from 'src/types/apps/task';

const style = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '85%',
    height: '85%',
    bgcolor: 'background.paper',
    boxShadow: 24,
};

function Row(props: { row: TaskType; id: number; courseId: string }) {
    const { row } = props;
    const dispatch = useDispatch();
    const [editTask, setEditTask] = React.useState(false);
    const [openTask, setTaskOpen] = React.useState(false);

    const handleTaskDeletion = () => {
        try {
            dispatch(deleteTaskFromCourse(props.courseId, row.id ? row.id.toString() : ''));
            dispatch(fetchCourseTasks(props.courseId));
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
                        <TaskInfo setIsWorking={setTaskOpen} id={props.id + 1} task={row} />
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
                        <TaskEdit courseId={props.courseId} type={1} task={row} />
                    </Box>
                </Fade>
            </Modal>
            <TableRow hover key={(row.id?row.id: 0 )+ props.id}>
                <TableCell>
                    <Typography variant="h6">{'Задача ' + (props.id + 1)}</Typography>
                </TableCell>
                <TableCell>
                    <Button onClick={() => setTaskOpen(true)}>
                        <Typography variant="h6">{row.title}</Typography>
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

const EditTaskTable = ({ tasks, courseId }: { tasks?: TaskType[]; courseId: string }) => {
    return (
        <PageContainer title="Группы" description="this is Fixed Header Table page">
            <BlankCard>
                <TableContainer
                    sx={{
                        maxHeight: 400,
                    }}
                >
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow
                                sx={{
                                    '& th': {
                                        backgroundColor: 'background.default',
                                    },
                                }}
                            >
                                <TableCell>
                                    <Typography
                                        variant="h6"
                                        textAlign={'left'}
                                        color="primary.light"
                                    >
                                        Уроки
                                    </Typography>
                                </TableCell>
                                <TableCell />
                                <TableCell />
                                <TableCell />
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {tasks?.map((row: any, index) => {
                                return <Row courseId={courseId} id={index} row={row} />;
                            })}
                        </TableBody>
                    </Table>
                </TableContainer>
            </BlankCard>
        </PageContainer>
    );
};

export default EditTaskTable;
