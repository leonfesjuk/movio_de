import { useEffect } from "react";
import { Badge } from "@/components/ui/badge";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useAppDispatch, useAppSelector } from "../../../app/hooks";
import {
  fetchAllTokens,
  selectTokens,
  selectIsLoading,
  selectFetchErrorMessage,
} from "../slice/inviteTokensSlice";

function formatDate(iso: string): string {
  return new Date(iso).toLocaleString("en-GB", {
    day: "2-digit",
    month: "short",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
}

export default function InviteTokensList() {
  const dispatch = useAppDispatch();
  const tokens = useAppSelector(selectTokens);
  const isLoading = useAppSelector(selectIsLoading);
  const fetchError = useAppSelector(selectFetchErrorMessage);

  useEffect(() => {
    dispatch(fetchAllTokens());
  }, [dispatch]);

  return (
    <div className="rounded-lg border bg-white shadow-sm">
      <div className="px-6 py-4 border-b">
        <h2 className="text-lg font-semibold tracking-tight">All tokens</h2>
        <p className="text-sm text-gray-500 mt-0.5">
          {tokens.length} token{tokens.length !== 1 ? "s" : ""} total
        </p>
      </div>

      {fetchError && (
        <div className="mx-6 mt-4 rounded-md bg-red-50 border border-red-200 p-3 text-sm text-red-700">
          {fetchError}
        </div>
      )}

      {isLoading ? (
        <div className="px-6 py-10 text-center text-sm text-gray-400">
          Loading…
        </div>
      ) : tokens.length === 0 ? (
        <div className="px-6 py-10 text-center text-sm text-gray-400">
          No tokens yet. Generate your first one above.
        </div>
      ) : (
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-80">ID</TableHead>
              <TableHead>Created</TableHead>
              <TableHead>Used at</TableHead>
              <TableHead className="text-right">Status</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {tokens.map((token) => (
              <TableRow key={token.token}>
                <TableCell className="font-mono text-xs text-gray-500">
                  {token.token}
                </TableCell>
                <TableCell className="text-sm">
                  {formatDate(token.createdAt)}
                </TableCell>
                <TableCell className="text-sm text-gray-500">
                  {token.usedAt ? formatDate(token.usedAt) : "—"}
                </TableCell>
                <TableCell className="text-right">
                  {token.used ? (
                    <Badge variant="secondary">Used</Badge>
                  ) : (
                    <Badge className="bg-green-100 text-green-800 hover:bg-green-100">
                      Available
                    </Badge>
                  )}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}
    </div>
  );
}