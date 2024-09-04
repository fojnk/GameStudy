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
import { useEffect, useState } from 'react';
import { toast } from 'react-hot-toast';
import CustomTextField from 'src/components/forms/theme-elements/CustomTextField';

import useAuth from 'src/guards/authGuard/UseAuth';
import { fetchCreatorCourses } from 'src/store/apps/courses/CourseSlise';
import { fetchCreatorGroups } from 'src/store/apps/groups/GroupSlice';
import { addLessonToCourse, fetchCourseLessons } from 'src/store/apps/lessons/LessonSlice';
import { fetchStudents } from 'src/store/apps/students/StudentSlice';
import { useDispatch, useSelector } from 'src/store/Store';
import { GroupType } from 'src/types/apps/group';
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

const AddGroup = ({courseId} : {courseId: string}) => {
    const dispatch = useDispatch();
    const { role, user } = useAuth();
    const [group, setGroup] = useState<number>();

    const handleChange = (event: SelectChangeEvent<typeof group>) => {
        const {
            target: { value },
        } = event;
        setGroup(
            // On autofill we get a stringified value.
            typeof value === 'string' ? 0 : value,
        );
    };

    const groupList: GroupType[] = useSelector((state) => {
        return state.groupReducer.groups;
    });

    useEffect(() => {
        dispatch(fetchCreatorGroups(user.id));
    }, [dispatch]);

    const formik = useFormik({
        initialValues: {
            submit: null,
        },

        onSubmit: async (values) => {
            try {
                const query = '?groupIds='+group;

                const response = await axiosServices.put('/api/v1/courses/' + courseId + '/groups' + query);

                await dispatch(fetchCreatorGroups(user.id));
                toast.success('Группа добавлена.');
                console.log(response);
            } catch (err: any) {
                toast.error('Группа не была добавлена.');
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
                        Добавить группу
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
                            <FormControl fullWidth>
                                <InputLabel id="demo-select-small-label">Группа</InputLabel>
                                <Select
                                    labelId="demo-select-small-label"
                                    id="demo-select-small"
                                    value={group}
                                    label="Группа"
                                    onChange={handleChange}
                                >
                                    {groupList.map((group) => (
                                        <MenuItem key={group.id} value={group.id}>
                                            {group.title}
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
                            Добавить
                        </Button>
                    </form>
                </FormikProvider>
            </Box>
        </>
    );
};

export default AddGroup;
