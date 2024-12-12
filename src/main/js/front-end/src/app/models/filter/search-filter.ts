import { SortDirection } from "./sort-direction";

export interface SearchFilter {
  page: number;
  size: number;
  sortKey: string;
  sortDirection: SortDirection;
}
