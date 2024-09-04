// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import React from 'react';

import { Box, Button, ButtonBase, Grid, styled, Typography } from '@mui/material';
import { Stack } from '@mui/system';
import PageContainer from 'src/components/container/PageContainer';
import { TaskType } from 'src/types/apps/task';
import CustomTextField from 'src/components/forms/theme-elements/CustomTextField';

const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
});

const ButtonSx = {
    color: 'primary.contrastText',
    backgroundColor: 'background.default',
    '&:hover': {
        color: 'secondary.contrastText',
        backgroundColor: 'secondary.main',
    },
    padding: '10px',
};

type Props = {
    task: TaskType | undefined;
    id: number;
    setIsWorking: React.Dispatch<React.SetStateAction<boolean>>;
};

const TaskInfo = (props: Props) => {
    const { task, id } = props;

    if (!props.task) {
        return <Typography>Урок не найден</Typography>;
    }

    return (
        <>
            <Box
                p={5}
                sx={{
                    width: '100%',
                    height: '80%',
                    overflowY: 'auto',
                }}
            >
                <Box>
                    <Stack direction="row" spacing={3}>
                        <Typography
                            fontSize={20}
                            gutterBottom
                            fontWeight={600}
                            variant="subtitle1"
                            component="div"
                        >
                            {'Задача ' + id}
                        </Typography>
                        <Typography fontSize={20} gutterBottom variant="subtitle1" component="div">
                            {task?.title}
                        </Typography>
                    </Stack>
                    <Typography variant="body2" color="text.secondary">
                        ID:{task?.id}
                    </Typography>
                    <Box mt={3}>
                        <ButtonBase
                            sx={{
                                float: 'left',
                                maxWidth: '20%',
                                marginRight: '5%',
                            }}
                        >
                            <Img src={task?.video_path} alt="" />
                        </ButtonBase>
                        </Box>
                    <Typography variant="body2" fontSize={15} gutterBottom>
                        {task?.description}
                    </Typography>
                </Box>
            </Box>
            <Box
                sx={{
                    width: '90%',
                    margin: 'auto',
                }}
            >
                <Grid container spacing={2}>
                    <Grid item xs={10}>
                        <Box>
                            <CustomTextField
                                id="surname"
                                variant="outlined"
                                placeholder="Ответ"
                                color="secondary"
                                sx={{
                                    boxShadow: 10,
                                }}
                                fullWidth
                            />
                        </Box>
                    </Grid>
                    <Grid item xs={2}>
                        <Button sx={ButtonSx} onClick={() => props.setIsWorking(true)}>
                            Проверить ответ
                        </Button>
                    </Grid>
                </Grid>
            </Box>
        </>
    );
};

export default TaskInfo;
