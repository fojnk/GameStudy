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
import { lessonType } from 'src/types/apps/lesson';
import axiosServices from 'src/utils/axios';

type Props = {
    courseId: string;
    lesson: lessonType;
}

const LessonEdit = ({lesson, courseId} : Props) => {
    const dispatch = useDispatch();
    const { user } = useAuth();
    
    const formik = useFormik({
        initialValues: {
            name: lesson.title,
            cost: '',
            image_path: '',
            description: lesson.description,
            video_path: lesson.video_path,
            experience: lesson.experience,
            complexity: lesson.complexity,
            submit: null,
        },

        onSubmit: async (values) => {
            try {

                const data = JSON.stringify({
                    "title": values.name,
                    "description": values.description,
                    "experience": values.experience,
                    "complexity": values.complexity,
                    "video_path": values.video_path,
                    "image_path": values.image_path,
                  });
                const response = await axiosServices.put('/api/v1/lessons/' + lesson.id, data, {
                    headers: {
                        'Content-type': 'application/json',
                    },
                });

                dispatch(fetchCourseLessons(courseId));
                toast.success('Урок обновлён.');
                console.log(response);
            } catch (err: any) {
                toast.error('Урок не был обновлён.');
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
                        Обновить урок
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
                            Обновить
                        </Button>
                    </form>
                </FormikProvider>
            </Box>
        </>
    )
}

export default LessonEdit;