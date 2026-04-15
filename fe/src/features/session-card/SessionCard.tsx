import { Card } from "@/components/ui/card.tsx"
import {SessionCardContent} from "@/features/session-card/SessionCardContent.tsx";
import {SessionCardActions} from "@/features/session-card/SessionCardActions.tsx";
import type {SessionCardProps} from "@/features/session-card/types.ts";



export const SessionCard = ({ session, isAuth }: SessionCardProps) => {
    return (
        <Card>
            <SessionCardContent session={session} />

            {isAuth && (
                <SessionCardActions session={session} />
            )}
        </Card>
    );
};