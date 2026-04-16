import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Check } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";

export default function EmailConfirmationPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const email = location.state?.email;
  return (
    <Card className="w-full max-w-sm mx-auto mt-10">
      <CardHeader>
        <CardTitle className="text-center">Create an account</CardTitle>
      </CardHeader>
      <CardContent className="text-center">
        <p className="inline-flex size-20 items-center justify-center">
          <Check className="text-green-400 size-20" />
        </p>
        <p>
          A link to confirm your registration has been sent to the email address
          you provided.
        </p>
        {email && (
          <p>({email})</p>
        )}
        <Button
          onClick={() => navigate("/login")}
          className="w-full hover:bg-zinc-800 focus:outline-none mt-4"
        >
          Login
        </Button>
      </CardContent>
    </Card>
  );
}
