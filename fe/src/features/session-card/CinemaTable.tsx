import type { Session } from "@/features/session-card/types";

type Props = {
    sessions: Session[];
    onEdit: (session: Session) => void;
    onDelete: (id: string) => void;
};

export const SessionTable = ({ sessions, onEdit, onDelete }: Props) => {
    return (
        <div className="overflow-x-auto mb-10">
            <table className="min-w-full border border-gray-300">
                <thead className="bg-gray-100">
                <tr>
                    <th className="p-2">#</th>
                    <th className="p-2">Title</th>
                    <th className="p-2">City</th>
                    <th className="p-2">Date</th>
                    <th className="p-2">Time</th>
                    <th className="p-2">Status</th>
                    <th className="p-2">Actions</th>
                </tr>
                </thead>

                <tbody>
                {sessions.map((session, index) => (
                    <tr key={session.id} className="border-t">
                        <td className="p-2">{index + 1}</td>
                        <td className="p-2">{session.title}</td>
                        <td className="p-2">{session.city}</td>
                        <td className="p-2">{session.date}</td>
                        <td className="p-2">{session.time}</td>
                        <td className="p-2">
                            {session.notificationsSent ? "Sent" : "Not sent"}
                        </td>
                        <td className="p-2 flex gap-2">
                            <button
                                onClick={() => onEdit(session)}
                                className="px-2 py-1 bg-blue-500 text-white rounded"
                            >
                                Edit
                            </button>

                            <button
                                onClick={() => onDelete(session.id)}
                                className="px-2 py-1 bg-red-500 text-white rounded"
                            >
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};