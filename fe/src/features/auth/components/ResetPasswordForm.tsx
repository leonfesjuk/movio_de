import { useFormik } from "formik";
import * as Yup from "yup";
import { useAppDispatch } from "@/app/hooks";
import { resetPassword } from "../slice/authSlice";
import { useNavigate, useSearchParams, Link } from "react-router-dom";
import { useState } from "react";
import { CustomInput } from "@/components/common/input/CustomInput";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
  CardAction,
} from "@/components/ui/card";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { AlertCircleIcon } from "lucide-react";
import { Button } from "@/components/ui/button";
import type { ValidationErrorResponse } from "../types";

const ResetPasswordForm = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const token = searchParams.get("token");

  const [serverPasswordErrors, setServerPasswordErrors] = useState<string[]>(
    [],
  );
  const [serverError, setServerError] = useState<string | null>(null);

  const formik = useFormik({
    initialValues: {
      newPassword: "",
      confirmPassword: "",
    },
    validationSchema: Yup.object({
      newPassword: Yup.string()
        .min(8, "Password must be at least 8 characters")
        .required("Password is required"),
      confirmPassword: Yup.string()
        .oneOf([Yup.ref("newPassword")], "Passwords must match")
        .required("Confirm your password"),
    }),
    onSubmit: async (values, { setSubmitting }) => {
      setServerPasswordErrors([]);
      setServerError(null);

      if (!token) {
        setServerError("Invalid or missing token");
        setSubmitting(false);
        return;
      }

      try {
        const dispatchResult = await dispatch(
          resetPassword({
            token,
            newPassword: values.newPassword,
          }),
        );
        if (resetPassword.fulfilled.match(dispatchResult)) {
          navigate("/auth/confirm-new-password");
          return;
        }

        if (resetPassword.rejected.match(dispatchResult)) {
          const payload = dispatchResult.payload as
            | ValidationErrorResponse
            | undefined;

          if (payload) {
            setServerError(payload.message || "Password reset failed");

            const passwordError = payload.errors?.find(
              (error) => error.field === "newPassword",
            );

            if (passwordError?.messages.length) {
              setServerPasswordErrors(passwordError.messages);
            }
          } else {
            setServerError("Password reset failed");
          }
        }
      } finally {
        setSubmitting(false);
      }
    },
  });

  return (
    <Card className="w-full max-w-sm mx-auto mt-10">
      <CardHeader>
        <CardTitle>Reset password</CardTitle>
        <CardDescription>Enter your new password below.</CardDescription>
        <CardAction>
          <Link
            to="/login"
            className="text-sm font-medium text-gray-500 hover:text-black"
          >
            Login
          </Link>
        </CardAction>
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
            id="newPassword"
            type="password"
            label="New Password"
            isViewSwitcher
            {...formik.getFieldProps("newPassword")}
            onChange={(e) => {
              formik.handleChange(e);
              setServerPasswordErrors([]);
              setServerError(null);
            }}
            error={
              formik.touched.newPassword &&
              (formik.errors.newPassword || serverPasswordErrors.length > 0) ? (
                <ul className="ml-6 list-disc text-red-500">
                  {formik.errors.newPassword && (
                    <li>{formik.errors.newPassword}</li>
                  )}
                  {serverPasswordErrors.map((error, index) => (
                    <li key={index}>{error}</li>
                  ))}
                </ul>
              ) : null
            }
            description={
              <ul className="pl-6 list-disc">
                <li>Password must contain at least 8 characters</li>
                <li>Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character, and only Latin letters.</li>
              </ul>
            }
          />

          <CustomInput
            id="confirmPassword"
            type="password"
            label="Confirm Password"
            isViewSwitcher
            {...formik.getFieldProps("confirmPassword")}
            error={
              formik.touched.confirmPassword &&
              formik.errors.confirmPassword ? (
                <ul className="pl-6 list-disc text-red-500">
                  <li>{formik.errors.confirmPassword}</li>
                </ul>
              ) : null
            }
          />

          <Button
            type="submit"
            className="w-full"
            size="lg"
            disabled={formik.isSubmitting}
          >
            Reset Password
          </Button>
        </form>
      </CardContent>
    </Card>
  );
};

export default ResetPasswordForm;
