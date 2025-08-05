import {Component, OnInit} from '@angular/core';
import {Mood} from '../../models/mood';
import {MoodService} from '../../services/mood-service';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-mood.details.component',
  imports: [
    DatePipe,
    RouterLink
  ],
  templateUrl: './mood.details.component.html',
  styleUrl: './mood.details.component.css'
})
export class MoodDetailsComponent implements OnInit {
  mood_object: Mood | undefined

  constructor(private moodService: MoodService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    const mood_id = Number(this.route.snapshot.paramMap.get('id'))
    if (mood_id){
      this.moodService.getMoodByIdFrom(mood_id).subscribe({
        next: mood => this.mood_object = mood
      })
    }else{
      this.router.navigate(['/moods'])
    }
  }


}
