import { Card } from "@/components/ui/Card"

export const SessionCard = ({ session, isAuth }) => {
    return (
        <Card>
            <SessionCardContent session={session} />

            {isAuth && (
                <SessionCardActions session={session} />
            )}
        </Card>
    )
}