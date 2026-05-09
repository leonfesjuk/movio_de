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
            ? dateObj.toTimeString().slice(0,5)
        : "";

        return {
            id: String(item.id),
            title: item.title || "",
            date,
            time,
            datetime: item.datetime,
            city: item.cinema?.cityName?.toLowerCase() || "",
            description: item.description || "",
            imageUrl: item.imageUrl,
            externalUrl: item.seanceLink,
            cinemaId: item.cinemaId,
            notificationsSent: false,
            timeFlags: item.timeFlags || {
                timeFlag1: false,
                timeFlag2: false,
                timeFlag3: false,
            },
        };
    });
};

export const createSession = async (session: Partial<ApiSession>)=>{
    const token = localStorage.getItem("accessToken");

    const  response = await fetch("/api/events", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify(session),
    });

    if (!response.ok){
        throw new Error("Failed to create session");
    }

    return await  response.json();
};

export const deleteSession = async (id:string)=>{
    const token = localStorage.getItem("accessToken");

    const response = await  fetch(`/api/events/${id}`,{
        method: "DELETE",
        headers:{
            "Authorization":  `Bearer ${token}`,
        },
    });

    if (!response.ok){
        throw new Error("Failed to delete session");
    }
};

export  const updateSession = async (id: string, session: Partial<ApiSession>)=>{
    const token = localStorage.getItem("accessToken");

    const  response = await  fetch(`/api/events/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type" : "application/json",
            "Authorization":  `Bearer ${token}`,
        },
        body: JSON.stringify(session),
    });

    if (!response.ok){
        throw new Error("Failed to update session");
    }

    return await  response.json();
};
