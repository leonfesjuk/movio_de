import {SessionCard} from "@/features/session-card/SessionCard";
import {Button} from "@/components/ui/button";
import type {Session} from "@/features/session-card/types.ts";
import {Trash2} from "lucide-react";

type Props = {
    session: Session
    onDelete: (id: string) => void;
    onEdit?: (session: Session) => void;
};

export const AdminSessionCard = ({session, onDelete}: Props) => {
    return (
        <div className="relative group hover:scale-[1.01] transition-transform">

            {/* BASE CARD */}
            <SessionCard session={session}
                         isAuth
                         showNotificationStatus
            />

            {/* ADMIN ACTIONS */}

            <div className="absolute top-2 right-2 flex gap-2 z-10">

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
