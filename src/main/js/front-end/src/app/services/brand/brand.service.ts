import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ROLE1_BRAND } from '@app/models/api-url-constants';
import { Brand } from '@app/models/brand/brand';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class BrandService {

    private readonly http = inject(HttpClient)

    constructor() { }

    getBrands(): Observable<Brand[]> {
        return this.http.get(ROLE1_BRAND) as Observable<Brand[]>;
    }
}
