import FilterBar from "@/components/common/FilterBar";
import { SessionCard } from "@/features/session-card/SessionCard";
import { useEffect, useState } from "react";
import { getSessions } from "@/features/services/sessionService";
import type {Session} from "@/features/session-card/types"

export default function Home() {
    const [sessions, setSessions] = useState<Session[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const [search, setSearch] = useState("");
    const [status, setStatus] = useState("");
    const [city, setCity] = useState("");
    const [date, setDate] = useState("");


    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getSessions();
                setSessions(data);
            } catch (e) {
                console.error(e);

                // fallback
                setSessions([
                    { id: "1",
                        title: "Avatar",
                        time: "18:00",
                        date: "2026-04-21",
                        city: "berlin",
                        notificationsSent: false },
                    { id: "2",
                        title: "Batman",
                        time: "18:00",
                        date: "2026-04-29",
                        city: "dresden",
                        notificationsSent: false },
                ]);

                setError("Failed to load sessions");
            } finally {
                setLoading(false);
            }
        };

        void fetchData();
    }, []);

    if (loading) {
        return <div className="p-10">Loading...</div>;
    }

    const filteredSessions = sessions.filter((session) => {
        const matchesSearch = session.title
            .toLowerCase()
            .includes(search.toLowerCase());

        const matchesStatus =
            status === ""
                ? true
                : status === "active"
                ? !session.notificationsSent
                : session.notificationsSent;

        const matchesCity =
            city === ""
                ? true
                : session.city.toLowerCase() === city.toLowerCase();

        const matchesDate =
            date === ""
                ? true
                : session.time.startsWith(date);

        return matchesSearch && matchesStatus && matchesCity && matchesDate;
    });

    return (
        <div className="p-10">
            <h1 className="text-2xl font-bold mb-6">
                Movie Sessions
            </h1>

            {error && (
                <div className="text-red-500 mb-4">
                    {error}
                </div>
            )}

            <div className="mb-6">
                <FilterBar
                    search={search}
                    setSearch={setSearch}
                    status={status}
                    setStatus={setStatus}
                    city={city}
                    setCity={setCity}
                    date={date}
                    setDate={setDate}
                />
            </div>

            {filteredSessions.length === 0 ? (
                <div className="text-gray-500">
                    No sessions found
                </div>
            ) : (
                <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                    {filteredSessions.map((session) => (
                        <SessionCard
                            key={session.id}
                            session={session}
                            isAuth={true}
                        />
                    ))}
                </div>
            )}
        </div>
    );
}
