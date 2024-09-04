// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Box, Typography } from '@mui/material';
import PageContainer from 'src/components/container/PageContainer';
import DashboardCard from '../../components/shared/DashboardCard';
import GameStudyLogo from './GameStudyLogo';


const SamplePage = () => {
    return (
        <>
            <PageContainer title="Sample Page" description="this is Sample page">
                <GameStudyLogo />
            </PageContainer>
        </>
    );
};

export default SamplePage;
