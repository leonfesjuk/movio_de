import { createAppSlice } from "../../../app/createAppSlice";
import type {
  AuthResponse,
  AuthSliceState,
  Credentials,
  User,
  UserRegistrationDto,
} from "../types";
import * as api from "../services/api";
import { isAxiosError } from "axios";

function checkToken() {
  const token = localStorage.getItem("accessToken");
  return !!token; // returns true if the token exists
}

const initialState: AuthSliceState = {
  isAuthenticated: checkToken(),
  user: undefined,
  loginErrorMessage: undefined,
  registerFieldErrors: undefined,
};

export const authSlice = createAppSlice({
  name: "auth",
  initialState,
  reducers: (create) => ({
    login: create.asyncThunk(
      async (credentials: Credentials, { dispatch }) => {
        const response = (await api.fetchLogin(credentials)) as AuthResponse;

        // Save tokens on successful login
        if (response.accessToken) {
          localStorage.setItem("accessToken", response.accessToken);
        }
        if (response.refreshToken) {
          localStorage.setItem("refreshToken", response.refreshToken);
        }
        // Save the authentication flag
        localStorage.setItem("is_authenticated", "true");

        await dispatch(me());
        return response;
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

    checkAuth: create.asyncThunk(
      async () => {
        const token = localStorage.getItem("accessToken");
        if (!token) {
          throw new Error("No token found");
        }
        // We just check that the token is valid.
        await api.fetchMe();
        return { success: true };
      },
      {
        pending: () => {
        },
        fulfilled: (state) => {
          state.isAuthenticated = true;
          localStorage.setItem("is_authenticated", "true");
        },
        rejected: (state) => {
          state.isAuthenticated = false;
          state.user = undefined;
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
          localStorage.removeItem("is_authenticated");
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

    updateProfile: create.asyncThunk(
      async (dto: Partial<User>) => {
        return api.fetchUpdateProfile(dto);
      },
      {
        fulfilled: (state, action) => {
          state.user = action.payload;
        }
      },
    ),

    clearAuthErrors: create.reducer((state) => {
      state.loginErrorMessage = undefined;
      state.registerFieldErrors = undefined;
    }),

    logout: create.asyncThunk(
      async () => {
        try {
          await api.fetchLogout();
        } finally {
          // Always remove tokens on logout
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
          localStorage.removeItem("is_authenticated");
        }
      },
      {
        fulfilled: (state) => {
          state.isAuthenticated = false;
          state.user = undefined;
          state.loginErrorMessage = undefined;
        },
      },
    ),
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
  checkAuth,
  me,
  register,
  forgotPassword,
  resetPassword,
  verifyEmail,
  clearAuthErrors,
  updateProfile,
  logout,
} = authSlice.actions;

// Selectors returned by `slice.selectors` take the root state as their first argument.
export const {
  selectIsAuthenticated,
  selectUser,
  selectRole,
  selectLoginError,
  selectRegisterError,
} = authSlice.selectors;
