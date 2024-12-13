export interface PageableResult<T> {
    totalPages: number;
    totalNbElements: number;
    pageNumber: number;
    pageSize: number;
    content: T[];
}
