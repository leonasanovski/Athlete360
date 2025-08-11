import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {Recommendation} from '../../../models/Recommendation';
import {NgClass} from '@angular/common';

@Component({
  selector: 'recommendation-card',
  imports: [
    NgClass
  ],
  templateUrl: './recommendation-card.html',
  standalone: true,
  styleUrl: './recommendation-card.css'
})
export class RecommendationCard implements OnChanges {
  @Input() recommendation: Recommendation | undefined;

  typeBadgeClass = '';
  typeClass = '';

  ngOnChanges(changes: SimpleChanges) {
    if (changes['recommendation']) {
      this.updateClasses();
    }
  }

  private updateClasses() {
    const t = (this.recommendation?.type ?? '').toString().toUpperCase();
    console.log(t)

    switch (t) {
      case 'DIET':
        this.typeBadgeClass = 'badge-diet';
        this.typeClass = 'type-diet';
        break;
      case 'TRAINING':
        this.typeBadgeClass = 'badge-training';
        this.typeClass = 'type-training';
        break;
      case 'SUPPLEMENT':
        this.typeBadgeClass = 'badge-supplement';
        this.typeClass = 'type-supplement';
        break;
      default:
        this.typeBadgeClass = 'badge-secondary';
        this.typeClass = '';
    }
  }
}
