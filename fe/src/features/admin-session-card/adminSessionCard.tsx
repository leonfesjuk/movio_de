import { SessionCard } from "@/features/session-card/SessionCard";
import { Button } from "@/components/ui/button";
import type { Session } from "@/features/session-card/types.ts";

type Props = {
    session: Session
    onDelete: (id: string) => void
}

export const AdminSessionCard = ({ session, onDelete }: Props) => {
    return (
        <div className="relative">

            {/* BASE CARD */}
            <SessionCard session={session} isAuth />

            {/* ADMIN ACTIONS */}
            <div className="absolute top-2 right-2 flex gap-2 z-10">
                <Button
                    size="sm"
                    variant="destructive"
                    onClick={() => onDelete(session.id)}
                >
                    Delete
                </Button>
            </div>
        </div>
    );
};
