import { AsyncPipe } from '@angular/common';
import { Component, inject, OnDestroy, OnInit, output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Brand } from '@app/models/brand/brand';
import { VehiclesFilter } from '@app/models/filter/vehicles-filter';
import { BrandService } from '@app/services/brand/brand.service';
import { QueryParamsService } from '@app/services/query-params/query-params.service';
import { VehicleService } from '@app/services/vehicle/vehicle.service';
import { PrimeTemplate } from 'primeng/api';
import { Button } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { PanelModule } from 'primeng/panel';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-vehicles-filter',
  templateUrl: './vehicles-filter.component.html',
  styleUrls: ['./vehicles-filter.component.scss'],
  imports: [
    AsyncPipe,
    ReactiveFormsModule,
    DropdownModule,
    Button,
    PanelModule,
    PrimeTemplate
  ],
  standalone: true
})
export class VehiclesFilterComponent implements OnInit, OnDestroy {
    private readonly brandService = inject(BrandService);
    private readonly vehicleService = inject(VehicleService);
    private readonly queryParamService = inject(QueryParamsService);
    private readonly formBuilder = inject(FormBuilder);
    private readonly route = inject(ActivatedRoute)

    brands: Observable<Brand[]> = this.brandService.getBrands();
    private vehiclesFilterChangeSub: Subscription;

    vehiclesFilter: VehiclesFilter = { brand: null, page: 0, size: 10, sortKey: null, sortDirection: null };
    vehiclesFilterForm: FormGroup = this.formBuilder.group({
        brand: [null]
    });

    ngOnInit(): void {
        const queryParams = this.route.snapshot.queryParams;
        this.queryParamService.updateFilterFromQueryParams(queryParams, this.vehiclesFilter);
        this.syncVehiclesFilterToVehicleFilterForm();
        this.vehicleService.vehiclesFilterChangedFromFilter(this.vehiclesFilter);

        this.vehiclesFilterChangeSub = this.vehicleService.vehiclesFilterChangeFromTable$.subscribe(
            (vehiclesFilter: VehiclesFilter) => {
                this.vehiclesFilter = vehiclesFilter;
                this.queryParamService.updateURLParameters(this.vehiclesFilter);
        })
    }

    ngOnDestroy(): void {
        this.vehiclesFilterChangeSub.unsubscribe();
    }

    brandChangeHandler() {
        this.vehiclesFilter.page = 0;
    }

    searchVehicles() {
        this.syncVehiclesFilterFormToVehicleFilter();
        this.queryParamService.updateURLParameters(this.vehiclesFilter);      
        this.vehicleService.vehiclesFilterChangedFromFilter(this.vehiclesFilter);
    }

    private syncVehiclesFilterFormToVehicleFilter() {
        this.vehiclesFilter.brand = this.vehiclesFilterForm.get('brand').value;
    }

    private syncVehiclesFilterToVehicleFilterForm() {
        this.vehiclesFilterForm.patchValue({
            brand: this.vehiclesFilter.brand
        })
    }
}
