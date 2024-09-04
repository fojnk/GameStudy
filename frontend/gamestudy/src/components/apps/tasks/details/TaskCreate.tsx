import { Box, Button, Typography } from '@mui/material';
import { FormikProvider, useFormik } from 'formik';
import { useState } from 'react';
import { toast } from 'react-hot-toast';
import CustomTextField from 'src/components/forms/theme-elements/CustomTextField';

import useAuth from 'src/guards/authGuard/UseAuth';
import { fetchCreatorCourses } from 'src/store/apps/courses/CourseSlise';
import { addTaskToLesson, fetchCourseLessons } from 'src/store/apps/lessons/LessonSlice';
import { addTaskToCourse, fetchCourseTasks } from 'src/store/apps/tasks/TaskSlice';
import { useDispatch, useSelector } from 'src/store/Store';
import { TagType } from 'src/types/apps/tags';
import axiosServices from 'src/utils/axios';

const CreateTask = ({ courseId,id, type }: { id: string; type: number; courseId: string; }) => {
    const dispatch = useDispatch();
    const { user } = useAuth();

    const formik = useFormik({
        initialValues: {
            name: '',
            cost: '',
            image_path: '',
            description: '',
            video_path: '',
            experience: '',
            complexity: '',
            answer: '',
            submit: null,
        },

        onSubmit: async (values) => {
            try {
                const data = JSON.stringify({
                    title: values.name,
                    description: values.description,
                    experience: values.experience,
                    complexity: values.complexity,
                    answer: values.answer,
                    discipline: 1,
                    game_methods: [],
                });
                const response = await axiosServices.post('/api/v1/tasks/', data, {
                    headers: {
                        'Content-type': 'application/json',
                    },
                });
 
                if (type == 1) {
                    await dispatch(addTaskToLesson(id, response.data));
                    await dispatch(fetchCourseLessons(courseId));
                } else {
                    dispatch(addTaskToCourse(id, response.data));
                    dispatch(fetchCourseTasks(id));
                }
                toast.success('Задача создан.');
                console.log(response);
            } catch (err: any) {
                console.log(err);
                if (!err.message.includes("404")) {
                    toast.error('Задача не была создан.'); 
                }
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
                        Добавить новую задачу
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
                            Создать
                        </Button>
                    </form>
                </FormikProvider>
            </Box>
        </>
    );
};

export default CreateTask;
