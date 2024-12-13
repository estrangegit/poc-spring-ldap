import { SearchFilter } from "@app/models/filter/search-filter";

export interface VehiclesFilter extends SearchFilter {
    brand: string;
}
