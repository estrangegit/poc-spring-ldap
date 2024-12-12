import { ErrorDetails } from "@app/models/error/error-details";

export const HTTP_ERROR_STATUS = {
  forbidden: {errorCode: 403, errorMessage: 'Invalid authorization'} as ErrorDetails,
  unauthorized: {errorCode: 401, errorMessage: 'Invalid credentials'} as ErrorDetails,
};

