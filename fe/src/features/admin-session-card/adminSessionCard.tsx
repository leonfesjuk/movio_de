import {SessionCard} from "@/features/session-card/SessionCard";
import {Button} from "@/components/ui/button";
import type {Session} from "@/features/session-card/types.ts";
import {Pencil, Trash2} from "lucide-react";

type Props = {
    session: Session
    onDelete: (id: string) => void;
    onEdit?: (session: Session) => void;
};

export const AdminSessionCard = ({session, onDelete, onEdit}: Props) => {
    return (
        <div className="relative group hover:scale-[1.01] transition-transform">

            {/* BASE CARD */}
            <SessionCard session={session} isAuth/>

            {/* ADMIN ACTIONS */}

            <div className="absolute top-2 right-2 flex gap-2 z-10">
                {onEdit && (
                    <Button
                        size="icon"
                        variant="secondary"
                        onClick={() => onEdit(session)}
                    >
                        <Pencil size={16}/>
                    </Button>
                )}

                <Button
                    size="icon"
                    variant="destructive"
                    className="bg-red-600 hover:bg-red-700 text-white"
                    onClick={() => onDelete(session.id)}
                >
                    <Trash2 size={16}/>
                </Button>
            </div>
        </div>
    );
};
