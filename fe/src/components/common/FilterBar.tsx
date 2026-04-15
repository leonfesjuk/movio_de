import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select";
import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";

export default function FilterBar(){
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

            <Input placeholder="Search movie..." className="w-[250px]"/>

            <Button>Apply</Button>
        </div>
    );
}