import { useAppSelector } from "@/app/hooks";
import type { AppDispatch, RootState } from "@/app/store";
import { logout, selectUser } from "@/features/auth/slice/authSlice";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";

export default function Header() {
  const user = useAppSelector(selectUser);
  const isAdmin = user?.role === "ROLE_ADMIN";
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  const isAuthenticated: boolean = useSelector(
    (state: RootState) => state.auth.isAuthenticated,
  );

  const handleLogout = () => {
    dispatch(logout());
    navigate("/");
  };

  return (
    <header className="w-full border-b bg-white shadow-sm">
      <div className="mx-auto flex max-w-7xl items-center justify-between px-4 py-4">
        {/* Logo / Brand */}
        <Link to="/" className="flex items-center gap-2">
          <img src="/movio_logo_2.png" alt="MOVIO" className="h-8 w-auto" />
        </Link>

        {/* Navigation Links */}
        <nav className="flex items-center space-x-4">
          <Link
            to="/"
            className="text-sm font-medium text-gray-600 hover:text-black transition-colors"
          >
            Home
          </Link>
          <Link
            to="/about"
            className="text-sm font-medium text-gray-600 hover:text-black transition-colors"
          >
            About
          </Link>
          {isAuthenticated ? (
            <>
              <Link
                to="/dashboard"
                className="text-sm font-medium text-gray-600 hover:text-black transition-colors"
              >
                Dashboard
              </Link>
              <Link
                to="/profile"
                className="text-sm font-medium text-gray-600 hover:text-black transition-colors"
              >
                Profile
              </Link>
              {isAdmin && (
                <Link
                  to="/invite-tokens"
                  className="text-sm font-medium text-gray-600 hover:text-black transition-colors"
                >
                  Invite tokens
                </Link>
              )}
              <button
                onClick={handleLogout}
                className="rounded bg-black px-4 py-1.5 text-sm font-medium text-white hover:bg-gray-800 transition"
              >
                Logout
              </button>
            </>
          ) : (
            <>
              <Link
                to="/register"
                className="rounded border border-gray-300 px-4 py-1.5 text-sm font-medium text-gray-700 hover:border-gray-500 hover:text-black transition"
              >
                Register
              </Link>
              <Link
                to="/login"
                className="rounded bg-black px-4 py-1.5 text-sm font-medium text-white hover:bg-gray-800 transition"
              >
                Login
              </Link>
            </>
          )}
        </nav>
      </div>
    </header>
  );
}