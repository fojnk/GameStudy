// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Grid } from '@mui/material';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import ChildCard from 'src/components/shared/ChildCard';

const tomorrow = dayjs().add(1, 'day');

const MuiDateTime = () => {

  return (

    <Grid item xs={12} lg={6} sm={6} display="flex" alignItems="stretch">
    <ChildCard title="Timepicker">
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DatePicker
            defaultValue={tomorrow}
            disableFuture
            views={['year', 'month', 'day']}
          />
    </LocalizationProvider>
    </ChildCard>
  </Grid>
  );
};

export default MuiDateTime;
