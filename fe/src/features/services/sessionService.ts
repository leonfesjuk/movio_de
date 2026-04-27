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

    if (!json.items){
        return [];
    }

    return json.items.map((item: ApiSession) =>{
        const dateObj = new Date(item.datetime || "");

        const date = dateObj.toISOString().split("T")[0];
        const time = dateObj.toISOString().slice(11,16);

        return{
            id:String(item.id),
            title: item.title || "",
            date,
            time,
            city: item.cinema?.cityName?.toLowerCase() || "",
            notificationsSent: false,
            imageUrl: item.imageUrl,
            externalUrl: item.seanceLink,
        };
    });
};