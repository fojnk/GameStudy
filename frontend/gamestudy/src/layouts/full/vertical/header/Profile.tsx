// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Avatar, Box, Button, ButtonBase, Divider, IconButton, Menu, Typography } from '@mui/material';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import * as dropdownData from './data';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Stack } from '@mui/system';
import { IconMail } from '@tabler/icons-react';

import ProfileImg from 'src/assets/images/profile/user-5.png';
import useAuth from 'src/guards/authGuard/UseAuth';
import useMounted from 'src/guards/authGuard/UseMounted';

const Profile = () => {
  const [anchorEl2, setAnchorEl2] = useState(null);
  const mounted = useMounted();
  const { logout, user } = useAuth();
  const navigate = useNavigate();

  const handleClick2 = (event: any) => {
    setAnchorEl2(event.currentTarget);
  };
  const handleClose2 = () => {
    setAnchorEl2(null);
  };

  const handleLogout = async () => {
    try {
      await logout();
      navigate('/');
      if (mounted.current) {
        handleClose2();
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Box>
      <IconButton
        size="large"
        aria-label="show 11 new notifications"
        color="inherit"
        aria-controls="msgs-menu"
        aria-haspopup="true"
        sx={{
          ...(typeof anchorEl2 === 'object' && {
            color: 'primary.main',
          }),
        }}
        onClick={handleClick2}
      >
        <Avatar
          src={ProfileImg}
          alt={ProfileImg}
          sx={{
            width: 35,
            height: 35,
          }}
        />
      </IconButton>
      {/* ------------------------------------------- */}
      {/* Message Dropdown */}
      {/* ------------------------------------------- */}
      <Menu
        id="msgs-menu"
        anchorEl={anchorEl2}
        keepMounted
        open={Boolean(anchorEl2)}
        onClose={handleClose2}
        anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
        transformOrigin={{ horizontal: 'right', vertical: 'top' }}
        sx={{
          '& .MuiMenu-paper': {
            width: '360px',
            p: 4,
          },
        }}
      >
        <Typography variant="h5">Профиль</Typography>
        <Stack direction="row" py={3} spacing={2} alignItems="center">
          <Avatar src={ProfileImg} alt={ProfileImg} sx={{ width: 95, height: 95 }} />
          <Box>
            <Typography variant="subtitle2" color="textPrimary" fontWeight={600}>
              {user.surname || user.name || user.father_name ? 
              (user.surname ? user.surname + " " : " ") + (user.name ? user.name + " " : " ") + (user.fathers_name ? user.fathers_name : " ")
              :"Фамилия Имя Отчество"}
            </Typography>
            <Typography variant="subtitle2" color="textSecondary">
              {user.role}
            </Typography>
            <Typography
              variant="subtitle2"
              color="textSecondary"
              display="flex"
              alignItems="center"
              gap={1}
            >
              <IconMail width={15} height={15} />
                {user.email}
            </Typography>
          </Box>
        </Stack>
        <Divider />
        {dropdownData.profile.map((profile) => (
          <Box key={profile.title}>
            <Box sx={{ py: 2, px: 0 }} className="hover-text-primary">
              <Box>
                <ButtonBase to={profile.href} component={Link}>
                <Stack direction="row" spacing={2}>
                  <Box
                    width="45px"
                    height="45px"
                    bgcolor="primary.light"
                    color={"primary.main"}
                    display="flex"
                    alignItems="center"
                    justifyContent="center"
                  >
                    <Avatar
                      src={profile.icon}
                      alt={profile.icon}
                      sx={{
                        width: 24,
                        height: 24,
                        borderRadius: 0,
                      }}
                    />
                  </Box>
                  <Box>
                    <Typography
                      variant="subtitle2"
                      fontWeight={600}
                      color="textPrimary"
                      className="text-hover"
                      noWrap
                      sx={{
                        width: '240px',
                      }}
                    >
                      {profile.title}
                    </Typography>
                    <Typography
                      color="textSecondary"
                      variant="subtitle2"
                      sx={{
                        width: '240px',
                      }}
                      noWrap
                    >
                      {profile.subtitle}
                    </Typography>
                  </Box>
                </Stack>
                </ButtonBase>
              </Box>
            </Box>
          </Box>
        ))}
        <Box mt={2}>
          <Button variant="outlined" color="secondary" fullWidth onClick={handleLogout}>
            Выход
          </Button>
        </Box>
      </Menu>
    </Box>
  );
};

export default Profile;
