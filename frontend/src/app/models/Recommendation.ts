import {RecommendationType} from './types/RecommendationType';
import {RestrictionLevel} from './types/RestrictionLevel';

export interface Recommendation {
    recommendationId: number | null;
    reportId: number;
    type: RecommendationType;
    restrictionLevel: RestrictionLevel;
    label: string;
    description: string;
    costPerMonth: number;
    durationWeeks: number;
    frequencyPerDay: number;
    targetGoal: string;
    effectivenessRating: number;
    doctorPersonalizedNotes: string;
}
