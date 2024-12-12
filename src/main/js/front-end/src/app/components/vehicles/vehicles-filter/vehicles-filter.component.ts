import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Brand } from '@app/models/brand/brand';
import { BrandService } from '@app/services/brand/brand.service';
import { QueryParamsService } from '@app/services/query-params/query-params.service';
import { PrimeTemplate } from 'primeng/api';
import { Button } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-vehicles-filter',
  templateUrl: './vehicles-filter.component.html',
  styleUrls: ['./vehicles-filter.component.scss'],
  imports: [
    AsyncPipe,
    ReactiveFormsModule,
    DropdownModule,
    Button
  ],
  standalone: true
})
export class VehiclesFilterComponent {
    private readonly brandService = inject(BrandService);
    private readonly queryParamService = inject(QueryParamsService);
    private readonly formBuilder = inject(FormBuilder);

    brands: Observable<Brand[]> = this.brandService.getBrands();

    vehiclesFilter: FormGroup = this.formBuilder.group({
        brand: [null]
    });

    clearFilter() {
        this.vehiclesFilter.patchValue({
            brand: null
        })
    }

    searchVehicles() {
        this.queryParamService.updateURLParameters(this.vehiclesFilter.value);
    }
}
