import ActivityAchievements from './ActivityAchievements';
import ActivityLastActions from './ActivityLastActions';
import ActivityLevel from './ActivityLevel';
import ActivityRules from './ActivityRules';

const Activity = () => {
    return (
        <>
            <ActivityRules />

            <ActivityLevel />

            <ActivityLastActions />

            <ActivityAchievements />
        </>
    );
};

export default Activity;
