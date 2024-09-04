// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Box, Button, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import CourseListing from 'src/components/apps/courses/listing/CourseListing';
import CreatorCourseListing from 'src/components/apps/courses/listing/CreatorCourseListing';
import StudentCourseListing from 'src/components/apps/courses/listing/StudentCourseListing';
import TeacherCourseListing from 'src/components/apps/courses/listing/TeacherCourseListing';
import PageContainer from 'src/components/container/PageContainer';
import useAuth from 'src/guards/authGuard/UseAuth';

const UserCourses = () => {
    const { user } = useAuth();
    return (
        <PageContainer title="User courses" description="this is user course page">
            {user.role == 'TEACHER' ? (
                <>
                    <Box
                        sx={{
                            margin: '4% 21% 0 79%',
                            width: '200px',
                        }}
                    >
                        <Button
                            to="/courses/create"
                            component={Link}
                            sx={{ padding: '15px', color: 'black' }}
                        >
                            <Typography fontSize={15}>Создать курс</Typography>
                        </Button>
                    </Box>
                    <CreatorCourseListing />
                    <TeacherCourseListing />
                </>
            ) : (
                <>
                    <CourseListing linkPath="/courses/" />
                    <StudentCourseListing />
                </>
            )}
        </PageContainer>
    );
};

export default UserCourses;
