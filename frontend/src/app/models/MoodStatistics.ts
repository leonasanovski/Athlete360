import {MoodEmotion} from "./types/MoodEmotion";
import {MoodProgress} from "./types/MoodProgress";

export interface ProgressEntry {
    date: string;
    progress: MoodProgress;
}

export interface MoodStatisticsDTO {
    averageSleepOverall: number;
    mostFrequentEmotion: MoodEmotion;
    totalMoodEntries: number;
    moodEmotionCounts: { [key in MoodEmotion]: number };
    moodProgressCounts: { [key in MoodProgress]: number };
    progressOverTime: ProgressEntry[];
}
