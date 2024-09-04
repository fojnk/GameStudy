// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import {
    Backdrop,
    Box,
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
import { useEffect, useState } from 'react';

import PageContainer from 'src/components/container/PageContainer';
import BlankCard from 'src/components/shared/BlankCard';
import { fetchCourses, fetchDisciplineCourses } from 'src/store/apps/courses/CourseSlise';
import { useDispatch, useSelector } from 'src/store/Store';

import { DisciplineType } from 'src/types/apps/discipline';
import PaginationCourseTable from './PaginationCourseTable';

interface columnType {
    id: string;
    label: string;
    minWidth: number;
}
const columns: columnType[] = [{ id: 'pname', label: 'Название дисциплины', minWidth: 170 }];

const style = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '60%',
    height: '60%',
    bgcolor: 'background.paper',
    border: '2px solid #000',
    overflowY: 'auto',
    boxShadow: 24,
    p: 4,
};

function Row(props: { row: DisciplineType; id: number }) {
    const { row, id } = props;
    const [openGroup, setGroupOpen] = useState(false);
    const dispatch = useDispatch();

    useEffect(() => {
        if (row.id) {
            dispatch(fetchDisciplineCourses(row.id.toString()));
        }
    }, [dispatch]);

    const courses = useSelector((state) => {
        return state.courseReduser.courses;
    });

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
                        <PaginationCourseTable
                            title={'Курсы по дисциплине: ' + row.title}
                            type={1}
                            linkPath="/courses/"
                            courses={courses}
                        />
                    </Box>
                </Fade>
            </Modal>
            <TableRow hover key={row.id}>
                <TableCell>
                    <ButtonBase onClick={() => setGroupOpen(true)}>
                        <Typography fontWeight={600}>{row.title}</Typography>
                    </ButtonBase>
                </TableCell>
            </TableRow>
        </>
    );
}

const DisciplineTable = ({ disciplines }: { disciplines?: DisciplineType[] }) => {
    return (
        <PageContainer title="Дисциплины" description="this is Fixed Header Table page">
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
                                        style={{ minWidth: column.minWidth }}
                                    >
                                        <Typography
                                            variant="h6"
                                            fontWeight="500"
                                            color="primary.light"
                                        >
                                            Дисциплины
                                        </Typography>
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {disciplines?.map((row, index) => {
                                return <Row row={row} id={index} />;
                            })}
                        </TableBody>
                    </Table>
                </TableContainer>
            </BlankCard>
        </PageContainer>
    );
};

export default DisciplineTable;
