import { styled } from '@mui/material/styles';
import { useEffect, useState } from 'react';
import PageContainer from 'src/components/container/PageContainer';
import { Grid, Box, Typography, ButtonBase, Modal, Backdrop, Fade } from '@mui/material';
import { useDispatch, useSelector } from 'src/store/Store';
import { fetchStudentAchievements } from 'src/store/apps/achievements/AchievementSlice';
import { achievementType } from 'src/types/apps/achievement';
import BlankCard from 'src/components/shared/BlankCard';
import { DisciplineType } from 'src/types/apps/discipline';

type Props = {
    id?: string;
};

const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '80%',
    maxHeight: '80%',
});

const style = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '30%',
    height: '30%',
    bgcolor: 'background.paper',
    border: '2px solid #000',
    overflowY: 'auto',
    boxShadow: 24,
    p: 4,
};

function Row(props: { row: DisciplineType; id: number }) {
    const { row, id } = props;
    const [openAchievement, setAchievementOpen] = useState(false);
    return (
        <>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                open={openAchievement}
                onClose={() => setAchievementOpen(false)}
                closeAfterTransition
                slots={{ backdrop: Backdrop }}
            >
                <Fade in={openAchievement}>
                    <Box sx={style}>
                        <Img
                            alt="https://michael.stapelberg.ch/posts/2023-01-15-gokrazy-instance-centric-config/gokrazy-logo.png"
                            src="https://michael.stapelberg.ch/posts/2023-01-15-gokrazy-instance-centric-config/gokrazy-logo.png"
                        />
                        <Typography color="primary.dark" fontSize={30} textAlign={'center'}>
                            {row.title}
                        </Typography>
                    </Box>
                </Fade>
            </Modal>
            <Grid item xs={1}>
                <Box sx={{ boxShadow: 5 }}>
                    <Box p={1} sx={{ backgroundColor: 'primary.main' }}>
                        <ButtonBase onClick={()=>setAchievementOpen(true)}>
                            <Img
                                alt="https://michael.stapelberg.ch/posts/2023-01-15-gokrazy-instance-centric-config/gokrazy-logo.png"
                                src="https://michael.stapelberg.ch/posts/2023-01-15-gokrazy-instance-centric-config/gokrazy-logo.png"
                            />
                        </ButtonBase>
                    </Box>
                </Box>
            </Grid>
        </>
    );
}

const Achievements = ({ id }: Props) => {
    const dispatch = useDispatch();

    if (id) {
        useEffect(() => {
            dispatch(fetchStudentAchievements(id));
        }, [dispatch]);
    }

    const achievements: achievementType[] = useSelector((state) => {
        return state.achievementReducer.studentAchievements;
    });

    if (achievements.length == 0) {
        return (
            <Typography
                marginTop={'50px'}
                textAlign={'center'}
                color={'primary.main'}
                fontSize={20}
            >
                Нет достижений
            </Typography>
        );
    }

    return (
        <PageContainer title="Disciplines" description="this is discipline page">
            <Grid container spacing={2}>
                {achievements.map((achievement, index) => {
                    return <Row row={achievement} id={index} />;
                })}
            </Grid>
        </PageContainer>
    );
};

export default Achievements;
