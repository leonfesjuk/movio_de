import { useEffect, useState } from "react";
import { getSessions } from "@/features/services/sessionService";
import type { Session } from "@/features/session-card/types";

export default function AdminPage() {
    const [sessions, setSessions] = useState<Session[]>([]);

    const [showForm, setShowForm] = useState(false);
    const [title, setTitle] = useState("");
    const [time, setTime] = useState("");
    const [city, setCity] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getSessions();
                setSessions(data);
            } catch (e) {
                console.error(e);

                setSessions([
                    { id: "1", title: "Avatar", time: "2026-04-21T18:00:00", city: "berlin", notificationsSent: false },
                    { id: "2", title: "Batman", time: "2026-04-22T19:40:00", city: "dresden", notificationsSent: false },
                ]);
            }
        };

        void fetchData();
    }, []);

    const handleDelete = (id: string) => {
        setSessions((prev) => prev.filter((session) => session.id !== id));
    };

    const handleCreate = () => {
        if (!title || !time || !city) {
            alert("Please fill all fields");
            return;
        }

        const newSession: Session = {
            id: Date.now().toString(),
            title,
            time,
            city,
            notificationsSent: false,
        };

        setSessions((prev) => [newSession, ...prev]);

        // очистка
        setTitle("");
        setTime("");
        setCity("");
        setShowForm(false);
    };

    return (
        <div className="p-10">
            <h1 className="text-2xl font-bold mb-6">Admin Panel</h1>

            <button
                onClick={() => setShowForm((prev) => !prev)}
                className="mb-4 px-4 py-2 bg-green-600 text-white rounded"
            >
                Add session
            </button>

            {showForm && (
                <div className="mb-6 border p-4 rounded-lg space-y-2">
                    <input
                        placeholder="Title"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        className="border p-2 w-full"
                    />

                    <input
                        type="datetime-local"
                        value={time}
                        onChange={(e) => setTime(e.target.value)}
                        className="border p-2 w-full"
                    />

                    <input
                        placeholder="City"
                        value={city}
                        onChange={(e) => setCity(e.target.value)}
                        className="border p-2 w-full"
                    />

                    <div className="flex gap-2">
                        <button
                            onClick={handleCreate}
                            className="px-3 py-1 bg-blue-500 text-white rounded"
                        >
                            Save
                        </button>

                        <button
                            onClick={() => {
                                setShowForm(false);
                                setTitle("");
                                setTime("");
                                setCity("");
                            }}
                            className="px-3 py-1 bg-gray-300 rounded"
                        >
                            Cancel
                        </button>
                    </div>
                </div>
            )}

            {sessions.length === 0 ? (
                <div className="text-gray-500">No sessions</div>
            ) : (
                <div className="space-y-4">
                    {sessions.map((session) => (
                        <div
                            key={session.id}
                            className="border p-4 rounded-lg flex justify-between items-center"
                        >
                            <div>
                                <div className="font-semibold">{session.title}</div>
                                <div className="text-sm text-gray-500">
                                    {session.city} — {new Date(session.time).toLocaleString()}
                                </div>
                            </div>

                            <div className="flex gap-2">
                                <button className="px-3 py-1 bg-blue-500 text-white rounded">
                                    Edit
                                </button>

                                <button
                                    onClick={() => handleDelete(session.id)}
                                    className="px-3 py-1 bg-red-500 text-white rounded"
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