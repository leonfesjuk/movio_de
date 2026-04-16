import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select";
import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";

type FilterBarProps = {
  search: string,
    setSearch: (value: string) => void;
  status: string,
    setStatus: (value: string) => void;
};

export default function FilterBar({
    search,
    setSearch,
    status,
    setStatus,
}: FilterBarProps){
    return(
        <div className="flex gap-4 items-center mb-6">

            <Select>
                <SelectTrigger className="w-[200px]">
                    <SelectValue placeholder="All cities"/>
                </SelectTrigger>

                <SelectContent>
                    <SelectItem value="essen">Essen</SelectItem>
                    <SelectItem value="berlin">Berlin</SelectItem>
                </SelectContent>
            </Select>

            <Input
                placeholder="Search movie..."
                className="w-[250px]"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
            />

            <Select
                value={status}
                onValueChange={(value) => setStatus(value)}
            >
                <SelectTrigger className="w-[150px]">
                    <SelectValue placeholder="Status"/>
                </SelectTrigger>

                <SelectContent>
                    <SelectItem value="active">Active</SelectItem>
                    <SelectItem value="inactive">Inactive</SelectItem>
                </SelectContent>
            </Select>

            <Button>Apply</Button>
        </div>
    );
}