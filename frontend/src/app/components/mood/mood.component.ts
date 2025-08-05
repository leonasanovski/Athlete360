import {Component, inject, OnInit} from '@angular/core';
import {MoodService} from '../../services/mood-service';
import {Mood} from '../../models/mood';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-mood',
  imports: [
    DatePipe
  ],
  templateUrl: './mood.component.html',
  styleUrl: './mood.component.css'
})
export class MoodComponent implements OnInit {
  mood_objects: Mood[] = []
  constructor(private moodService: MoodService) {}

  ngOnInit(): void {
    console.log("Mood Component initialized")
    this.loadMoods()
  }

  loadMoods(): void {
    this.moodService.getAllMoods().subscribe(
      (objects) => {
        this.mood_objects = objects
      }
    )
  }
}
