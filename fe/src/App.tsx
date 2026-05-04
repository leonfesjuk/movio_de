import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import About from "./pages/About";
import Layout from "./layouts/Layout";
import Registration from "./pages/Registration";
import Login from "./pages/Login";
import Projects from "./pages/Projects";
import TestUI from "@/pages/TestUI";
import EmailConfirmationPage from "./pages/EmailConfirmationPage";
import ResetPasswordPage from "./pages/ResetPasswordPage";
import NewPasswordConfirmationPage from "./pages/NewPasswordConfirmationPage";
import VerifyEmailPage from "./pages/VerifyEmailPage";
import ForgotPasswordPage from "./pages/ForgotPasswordPage";
import EmailConfirmationPasswordPage from "./pages/EmailConfirmationPasswordPage";
import RequireRole from "./components/auth/RequireRole";
import AdminPage from "@/pages/AdminPage";
import Profile from "./pages/Profile";
import ChangePassword from "./pages/ChangePassword";

import { useAppDispatch } from "./app/hooks";
import type { AppDispatch, RootState } from "./app/store";
import { useSelector } from "react-redux";
import { useEffect } from "react";
import { checkAuth, me } from "./features/auth/slice/authSlice";

const AUTH_STORAGE_KEY = "is_authenticated";

function App() {
  const dispatch = useAppDispatch<AppDispatch>();
  const isAuthenticated: boolean = useSelector(
      (state: RootState) => state.auth.isAuthenticated,
  );

  useEffect(() => {
    localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(isAuthenticated));
  }, [isAuthenticated]);

  useEffect(() => {
    const initAuth = async () => {
      const result = await dispatch(checkAuth());

      if (checkAuth.fulfilled.match(result)) {
        await dispatch(me());
      }
    };

    void initAuth();
  }, [dispatch]);

  return (
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="about" element={<About />} />
          <Route path="register" element={<Registration />} />
          <Route path="login" element={<Login />} />
          <Route path="projects" element={<Projects />} />
          <Route path="test-ui" element={<TestUI />} />
          <Route path="check-email" element={<EmailConfirmationPage />} />
          <Route path="verify-email" element={<VerifyEmailPage />} />
          <Route path="forgot-password" element={<ForgotPasswordPage />} />

          <Route
              path="check-email-password"
              element={<EmailConfirmationPasswordPage />}
          />

          <Route path="auth/reset-password" element={<ResetPasswordPage />} />

          <Route
              path="auth/confirm-new-password"
              element={<NewPasswordConfirmationPage />}
          />

          <Route
              path="admin"
              element={
                <RequireRole role="ROLE_ADMIN">
                  <AdminPage />
                </RequireRole>
              }
          />


          <Route path="profile" element={<Profile />} />
          <Route path="profile/change-password" element={<ChangePassword />} />
        </Route>
      </Routes>
  );
}

export default App;