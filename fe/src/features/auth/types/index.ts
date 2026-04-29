// for login
export interface Credentials {
  email: string;
  password: string;
}

export interface UserRegistrationDto {
  email: string;
  password: string;
}

export type ROLE = "ROLE_USER" | "ROLE_ADMIN";

export interface User {
  id: number;
  email: string;
  role: ROLE;
  confirmationResent: boolean;
  name: string;
  webLink: string;
}

export interface AuthSliceState {
  isAuthenticated: boolean;
  user?: User;
  loginErrorMessage?: string;
  registerFieldErrors?: Record<string, string[]>;
}

export type ValidationErrorResponse = {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  errors: {
    field: string;
    messages: string[];
  }[];
  path: string;
};

export interface PasswordResetDto {
  token: string;
  newPassword: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  user: User;
}