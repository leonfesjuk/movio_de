import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select";
import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";

type FilterBarProps = {
  search: string,
    setSearch: (value: string) => void;
  status: string,
    setStatus: (value: string) => void;
  city: string;
  setCity: (value: string) => void;
  date: string;
  setDate: (value: string) => void;
};

export default function FilterBar({
    search,
    setSearch,
    status,
    setStatus,
    city,
    setCity,
    date,
    setDate,
}: FilterBarProps){
    const cities = [
        {value: "berlin", label:"Berlin"},
        {value: "dresden", label: "Dresden"}
        ];

    return(
        <div className="flex gap-4 items-center mb-6">

            <Input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
            />
            <Select value={city} onValueChange={setCity}>
                <SelectTrigger className="w-[200px]">
                    <SelectValue placeholder="All cities"/>
                </SelectTrigger>

                <SelectContent>
                    {cities.map((c) =>(
                        <SelectItem key={c.value} value={c.value}>
                            {c.label}
                        </SelectItem>
                    ))}
                </SelectContent>
            </Select>

            <Input
                placeholder="Search movie..."
                className="w-[250px]"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
            />

            <Select value={status} onValueChange={setStatus}>
                <SelectTrigger className="w-[150px]">
                    <SelectValue placeholder="Status"/>
                </SelectTrigger>
                <SelectContent>
                    <SelectItem value="active">Active</SelectItem>
                    <SelectItem value="inactive">Inactive</SelectItem>
                </SelectContent>
            </Select>

            <Button variant="outline"
                    onClick={()=>{
                        setSearch("");
                        setStatus("");
                        setCity("");
                        setDate("");
                    }}>
                Reset
            </Button>

        </div>
    );
}