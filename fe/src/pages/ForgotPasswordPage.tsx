import { useFormik } from "formik";
import * as Yup from "yup";
import { useAppDispatch } from "@/app/hooks";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
  CardDescription,
} from "@/components/ui/card";

import { CustomInput } from "@/components/common/input/CustomInput";
import { Button } from "@/components/ui/button";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { AlertCircleIcon } from "lucide-react";
import { forgotPassword } from "@/features/auth/slice/authSlice";

const ForgotPasswordPage = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const [serverError, setServerError] = useState<string | null>(null);

  const formik = useFormik({
    initialValues: {
      email: "",
    },
    validationSchema: Yup.object({
      email: Yup.string()
        .email("Invalid email address")
        .required("Email is required"),
    }),
    onSubmit: async (values, { setSubmitting }) => {
      setServerError(null);

      try {
        const result = await dispatch(forgotPassword(values.email));

        if (forgotPassword.fulfilled.match(result)) {
          navigate("/check-email-password", {
            state: { email: values.email },
          });
          return;
        }

        if (forgotPassword.rejected.match(result)) {
          const payload = result.payload as any;
          setServerError(payload?.message || "Request failed");
        }
      } finally {
        setSubmitting(false);
      }
    },
  });

  return (
    <Card className="w-full max-w-sm mx-auto mt-10">
      <CardHeader>
        <CardTitle>Forgot password</CardTitle>
        <CardDescription>
          Enter your email and we will send you reset link.
        </CardDescription>
      </CardHeader>

      <CardContent>
        <form onSubmit={formik.handleSubmit} className="flex flex-col gap-6">
          {serverError && (
            <Alert variant="destructive">
              <AlertCircleIcon />
              <AlertTitle>Error</AlertTitle>
              <AlertDescription>{serverError}</AlertDescription>
            </Alert>
          )}

          <CustomInput
            id="email"
            type="email"
            label="Email"
            placeholder="Enter your email"
            {...formik.getFieldProps("email")}
            error={
              formik.touched.email && formik.errors.email
            }
          />

          <Button type="submit" className="w-full" size="lg">
            Send reset link
          </Button>
        </form>
      </CardContent>
    </Card>
  );
};

export default ForgotPasswordPage;