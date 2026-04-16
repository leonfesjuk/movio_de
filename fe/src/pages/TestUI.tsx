import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Input} from "@/components/ui/input";
import {Label} from "@/components/ui/label";
import {Button} from "@/components/ui/button";
import {Textarea} from "@/components/ui/textarea";
import FilterBar from "@/components/common/FilterBar";
import { useState } from "react";

export default function TestUI(){
    const [search, setSearch] = useState("");
    const [status, setStatus] = useState("");

    return(

        <div className="p-10 space-y-6">
            <FilterBar
                search={search}
                setSearch={setSearch}
                status={status}
                setStatus={setStatus}
            />
            <h1 className="text-2xl font-bold">UI Components Test</h1>

            <Card className="w-[300px]">
                <CardHeader>
                    <CardTitle>Login</CardTitle>
                </CardHeader>

                <CardContent className="space-y-3">
                    <div>
                        <Label>Email</Label>
                        <Input placeholder="Enter email"/>
                    </div>

                    <div>
                        <Label>Password</Label>
                        <Input type="password"/>
                    </div>

                    <Button className="w-full">Login</Button>
                </CardContent>
            </Card>

            <Textarea placeholder="Write something..."/>


        </div>
    )
}