import type {SessionCardProps} from "@/features/session-card/types.ts";
import {ExternalLink} from "lucide-react";
import {Button} from "@/components/ui/button";
import {
    Card,
    CardAction,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle
} from "@/components/ui/card.tsx";


export const SessionCard = ({session, isAuth}: SessionCardProps) => {
    return (
        <Card className="overflow-hidden flex flex-col h-full">

            {/* IMAGE */}
            <img
                src={session.imageUrl ?? "/placeholder.jpg"}
                alt={session.title}
                className="w-full aspect-video object-cover"
            />

            {/* HEADER */}
            <CardHeader className="space-y-2">
                <CardTitle className="text-lg leading-tight">{session.title}</CardTitle>
                <div className="text-sm text-muted-foreground">
                    {session.city}
                </div>

                <CardDescription>
                    <div className="flex justify-between">
                        <span>{session.date}</span>
                        <span>{session.time}</span>
                    </div>
                </CardDescription>

                <CardAction>
                </CardAction>
            </CardHeader>

            {/* CONTENT  */}
            <CardContent>
                <p className="text-sm font-medium text-foreground">
                    Description:
                </p>
                <div className="text-sm text-muted-foreground">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed
                    cursus ante dapibus diam. Sed nisi.
                </div>
            </CardContent>

            {/* FOOTER */}
            {isAuth && (
                <CardFooter className="p-0 mt-auto">
                    <Button
                        asChild
                        className="w-full justify-between rounded-none bg-blue-400 text-blue-50 hover:bg-blue-100">


                        <a
                            href={session.externalUrl}
                            target="_blank"
                            rel="noreferrer"
                        >
                            <span>TO POSTER</span>
                            <ExternalLink size={16}/>
                        </a>
                    </Button>
                </CardFooter>

            )}
        </Card>
    );
};