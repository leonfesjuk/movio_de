import { useFormik } from "formik";
import * as Yup from "yup";
import { register } from "../slice/authSlice";
import { useAppDispatch } from "../../../app/hooks";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { CustomInput } from "@/components/common/input/CustomInput";
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import type { ValidationErrorResponse } from "../types";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { AlertCircleIcon } from "lucide-react";
import { Button } from "@/components/ui/button";

const RegistrationForm = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [serverFormError, setServerFormError] = useState<string | null>(null);
  const [serverPasswordErrors, setServerPasswordErrors] = useState<
    Record<string, string[]>
  >({});
  // const registerFieldErrors = useAppSelector(selectRegisterError);
  const formik = useFormik({
    initialValues: {
      email: "",
      password: "",
    },
    validationSchema: Yup.object({
      email: Yup.string()
        .email("Invalid email address")
        .required("Email is required"),
      password: Yup.string()
        .min(8, "Password must be at least 8 characters")
        .required("Password is required"),
    }),
    onSubmit: async (values, { setSubmitting }) => {
      setServerPasswordErrors({});
      setServerFormError(null);

      try {
        const dispatchResult = await dispatch(register(values));

        if (register.fulfilled.match(dispatchResult)) {
          navigate("/check-email", {
            state: { email: values.email },
          });
          return;
        }

        if (register.rejected.match(dispatchResult)) {
          const payload = dispatchResult.payload as
            | ValidationErrorResponse
            | undefined;

          if (payload) {
            setServerFormError(payload.message || "Registration failed");

            const fieldErrors: Record<string, string[]> = {};

            payload.errors?.forEach((e) => {
              fieldErrors[e.field] = e.messages;
            });

            setServerPasswordErrors(fieldErrors);
          } else {
            setServerFormError("Registration failed");
          }
        }
      } finally {
        setSubmitting(false);
      }
    },
  });

  const passwordFormikError = formik.touched.password && formik.errors.password;
  const passwordServerErrors = serverPasswordErrors.password ?? [];
  const emailFormikError = formik.touched.email && formik.errors.email;
  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    formik.handleChange(e);
    setServerPasswordErrors({});
    setServerFormError(null);
  };

  return (
    <Card className="w-full max-w-sm mx-auto mt-10">
      <CardHeader>
        <CardTitle>Create an account</CardTitle>
        <CardDescription>
          Enter your email address below, which will be used to log in to your
          account.
        </CardDescription>
        <CardAction>
          <Link
            to="/login"
            className="text-sm font-medium text-gray-500 hover:text-black transition-colors"
          >
            Login
          </Link>
        </CardAction>
      </CardHeader>
      <CardContent>
        <form onSubmit={formik.handleSubmit} className="flex flex-col gap-6">
          {serverFormError && (
            <Alert variant="destructive" className="max-w-md">
              <AlertCircleIcon />
              <AlertTitle>Registration failed</AlertTitle>
              <AlertDescription>{serverFormError}</AlertDescription>
            </Alert>
          )}
          {/* Email Field */}
          <CustomInput
            id="email"
            type="email"
            label="Email"
            placeholder="Enter your email"
            {...formik.getFieldProps("email")}
            error={emailFormikError}
          />

          {/* Password Field */}
          <CustomInput
            id="password"
            type="password"
            isViewSwitcher
            label="Password"
            placeholder="Create a password"
            {...formik.getFieldProps("password")}
            onChange={handlePasswordChange}
            error={
              passwordFormikError || passwordServerErrors.length > 0 ? (
                <ul className="pl-6 list-disc text-red-500">
                  {passwordFormikError && <li>{passwordFormikError}</li>}

                  {passwordServerErrors.map((error, index) => (
                    <li key={index}>{error}</li>
                  ))}
                </ul>
              ) : null
            }
            description={
              <ul className="pl-6 list-disc text-muted-foreground">
                <li>Password must contain at least 8 characters</li>
                <li>Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character, and only Latin letters.</li>
              </ul>
            }
          />

          {/* Submit Button */}
          <Button
            type="submit"
            className="w-full hover:bg-zinc-800 focus:outline-none"
            size="lg"
            disabled={formik.isSubmitting}
          >
            Register
          </Button>
        </form>
      </CardContent>
    </Card>
  );
};

export default RegistrationForm;
