export const getSessions = async () => {
    const response = await  fetch("/api/events");

    if (!response.ok){
        throw  new Error("Failed to fetch sessions");
    }

    return response.json();
};