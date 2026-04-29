import { useEffect, useState } from "react";
import type { Session } from "@/features/session-card/types";
import {AdminSessionCard} from "@/features/admin-session-card/adminSessionCard";
import {
    getSessions,
    deleteSession,
    createSession,
    updateSession,
} from "@/features/services/sessionService";

export default function AdminPage() {
    const [sessions, setSessions] = useState<Session[]>([]);

    const [showForm, setShowForm] = useState(false);
    const [editingId, setEditingId] = useState<string | null>(null);

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
                    { id: "1",
                      title: "Avatar",
                      time: "2026-04-21T18:00:00",
                      date: "2026-04-21T18:00:00",
                      city: "berlin",
                      notificationsSent: false },
                    { id: "2",
                        title: "Batman",
                        time: "2026-04-21T19:40:00",
                        date: "2026-04-21T19:40:00",
                        city: "dresden",
                        notificationsSent: false },
                ]);
            }
        };

        void fetchData();
    }, []);

    const handleDelete = async (id:string)=>{
        try {
            await deleteSession(id);
            setSessions((prev)=> prev.filter((s)=> s.id !== id));
        }catch (e){
            console.error(e);
            alert("Delete failed");
        }
    };

    const resetForm = () => {
        setTitle("");
        setTime("");
        setCity("");
        setEditingId(null);
        setShowForm(false);
    };

    const handleCreateOrEdit = async () => {
        if (!title || !time || !city) {
            alert("Please fill all fields");
            return;
        }

        try {
            if (editingId) {
                await updateSession(editingId, {
                    title,
                    datetime: time,
                    cinema: {
                        cityName: city,
                    },
                });
            } else {
                await createSession({
                    title,
                    datetime: time,
                    cinema: {
                        cityName: city,
                    },
                });
            }
            const data = await getSessions();
            setSessions(data);

            resetForm();

        } catch (e){
            console.error(e);
            alert("Save failed");
        }
    };

    const handleEdit = (session: Session) => {
        setTitle(session.title);
        setTime(`${session.date}T${session.time}`);
        setCity(session.city);
        setEditingId(session.id);
        setShowForm(true);
    };

    return (
        <div className="p-10">
            <h1 className="text-2xl font-bold mb-6">Admin Panel</h1>

            <button
                onClick={() => {
                    setShowForm((prev) => !prev);
                    setEditingId(null);
                }}
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
                            onClick={handleCreateOrEdit}
                            className="px-3 py-1 bg-blue-500 text-white rounded"
                        >
                            {editingId ? "Update" : "Save"}
                        </button>

                        <button
                            onClick={resetForm}
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
 features/adminSessionCard
                <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">

                            {sessions.map((session) => (
                                <AdminSessionCard
                                    key={session.id}
                                    session={session}
                                    onEdit={handleEdit}
                                    onDelete={handleDelete}
                                />
                            ))}

                </div>
            )}
        </div>
    );
}
