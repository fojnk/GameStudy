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
    Typography
} from '@mui/material';
import { FormikProvider, useFormik } from 'formik';
import { useEffect, useState } from 'react';
import { toast } from 'react-hot-toast';
import CustomTextField from 'src/components/forms/theme-elements/CustomTextField';

import useAuth from 'src/guards/authGuard/UseAuth';
import { fetchCreatorCourses } from 'src/store/apps/courses/CourseSlise';
import { fetchCreatorGroups } from 'src/store/apps/groups/GroupSlice';
import { addLessonToCourse, fetchCourseLessons } from 'src/store/apps/lessons/LessonSlice';
import { fetchStudents } from 'src/store/apps/students/StudentSlice';
import { useDispatch, useSelector } from 'src/store/Store';
import { StudentType } from 'src/types/apps/student';
import axiosServices from 'src/utils/axios';

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

const CreateGroup = () => {
    const dispatch = useDispatch();
    const { role, user } = useAuth();
    const [students, setStudents] = useState<string[]>([]);
    const studentList: StudentType[] = useSelector((state) => {
        return state.studentReducer.students;
    });

    useEffect(() => {
        dispatch(fetchStudents());
    }, [dispatch]);

    const handleStudentChange = (event: SelectChangeEvent<typeof students>) => {
        const {
            target: { value },
        } = event;
        setStudents(
            // On autofill we get a stringified value.
            typeof value === 'string' ? value.split(',') : value,
        );
    };

    const formik = useFormik({
        initialValues: {
            title: '',
            students: '',
            creator_uid: user.id,
            submit: null,
        },

        onSubmit: async (values) => {
            try {

                const data = JSON.stringify({
                    "title": values.title,
                    "students": students,
                    "creator_uid": user.id,
                  });

                  console.log(data)
                const response = await axiosServices.post('/api/v1/students/groups', data, {
                    headers: {
                        'Content-type': 'application/json',
                    },
                });

                await dispatch(fetchCreatorGroups(user.id));
                toast.success('Группа создана.');
                console.log(response);
            } catch (err: any) {
                toast.error('Группа не была создана.');
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
                        Добавить новую группу
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
                                id="title"
                                variant="outlined"
                                placeholder="Название"
                                color="primary"
                                onChange={formik.handleChange}
                                value={formik.values.title}
                                fullWidth
                            />
                        </Box>
                        <Box mb={2}>
                            <FormControl fullWidth>
                                <InputLabel id="demo-multiple-chip-label1">Студенты</InputLabel>
                                <Select
                                    labelId="demo-multiple-chip-label1"
                                    id="demo-multiple-chip1"
                                    multiple
                                    value={students}
                                    sx={{ boxShadow: 3 }}
                                    onChange={handleStudentChange}
                                    input={<OutlinedInput id="select-multiple-chip1" label="Студенты" />}
                                    renderValue={(selected1) => (
                                        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                            {selected1.map((value) => (
                                                <Chip key={value} label={value} />
                                            ))}
                                        </Box>
                                    )}
                                    MenuProps={MenuProps}
                                >
                                    {studentList.map((student) => (
                                        <MenuItem key={student.id} value={student.id}>
                                            {student.user?.surname + ' '}
                                            {student.user?.name + ' '}
                                            {student.user?.fathers_name + ' '}
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

export default CreateGroup;