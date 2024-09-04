// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Box, Grid, styled, Typography } from '@mui/material';
import PageContainer from 'src/components/container/PageContainer';
import img1 from "../../../assets/images/profile/biba.jpg"
import img2 from "../../../assets/images/profile/boba.gif"

const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
})

const About = () => {
    return (
        <PageContainer title="О нас" description="information about project">
            <Grid container spacing={4} mt = {5}>
                <Grid item xs={6}>
                    <Box p={2} sx={{ backgroundColor: 'white' }} textAlign={'center'}>
                        <Img src={img1} alt="hoho"/>
                        <Box mt={3}>
                        <Typography fontSize={30} sx={{color: "primary.dark"}}>
                            Биба (backend)
                        </Typography>
                        </Box>
                    </Box>
                </Grid>
                <Grid item xs={6}>
                    <Box p={2} sx={{ backgroundColor: 'white' }} textAlign={'center'}>
                    <Img src={img2} alt="hoho"/>
                    <Box mt={3}>
                        <Typography fontSize={30} sx={{color: "primary.dark"}}>
                            Боба (fullstack-_-)
                        </Typography>
                        </Box>
                    </Box>
                </Grid>
            </Grid>
        </PageContainer>
    );
};

export default About;
