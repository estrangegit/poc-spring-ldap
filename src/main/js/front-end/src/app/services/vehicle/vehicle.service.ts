import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ROLE1_VEHICLE } from '@app/models/api-url-constants';
import { PageableResult } from '@app/models/filter/pageable.result';
import { VehiclesFilter } from '@app/models/filter/vehicles-filter';
import { Vehicle } from '@app/models/vehicle/vehicle';
import { Observable, Subject } from 'rxjs';

@Injectable({providedIn: 'root'})
export class VehicleService {

    private readonly http = inject(HttpClient)

    private vehiclesFilterChangeFromFilterSource = new Subject<VehiclesFilter>();
    vehiclesFilterChangeFromFilter$ = this.vehiclesFilterChangeFromFilterSource.asObservable();

    private vehiclesFilterChangeFromTableSource = new Subject<VehiclesFilter>();
    vehiclesFilterChangeFromTable$ = this.vehiclesFilterChangeFromTableSource.asObservable();

    vehiclesFilterChangedFromFilter(vehiclesFilter: VehiclesFilter) {
        this.vehiclesFilterChangeFromFilterSource.next(vehiclesFilter);
    }

    vehiclesFilterChangedFromTable(vehiclesFilter: VehiclesFilter) {
        this.vehiclesFilterChangeFromTableSource.next(vehiclesFilter);
    }

    getVehicles(vehiclesFilter: VehiclesFilter): Observable<PageableResult<Vehicle>> {
        return this.http.post(ROLE1_VEHICLE, vehiclesFilter) as Observable<PageableResult<Vehicle>>;
    }
}
