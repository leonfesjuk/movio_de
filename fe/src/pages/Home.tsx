import FilterBar from "@/components/common/FilterBar";
import {SessionCard} from "@/features/SessionCard/SessionCard";


export default function Home() {
  return(
      <div className="p-10">

        <h1 className="text-2xl font-bold mb-6">
          Movie Sessions
        </h1>

          <div className="mb-6">
              <FilterBar/>
          </div>

        <div className="grid grid-cols-4 gap-6">

            <SessionCard
                session={{
                    id:"1",
                    title: "Avatar",
                    time: "18:00",
                    notificationsSent: false
                }}
                isAuth={true}
            />
        </div>
      </div>
  )
}
