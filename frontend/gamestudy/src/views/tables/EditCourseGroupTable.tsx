// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import {
    Backdrop,
    Box,
    ButtonBase,
    Fade,
    Modal,
    Stack,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from '@mui/material';
import { useState } from 'react';

import PageContainer from 'src/components/container/PageContainer';

import BlankCard from 'src/components/shared/BlankCard';
import { GroupType } from 'src/types/apps/group';
import StudentTable from './StudentTable';
import { IconEdit } from '@tabler/icons-react';
import { AddCircleOutline, DeleteOutline } from '@mui/icons-material';
import CreateGroup from 'src/components/apps/groups/details/CreateGroup';
import { deleteGroup, deleteGroupFromCourse } from 'src/store/apps/groups/GroupSlice';
import { useDispatch } from 'src/store/Store';
import toast from 'react-hot-toast';
import AddGroup from 'src/components/apps/groups/details/AddGroup';

interface columnType {
    id: string;
    label: string;
}

const style = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '50%',
    height: '85%',
    bgcolor: 'background.paper',
    border: '2px solid #000',
    overflowY: 'auto',
    boxShadow: 24,
    p: 4,
};

const columns: columnType[] = [{ id: 'pname', label: 'Название группы'}];

function Row(props: { row: GroupType; id: number; courseId: string }) {
    const { row, id } = props;
    const [openGroup, setGroupOpen] = useState(false);
    const dispatch = useDispatch();

    const handleGroupDeletion = () => {
        try {
            dispatch(deleteGroupFromCourse(props.courseId , row.id ? row.id.toString() : ''));
            toast.success('Группа успешно удалена.');
        } catch (err) {
            toast.error('Группа не была удалена.');
        }
    };
    return (
        <>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={openGroup}
                onClose={() => setGroupOpen(false)}
                closeAfterTransition
                slots={{ backdrop: Backdrop }}
            >
                <Fade in={openGroup}>
                    <Box sx={style}>
                        <StudentTable group={row} />
                    </Box>
                </Fade>
            </Modal>
            <TableRow hover key={row.id}>
                <TableCell>
                    <ButtonBase onClick={() => setGroupOpen(true)}>
                        <Typography fontWeight={600}>{row.title}</Typography>
                    </ButtonBase>
                </TableCell>
                <TableCell >
                    <ButtonBase sx={{marginLeft: "30px"}}>
                        <IconEdit />
                    </ButtonBase>
                    <ButtonBase sx={{marginLeft: "30px"}} onClick={() => handleGroupDeletion()}>
                        <DeleteOutline />
                    </ButtonBase>
                </TableCell>
            </TableRow>
        </>
    );
}

const EditCourseGroupTable = ({ groups, courseId }: { groups?: GroupType[], courseId: string }) => {
    const [addGroup, setAddGroup] = useState(false);

    return (
        <>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={addGroup}
                onClose={() => setAddGroup(false)}
                closeAfterTransition
                slots={{ backdrop: Backdrop }}
            >
                <Fade in={addGroup}>
                    <Box sx={style}>
                        <AddGroup courseId={courseId}/>
                    </Box>
                </Fade>
            </Modal>
            <PageContainer title="Группы" description="this is Fixed Header Table page">
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
                                    {columns.map((column) => (
                                        <TableCell
                                            key={column.id}
                                        >
                                            <Typography
                                                variant="h6"
                                                fontWeight="500"
                                                color="primary.light"
                                            >
                                                Группы
                                            </Typography>
                                        </TableCell>
                                    ))}
                                    <TableCell >
                                            <ButtonBase onClick={() => setAddGroup(true)}>
                                            <Stack direction="row" spacing={1}>
                                                <Typography sx={{color: "white"}}>Добавить группу</Typography>
                                                <AddCircleOutline sx={{color: "white"}}/>
                                            </Stack>
                                        </ButtonBase>
                                        </TableCell>

                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {groups?.map((row, index) => {
                                    return <Row courseId={courseId} row={row} id={index} />;
                                })}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </BlankCard>
            </PageContainer>
        </>
    );
};

export default EditCourseGroupTable;
