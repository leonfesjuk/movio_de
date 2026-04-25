import { useAppSelector } from "@/app/hooks";
import { selectUser } from "@/features/auth/slice/authSlice";
import { Link } from "react-router-dom";

export default function Header() {
  const user = useAppSelector(selectUser);
  const isAdmin = user?.role === "ROLE_ADMIN";

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
          {isAdmin && (
            <Link
              to="/invite-tokens"
              className="text-sm font-medium text-gray-600 hover:text-black transition-colors"
            >
              Invite tokens
            </Link>
          )}
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
        </nav>
      </div>
    </header>
  );
}
