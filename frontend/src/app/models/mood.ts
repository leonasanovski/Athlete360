export interface Mood {
  moodId: number;
  moodProgress: string;
  moodEmotion: string;
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
