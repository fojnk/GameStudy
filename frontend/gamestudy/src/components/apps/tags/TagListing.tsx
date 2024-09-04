import { Box, Grid, Typography } from '@mui/material';
import { useEffect } from 'react';
import { fetchCourseTags } from 'src/store/apps/tags/TagSlice';
import { useDispatch, useSelector } from 'src/store/Store';
import { TagType } from 'src/types/apps/tags';

type Props = {
    id?: string;
};

function Tags({ id }: Props) {
    const dispatch = useDispatch();
    const tags: TagType[] = useSelector((state) => {
        return state.tagReducer.tags;
    });

    useEffect(() => {
        if (id) {
            dispatch(fetchCourseTags(id));
        }
    }, [dispatch, tags]);

    

    if (tags.length == 0) {
        return (
            <Typography
                marginTop={'50px'}
                textAlign={'center'}
                color={'primary.main'}
                fontSize={20}
            >
                Нет тегов
            </Typography>
        );
    }

    return (
        <Box sx={{ width: '70%' }}>
            <Grid container spacing={0}>
                {tags.map((tag, _) => {
                    return (
                        <Grid item xs={1}>
                            <Box
                                mt={1}
                                ml={1}
                                sx={{ backgroundColor: 'primary.main', boxShadow: 3 }}
                            >
                                <Typography
                                    color="primary.contrastText"
                                    fontSize={10}
                                    textAlign={'center'}
                                >
                                    {tag.title}
                                </Typography>
                            </Box>
                        </Grid>
                    );
                })}
            </Grid>
        </Box>
    );
};

export default Tags;
