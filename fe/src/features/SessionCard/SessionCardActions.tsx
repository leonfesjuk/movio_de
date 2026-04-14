import { Button } from "@/components/ui/Button"
import { Badge } from "@/components/ui/Badge"

export const SessionCardActions = ({ session }) => {
    const hasNotifications = session.notificationsSent

    return (
        <div>
            <Badge>
                {hasNotifications ? "Отправлено" : "Не отправлено"}
            </Badge>

            {!hasNotifications && (
                <Button>Удалить</Button>
            )}
        </div>
    )
}