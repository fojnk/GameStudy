import { IconButton, Box, AppBar, useMediaQuery, Toolbar, styled, Stack } from '@mui/material';

import { useSelector, useDispatch } from 'src/store/Store';
import { toggleSidebar, toggleMobileSidebar } from 'src/store/customizer/CustomizerSlice';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { IconMenu2 } from '@tabler/icons-react';
import Notifications from './Notification';
import Profile from './Profile';
import Cart from './Cart';
import Search from './Search';
import Language from './Language';
import { AppState } from 'src/store/Store';
import Navigation from './Navigation';
import MobileRightSidebar from './MobileRightSidebar';

const Header = () => {
  const lgUp = useMediaQuery((theme: any) => theme.breakpoints.up('lg'));
  const lgDown = useMediaQuery((theme: any) => theme.breakpoints.down('lg'));

  // drawer
  const customizer = useSelector((state: AppState) => state.customizer);
  const dispatch = useDispatch();

  const AppBarStyled = styled(AppBar)(({ theme }) => ({
    boxShadow: 'none',
    background: theme.palette.primary.light,
    justifyContent: 'center',
    backdropFilter: 'blur(4px)'
  }));
  const ToolbarStyled = styled(Toolbar)(({ theme }) => ({
    width: '100%',
    color: theme.palette.text.secondary
  }));

  return (
    <Box
      sx={{
        borderBottom: 1,
        borderBottomColor: "primary",
        borderRadius: 0
      }}
    >
    <AppBarStyled position="sticky" color="default">
      <ToolbarStyled>
        {/* ------------------------------------------- */}
        {/* Toggle Button Sidebar */}
        {/* ------------------------------------------- */}
        <IconButton
          sx={{color: "black"}}
          aria-label="menu"
          onClick={lgUp ? () => dispatch(toggleSidebar()) : () => dispatch(toggleMobileSidebar())}
        >
          <IconMenu2 size="30" />
        </IconButton>

      
        {lgUp ? (
          <>
            <Navigation />
          </>
        ) : null}

        <Box flexGrow={1} />
        <Stack spacing={1} direction="row" alignItems="center">
          {/* <Cart /> */}
          {/* ------------------------------------------- */}
          {/* End Ecommerce Dropdown */}
          {/* ------------------------------------------- */}
          <Search />
          <Notifications />
          {/* ------------------------------------------- */}
          {/* Toggle Right Sidebar for mobile */}
          {/* ------------------------------------------- */}
          {lgDown ? <MobileRightSidebar /> : null}
          <Profile />
            {/* ------------------------------------------- */}
            {/* Search Dropdown */}
            {/* ------------------------------------------- */}
        </Stack>
      </ToolbarStyled>
    </AppBarStyled>
    </Box>
  );
};

export default Header;
