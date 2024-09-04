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
import { useState } from 'react';

import PageContainer from 'src/components/container/PageContainer';

import BlankCard from 'src/components/shared/BlankCard';
import { GroupType } from 'src/types/apps/group';
import StudentTable from './StudentTable';

interface columnType {
    id: string;
    label: string;
    minWidth: number;
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

const columns: columnType[] = [{ id: 'pname', label: 'Название группы', minWidth: 170 }];

function Row(props: { row: GroupType }) {
    const { row } = props;
    const [openGroup, setGroupOpen] = useState(false);
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
            </TableRow>
        </>
    );
}

const GroupTable = ({ groups }: { groups?: GroupType[] }) => {
    return (
        <>
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
                                            style={{ minWidth: column.minWidth }}
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
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {groups?.map((row) => {
                                    return <Row row={row} />;
                                })}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </BlankCard>
            </PageContainer>
        </>
    );
};

export default GroupTable;
