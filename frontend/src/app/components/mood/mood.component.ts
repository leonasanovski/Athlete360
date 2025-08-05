import {Component, inject, OnInit} from '@angular/core';
import {MoodService} from '../../services/mood-service';
import {Mood} from '../../models/mood';
import {DatePipe, SlicePipe} from '@angular/common';
import {ActivatedRoute, RouterLink} from '@angular/router';

@Component({
    selector: 'app-mood',
    imports: [
        DatePipe,
        SlicePipe,
        RouterLink
    ],
    templateUrl: './mood.component.html',
    styleUrl: './mood.component.css'
})
export class MoodComponent implements OnInit {
    mood_objects: Mood[] = []

    constructor(private moodService: MoodService, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        console.log("Mood Component initialized")
        // this.loadMoods()
        const patientId = Number(this.route.snapshot.paramMap.get('id'))
        if (patientId) {
            this.getPatientMoods(patientId)
        } else {
            this.loadMoods()
        }
    }

    loadMoods(): void {
        this.moodService.getAllMoods().subscribe(
            (objects) => {
                this.mood_objects = objects
            }
        )
    }

    getPatientMoods(id: number): void {

        this.moodService.getMoodsByPatientId(id).subscribe(
            {
                next: moods => this.mood_objects = moods,
                error: err => console.error('Error loading all moods:', err)
            }
        )
    }
}
