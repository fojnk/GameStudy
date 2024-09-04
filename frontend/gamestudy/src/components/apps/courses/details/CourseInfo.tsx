import React from 'react';

import { Box, Button, ButtonBase, Grid, Rating, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import PageContainer from 'src/components/container/PageContainer';
import useAuth from 'src/guards/authGuard/UseAuth';
import { CourseProgressType, CourseType } from 'src/types/apps/course';
import Tags from '../../tags/TagListing';
import TeacherListing from '../../teachers/TeacherListing';
import Disciplines from '../../disciplines/DisciplinesList';
import { Link } from 'react-router-dom';
import { useDispatch } from 'src/store/Store';
import { addCourseProgress } from 'src/store/apps/courses/CourseSlise';

const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
})

const ButtonSx = {
    color: 'primary.contrastText',
    backgroundColor: 'background.default',
    '&:hover': {
        color: 'secondary.contrastText',
        backgroundColor: 'secondary.main',
    },
};

type Props = {
    course: CourseType | undefined;
    setIsWorking: React.Dispatch<React.SetStateAction<boolean>>;
};

const CourseInfo = (props: Props) => {
    const { user, role } = useAuth();
    const dispatch = useDispatch();

    if (!props.course) {
        return <Typography>Курс не найден</Typography>;
    }

    const handleStartCourse = () => {
        const data: CourseProgressType = {
            course_id: props.course?.id,
            student_id: role.id, 
            reached_exp: 0,
            passed_lesson_ids: [],
            passed_task_ids: [],
        }
        dispatch(addCourseProgress(data))
    }

    return (
        <PageContainer title="Courses" description="this is course page">
            <Box sx={{ backgroundColor: 'white', waxWidht: '90%' }} p={3} m={4}>
                <Grid container spacing={2}>
                    <Grid item>
                        <ButtonBase sx={{ width: 200, height: 200 }}>
                            <Img
                                alt="complex"
                                src={props.course?.image_path}
                            />
                        </ButtonBase>
                    </Grid>
                    <Grid item xs={12} sm container>
                        <Grid item xs container direction="column" spacing={2}>
                            <Grid item xs>
                                <Typography
                                    fontSize={30}
                                    gutterBottom
                                    variant="subtitle1"
                                    component="div"
                                >
                                    {props.course?.title}
                                </Typography>
                                <Typography variant="body2" fontSize={15} gutterBottom>
                                    {props.course?.description}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    ID:{props.course?.id}
                                </Typography>
                            </Grid>
                        </Grid>
                        <Grid item>
                            <Typography variant="subtitle1" component="div">
                                <Rating name="customized-5" defaultValue={props.course.rating} max={5} />
                            </Typography>
                        </Grid>
                    </Grid>
                </Grid>
                <Box mt={2}>
                    {/* <Disciplines id={props.course?.id?.toString()}  type={1} /> */}
                </Box>
                <Box>
                    <Tags id={props.course?.id?.toString()} />
                </Box>
                <Box mt={3}>
                    <Typography fontSize={20} variant="subtitle1" component="div">
                        Преподаватели
                    </Typography>
                    <Box m={1}>
                        <TeacherListing id={props.course?.id?.toString()} />
                    </Box>
                </Box>

                <Grid container>
                    <Box m={'auto'} mt={3}>
                        {user.role == 'STUDENT' ? (
                            role.course_ids.includes(props.course.id) ? (
                                <Button sx={ButtonSx} onClick={() => props.setIsWorking(true)}>
                                    Продолжить обучение
                                </Button>
                            ) : (
                                <Button sx={ButtonSx} onClick={() => handleStartCourse()}>Начать курс</Button>
                            )
                        ) : (
                            (user.id == props.course.creator_uid ?
                                <Button sx={ButtonSx} to={'/courses/'+ props.course.id + '/edit'} component={Link}>
                                    Редактировать
                                </Button> 
                            : <></>)
                        )}
                    </Box>
                </Grid>
            </Box>
        </PageContainer>
    );
};

export default CourseInfo;
