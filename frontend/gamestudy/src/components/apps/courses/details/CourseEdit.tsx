import { Backdrop, Box, ButtonBase, Fade, Grid, Modal, Typography } from '@mui/material';
import PageContainer from 'src/components/container/PageContainer';
import { CourseProgressType, CourseType } from 'src/types/apps/course';
import Lessons from '../../lessons/LessonListing';
import Tasks from '../../tasks/TaskListing';
import { useParams } from 'react-router';
import { useEffect, useState } from 'react';
import { fetchCourse, fetchCourseProgress } from 'src/store/apps/courses/CourseSlise';
import { useDispatch, useSelector } from 'src/store/Store';
import { IconPlus } from '@tabler/icons-react';
import { AddCircleOutline, Group } from '@mui/icons-material';
import { Stack } from '@mui/system';
import CreateLesson from '../../lessons/details/LessonCreate';
import CreateTask from '../../tasks/details/TaskCreate';
import EditTaskTable from 'src/views/tables/EditTaskTable';
import { fetchCourseLessons } from 'src/store/apps/lessons/LessonSlice';
import { fetchCourseTags } from 'src/store/apps/tags/TagSlice';
import { fetchCourseTasks } from 'src/store/apps/tasks/TaskSlice';
import CourseMainRating from 'src/views/tables/CourseMainRating';
import Groups from '../../groups/GroupListing';

const style = {
    position: 'relative',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '50%',
    height: '80%',
    bgcolor: 'background.paper',
    boxShadow: 24,
};

const CourseEdit = () => {
    const dispatch = useDispatch();
    const { id } = useParams<{ id: string }>();
    const [createLesson, setCreateLesson] = useState(false);
    const [createTask, setCreateTask] = useState(false);

    useEffect(() => {
        if (id) {
            dispatch(fetchCourse(id));
            dispatch(fetchCourseLessons(id));
            dispatch(fetchCourseTasks(id));
            dispatch(fetchCourseProgress(id));
        }
    }, [dispatch]);

    const res: CourseType | undefined = useSelector((state) => {
        return state.courseReduser.selectedCourse;
    });

    const progresses: CourseProgressType[] | undefined = useSelector((state) => {
        return state.courseReduser.selectedCourseProgress;
    });

    var course: CourseType;

    if (!res) {
        return <Typography>Курс не найден</Typography>;
    }
    {
        course = res;
    }

    return (
        <PageContainer title="Courses" description="this is course page">
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={createLesson}
                onClose={() => setCreateLesson(false)}
                closeAfterTransition
                slots={{ backdrop: Backdrop }}
            >
                <Fade in={createLesson}>
                    <Box sx={style}>
                        <CreateLesson key={course.id} id={course.id ? course.id.toString() : ''} />
                    </Box>
                </Fade>
            </Modal>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={createTask}
                onClose={() => setCreateTask(false)}
                closeAfterTransition
                slots={{ backdrop: Backdrop }}
            >
                <Fade in={createTask}>
                    <Box sx={style}>
                        <CreateTask
                            courseId={course.id ? course.id.toString() : ''}
                            type={2}
                            key={course.id ? course.id : 0 + 1}
                            id={course.id ? course.id.toString() : ''}
                        />
                    </Box>
                </Fade>
            </Modal>
            <Box p={3} m={4}>
                <Grid container spacing={3}>
                    <Grid item xs={8}>
                        <Box sx={{ backgroundColor: 'white' }} p={3}>
                            <Box mb={15} sx={{ maxWidth: '70%' }}>
                                <Typography
                                    fontSize={30}
                                    gutterBottom
                                    variant="subtitle1"
                                    component="div"
                                >
                                    {course?.title}
                                </Typography>

                                <Typography variant="body2" fontSize={15} gutterBottom>
                                    {course?.description}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    ID:{course?.id}
                                </Typography>
                            </Box>
                            <Box>
                                <ButtonBase onClick={() => setCreateLesson(true)}>
                                    <Stack direction="row" spacing={1}>
                                        <Typography>Добавить урок</Typography>
                                        <AddCircleOutline />
                                    </Stack>
                                </ButtonBase>
                            </Box>
                            <Box mt={3}>
                                <Lessons type={2} id={course.id?.toString()} key={course.id} />
                            </Box>
                            <Box mt={3}>
                                <ButtonBase onClick={() => setCreateTask(true)}>
                                    <Stack direction="row" spacing={1}>
                                        <Typography>Добавить задачу</Typography>
                                        <AddCircleOutline />
                                    </Stack>
                                </ButtonBase>
                            </Box>
                            <Box mt={3}>
                                <Tasks type={2} id={course.id?.toString()} key={course.id} />
                            </Box>
                        </Box>
                    </Grid>

                    <Grid item xs={4}>
                        <Groups type={3} id={course.id?.toString()}/>
                    </Grid>
                </Grid>
            </Box>
        </PageContainer>
    );
};

export default CourseEdit;
