import { Typography } from '@mui/material';
import { Box } from '@mui/system';
import { group } from 'console';
import { useEffect } from 'react';
import PageContainer from 'src/components/container/PageContainer';
import BlankCard from 'src/components/shared/BlankCard';
import ParentCard from 'src/components/shared/ParentCard';
import { useDispatch, useSelector } from 'src/store/Store';
import { fetchCreatorCourses } from 'src/store/apps/courses/CourseSlise';
import { fetchCourseGroups, fetchCreatorGroups, fetchStudentGroups } from 'src/store/apps/groups/GroupSlice';
import { GroupType } from 'src/types/apps/group';
import EditCourseGroupTable from 'src/views/tables/EditCourseGroupTable';
import EditGroupTable from 'src/views/tables/EditGroupTable';
import GroupTable from 'src/views/tables/GroupTable';

type Props = {
    id?: string;
    type: number;
};

const Groups = ({ id, type }: Props) => {
    const dispatch = useDispatch();
    const groups: GroupType[] = useSelector((state) => {
        return state.groupReducer.groups;
    });

    if (id) {
        useEffect(() => {
            if (type == 1) {
                dispatch(fetchStudentGroups(id));
            } else if (type==2) {
                dispatch(fetchCreatorGroups(id));
            } else {
                dispatch(fetchCourseGroups(id));
            }
        }, [dispatch]);
    }


    return (
        (type==1 ?
            <GroupTable groups={groups} />
        : (type == 2?
            <EditGroupTable groups={groups} />
            :
            <EditCourseGroupTable groups={groups} courseId={id ? id.toString():''}/>)
        )
        
    );
};

export default Groups;
