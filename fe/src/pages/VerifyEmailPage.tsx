import { useEffect, useState } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import { useAppDispatch } from "@/app/hooks";
import { verifyEmail } from "@/features/auth/slice/authSlice";

import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
  CardDescription,
} from "@/components/ui/card";

import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { Button } from "@/components/ui/button";
import { AlertCircleIcon, CheckCircle2Icon } from "lucide-react";
import type { ValidationErrorResponse } from "@/features/auth/types";

const VerifyEmailPage = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  const code = searchParams.get("code");

  const [status, setStatus] = useState<"loading" | "success" | "error">(
    "loading",
  );
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const verify = async () => {
      if (!code) {
        setStatus("error");
        setError("Missing verification code");
        return;
      }

      try {
        const result = await dispatch(verifyEmail(code));

        if (verifyEmail.fulfilled.match(result)) {
          setStatus("success");
          return;
        }

        if (verifyEmail.rejected.match(result)) {
          const payload = result.payload as ValidationErrorResponse;
          setStatus("error");
          setError(payload?.message || "Verification failed");
        }
      } catch {
        setStatus("error");
        setError("Unexpected error");
      }
    };

    verify();
  }, [code, dispatch]);

  return (
    <Card className="w-full max-w-md mx-auto mt-10">
      <CardHeader>
        <CardTitle>Email verification</CardTitle>
        <CardDescription>
          We are verifying your email address...
        </CardDescription>
      </CardHeader>

      <CardContent className="flex flex-col gap-4">
        {status === "loading" && (
          <p className="text-gray-500">Verifying email...</p>
        )}

        {status === "success" && (
          <>
            <Alert>
              <CheckCircle2Icon className="text-green-500" />
              <AlertTitle>Email verified</AlertTitle>
              <AlertDescription>
                Your account has been successfully activated.
              </AlertDescription>
            </Alert>

            <Button onClick={() => navigate("/login")} className="w-full hover:bg-zinc-800 focus:outline-none">
              Login
            </Button>
          </>
        )}

        {status === "error" && (
          <>
            <Alert variant="destructive">
              <AlertCircleIcon />
              <AlertTitle>Verification failed</AlertTitle>
              <AlertDescription>{error}</AlertDescription>
            </Alert>

            <Button onClick={() => navigate("/login")} className="w-full hover:bg-zinc-800 focus:outline-none">
              Login
            </Button>
          </>
        )}
      </CardContent>
    </Card>
  );
};

export default VerifyEmailPage;