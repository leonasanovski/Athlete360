import {Component, Input} from '@angular/core';
import {Recommendation} from '../../../models/Recommendation';

@Component({
  selector: 'recommendation-card',
  imports: [],
  templateUrl: './recommendation-card.html',
  standalone: true,
  styleUrl: './recommendation-card.css'
})
export class RecommendationCard {
  @Input() recommendation: Recommendation | undefined;
}
