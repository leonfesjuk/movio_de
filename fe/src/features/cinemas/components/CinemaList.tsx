import { useEffect, useState } from "react";
import { useAppDispatch, useAppSelector } from "../../../app/hooks";
import {
  fetchAllCinemas,
  selectCinemas,
  selectIsLoading,
  selectFetchErrorMessage,
  loadMoreCinemas,
  selectHasMore,
  deleteCinema,
} from "../slice/cinemaSlice";

import {
  Table, TableBody, TableCell, TableHead, TableHeader, TableRow,
} from "@/components/ui/table";

import {
  DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";

import { Button } from "@/components/ui/button";
import { MoreHorizontalIcon } from "lucide-react";
import CinemaForm from "./CinemaForm";
import type { Cinema } from "../types";

export default function CinemaList() {
  const dispatch = useAppDispatch();

  const cinemas = useAppSelector(selectCinemas);
  const isLoading = useAppSelector(selectIsLoading);
  const errorMessage = useAppSelector(selectFetchErrorMessage);
  const hasMore = useAppSelector(selectHasMore);

  const [editingCinema, setEditingCinema] = useState<Cinema | null>(null);

  useEffect(() => {
    dispatch(fetchAllCinemas());
  }, [dispatch]);

  if (isLoading) return <div>Loading...</div>;
  if (errorMessage) return <div>{errorMessage}</div>;

  return (
      <div>
        <h2>Cinemas</h2>

        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>#</TableHead>
              <TableHead>Name</TableHead>
              <TableHead>City</TableHead>
              <TableHead>Address</TableHead>
              <TableHead></TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {cinemas.map((cinema, index) => (
                <TableRow key={cinema.id}>
                  <TableCell>{index + 1}</TableCell>
                  <TableCell>{cinema.name}</TableCell>
                  <TableCell>{cinema.cityName}</TableCell>
                  <TableCell>{cinema.address}</TableCell>

                  <TableCell>
                    <DropdownMenu>
                      <DropdownMenuTrigger asChild>
                        <Button variant="ghost">
                          <MoreHorizontalIcon />
                        </Button>
                      </DropdownMenuTrigger>

                      <DropdownMenuContent>
                        <DropdownMenuItem
                            onClick={() => setEditingCinema(cinema)}
                        >
                          Edit
                        </DropdownMenuItem>

                        <DropdownMenuItem
                            onClick={() => {
                              if (confirm("Delete?")) {
                                dispatch(deleteCinema(cinema.id));
                              }
                            }}
                        >
                          Delete
                        </DropdownMenuItem>
                      </DropdownMenuContent>
                    </DropdownMenu>
                  </TableCell>
                </TableRow>
            ))}
          </TableBody>
        </Table>

        {editingCinema && (
            <CinemaForm
                initialValues={{
                  id: editingCinema.id,
                  name: editingCinema.name,
                  address: editingCinema.address,
                  webLink: "",
                  geonameId: 0,
                }}
                isEdit
                onClose={() => setEditingCinema(null)}
            />
        )}

        {hasMore && (
            <button onClick={() => dispatch(loadMoreCinemas())}>
              Load more
            </button>
        )}
      </div>
  );
}