import CustomException from "@app/models/error/custom-exception";

export const customErrorMessages: Map<CustomException, string> = new Map<CustomException, string>([
  [CustomException.AUTHENTICATION_EXCEPTION, 'Invalid credentials'],
  [CustomException.EXPIRED_AUTHORIZATION_EXCEPTION, 'Expired authorization'],
  [CustomException.SIGNATURE_AUTHORIZATION_EXCEPTION, 'Invalid authorization'],
  [CustomException.AUTHORIZATION_EXCEPTION, 'Invalid authorization'],
  [CustomException.REFRESH_TOKEN_EXCEPTION, 'Expired authorization'],
  [CustomException.METHOD_ARGUMENT_NOT_VALID_EXCEPTION, 'Invalid argument']
]);
