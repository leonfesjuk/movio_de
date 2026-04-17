import FilterBar from "@/components/common/FilterBar";
import {SessionCard} from "@/features/session-card/SessionCard";
import {useEffect, useState} from "react";
import { getSessions } from "@/features/services/sessionService";

type Session ={
    id:string;
    title:string;
    time:string;
    notificationsSent: boolean;
};

export default function Home() {

    const [sessions, setSessions] = useState<Session[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const [search, setSearch] = useState("");
    const [status, setStatus] = useState("");


    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getSessions();
                setSessions(data);
            } catch (e) {
                console.error(e);

                setSessions([
                    {id: "1", title: "Avatar", time: "18:00", notificationsSent: false},
                    {id: "2", title: "Batman", time: "19:40", notificationsSent: true},
                ]);
                setError("Failed to load sessions");
            } finally {
                setLoading(false);
            }
        };

        void fetchData()
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
        return matchesSearch && matchesStatus;
    });

    return (
        <div className="p-10">

            <h1 className="text-2xl font-bold mb-6">
                Movie Sessions
            </h1>

            {error &&(
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