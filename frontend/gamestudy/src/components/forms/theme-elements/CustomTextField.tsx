// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import React from 'react';
import { styled } from '@mui/material/styles';
import { TextField } from '@mui/material';

const CustomTextField = styled((props: any) => <TextField  sx={{boxShadow:1, borderRadius: "9px"}} {...props} />)(({ theme }) => ({
  '& .MuiOutlinedInput-input::-webkit-input-placeholder': {
    color: '#767e89',
    opacity: '2',
  },
  '& .MuiOutlinedInput-notchedOutline': {
    borderColor: "black",
  },
  '& .MuiOutlinedInput-input.Mui-disabled': {
    backgroundColor: `${theme.palette.mode === 'dark' ? 'rgba(0, 0, 0, 0.12) ' : 'white'}`,
  },
  '& .MuiOutlinedInput-input.Mui-disabled::-webkit-input-placeholder': {
    color: '#767e89',
    opacity: '2',
  },
}));

export default CustomTextField;
