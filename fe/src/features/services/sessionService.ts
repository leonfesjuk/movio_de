import type {ApiSession, Session} from "@/features/session-card/types";

type ApiResponse = {
    items: ApiSession[];
    pagination:{
        hasMore: boolean;
    };
};

export const getSessions = async (): Promise<Session[]> => {
    const response = await  fetch("/api/events");

    if (!response.ok){
        throw  new Error("Failed to fetch sessions");
    }

    const json: ApiResponse = await response.json();

    return json.items.map((item: ApiSession) =>({
        id:String(item.id),
        title: item.title || "",
        time: item.datetime,
        date: item.datetime,
        city: item.cinema?.cityName?.toLowerCase() || "",
        notificationsSent: false,
        imageUrl: item.imageUrl,
        externalUrl: item.seanceLink,
    }));
};