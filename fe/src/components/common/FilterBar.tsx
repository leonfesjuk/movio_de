import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select";
import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";

export default function FilterBar(){
    return(
        <div className="flex items-center gap-4 mb-6">

            <Input
                placeholder="Search..."
                className="w-[200px]"
            />

            <Select>
                <SelectTrigger className="w-[180px]">
                    <SelectValue placeholder="Status"/>
                </SelectTrigger>

                <SelectContent>
                    <SelectItem value="active">Active</SelectItem>
                    <SelectItem value="inactive">Inactive</SelectItem>
                </SelectContent>
            </Select>

            <Button>Filter</Button>
        </div>
    );
}