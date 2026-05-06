import { useEffect } from "react";
import { useAppDispatch, useAppSelector } from "../../../app/hooks";
import {
  fetchAllCinemas,
  selectCinemas,
  selectIsLoading,
  selectFetchErrorMessage,
  loadMoreCinemas,
  selectHasMore,
  selectIsLoadingMore,
} from "../slice/cinemaSlice";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Button } from "@/components/ui/button";
import { MoreHorizontalIcon } from "lucide-react";

export default function CinemaList() {
  const dispatch = useAppDispatch();

  const cinemas = useAppSelector(selectCinemas);
  const isLoading = useAppSelector(selectIsLoading);
  const errorMessage = useAppSelector(selectFetchErrorMessage);
  const hasMore = useAppSelector(selectHasMore);
  const isLoadingMore = useAppSelector(selectIsLoadingMore);

  useEffect(() => {
    dispatch(fetchAllCinemas());
  }, [dispatch]);

  if (isLoading) {
    return <div className="p-4 text-sm text-gray-500">Loading cinemas...</div>;
  }

  if (errorMessage) {
    return <div className="p-4 text-sm text-red-600">{errorMessage}</div>;
  }

  return (
    <div className="rounded-lg border bg-white shadow-sm p-6 space-y-4">
      <h2 className="text-lg font-semibold tracking-tight">Cinemas</h2>

      {cinemas.length === 0 ? (
        <p className="text-sm text-gray-500">No cinemas found</p>
      ) : (
        <div className="overflow-x-auto">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>#</TableHead>
                <TableHead>Name</TableHead>
                <TableHead>City</TableHead>
                <TableHead>Address</TableHead>
                <TableHead className="text-center">
                  Posters
                  <br />
                  (all / active)
                </TableHead>
                <TableHead className="text-right">Actions</TableHead>
              </TableRow>
            </TableHeader>

            <TableBody>
              {cinemas.map((cinema, index) => (
                <TableRow key={cinema.id}>
                  {/* # */}
                  <TableCell>{index + 1}</TableCell>

                  {/* Name */}
                  <TableCell>{cinema.name}</TableCell>

                  {/* City */}
                  <TableCell>{cinema.cityName}</TableCell>

                  {/* Address */}
                  <TableCell>{cinema.address}</TableCell>

                  {/* Posters (mock пока что) */}
                  <TableCell className="text-center">0 / 0</TableCell>

                  {/* Actions */}
                  <TableCell className="text-right">
                    <DropdownMenu>
                      <DropdownMenuTrigger asChild>
                        <Button variant="ghost" size="icon" className="size-8">
                          <MoreHorizontalIcon />
                          <span className="sr-only">Open menu</span>
                        </Button>
                      </DropdownMenuTrigger>

                      <DropdownMenuContent align="end">
                        <DropdownMenuItem>Edit</DropdownMenuItem>
                        <DropdownMenuItem variant="destructive">
                          Delete
                        </DropdownMenuItem>
                      </DropdownMenuContent>
                    </DropdownMenu>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>
      )}
      {hasMore && (
        <div className="pt-4 flex justify-center">
          <button
            onClick={() => dispatch(loadMoreCinemas())}
            disabled={isLoadingMore}
            className="text-sm text-blue-600 hover:underline"
          >
            {isLoadingMore ? "Loading..." : "Load more"}
          </button>
        </div>
      )}
    </div>
  );
}
