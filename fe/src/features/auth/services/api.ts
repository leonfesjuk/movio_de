import axiosInstance from "../../../lib/axiosInstance";
import type { Credentials, User } from "../types";

// we already added  prefix /api in axios config

const API = {
  AUTH: {
    LOGIN: "/auth/login",
    RESET_PASSWORD: "/auth/reset-password",
    FORGOT_PASSWORD: "/auth/forgot-password",
    ME: "/auth/me",
    LOGOUT: "/auth/logout"
  },
  USERS: {
    REGISTER: "/users/register",
    VERIFY_EMAIL: "/users/confirm",
    PROFILE: "/users/profile/me",
  },
} as const;

export const fetchLogin = async (credentials: Credentials) => {
  const res = await axiosInstance.post(API.AUTH.LOGIN, credentials);
  return res.data;
};

export const fetchMe = async (): Promise<User> => {
  const res = await axiosInstance.get(API.AUTH.ME);
  return res.data;
};

export const fetchUpdateProfile = async (dto: Partial<User>) => {
  const res = await axiosInstance.put(API.USERS.PROFILE, dto);
  return res.data;
};

export const fetchRegister = async (credentials: Credentials) => {
  const res = await axiosInstance.post(API.USERS.REGISTER, credentials);
  return res.data;
};

export const fetchVerifyEmail = async (code: string) => {
  const res = await axiosInstance.get(`${API.USERS.VERIFY_EMAIL}/${code}`);
  return res.data;
};

export const fetchForgotPassword = async (email: string) => {
  const res = await axiosInstance.post(API.AUTH.FORGOT_PASSWORD, { email });
  return res.data;
};

export const fetchResetPassword = async (data: {
  token: string;
  newPassword: string;
}) => {
  const res = await axiosInstance.post(API.AUTH.RESET_PASSWORD, data);
  return res.data;
};

export const fetchLogout = async () => {
  const res = await axiosInstance.post(API.AUTH.LOGOUT);
  return res.data;
};