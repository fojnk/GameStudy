import {
    Box,
    Button,
    Chip,
    FormControl,
    InputLabel,
    MenuItem,
    OutlinedInput,
    Select,
    SelectChangeEvent,
    Typography,
} from '@mui/material';
import { FormikProvider, useFormik } from 'formik';
import { orderBy } from 'lodash';
import { useEffect, useState } from 'react';
import { toast } from 'react-hot-toast';
import CustomTextField from 'src/components/forms/theme-elements/CustomTextField';

import useAuth from 'src/guards/authGuard/UseAuth';
import { fetchCreatorCourses } from 'src/store/apps/courses/CourseSlise';
import { fetchTags } from 'src/store/apps/tags/TagSlice';
import { fetchTeachers } from 'src/store/apps/teachers/TeacherSlice';
import { useDispatch, useSelector } from 'src/store/Store';
import { CourseType } from 'src/types/apps/course';
import { TagType } from 'src/types/apps/tags';
import { TeacherType } from 'src/types/apps/teacher';
import axiosServices from 'src/utils/axios';
import CourseEditTable from 'src/views/tables/CourseEditTable';

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

const CreateCourse = () => {
    const dispatch = useDispatch();
    const { user } = useAuth();
    const [tags, setTags] = useState<string[]>([]);
    const [teachers, setTeachers] = useState<string[]>([]);
    const tagList: TagType[] = useSelector((state) => {
        return state.tagReducer.tags;
    });
    const teacherList: TeacherType[] = useSelector((state) => {
        return state.teacherReducer.teachers;
    });

    if (user.id) {
        useEffect(() => {
            dispatch(fetchTags());
            dispatch(fetchTeachers());
            dispatch(fetchTeachers());
            dispatch(fetchCreatorCourses(user.id));
        }, [dispatch]);
    }

    const filterCourses = (courses: CourseType[], sortBy: string, _cSearch: string) => {
        // SORT BY

        if (sortBy === 'newest') {
            courses = orderBy(courses, ['date_start'], ['desc']);
        }

        return courses;
    };

    const courses = useSelector((state) =>
        filterCourses(
            state.courseReduser.creatorCourses,
            state.courseReduser.sortBy,
            state.courseReduser.courseSearch,
        ),
    );

    const handleTagsChange = (event: SelectChangeEvent<typeof tags>) => {
        const {
            target: { value },
        } = event;
        setTags(
            // On autofill we get a stringified value.
            typeof value === 'string' ? value.split(',') : value,
        );
    };

    const handleTeacherChange = (event: SelectChangeEvent<typeof teachers>) => {
        const {
            target: { value },
        } = event;
        setTeachers(
            // On autofill we get a stringified value.
            typeof value === 'string' ? value.split(',') : value,
        );
    };

    const formik = useFormik({
        initialValues: {
            name: '',
            cost: '',
            image_path: '',
            description: '',
            submit: null,
        },

        onSubmit: async (values) => {
            try {
                const tag_ids = tagList
                    .filter((one) => tags.includes(one.title ? one.title : 'd'))
                    .map((one) => one.id);
                
                const teacher_ids =  teacherList
                .filter((one) => teachers.includes(one.id ? one.id.toString() : 'd'))
                .map((one) => one.id);
                
                const data = JSON.stringify({
                    title: values.name,
                    description: values.description,
                    cost: values.cost,
                    rating: 0,
                    complexity: 0,
                    amt_passed_users: 0,
                    image_path: values.image_path,
                    creator_uid: user.id,
                    lesson_ids: [],
                    tag_ids: tag_ids,
                    task_ids: [],
                    board_ids: [],
                    teacher_ids: teacher_ids,
                });
                const response = await axiosServices.post('/api/v1/courses/', data, {
                    headers: {
                        'Content-type': 'application/json',
                    },
                });

                toast.success('Курс создан.');
                dispatch(fetchCreatorCourses(user.id));
                console.log(response);
            } catch (err: any) {
                toast.error('Курс не был создан.');
                console.log(err);
            }
        },
    });

    return (
        <>
            <Box
                m={7}
                p={4}
                sx={{
                    backgroundColor: 'white',
                }}
            >
                <Box mb={3}>
                    <Typography fontSize={18} fontWeight={600}>
                        Создать новый курс
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
                            <FormControl sx={{ width: 300 }}>
                                <InputLabel id="demo-multiple-chip-label">Тэги</InputLabel>
                                <Select
                                    labelId="demo-multiple-chip-label"
                                    id="demo-multiple-chip"
                                    multiple
                                    value={tags}
                                    sx={{ boxShadow: 3 }}
                                    onChange={handleTagsChange}
                                    input={<OutlinedInput id="select-multiple-chip" label="Теги" />}
                                    renderValue={(selected) => (
                                        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                            {selected.map((value) => (
                                                <Chip key={value} label={value} />
                                            ))}
                                        </Box>
                                    )}
                                    MenuProps={MenuProps}
                                >
                                    {tagList.map((tag) => (
                                        <MenuItem key={tag.title} value={tag.title}>
                                            {tag.title}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                        </Box>
                        <Box mb={2}>
                            <FormControl sx={{ width: 300 }}>
                                <InputLabel id="demo-multiple-chip-label1">Преподаватели</InputLabel>
                                <Select
                                    labelId="demo-multiple-chip-label1"
                                    id="demo-multiple-chip1"
                                    multiple
                                    value={teachers}
                                    sx={{ boxShadow: 3 }}
                                    onChange={handleTeacherChange}
                                    input={<OutlinedInput id="select-multiple-chip1" label="Учителя" />}
                                    renderValue={(selected1) => (
                                        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                            {selected1.map((value) => (
                                                <Chip key={value} label={value} />
                                            ))}
                                        </Box>
                                    )}
                                    MenuProps={MenuProps}
                                >
                                    {teacherList.map((teacher) => (
                                        <MenuItem key={teacher.id} value={teacher.id}>
                                            {teacher.user?.surname + ' '}
                                            {teacher.user?.name + ' '}
                                            {teacher.user?.fathers_name + ' '}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                        </Box>
                        <Button
                            type="submit"
                            sx={{
                                color: 'white',
                                backgroundColor: 'black',
                                margin: '10% auto 0 auto',
                            }}
                        >
                            Создать
                        </Button>
                    </form>
                </FormikProvider>
            </Box>

            <Box
                m={7}
                p={4}
                sx={{
                    backgroundColor: 'white',
                }}
            >
                <Typography fontSize={18} fontWeight={600}>
                    Черновики
                </Typography>
                <CourseEditTable courses={courses} />
            </Box>
        </>
    );
};

export default CreateCourse;
