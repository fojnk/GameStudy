import { AppBar, Box, Stack, Toolbar, Typography, styled, useMediaQuery } from '@mui/material';

import Search from '../../full/vertical/header/Search';
import Navigation from './GuestNavigation';

const Header = () => {
    const lgUp = useMediaQuery((theme: any) => theme.breakpoints.up('lg'));

    const AppBarStyled = styled(AppBar)(({ theme }) => ({
        boxShadow: 'none',
        background: theme.palette.primary.light,
        justifyContent: 'center',
        backdropFilter: 'blur(4px)',
    }));

    const ToolbarStyled = styled(Toolbar)(({ theme }) => ({
        width: '100%',
        color: theme.palette.text.secondary,
    }));

    return (
        <Box
            sx={{
                borderBottom: 1,
                borderBottomColor: 'primary',
                borderRadius: 0,
            }}
        >
            <AppBarStyled position="sticky" color="default">
                <ToolbarStyled>
                    <Typography
                        sx={{
                            width: '15%',
                            fontSize: 20,
                            color: 'primary.main',
                        }}
                    >
                        GameStudy
                    </Typography>

                    {lgUp ? (
                        <>
                            <Navigation />
                        </>
                    ) : null}

                    <Box flexGrow={1} />
                    <Stack spacing={1} direction="row" alignItems="center">
                        <Search />
                    </Stack>
                </ToolbarStyled>
            </AppBarStyled>
        </Box>
    );
};

export default Header;
