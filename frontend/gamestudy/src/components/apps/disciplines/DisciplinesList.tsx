import { Box, Grid, Typography } from '@mui/material';
import { useEffect } from 'react';
import PageContainer from 'src/components/container/PageContainer';
import {
    fetchCourseDisciplines,
    fetchDisciplines,
} from 'src/store/apps/disciplines/DisciplineSlice';
import { useDispatch, useSelector } from 'src/store/Store';
import { DisciplineType } from 'src/types/apps/discipline';
import DisciplineTable from 'src/views/tables/DisciplineTable';

type Props = {
    id?: string;
    type: number;
};

const Disciplines = (props: Props) => {
    const dispatch = useDispatch();
    const { id, type } = props;

    useEffect(() => {
        if (type == 1 && id) {
            dispatch(fetchCourseDisciplines(id));
        } else {
            dispatch(fetchDisciplines());
        }
    }, [dispatch]);

    const disciplines: DisciplineType[] = useSelector((state) => {
        return state.disciplineReducer.disciplines;
    });

    console.log(disciplines)

    if (disciplines.length == 0) {
        return (
            <Typography
                marginTop={'50px'}
                textAlign={'center'}
                color={'primary.main'}
                fontSize={40}
            >
                Нет дисциплин
            </Typography>
        );
    }

    return (
        <>
            {type == 1 ? (
                <Box ml={1}>
                    <Grid container spacing={2}>
                        {disciplines.map((discipline, _) => {
                            return (
                                <Box
                                    ml={1}
                                    sx={{
                                        backgroundColor: 'primary.light',
                                        boxShadow: 3,
                                        padding: '4px 8px 4px 8px ',
                                    }}
                                >
                                    <Typography color="black" fontSize={11}>
                                        {discipline.title ? discipline.title: ''}
                                    </Typography>
                                </Box>
                            );
                        })}
                    </Grid>
                </Box>
            ) : (
                <Box m={5}>
                    <DisciplineTable disciplines={disciplines} />
                </Box>
            )}
        </>
    );
};

export default Disciplines;
