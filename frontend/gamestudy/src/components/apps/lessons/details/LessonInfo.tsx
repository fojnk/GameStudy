import React from 'react';

import { Box, Button, ButtonBase, Stack, styled, Typography } from '@mui/material';
import PageContainer from 'src/components/container/PageContainer';
import { CourseLesson } from 'src/types/apps/lesson';
import ReactPlayer from 'react-player';
import { borderBottom, borderRadius, width } from '@mui/system';

const ButtonSx = {
    color: 'primary.contrastText',
    backgroundColor: 'background.default',
    '&:hover': {
        color: 'secondary.contrastText',
        backgroundColor: 'secondary.main',
    },
    position: 'absolute',
    left: '88%',
    top: '90%',
};

const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
})

type Props = {
    lesson: CourseLesson | undefined;
    id: number;
    setIsWorking: React.Dispatch<React.SetStateAction<boolean>>;
};

const LessonInfo = (props: Props) => {
    const { lesson, id } = props;

    if (!props.lesson) {
        return <Typography>Урок не найден</Typography>;
    }

    return (
        <>
            <Box
                p={5}
                sx={{
                    width: '100%',
                    height: '100%',
                    overflowY: 'auto',
                }}
            >
                <Box mb={5}>
                <Stack direction="row" spacing={3}>
                    <Typography
                        fontSize={20}
                        gutterBottom
                        fontWeight={600}
                        variant="subtitle1"
                        component="div"
                    >
                        {'Урок ' + id}
                    </Typography>
                    <Typography fontSize={20} gutterBottom variant="subtitle1" component="div">
                        {lesson?.title}
                    </Typography>
                </Stack>
                <Typography variant="body2" color="text.secondary">
                    ID:{lesson?.id}
                </Typography>
                <Box mb={6} mt={3}>
                <ButtonBase sx={{
                    float: "left",
                    maxWidth: "20%",
                    marginRight: "5%"
                }}>
                    <Img src={lesson?.image_path} alt=''/>
                </ButtonBase>
                <Typography variant="body2" fontSize={15} gutterBottom>
                    {lesson?.description}
                </Typography>
                </Box>
                </Box>
                <Box mt={5}>
                <ReactPlayer url={lesson?.video_path} controls={true} />
                </Box>
            </Box>

            <Button sx={ButtonSx} onClick={() => props.setIsWorking(true)}>
                Пройден
            </Button>
        </>
    );
};

export default LessonInfo;
