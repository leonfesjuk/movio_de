export type Session = {
    id: string;
    title: string;
    city: string;
    date: string;
    time: string;
    datetime?: string;
    description?: string;
    imageUrl?: string;
    externalUrl?: string;
    cinemaId?: string;
    notificationsSent: boolean;
    timeFlags?: {
        timeFlag1: boolean;
        timeFlag2: boolean;
        timeFlag3: boolean;
    };
};

export type SessionCardActionsProps = {
    session: Session
}

export type SessionCardContentProps = {
    session: Session
}

export type SessionCardProps = {
    session: Session;
    isAuth: boolean;
    showNotificationStatus?: boolean;
};

export type ApiSession = {
    id: number;
    title: string;
    description?: string;
    imageUrl?: string;
    seanceLink?: string;
    datetime: string;

    cinemaId?: string;

    timeFlags?: {
        timeFlag1: boolean;
        timeFlag2: boolean;
        timeFlag3: boolean;
    };

    cinema: {
        cityName: string;
    };
};
