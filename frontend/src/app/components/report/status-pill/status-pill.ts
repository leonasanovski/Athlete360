import {Component, Input} from '@angular/core';

@Component({
  selector: 'status-pill',
  imports: [],
  templateUrl: './status-pill.html',
  standalone: true,
  styleUrl: './status-pill.css'
})
export class StatusPill {
  @Input() status = '';
  @Input() fontSize = '';
}
