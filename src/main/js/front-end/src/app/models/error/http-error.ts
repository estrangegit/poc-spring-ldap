import { ErrorDetails } from "@app/models/error/error-details";

export interface HttpError {
    error: string;
    path: string;
    status: number;
    timestamp: string;
    customError: ErrorDetails;
}
