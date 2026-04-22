import {useEffect, useState} from "react";
import {getSessions} from "@/features/services/sessionService";

type Session = {
    id: string;
    title: string;
    time: string;
    city: string;
};

export default function AdminPage(){
    const [sessions, setSessions] = useState<Session[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getSessions();
                setSessions(data);
            } catch (e) {
                console.error(e);

                setSessions([
                    { id: "1", title: "Avatar", time: "2026-04-21T18:00:00", city: "berlin"},
                    { id: "2", title: "Batman", time: "2026-04-22T19:40:00", city: "dresden"},
                ]);
            }
        };

        void fetchData();
    }, []);

    const handleDelete = (id: string) => {
        setSessions((prev) => prev.filter((session) => session.id !== id));
    };

    return (
        <div className="p-10">
            <h1 className="text-2xl font-bold mb-6">Admin Panel</h1>

            {sessions.length === 0 ? (
                <div className="text-gray-500">No sessions</div>
            ) : (
            <div className="space-y-4">
                {sessions.map((session) =>(
                    <div
                    key={session.id}
                    className="border p-4 rounded-lg flex justify-between items-center"
                    >
                        <div>
                            <div className="font-semibold">{session.title}</div>
                            <div className="text-sm text-gray-500">
                                {session.city} — {session.time}
                            </div>
                        </div>

                        <div className="flex gap-2">
                            <button className="px-3 py-1 bg-blue-500 text-white rounded">
                                Edit
                            </button>
                            <button
                                onClick={() => handleDelete(session.id)}
                                className="px-3 py-1  bg-red-500 text-white rounded"
                            >
                                Delete
                            </button>
                        </div>
                    </div>
                ))}
            </div>
                )}
        </div>
    );
}