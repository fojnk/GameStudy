import {
    Box,
    Button,
    Typography
} from '@mui/material';
import { FormikProvider, useFormik } from 'formik';
import { toast } from 'react-hot-toast';
import CustomTextField from 'src/components/forms/theme-elements/CustomTextField';

import useAuth from 'src/guards/authGuard/UseAuth';
import { fetchCreatorCourses } from 'src/store/apps/courses/CourseSlise';
import { addLessonToCourse, fetchCourseLessons } from 'src/store/apps/lessons/LessonSlice';
import { useDispatch } from 'src/store/Store';
import axiosServices from 'src/utils/axios';


const CreateLesson = ({id} : {id: string}) => {
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
            submit: null,
        },

        onSubmit: async (values) => {
            try {

                const data = JSON.stringify({
                    "title": values.name,
                    "description": values.description,
                    "experience": values.experience,
                    "complexity": values.complexity,
                    "discipline": 1,
                    "video_path": values.video_path,
                    "image_path": values.image_path,
                    "tasks": [],
                    "game_methods": []
                  });
                const response = await axiosServices.post('/api/v1/lessons/', data, {
                    headers: {
                        'Content-type': 'application/json',
                    },
                });

                dispatch(addLessonToCourse(id, response.data))
                dispatch(fetchCourseLessons(id));
                toast.success('Урок создан.');
                console.log(response);
            } catch (err: any) {
                toast.error('Урок не был создан.');
                console.log(err);
            }
        },
    });

    return (
        <>
            <Box
                p={4}
                sx={
                    {
                        width: "90%",
                        margin: "auto"
                    }
                }
            >
                <Box mb={3}>
                    <Typography fontSize={18} fontWeight={600}>
                        Добавить новый урок
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
                                id="video_path"
                                type="outlined"
                                variant="outlined"
                                placeholder="Видео"
                                onChange={formik.handleChange}
                                value={formik.values.video_path}
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
    )
}

export default CreateLesson;