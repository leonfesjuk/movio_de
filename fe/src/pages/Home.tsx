import FilterBar from "@/components/common/FilterBar.tsx";git checkout alexandra-fe
export default function Home() {
  return(
      <div className="p-10">
        <h1 className="text-2xl font-bold mb-6">
          Movie Sessions
        </h1>

        <FilterBar/>

        <div className="grid grid-cols-4 gap-6">
          <div className="h-[250px] by-gray-200 rounder-xl"/>
          <div className="h-[250px] by-gray-200 rounder-xl"/>
          <div className="h-[250px] by-gray-200 rounder-xl"/>
          <div className="h-[250px] by-gray-200 rounder-xl"/>
        </div>
      </div>
  );
}
