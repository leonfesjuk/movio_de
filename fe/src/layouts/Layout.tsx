import { Outlet } from "react-router-dom";
import Header from "../components/Header";

export default function Layout() {
    return (
        <div className="min-h-screen flex flex-col bg-gray-50 text-gray-900">
            <Header />

            <main className="flex-1 w-full max-w-7xl mx-auto px-4 py-6">
                <Outlet />
            </main>

            <footer className="w-full border-t py-4 text-sm text-center text-gray-500">
                &copy; {new Date().getFullYear()} Tracker App. All rights reserved.
            </footer>
        </div>
    );
}