import { Card } from "@/components/ui/card.tsx"
import {SessionCardContent} from "@/features/SessionCard/SessionCardContent.tsx";
import {SessionCardActions} from "@/features/SessionCard/SessionCardActions.tsx";
import type {SessionCardProps} from "@/features/SessionCard/types.ts";



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