// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import React from 'react';
import PageContainer from 'src/components/container/PageContainer';
import CourseListing from 'src/components/apps/courses/listing/CourseListing';

const Courses = () => {
    return (
        <PageContainer title="Courses" description="this is course page">
            <CourseListing linkPath="/auth/courses/" />
        </PageContainer>
    );
};

export default Courses;
