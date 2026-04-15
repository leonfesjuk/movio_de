import FilterBar from "@/components/common/FilterBar";
import {SessionCard} from "@/features/SessionCard/SessionCard";


export default function Home() {

    const sessions = [
        {id: "1", title: "Avatar", time: "18:00", notificationsSent: false},
        {id: "2", title: "Batman", time: "19:30", notificationsSent: true},
        {id: "3", title: "Avatar", time: "20:40", notificationsSent: false},
    ];

  return(
      <div className="p-10">

        <h1 className="text-2xl font-bold mb-6">
          Movie Sessions
        </h1>

          <div className="mb-6">
              <FilterBar/>
          </div>

        <div className="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 ">

            {sessions.map((session) => (
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
