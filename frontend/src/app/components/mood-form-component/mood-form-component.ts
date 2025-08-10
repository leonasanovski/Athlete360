import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {MoodService} from '../../services/mood-service';

@Component({
  selector: 'app-mood-form-component',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './mood-form-component.html',
  styleUrl: './mood-form-component.css'
})
export class MoodFormComponent implements OnInit {
  formBuilder: FormBuilder = inject(FormBuilder)
  route = inject(ActivatedRoute);
  router = inject(Router);
  moodForm!: FormGroup;
  moodEmotionOptions = ['EXCITED', 'HAPPY', 'NEUTRAL', 'TIRED', 'STRESSED', 'SAD'];
  patientId!: number
  moodService: MoodService = inject(MoodService)

  ngOnInit(): void {
    this.route.queryParams.subscribe(parameteres => {
      this.patientId = +parameteres['patientId']
      this.moodForm = this.formBuilder.group(
        {
          patientId: [this.patientId, Validators.required],
          moodEmotion: ['', Validators.required],
          moodDescription: ['', [Validators.required, Validators.minLength(60)]],
          hoursSleptAverage: ['', [Validators.required, Validators.min(0), Validators.max(24)]]
        }
      );
    })
  }

  onSubmit(): void {
    this.moodService.createMood(this.moodForm.value).subscribe({
      next: () => {
        this.router.navigate([`/moods/${this.patientId}/search`]);
      },
      error: (err) => {
        console.error("Error creating mood:", err);
      }
    })
  }
}
