// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import {
    Backdrop,
    Box,
    Button,
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
import React from 'react';

import TaskInfo from 'src/components/apps/tasks/details/TaskInfo';
import PageContainer from 'src/components/container/PageContainer';
import BlankCard from 'src/components/shared/BlankCard';
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

function Row(props: { row: TaskType; id: number }) {
    const { row } = props;
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
                        <TaskInfo setIsWorking={setTaskOpen} id={props.id + 1} task={row} />
                    </Box>
                </Fade>
            </Modal>
            <TableRow hover key={row.id}>
                <TableCell>
                    <Typography variant="h6">{'Задача ' + (props.id + 1)}</Typography>
                </TableCell>
                <TableCell>
                    <Button onClick={() => setTaskOpen(true)}>
                        <Typography variant="h6">{row.title}</Typography>
                    </Button>
                </TableCell>
                <TableCell>
                    <Typography variant="h6">{row.experience + ' баллов'}</Typography>
                </TableCell>
                <TableCell>
                    <Typography variant="h6">{'Х валюты'}</Typography>
                </TableCell>
            </TableRow>
        </>
    );
}

const TaskTable = ({ tasks }: { tasks?: TaskType[] }) => {
    return (
        <PageContainer title="Группы" description="this is Fixed Header Table page">
            <BlankCard>
                <TableContainer
                    sx={{
                        maxHeight: 200,
                    }}
                >
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow
                                sx={{
                                    '& th': {
                                        backgroundColor: 'secondary.main',
                                    },
                                }}
                            >
                                <TableCell>
                                    <Typography
                                        variant="h6"
                                        textAlign={'left'}
                                        color="primary.light"
                                    >
                                        Задачи
                                    </Typography>
                                </TableCell>
                                <TableCell />
                                <TableCell />
                                <TableCell />
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {tasks?.map((row: any, index) => {
                                return <Row id={index} row={row} />;
                            })}
                        </TableBody>
                    </Table>
                </TableContainer>
            </BlankCard>
        </PageContainer>
    );
};

export default TaskTable;
