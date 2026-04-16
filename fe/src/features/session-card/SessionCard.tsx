import {Card, CardContent} from "@/components/ui/card.tsx"
import {SessionCardContent} from "@/features/session-card/SessionCardContent.tsx";
import {SessionCardActions} from "@/features/session-card/SessionCardActions.tsx";
import type {SessionCardProps} from "@/features/session-card/types.ts";



export const SessionCard = ({ session, isAuth }: SessionCardProps) => {
    return (
        <Card>
            <CardContent>
            <SessionCardContent session={session} />
                </CardContent>

            {isAuth && (
                <SessionCardActions session={session} />
            )}
        </Card>
    );
};