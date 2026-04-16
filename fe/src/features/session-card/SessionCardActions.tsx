import { Button } from "@/components/ui/button"
import type { SessionCardActionsProps } from "./types"

export const SessionCardActions = ({ session }: SessionCardActionsProps) => {
    const hasNotifications = session.notificationsSent

    return (
        <div>

            {!hasNotifications && (
                <Button> </Button>
            )}
        </div>
    )
}