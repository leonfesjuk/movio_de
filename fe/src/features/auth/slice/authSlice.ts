import { createAppSlice } from "../../../app/createAppSlice";
import type {
  AuthSliceState,
  Credentials,
  UserRegistrationDto,
} from "../types";
import * as api from "../services/api";
import { isAxiosError } from "axios";

const initialState: AuthSliceState = {
  isAuthenticated: false,
  user: undefined,
  loginErrorMessage: undefined,
  registerFieldErrors: undefined,
};

export const authSlice = createAppSlice({
  name: "auth",
  initialState,
  reducers: (create) => ({
    login: create.asyncThunk(
      async (credentials: Credentials) => {
        return api.fetchLogin(credentials).catch((err) => {
          if (isAxiosError(err)) {
            throw new Error(
              err.response?.data?.message || "Internal Server Error",
            );
          }
        });
      },
      {
        pending: (state) => {
          state.isAuthenticated = false;
        },
        fulfilled: (state) => {
          state.isAuthenticated = true;
          state.loginErrorMessage = undefined;
        },
        rejected: (state, action) => {
          state.isAuthenticated = false;
          state.user = undefined;
          state.loginErrorMessage = action.error.message;
        },
      },
    ),

    me: create.asyncThunk(
      async () => {
        return await api.fetchMe();
      },
      {
        fulfilled: (state, action) => {
          state.user = action.payload;
        },
      },
    ),

    register: create.asyncThunk(
      async (dto: UserRegistrationDto, { rejectWithValue }) => {
        try {
          return await api.fetchRegister(dto);
        } catch (err) {
          if (isAxiosError(err)) {
            return rejectWithValue(err.response?.data);
          }

          return rejectWithValue({
            message: "Unknown error",
          });
        }

        // The value we return becomes the `fulfilled` action payload
      },
      {
        pending: (state) => {
          state.isAuthenticated = false;
        },
        fulfilled: (state, action) => {
          state.isAuthenticated = true;
          state.user = action.payload;
          state.registerFieldErrors = undefined;
        },
        rejected: (state, action) => {
          state.isAuthenticated = false;
          state.user = undefined;

          if (
            action.payload &&
            typeof action.payload === "object" &&
            "message" in action.payload
          ) {
            state.loginErrorMessage = String(action.payload.message);
          } else {
            state.loginErrorMessage = action.error.message;
          }
        },
      },
    ),

    forgotPassword: create.asyncThunk(
      async (email: string, { rejectWithValue }) => {
        try {
          return await api.fetchForgotPassword(email);
        } catch (err) {
          if (isAxiosError(err)) {
            return rejectWithValue(err.response?.data);
          }

          return rejectWithValue({
            message: "Unknown error",
          });
        }
      },
    ),

    resetPassword: create.asyncThunk(
      async (
        data: { token: string; newPassword: string },
        { rejectWithValue },
      ) => {
        try {
          return await api.fetchResetPassword(data);
        } catch (err) {
          if (isAxiosError(err)) {
            return rejectWithValue(err.response?.data);
          }

          return rejectWithValue({
            message: "Unknown error",
          });
        }
      },
      {
        fulfilled: (state) => {
          state.loginErrorMessage = undefined;
        },
        rejected: (state, action) => {
          if (
            action.payload &&
            typeof action.payload === "object" &&
            "message" in action.payload
          ) {
            state.loginErrorMessage = String(action.payload.message);
          } else {
            state.loginErrorMessage = action.error.message;
          }
        },
      },
    ),

    verifyEmail: create.asyncThunk(
      async (code: string, { rejectWithValue }) => {
        try {
          return await api.fetchVerifyEmail(code);
        } catch (err) {
          if (isAxiosError(err)) {
            return rejectWithValue(err.response?.data);
          }

          return rejectWithValue({
            message: "Unknown error",
          });
        }
      },
    ),

    clearAuthErrors: create.reducer((state) => {
      state.loginErrorMessage = undefined;
      state.registerFieldErrors = undefined;
    }),
  }),
  // You can define your selectors here. These selectors receive the slice
  // state as their first argument.
  selectors: {
    selectIsAuthenticated: (state) => state.isAuthenticated,
    selectUser: (state) => state.user,
    selectRole: (state) => state.user?.role,
    selectLoginError: (state) => state?.loginErrorMessage,
    selectRegisterError: (state) => state?.registerFieldErrors,
  },
});

// // Action creators are generated for each case reducer function.
export const {
  login,
  me,
  register,
  forgotPassword,
  resetPassword,
  verifyEmail,
  clearAuthErrors,
} = authSlice.actions;

// Selectors returned by `slice.selectors` take the root state as their first argument.
export const {
  selectIsAuthenticated,
  selectUser,
  selectRole,
  selectLoginError,
  selectRegisterError,
} = authSlice.selectors;
