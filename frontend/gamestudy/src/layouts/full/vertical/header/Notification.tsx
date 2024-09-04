import {
    Avatar,
    Badge,
    Box,
    Button,
    Chip,
    IconButton,
    Menu,
    MenuItem,
    Typography,
} from '@mui/material';
import { useEffect, useState } from 'react';
import Scrollbar from 'src/components/custom-scroll/Scrollbar';

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Stack } from '@mui/system';
import { IconBellRinging } from '@tabler/icons-react';
import { Link } from 'react-router-dom';
import useAuth from 'src/guards/authGuard/UseAuth';
import { fetchUserNotifications } from 'src/store/apps/notifications/NotificationSlice';
import { useDispatch, useSelector } from 'src/store/Store';
import { NotificationType } from 'src/types/apps/notifications';
import img1 from '../../../../assets/images/profile/user-1.jpg'

const Notifications = () => {
    const [anchorEl2, setAnchorEl2] = useState(null);
    const { user } = useAuth();
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(fetchUserNotifications(user.id));
    }, [dispatch]);

    const notifications: NotificationType[] = useSelector((state) => {
        return state.notificationReducer.notifications;
    });

    const handleClick2 = (event: any) => {
        setAnchorEl2(event.currentTarget);
    };

    const handleClose2 = () => {
        setAnchorEl2(null);
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
                    color: anchorEl2 ? '' : 'black',
                }}
                onClick={handleClick2}
            >
                <Badge variant="dot" color="primary">
                    <IconBellRinging size="21" stroke="1.5" />
                </Badge>
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
                    },
                }}
            >
                <Stack
                    direction="row"
                    py={2}
                    px={4}
                    justifyContent="space-between"
                    alignItems="center"
                >
                    <Typography variant="h6">Уведомления</Typography>
                    <Chip label={notifications.length} color="primary" size="small" />
                </Stack>
                <Scrollbar sx={{ height: '385px' }}>
                    {notifications.map((notification, index) => (
                        <Box key={index}>
                            <MenuItem sx={{ py: 2, px: 4 }}>
                                <Stack direction="row" spacing={2}>
                                    <Avatar
                                        src={
                                            img1
                                        }
                                        alt={
                                            img1
                                        }
                                        sx={{
                                            width: 48,
                                            height: 48,
                                        }}
                                    />
                                    <Box>
                                        <Typography
                                            variant="subtitle2"
                                            color="textPrimary"
                                            fontWeight={600}
                                            noWrap
                                            sx={{
                                                width: '240px',
                                            }}
                                        >
                                            {notification.title}
                                        </Typography>
                                        <Typography
                                            color="textSecondary"
                                            variant="subtitle2"
                                            sx={{
                                                width: '240px',
                                            }}
                                            noWrap
                                        >
                                            {notification.details}
                                        </Typography>
                                    </Box>
                                </Stack>
                            </MenuItem>
                        </Box>
                    ))}
                </Scrollbar>
                <Box p={3} pb={1}>
                    <Button
                        to="/apps/email"
                        variant="outlined"
                        component={Link}
                        color="primary"
                        fullWidth
                    >
                        Все уведомления
                    </Button>
                </Box>
            </Menu>
        </Box>
    );
};

export default Notifications;
