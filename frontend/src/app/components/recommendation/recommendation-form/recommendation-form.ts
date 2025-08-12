import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'recommendation-form',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './recommendation-form.html',
  standalone: true,
  styleUrl: './recommendation-form.css'
})
export class RecommendationForm {
  @Input() form!: FormGroup;
  @Output() remove = new EventEmitter<void>();
}
