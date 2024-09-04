// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'src/store/Store';
import { fetchCourseTasks } from 'src/store/apps/tasks/TaskSlice';
import { TaskType } from 'src/types/apps/task';
import EditTaskTable from 'src/views/tables/EditTaskTable';
import TaskTable from 'src/views/tables/TaskTable';

type Props = {
    id?: string;
    type?: number;
};

const Tasks = (props: Props) => {
    const dispatch = useDispatch();

    if (props.id != undefined) {
        useEffect(() => {
            dispatch(fetchCourseTasks(props.id ? props.id : ''));
        }, [dispatch]);
    } else {
        return <></>
    }

    const tasks: TaskType[] = useSelector((state) => {
        return state.taskReducer.tasks;
    });

    if (tasks.length == 0) {
        return <></>;
    }

    return (
        (props.type == 1 ?
            <TaskTable tasks={tasks} />
            : 
            <EditTaskTable courseId={props.id} tasks={tasks} />
        )
    );
};

export default Tasks;
