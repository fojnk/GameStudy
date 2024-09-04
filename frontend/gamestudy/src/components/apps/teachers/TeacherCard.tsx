// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore

import { Box, ButtonBase, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import { TeacherType } from 'src/types/apps/teacher';

type Props = {
    teacher?: TeacherType;
};

const Img = styled('img')({
    margin: 'auto',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
});

const TeacherCard = ({ teacher }: Props) => {
    return (
        <Box m={1} sx={{ width: '120px', height: '170px', padding: '1px 8px', boxShadow: 5 }}>
            <ButtonBase sx={{ width: 105, height: 100, margin: '10% auto 0 auto' }}>
                <Img
                    alt="complex"
                    src="https://www.pngall.com/wp-content/uploads/8/Professor-PNG-Free-Image.png"
                />
            </ButtonBase>
            {teacher ? (
                <Box>
                    <Typography
                        color="primary.dark"
                        fontSize={10}
                        gutterBottom
                        variant="subtitle1"
                        component="div"
                    >
                        {teacher.user?.surname} {teacher.user?.name} {teacher.user?.fathers_name}
                    </Typography>
                    <Typography variant="body2" fontSize={10} gutterBottom>
                        {teacher.qualification}
                    </Typography>
                </Box>
            ) : (
                <Typography variant="body2" gutterBottom>
                    Uknown teacher
                </Typography>
            )}
        </Box>
    );
};

export default TeacherCard;
