export type Session = {
    id: string
    title: string
    time: string
    city: string
    notificationsSent: boolean
    imageUrl?: string;
    externalUrl?: string;
}

export type SessionCardActionsProps = {
    session: Session
}

export type SessionCardContentProps = {
    session: Session
}

export type SessionCardProps = {
    session: Session;
    isAuth: boolean;
};

export type ApiSession = {
    id: number;
    title:string;
    datetime: string;
    imageUrl?: string;
    seanceLink?: string;
    cinema: {
        cityName:string;
    }
};
