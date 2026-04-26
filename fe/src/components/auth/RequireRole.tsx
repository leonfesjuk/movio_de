import { Navigate } from "react-router-dom";
import { useAppSelector } from "@/app/hooks";
import { selectIsAuthenticated, selectUser } from "@/features/auth/slice/authSlice";
import type { JSX } from "react";

interface RequireRoleProps {
  children: JSX.Element;
  role: string;
}

export default function RequireRole({ children, role }: RequireRoleProps) {
  const isAuthenticated = useAppSelector(selectIsAuthenticated);
  const user = useAppSelector(selectUser);

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (user?.role !== role) {
    return <Navigate to="/" replace />;
  }

  return children;
}