import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import type { SessionCardActionsProps } from "./types"

export const SessionCardActions = ({ session }: SessionCardActionsProps) => {
    const hasNotifications = session.notificationsSent

    return (
        <div>
            <Badge>
                {hasNotifications ? "Send" : "Not send"}
            </Badge>

            {!hasNotifications && (
                <Button>Delete</Button>
            )}
        </div>
    )
}