// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Typography } from '@mui/material';
import { useEffect } from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick-theme.css';
import 'slick-carousel/slick/slick.css';
import { useDispatch, useSelector } from 'src/store/Store';
import { fetchCourseTeachers } from 'src/store/apps/teachers/TeacherSlice';
import TeacherCard from './TeacherCard';

type Props = {
    id?: string;
};

const TeacherListing = ({ id }: Props) => {
    const dispatch = useDispatch();

    if (id) {
        useEffect(() => {
            dispatch(fetchCourseTeachers(id));
        }, [dispatch]);
    } else {
        return <Typography>Id курса не получен</Typography>;
    }

    const teachers = useSelector((state) => {
        return state.teacherReducer.teachers;
    });

    if (teachers.length == 0) {
        return (
            <Typography
                marginTop={'50px'}
                textAlign={'center'}
                color={'primary.main'}
                fontSize={40}
            >
                Нет преподавателей
            </Typography>
        );
    }

    var settings = {
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: 6,
        slidesToScroll: 2,
        color: 'black',
        adaptiveHeight: true,
        draggable: true,
        autoplay: true,
        autoplaySpeed: 1000,
    };

    return (
        <Slider {...settings}>
            {teachers.map((teacher, _) => {
                return <TeacherCard teacher={teacher} />;
            })}
        </Slider>
    );
};

export default TeacherListing;
