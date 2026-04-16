import { useState } from "react";
import FilterBar from "@/components/common/FilterBar";
import {SessionCard} from "@/features/SessionCard/SessionCard";


export default function Home() {

    const [search, setSearch] = useState("");
    const [status, setStatus] = useState("");

    const sessions = [
        {id: "1", title: "Avatar", time: "18:00", notificationsSent: false},
        {id: "2", title: "Batman", time: "19:30", notificationsSent: true},
        {id: "3", title: "Avatar", time: "20:40", notificationsSent: false},
    ];

    const filteredSessions = sessions.filter((session) => {
        const  matchesSearch = session.title
            .toLowerCase()
            .includes(search.toLowerCase());

        const matchesStatus =
            status === ""
                ?true
                :status === "active"
                ? !session.notificationsSent
                :session.notificationsSent;
        return matchesSearch && matchesStatus;
    });

  return(
      <div className="p-10">

        <h1 className="text-2xl font-bold mb-6">
          Movie Sessions
        </h1>

          <div className="mb-6">
              <FilterBar
              search={search}
              setSearch={setSearch}
              status={status}
              setStatus={setStatus}
              />
          </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 ">
            {filteredSessions.map((session) => (
            <SessionCard
                key={session.id}
                session={session}
                isAuth={true}
                />
            ))}
        </div>
      </div>
  );
}
