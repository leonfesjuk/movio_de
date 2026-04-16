
import type {SessionCardProps} from "@/features/session-card/types.ts";
import {
    Card,
    CardAction,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle
} from "@/components/ui/card.tsx";



export const SessionCard = ({ session, isAuth }: SessionCardProps) => {
    return (
        <Card className="overflow-hidden">

            {/* IMAGE */}
            <img
                src={session.imageUrl ?? "/placeholder.jpg"}
                alt={session.title}
                className="w-full aspect-video object-cover"
            />

            {/* HEADER */}
            <CardHeader>
                <CardTitle>{session.title}</CardTitle>
                <CardDescription>
                    {session.time}
                </CardDescription>

                <CardAction>
                </CardAction>
            </CardHeader>

            {/* CONTENT  */}
            <CardContent>
                <div className="text-sm text-muted-foreground">
                    INFO
                </div>
            </CardContent>

            {/* FOOTER */}
            {isAuth && (
                <CardFooter className="flex justify-between">


                    <a
                        href={session.externalUrl}
                        target="_blank"
                        rel="noreferrer"
                        className="block w-full bg-blue-500 text-white text-center py-3 hover:bg-blue-600 transition"
                    >
                        LINK
                    </a>
                </CardFooter>
            )}
        </Card>
    );
};