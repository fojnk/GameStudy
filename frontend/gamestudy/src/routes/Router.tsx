// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { lazy } from 'react';
import { Navigate } from 'react-router-dom';
import CourseEdit from 'src/components/apps/courses/details/CourseEdit';
import CoursePage from 'src/components/apps/courses/details/СoursePage';
import Disciplines from 'src/components/apps/disciplines/DisciplinesList';
import ProfileSettings from 'src/components/apps/profile/shared/ProfileSettings';
import UserProfile from 'src/components/apps/profile/shared/UserProfile';
import Activity from 'src/components/apps/profile/student/activity/Activity';
import AuthGuard from 'src/guards/authGuard/AuthGuard';
import GuestGuard from 'src/guards/authGuard/GuestGaurd';
import GuestLayout from 'src/layouts/guest/GuestLayout';
import ForgotPassword from 'src/views/authentication/auth1/ForgotPassword';
import CreateCourse from 'src/views/pages/full/CreateCourse';
import StudentRatings from 'src/views/pages/full/StudentRatings';
import UserCourses from 'src/views/pages/full/UserCourses';
import About from 'src/views/pages/guest/about';
import Courses from 'src/views/pages/guest/CoursePage';
import Faq from 'src/views/pages/shared/Faq';
import Loadable from '../layouts/full/shared/loadable/Loadable';
import RatingPage from 'src/views/pages/full/RatingPage';

/* ***Layouts**** */
const FullLayout = Loadable(lazy(() => import('../layouts/full/FullLayout')));
const BlankLayout = Loadable(lazy(() => import('../layouts/blank/BlankLayout')));

/* ****Pages***** */
const SamplePage = Loadable(lazy(() => import('../views/sample-page/SamplePage')));
const Error = Loadable(lazy(() => import('../views/authentication/Error')));

// authentication
const Login = Loadable(lazy(() => import('../views/authentication/auth1/Login')));
const Register = Loadable(lazy(() => import('../views/authentication/auth1/Register')));

const Router = [
    {
        path: '/',
        element: (
            <AuthGuard>
                <FullLayout />
            </AuthGuard>
        ),
        children: [
            { path: '/', element: <Navigate to="/sample-page" /> },
            { path: '/sample-page', element: <SamplePage /> },
            { path: '/courses', element: <UserCourses /> },
            { path: '/courses/create', element: <CreateCourse /> },
            { path: '/courses/:id', exact: true, element: <CoursePage /> },
            { path: '/courses/:id/edit', exact: true, element: <CourseEdit /> },
            { path: '/disciplines', exact: true, element: <Disciplines type={2} /> },
            { path: '/profile', exact: true, element: <UserProfile /> },
            { path: '/ratings', exact: true, element: <RatingPage /> },
            { path: '/profile/edit', exact: true, element: <ProfileSettings /> },
            { path: '/profile/activity', exact: true, element: <Activity /> },
            { path: '/faq', exact: true, element: <Faq /> },
            { path: '*', element: <Navigate to="/auth/404" /> },
        ],
    },
    {
        path: '/auth',
        element: (
            <GuestGuard>
                <GuestLayout />
            </GuestGuard>
        ),
        children: [
            { path: '/auth/login', element: <Login /> },
            { path: '/auth/register', element: <Register /> },
            { path: '/auth/forgot', element: <ForgotPassword /> },
            { path: '/auth/about', element: <About /> },
            { path: '/auth/faq', element: <Faq /> },
            { path: '/auth/courses', element: <Courses /> },
            { path: '/auth/courses/:id', element: <CoursePage /> },
            { path: '/auth/disciplines', element: <Disciplines type={2} /> },
        ],
    },
    {
        path: '/auth',
        element: <BlankLayout />,
        children: [
            { path: '404', element: <Error /> },
            { path: '*', element: <Navigate to="/auth/404" /> },
        ],
    },
];

export default Router;
