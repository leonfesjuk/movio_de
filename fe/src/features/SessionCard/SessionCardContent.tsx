export const SessionCardContent = ({ session }) => {
    return (
        <div>
            <h3>{session.title}</h3>
            <p>{session.time}</p>
        </div>
    )
}