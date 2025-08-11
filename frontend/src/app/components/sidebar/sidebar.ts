import {Component, Input} from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'sidebar',
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './sidebar.html',
  standalone: true,
  styleUrl: './sidebar.css'
})
export class Sidebar {
  @Input() role: String | undefined;
}
