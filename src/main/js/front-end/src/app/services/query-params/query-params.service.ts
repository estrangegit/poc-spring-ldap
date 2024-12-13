import { inject, Injectable } from '@angular/core';
import { Params, Router } from "@angular/router";
import { SearchFilter } from '@app/models/filter/search-filter';
import { SortDirection } from '@app/models/filter/sort-direction';

import { BehaviorSubject } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class QueryParamsService {
    public filterChangeFromParent = new BehaviorSubject(null);
    private readonly router = inject(Router);

    updateURLParameters(queryParams: SearchFilter, newURl?: string) {
        if (newURl) {
            this.router.navigate([newURl], { queryParams: queryParams });
        } else {
            this.router.navigate([], { queryParams: queryParams });
        }
    }

    updateFilterFromQueryParams(queryParams: Params, filter: any) {
        Object.keys(queryParams).forEach((key) => {
            if (filter.hasOwnProperty(key)) {
                const value = queryParams[key];
                if (typeof filter[key] === 'number') {
                    filter[key] = +value;
                } else if (value === 'true' || value === 'false') {
                    filter[key] = JSON.parse(value)
                } else {
                    filter[key] = value;
                }
            }
        });
    }

    updatesortKey(filter: SearchFilter, sortKey: string, sortDirection: SortDirection) {
        filter.sortKey = sortKey
        if (filter.sortKey && sortDirection)
            filter.sortDirection = sortDirection
        else {
            filter.sortDirection = null;
            filter.sortKey = null;
        }
    }
}
