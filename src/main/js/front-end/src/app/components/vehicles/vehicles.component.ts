import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { PageableResult } from '@app/models/filter/pageable.result';
import { VehiclesFilter } from '@app/models/filter/vehicles-filter';
import { Vehicle } from '@app/models/vehicle/vehicle';
import { SpinnerService } from '@app/services/spinner/spinner.service';
import { VehicleService } from '@app/services/vehicle/vehicle.service';
import { PaginatorModule, PaginatorState } from 'primeng/paginator';
import { PanelModule } from 'primeng/panel';
import { TableModule } from 'primeng/table';
import { finalize, Subscription } from 'rxjs';
import { VehiclesFilterComponent } from './vehicles-filter/vehicles-filter.component';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.scss'],
  imports: [
    VehiclesFilterComponent,
    PanelModule,
    TableModule,
    PaginatorModule 
  ],
  standalone: true
})
export class VehiclesComponent implements OnInit, OnDestroy {
    private readonly vehicleService: VehicleService = inject(VehicleService);
    public readonly spinnerService = inject(SpinnerService);

    pageableVehicles: PageableResult<Vehicle>;
    vehiclesFilter: VehiclesFilter = { brand: null, page: 0, size: 10, sortKey: null, sortDirection: null };
    rows: number = 10;

    private vehiclesFilterChangeSub: Subscription;

    ngOnInit(): void {
        this.vehiclesFilterChangeSub = this.vehicleService.vehiclesFilterChangeFromFilter$.subscribe(
            (vehiclesFilter: VehiclesFilter) => {
                this.vehiclesFilter = vehiclesFilter;
                this.rows = this.vehiclesFilter.size;
                this.refreshPageableVehicles(this.vehiclesFilter);
        })
    }

    ngOnDestroy(): void {
        this.vehiclesFilterChangeSub.unsubscribe();
    }

    pageChange(event: PaginatorState) {
        this.vehiclesFilter.page = event.page;
        this.vehiclesFilter.size = event.rows;
        this.vehicleService.vehiclesFilterChangedFromTable(this.vehiclesFilter);
        this.refreshPageableVehicles(this.vehiclesFilter);
    }

    private refreshPageableVehicles(vehiclesFilter: VehiclesFilter) {
        this.vehicleService.getVehicles(vehiclesFilter)
            .subscribe({ next: res => {
                this.pageableVehicles = res
            }})
    }
}
