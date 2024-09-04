import { Box, Button, Typography } from '@mui/material';
import { FormikProvider, useFormik } from 'formik';
import { toast } from 'react-hot-toast';
import CustomTextField from 'src/components/forms/theme-elements/CustomTextField';

import useAuth from 'src/guards/authGuard/UseAuth';
import { fetchCreatorCourses } from 'src/store/apps/courses/CourseSlise';
import { fetchCourseLessons } from 'src/store/apps/lessons/LessonSlice';
import { fetchCourseTasks } from 'src/store/apps/tasks/TaskSlice';
import { useDispatch } from 'src/store/Store';
import { TaskType } from 'src/types/apps/task';
import axiosServices from 'src/utils/axios';

type Props = {
    courseId: string;
    task: TaskType;
    type: number;
}

const TaskEdit = ({ task, courseId, type }: Props) => {
    const dispatch = useDispatch();
    const { user } = useAuth();

    const formik = useFormik({
        initialValues: {
            name: task.title,
            cost: '',
            image_path: '',
            description: task.description,
            experience: task.experience,
            complexity: task.complexity,
            answer: task.answer,
            submit: null,
        },

        onSubmit: async (values) => {
            try {
                const data = JSON.stringify({
                    title: values.name,
                    description: values.description,
                    time: '00:20:00',
                    experience: values.experience,
                    complexity: values.complexity,
                    answer: values.answer,
                    video_path: values.image_path,
                    game_methods: [],
                });

                const response = await axiosServices.put('/api/v1/tasks/' + task.id, data, {
                    headers: {
                        'Content-type': 'application/json',
                    },
                });

                toast.success('Задача обновлена.');
                if (type==1) {
                    await dispatch(fetchCourseTasks(courseId));
                } else {
                    await dispatch(fetchCourseLessons(courseId));
                }
                
                console.log(response);
            } catch (err: any) {
                toast.error('Задача не была обновлена.');
                console.log(err);
            }
        },
    });

    return (
        <>
            <Box
                p={4}
                sx={{
                    width: '90%',
                    margin: 'auto',
                }}
            >
                <Box mb={3}>
                    <Typography fontSize={18} fontWeight={600}>
                        Обновить задачу
                    </Typography>
                </Box>
                <FormikProvider value={formik}>
                    <form
                        onSubmit={(e) => {
                            e.preventDefault();
                            formik.handleSubmit(e);
                        }}
                    >
                        <Box mb={2}>
                            <CustomTextField
                                id="name"
                                variant="outlined"
                                placeholder="Название"
                                color="primary"
                                onChange={formik.handleChange}
                                value={formik.values.name}
                                fullWidth
                            />
                        </Box>
                        <Box mb={2}>
                            <CustomTextField
                                id="description"
                                type="outlined"
                                variant="outlined"
                                placeholder="Описание"
                                onChange={formik.handleChange}
                                value={formik.values.description}
                                fullWidth
                            />
                        </Box>
                        <Box mb={2}>
                            <CustomTextField
                                id="cost"
                                type="outlined"
                                variant="outlined"
                                placeholder="Стоимость"
                                onChange={formik.handleChange}
                                value={formik.values.cost}
                                fullWidth
                            />
                        </Box>
                        <Box mb={2}>
                            <CustomTextField
                                id="image_path"
                                type="outlined"
                                variant="outlined"
                                placeholder="Изображение"
                                onChange={formik.handleChange}
                                value={formik.values.image_path}
                                fullWidth
                            />
                        </Box>
                        <Box mb={2}>
                            <CustomTextField
                                id="experience"
                                type="outlined"
                                variant="outlined"
                                placeholder="Опыт"
                                onChange={formik.handleChange}
                                value={formik.values.experience}
                                fullWidth
                            />
                        </Box>
                        <Box mb={2}>
                            <CustomTextField
                                id="complexity"
                                type="outlined"
                                variant="outlined"
                                placeholder="Сложность"
                                onChange={formik.handleChange}
                                value={formik.values.complexity}
                                fullWidth
                            />
                        </Box>
                        <Box mb={2}>
                            <CustomTextField
                                id="answer"
                                type="outlined"
                                variant="outlined"
                                placeholder="Ответ"
                                onChange={formik.handleChange}
                                value={formik.values.answer}
                                fullWidth
                            />
                        </Box>
                        <Button
                            type="submit"
                            sx={{
                                color: 'white',
                                backgroundColor: 'black',
                            }}
                        >
                            Сохранить
                        </Button>
                    </form>
                </FormikProvider>
            </Box>
        </>
    );
};

export default TaskEdit;
