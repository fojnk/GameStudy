// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { DeleteOutline } from '@mui/icons-material';
import {
    ButtonBase,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableRow,
    Typography,
} from '@mui/material';
import { Box } from '@mui/system';
import { IconEdit } from '@tabler/icons-react';
import toast from 'react-hot-toast';
import { Link } from 'react-router-dom';
import useAuth from 'src/guards/authGuard/UseAuth';
import { deleteCourse, deleteCrs, fetchCreatorCourses } from 'src/store/apps/courses/CourseSlise';
import { useDispatch } from 'src/store/Store';
import { CourseType } from 'src/types/apps/course';

function Row(props: { row: CourseType }) {
    const dispatch = useDispatch();
    const { user } = useAuth();
    const {  row } = props;

    const deleteC = () => {
        try {
            dispatch(deleteCrs(row.id ? row.id.toString() : ''));
            toast.success('Курс удален.');
        } catch (err) {
            toast.error('Не удалось удалить курс.');
        }
    };

    return (
        <>
            <TableRow key={row.id}>
                <TableCell>
                    <Typography>{row.title}</Typography>
                </TableCell>
                <TableCell>
                    <Typography textAlign={'center'}>Создан</Typography>
                </TableCell>
                <TableCell>
                    <Typography textAlign={'center'}>
                        {row.created_at ? row.created_at : '__-__-____'}
                    </Typography>
                </TableCell>
                <TableCell>
                    <ButtonBase to={'/courses/' + row.id + '/edit'} component={Link}>
                        <Typography textAlign={'center'}>
                            <IconEdit />
                        </Typography>
                    </ButtonBase>
                </TableCell>
                <TableCell>
                    <ButtonBase onClick={() => deleteC()}>
                        <Typography textAlign={'center'}>
                            <DeleteOutline />
                        </Typography>
                    </ButtonBase>
                </TableCell>
            </TableRow>
        </>
    );
}

const CourseEditTable = ({ courses }: { courses?: CourseType[] }) => {
    return (
        <>
            <Box mt={2} p={2}>
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

export default CourseEditTable;
