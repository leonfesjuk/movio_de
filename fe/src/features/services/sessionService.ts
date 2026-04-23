import type {ApiSession, Session} from "@/features/session-card/types";

type ApiResponse = {
    data: {
        items:ApiSession[];
    };
};

export const getSessions = async (): Promise<Session[]> => {
    const response = await  fetch("/api/events");

    if (!response.ok){
        throw  new Error("Failed to fetch sessions");
    }

    const json: ApiResponse = await response.json();

    return json.data.items.map((item) =>({
        id:String(item.id),
        title: item.title || "",
        time: item.datetime,
        city: item.cinema?.cityName?.toLowerCase() || "",
        notificationsSent: false,
        imageUrl: item.imageUrl,
        externalUrl: item.seanceLink,
    }));
};