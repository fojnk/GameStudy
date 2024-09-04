import { Box, Container, styled } from '@mui/material';
import { FC } from 'react';
import { Outlet } from 'react-router-dom';
import { AppState, useSelector } from 'src/store/Store';
import Navigation from '../full/horizontal/navbar/Navigation';
import Customizer from '../full/shared/customizer/Customizer';
import Header from './header/GuestHeader';

const MainWrapper = styled('div')(() => ({
    top: '0',
    display: 'flex',
    minHeight: '100vh',
    width: '100%',
}));

const PageWrapper = styled('div')(() => ({
    display: 'flex',
    flexGrow: 1,
    paddingBottom: '60px',
    flexDirection: 'column',
    zIndex: 1,
    backgroundColor: 'transparent',
}));

const GuestLayout: FC = () => {
    const customizer = useSelector((state: AppState) => state.customizer);

    return (
        <>
            <MainWrapper
                className={customizer.activeMode === 'dark' ? 'darkbg mainwrapper' : 'mainwrapper'}
            >
                <PageWrapper
                    className="page-wrapper"
                    sx={{
                        backgroundColor: 'background.default',
                    }}
                >
                    {/* ------------------------------------------- */}
                    {/* Header */}
                    {/* ------------------------------------------- */}
                    <Header />
                    {/* PageContent */}
                    {customizer.isHorizontal ? <Navigation /> : ''}
                    <Container
                        sx={{
                            maxWidth: customizer.isLayout === 'boxed' ? 'lg' : '100%!important',
                        }}
                    >
                        {/* ------------------------------------------- */}
                        {/* PageContent */}
                        {/* ------------------------------------------- */}
                        <Box
                            sx={{
                                minHeight: 'calc(100vh - 170px)',
                                bottom: 0,
                                top: 0,
                                width: '70%',
                                margin: '0 auto 0 auto',
                                paddingTop: '0',
                                borderRadius: 0,
                            }}
                        >
                            <Outlet />
                        </Box>
                    </Container>
                    <Customizer />
                </PageWrapper>
            </MainWrapper>
        </>
    );
};

export default GuestLayout;
