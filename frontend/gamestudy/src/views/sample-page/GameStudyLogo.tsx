// src/GameStudy.tsx
import React from 'react';
import './GameStudyLogo.css';
import { AutoStories, SportsEsports } from '@mui/icons-material';

const GameStudy: React.FC = () => {
  return (
    <div className="game-study-container">
      <div className="game-study-word">GameStudy</div>
      <div className="rotating-elements">
        <div className="book1"><AutoStories sx={{transform: "scale(2)", color:"primary.main"}}/></div>
        <div className="joystick1"><SportsEsports sx={{transform: "scale(2)",  color:"secondary.light"}}/></div>
        
        <div className="joystick2"><SportsEsports sx={{transform: "scale(2)", color:"secondary.light"}}/></div>
        <div className="book2"><AutoStories sx={{transform: "scale(2)", color:"primary.main"}}/></div>
      </div>
    </div>
  );
};

export default GameStudy;
