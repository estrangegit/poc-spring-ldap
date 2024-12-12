import { SearchFilter } from "@app/models/filter/search-filter";

export interface VehicleFilter extends SearchFilter {
    licencePlate: string;
    brand: string;
    minIssuanceDate: string;
    maxIssuanceDate: string;
}


