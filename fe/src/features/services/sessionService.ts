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
        const dateObj = item.datetime ? new Date(item.datetime) : null;

        const date = dateObj
            ? dateObj.toISOString().split("T")[0]
            : "";

        const time = dateObj
            ? dateObj.toISOString().slice(11,16)
        : "";

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

export const createSession = async (session: Partial<ApiSession>)=>{
    const  response = await fetch("/api/events", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(session),
    });

    if (!response.ok){
        throw new Error("Failed to create session");
    }

    return await  response.json();
};

export const deleteSession = async (id:string)=>{
    const response = await  fetch(`/api/events/${id}`,{
        method: "DELETE",
    });

    if (!response.ok){
        throw new Error("Failed to delete session");
    }
};

export  const updateSession = async (id: string, session: Partial<ApiSession>)=>{
    const  response = await  fetch(`/api/events/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type" : "application/json",
        },
        body: JSON.stringify(session),
    });

    if (!response.ok){
        throw new Error("Failed to update session");
    }

    return await  response.json();
};
