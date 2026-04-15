import type { SessionCardContentProps } from "./types"

export const SessionCardContent = ({ session }: SessionCardContentProps) => {
    return (
        <div>
            <h3>{session.title}</h3>
            <p>{session.time}</p>
        </div>
    )
}