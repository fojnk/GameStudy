// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import * as React from 'react';

import {
    Box,
    ButtonBase,
    IconButton,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableFooter,
    TableHead,
    TablePagination,
    TableRow,
    Typography,
} from '@mui/material';
import { useTheme } from '@mui/material/styles';

import FirstPageIcon from '@mui/icons-material/FirstPage';
import KeyboardArrowLeft from '@mui/icons-material/KeyboardArrowLeft';
import KeyboardArrowRight from '@mui/icons-material/KeyboardArrowRight';
import LastPageIcon from '@mui/icons-material/LastPage';

import { Link } from 'react-router-dom';
import PageContainer from 'src/components/container/PageContainer';
import { CourseType } from 'src/types/apps/course';
import BlankCard from '../../components/shared/BlankCard';
import ParentCard from '../../components/shared/ParentCard';
import Course from 'src/components/apps/courses/details/Course';
import { RedoTwoTone } from '@mui/icons-material';

interface TablePaginationActionsProps {
    count: number;
    page: number;
    rowsPerPage: number;
    onPageChange: (event: React.MouseEvent<HTMLButtonElement>, newPage: number) => void;
}

function TablePaginationActions(props: TablePaginationActionsProps) {
    const theme = useTheme();
    const { count, page, rowsPerPage, onPageChange } = props;

    const handleFirstPageButtonClick = (event: any) => {
        onPageChange(event, 0);
    };

    const handleBackButtonClick = (event: any) => {
        onPageChange(event, page - 1);
    };

    const handleNextButtonClick = (event: any) => {
        onPageChange(event, page + 1);
    };

    const handleLastPageButtonClick = (event: any) => {
        onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
    };

    return (
        <Box sx={{ flexShrink: 0, ml: 2.5 }}>
            <IconButton
                onClick={handleFirstPageButtonClick}
                disabled={page === 0}
                aria-label="first page"
            >
                {theme.direction === 'rtl' ? <LastPageIcon /> : <FirstPageIcon />}
            </IconButton>
            <IconButton
                onClick={handleBackButtonClick}
                disabled={page === 0}
                aria-label="previous page"
            >
                {theme.direction === 'rtl' ? <KeyboardArrowRight /> : <KeyboardArrowLeft />}
            </IconButton>
            <IconButton
                onClick={handleNextButtonClick}
                disabled={page >= Math.ceil(count / rowsPerPage) - 1}
                aria-label="next page"
            >
                {theme.direction === 'rtl' ? <KeyboardArrowLeft /> : <KeyboardArrowRight />}
            </IconButton>
            <IconButton
                onClick={handleLastPageButtonClick}
                disabled={page >= Math.ceil(count / rowsPerPage) - 1}
                aria-label="last page"
            >
                {theme.direction === 'rtl' ? <FirstPageIcon /> : <LastPageIcon />}
            </IconButton>
        </Box>
    );
}

type Props = {
    title: string;
    type: number;
    courses: CourseType[];
    linkPath?: string;
};

const PaginationCourseTable = (props: Props) => {
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);

    // Avoid a layout jump when reaching the last page with empty rows.
    const emptyRows = page > 0 ? Math.max(0, (1 + page) * rowsPerPage - props.courses.length) : 0;
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    const handleChangePage = (event: any, newPage: any) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: any) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    return (
        <PageContainer title={props.title}>
            <ParentCard title={props.title}>
                <BlankCard>
                    <TableContainer>
                        <Table
                            aria-label="custom pagination table"
                            sx={{
                                whiteSpace: 'nowrap',
                            }}
                        >
                            <TableHead>
                                <TableRow
                                    sx={{
                                        '& th': {
                                            backgroundColor: 'primary.main',
                                        },
                                    }}
                                >
                                    <TableCell>
                                        <Typography color="primary.light" variant="h6">
                                            Название курса
                                        </Typography>
                                    </TableCell>
                                    <TableCell>
                                        <Typography
                                            color="primary.light"
                                            variant="h6"
                                            textAlign={'center'}
                                        >
                                            Уроки
                                        </Typography>
                                    </TableCell>
                                    <TableCell>
                                        <Typography
                                            color="primary.light"
                                            variant="h6"
                                            textAlign={'center'}
                                        >
                                            Задачи
                                        </Typography>
                                    </TableCell>
                                    {props.type == 2 ? (
                                        <>
                                            <TableCell>
                                                <Typography
                                                    color="primary.light"
                                                    variant="h6"
                                                    textAlign={'center'}
                                                >
                                                    Группы
                                                </Typography>
                                            </TableCell>
                                            <TableCell>
                                                <Typography
                                                    color="primary.light"
                                                    variant="h6"
                                                    textAlign={'center'}
                                                >
                                                    Дата создания
                                                </Typography>
                                            </TableCell>
                                            <TableCell>
                                                <Typography
                                                    color="primary.light"
                                                    variant="h6"
                                                    textAlign={'center'}
                                                >
                                                    Дата окончания
                                                </Typography>
                                            </TableCell>
                                            <TableCell>
                                                <Typography
                                                    color="primary.light"
                                                    variant="h6"
                                                    textAlign={'center'}
                                                >
                                                    Рейтинг
                                                </Typography>
                                            </TableCell>
                                        </>
                                    ) : (
                                        <></>
                                    )}
                                    {props.type == 3 ? (
                                        <>
                                            <TableCell>
                                                <Typography
                                                    color="primary.light"
                                                    variant="h6"
                                                    textAlign={'center'}
                                                >
                                                    Должность
                                                </Typography>
                                            </TableCell>
                                        </>
                                    ) : (
                                        <></>
                                    )}
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {(rowsPerPage > 0
                                    ? props.courses.slice(
                                          page * rowsPerPage,
                                          page * rowsPerPage + rowsPerPage,
                                      )
                                    : props.courses
                                ).map((row) => (
                                    <TableRow hover key={row.id}>
                                        <ButtonBase
                                            to={
                                                props.linkPath
                                                    ? props.linkPath + row.id
                                                    : '/auth/courses/' + row.id
                                            }
                                            component={Link}
                                        >
                                            <TableCell>
                                                <Typography
                                                    color="textSecondary"
                                                    variant="h6"
                                                    fontWeight="400"
                                                >
                                                    {row.title}
                                                </Typography>
                                            </TableCell>
                                        </ButtonBase>
                                        <TableCell>
                                            <Typography
                                                color="textSecondary"
                                                variant="h6"
                                                fontWeight="400"
                                                textAlign={'center'}
                                            >
                                                {row.lesson_ids?.length}
                                            </Typography>
                                        </TableCell>

                                        <TableCell>
                                            <Typography
                                                color="textSecondary"
                                                variant="h6"
                                                fontWeight="400"
                                                textAlign={'center'}
                                            >
                                                {row.task_ids?.length}
                                            </Typography>
                                        </TableCell>
                                        {props.type == 2 ? (
                                            <>
                                                <TableCell>
                                                    <Typography
                                                        color="textSecondary"
                                                        variant="h6"
                                                        fontWeight="400"
                                                        textAlign={'center'}
                                                    >
                                                        X
                                                    </Typography>
                                                </TableCell>

                                                <TableCell>
                                                    <Typography
                                                        color="textSecondary"
                                                        variant="h6"
                                                        fontWeight="400"
                                                        textAlign={'center'}
                                                    >
                                                        {row.created_at? row.created_at : "X"}
                                                    </Typography>
                                                </TableCell>

                                                <TableCell>
                                                    <Typography
                                                        color="textSecondary"
                                                        variant="h6"
                                                        fontWeight="400"
                                                        textAlign={'center'}
                                                    >
                                                         {row.endDate? row.endDate : "X"}
                                                    </Typography>
                                                </TableCell>

                                                <TableCell>
                                                    <Typography
                                                        color="textSecondary"
                                                        variant="h6"
                                                        fontWeight="400"
                                                        textAlign={'center'}
                                                    >
                                                        {row.rating}
                                                    </Typography>
                                                </TableCell>
                                            </>
                                        ) : (
                                            <></>
                                        )}
                                        {props.type == 3 ? (
                                            <>
                                                <TableCell>
                                                    <Typography
                                                        color="textSecondary"
                                                        variant="h6"
                                                        fontWeight="400"
                                                        textAlign={'center'}
                                                    >
                                                        Преподаватель
                                                    </Typography>
                                                </TableCell>
                                            </>
                                        ) : (
                                            <></>
                                        )}
                                    </TableRow>
                                ))}

                                {emptyRows > 0 && (
                                    <TableRow style={{ height: 53 * emptyRows }}>
                                        <TableCell colSpan={6} />
                                    </TableRow>
                                )}
                            </TableBody>
                            <TableFooter>
                                <TableRow>
                                    <TablePagination
                                        rowsPerPageOptions={[
                                            5,
                                            10,
                                            25,
                                            { label: 'All', value: -1 },
                                        ]}
                                        colSpan={6}
                                        count={props.courses.length}
                                        rowsPerPage={rowsPerPage}
                                        page={page}
                                        SelectProps={{
                                            native: true,
                                        }}
                                        onPageChange={handleChangePage}
                                        onRowsPerPageChange={handleChangeRowsPerPage}
                                        ActionsComponent={TablePaginationActions}
                                    />
                                </TableRow>
                            </TableFooter>
                        </Table>
                    </TableContainer>
                </BlankCard>
            </ParentCard>
        </PageContainer>
    );
};

export default PaginationCourseTable;
