export const getSessions = async () => {
    const response = await  fetch("/api/events");

    if (!response.ok){
        throw  new Error("Failed to fetch sessions");
    }

    const json = await response.json();
    return json.data.items;
};