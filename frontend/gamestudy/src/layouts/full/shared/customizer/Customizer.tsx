import {
    Divider,
    Drawer,
    Fab,
    Grid,
    IconButton,
    Slider,
    Stack,
    styled,
    Tooltip,
    Typography,
} from '@mui/material';
import Box, { BoxProps } from '@mui/material/Box';
import { FC, useState } from 'react';
import { useDispatch, useSelector } from 'src/store/Store';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { BorderOuter } from '@mui/icons-material';
import AspectRatioTwoToneIcon from '@mui/icons-material/AspectRatioTwoTone';
import CallToActionTwoToneIcon from '@mui/icons-material/CallToActionTwoTone';
import { IconCheck, IconSettings, IconX } from '@tabler/icons-react';
import Scrollbar from 'src/components/custom-scroll/Scrollbar';
import {
    setBorderRadius,
    setCardShadow,
    setTheme,
    toggleLayout,
} from 'src/store/customizer/CustomizerSlice';
import { AppState } from 'src/store/Store';

const SidebarWidth = '320px';
interface colors {
    id: number;
    bgColor: string;
    disp?: string;
}
const Customizer: FC = () => {
    const [showDrawer, setShowDrawer] = useState(false);
    const customizer = useSelector((state: AppState) => state.customizer);

    const dispatch = useDispatch();

    const StyledBox = styled(Box)<BoxProps>(({ theme }) => ({
        boxShadow: theme.shadows[8],
        padding: '20px',
        cursor: 'pointer',
        justifyContent: 'center',
        display: 'flex',
        transition: '0.1s ease-in',
        border: '1px solid rgba(145, 158, 171, 0.12)',
        '&:hover': {
            transform: 'scale(1.05)',
        },
    }));

    const thColors: colors[] = [
        {
            id: 1,
            bgColor: '#C2C2C2',
            disp: 'GRAY_THEME',
        },
        {
            id: 2,
            bgColor: '#72C81F',
            disp: 'NSU_GREEN_THEME',
        },
        {
            id: 3,
            bgColor: '#DE2022',
            disp: 'NSU_RED_THEME',
        },
        {
            id: 6,
            bgColor: '#1DBFEA',
            disp: 'NSU_BLUE_THEME',
        },
    ];

    return (
        <div>
            {/* ------------------------------------------- */}
            {/* --Floating Button to open customizer ------ */}
            {/* ------------------------------------------- */}
            <Tooltip title="Settings">
                <Fab
                    color="primary"
                    aria-label="settings"
                    sx={{ position: 'fixed', right: '25px', bottom: '15px' }}
                    onClick={() => setShowDrawer(true)}
                >
                    <IconSettings stroke={1.5} />
                </Fab>
            </Tooltip>
            <Drawer
                anchor="right"
                open={showDrawer}
                onClose={() => setShowDrawer(false)}
                PaperProps={{
                    sx: {
                        width: SidebarWidth,
                    },
                }}
            >
                {/* ------------------------------------------- */}
                {/* ------------ Customizer Sidebar ------------- */}
                {/* ------------------------------------------- */}
                <Scrollbar sx={{ height: 'calc(100vh - 5px)' }}>
                    <Box p={2} display="flex" justifyContent={'space-between'} alignItems="center">
                        <Typography variant="h4">Settings</Typography>

                        <IconButton color="inherit" onClick={() => setShowDrawer(false)}>
                            <IconX size="1rem" />
                        </IconButton>
                    </Box>
                    <Divider />
                    <Box p={3}>
                        {/* ------------------------------------------- */}
                        {/* ------------ Dark light theme setting ------------- */}
                        {/* ------------------------------------------- */}
                        {/* <Typography variant="h6" gutterBottom>
              Theme Option
            </Typography>
            <Stack direction={'row'} gap={2} my={2}>
              <StyledBox onClick={() => dispatch(setDarkMode('light'))} display="flex" gap={1}>
                <WbSunnyTwoToneIcon
                  color={customizer.activeMode === 'light' ? 'primary' : 'inherit'}
                />
                Light
              </StyledBox>
              <StyledBox onClick={() => dispatch(setDarkMode('dark'))} display="flex" gap={1}>
                <DarkModeTwoToneIcon
                  color={customizer.activeMode === 'dark' ? 'primary' : 'inherit'}
                />
                Dark
              </StyledBox>
            </Stack> */}

                        {/* ------------------------------------------- */}
                        {/* ------------ Theme Color setting ------------- */}
                        {/* ------------------------------------------- */}
                        <Typography variant="h6" gutterBottom>
                            Theme Colors
                        </Typography>
                        <Grid container spacing={2}>
                            {thColors.map((thcolor) => (
                                <Grid item xs={4} key={thcolor.id}>
                                    <StyledBox onClick={() => dispatch(setTheme(thcolor.disp))}>
                                        <Tooltip title={`${thcolor.disp}`} placement="top">
                                            <Box
                                                sx={{
                                                    backgroundColor: thcolor.bgColor,
                                                    width: '25px',
                                                    height: '25px',
                                                    borderRadius: '60px',
                                                    alignItems: 'center',
                                                    justifyContent: 'center',
                                                    display: 'flex',
                                                    color: 'white',
                                                }}
                                                aria-label={`${thcolor.bgColor}`}
                                            >
                                                {customizer.activeTheme === thcolor.disp ? (
                                                    <IconCheck width={13} />
                                                ) : (
                                                    ''
                                                )}
                                            </Box>
                                        </Tooltip>
                                    </StyledBox>
                                </Grid>
                            ))}
                        </Grid>
                        {/* ------------------------------------------- */}
                        {/* ------------ Layout Boxed / Full ------------- */}
                        {/* ------------------------------------------- */}
                        <Typography variant="h6" gutterBottom>
                            Container Option
                        </Typography>
                        <Stack direction={'row'} gap={2} my={2}>
                            <StyledBox
                                onClick={() => dispatch(toggleLayout('boxed'))}
                                display="flex"
                                gap={1}
                            >
                                <CallToActionTwoToneIcon
                                    color={customizer.isLayout === 'boxed' ? 'primary' : 'inherit'}
                                />
                                Boxed
                            </StyledBox>
                            <StyledBox
                                onClick={() => dispatch(toggleLayout('full'))}
                                display="flex"
                                gap={1}
                            >
                                <AspectRatioTwoToneIcon
                                    color={customizer.isLayout === 'full' ? 'primary' : 'inherit'}
                                />
                                Full
                            </StyledBox>
                        </Stack>
                        <Typography variant="h6" gutterBottom>
                            Card With
                        </Typography>
                        <Stack direction={'row'} gap={2} my={2}>
                            <StyledBox
                                onClick={() => dispatch(setCardShadow(false))}
                                display="flex"
                                gap={1}
                            >
                                <BorderOuter
                                    color={!customizer.isCardShadow ? 'primary' : 'inherit'}
                                />
                                Border
                            </StyledBox>
                            <StyledBox
                                onClick={() => dispatch(setCardShadow(true))}
                                display="flex"
                                gap={1}
                            >
                                <CallToActionTwoToneIcon
                                    color={customizer.isCardShadow ? 'primary' : 'inherit'}
                                />
                                Shadow
                            </StyledBox>
                        </Stack>
                        <Box pt={4} />
                        {/* ------------------------------------------- */}
                        {/* ------------ Theme Color setting ------------- */}
                        {/* ------------------------------------------- */}
                        <Typography variant="h6" gutterBottom>
                            Theme Border Radius
                        </Typography>

                        <Slider
                            size="small"
                            value={customizer.borderRadius}
                            aria-label="Small"
                            min={4}
                            max={24}
                            onChange={(event: any) => dispatch(setBorderRadius(event.target.value))}
                            valueLabelDisplay="auto"
                        />
                    </Box>
                </Scrollbar>
            </Drawer>
        </div>
    );
};

export default Customizer;
