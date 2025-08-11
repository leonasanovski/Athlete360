import {MoodProgress} from './types/MoodProgress';
import {MoodEmotion} from './types/MoodEmotion';

export interface Mood {
  moodId: number;
  moodProgress: MoodProgress;
  moodEmotion: MoodEmotion;
  hoursSleptAverage: number;
  moodDescription: string;
  createdAt: string;
  patientId: number;
  patientName: string;
}

export interface MoodResponse{
  patientId: number,
  moodEmotion: string,
  moodDescription: string,
  hoursSleptAverage: number
}
