import {Component, inject, OnInit} from '@angular/core';
import { PanelModule } from 'primeng/panel';
import { VehiclesFilterComponent } from './vehicles-filter/vehicles-filter.component';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.scss'],
  imports: [
    VehiclesFilterComponent,
    PanelModule
  ],
  standalone: true
})
export class VehiclesComponent {}
