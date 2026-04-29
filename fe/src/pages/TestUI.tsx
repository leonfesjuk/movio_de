import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Input} from "@/components/ui/input";
import {Label} from "@/components/ui/label";
import {Button} from "@/components/ui/button";
import {Textarea} from "@/components/ui/textarea";
import FilterBar from "@/components/common/FilterBar";
import { CustomInput } from "@/components/common/input";
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

            <Button>Button</Button>

            <CustomInput
                type="password"
                label="Password"
                name="password_1"
                id="id_password"
                placeholder="Entry password"
                required
                readOnly
                isViewSwitcher
                description={
                <ul className="ml-6 list-disc">
                    <li>1st level of puns: 5 gold coins</li>
                    <li>2nd level of jokes: 10 gold coins</li>
                    <li>3rd level of one-liners : 20 gold coins</li>
                </ul>
                }
                error={
                <ul className="ml-6 list-disc">
                    <li>1st level of puns: 5 gold coins</li>
                    <li>2nd level of jokes: 10 gold coins</li>
                    <li>3rd level of one-liners : 20 gold coins</li>
                </ul>
                }
            />

        </div>
    )
}