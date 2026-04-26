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
import InviteTokensPage from "./pages/InviteTokensPage";
import RequireRole from "./components/auth/RequireRole";
import AdminPage from "@/pages/AdminPage.tsx";

function App() {
  return (
    <div>
      <nav></nav>
      <Layout>
        <Routes>
          <Route index element={<Home />} />
          <Route path="/about" element={<About />} />
          <Route path="/register" element={<Registration />} />
          <Route path="/login" element={<Login />} />
          <Route path="/projects" element={<Projects />} />
          <Route path="/test-ui" element={<TestUI />} />
          <Route path="/check-email" element={<EmailConfirmationPage />} />
          <Route path="/verify-email" element={<VerifyEmailPage />} />
          <Route path="/forgot-password" element={<ForgotPasswordPage />} />
          <Route
            path="/check-email-password"
            element={<EmailConfirmationPasswordPage />}
          />
          <Route path="/auth/reset-password" element={<ResetPasswordPage />} />
          <Route
            path="/auth/confirm-new-password"
            element={<NewPasswordConfirmationPage />}
          />
          <Route
            path="/invite-tokens"
            element={
              <RequireRole role="ROLE_ADMIN">
                <InviteTokensPage />
              </RequireRole>
            }
          />
          <Route path="/admin" element={<AdminPage/>}/>
        </Routes>
      </Layout>
    </div>
  );
}

export default App;
