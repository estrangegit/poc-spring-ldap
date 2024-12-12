import {Component} from '@angular/core';
import {PanelModule} from "primeng/panel";

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.scss'],
  imports: [
    PanelModule
  ],
  standalone: true
})
export class PageNotFoundComponent {
}
