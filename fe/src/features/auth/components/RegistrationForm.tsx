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
  const [serverFieldErrors, setServerFieldErrors] = useState<
    Record<string, string[]>
  >({});
  const formik = useFormik({
    initialValues: {
      inviteToken: "",
      email: "",
      password: "",
      name: "",
      webLink: "",
    },
    validationSchema: Yup.object({
      inviteToken: Yup.string()
        .trim()
        .required("Invite token is required"),
      email: Yup.string()
        .trim()
        .email("Invalid email address")
        .required("Email is required"),
      password: Yup.string()
        .trim()
        .min(8, "Password must be at least 8 characters")
        .required("Password is required"),
      name: Yup.string()
        .trim()
        .required("Organization name is required"),
      webLink: Yup.string()
        .trim()
        .required("Web-link is required"),
    }),
    onSubmit: async (values, { setSubmitting }) => {
      setServerFieldErrors({});
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
          const payload = dispatchResult.payload as ValidationErrorResponse;

          if (payload) {
            setServerFormError(payload.message || "Registration failed");

            const fieldErrors: Record<string, string[]> = {};

            payload.errors?.forEach((e) => {
              fieldErrors[e.field] = e.messages;
            });

            setServerFieldErrors(fieldErrors);
          } else {
            setServerFormError("Registration failed");
          }
        }
      } finally {
        setSubmitting(false);
      }
    },
  });

  const getFieldError = (field: string) => {
    const formikError =
      formik.touched[field as keyof typeof formik.touched] &&
      formik.errors[field as keyof typeof formik.errors];

    const serverErrors = serverFieldErrors[field] ?? [];

    if (!formikError && serverErrors.length === 0) return null;

    return (
      <ul className="pl-6 list-disc text-red-500">
        {formikError && <li>{formikError}</li>}
        {serverErrors.map((e, i) => (
          <li key={i}>{e}</li>
        ))}
      </ul>
    );
  };
  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    formik.handleChange(e);
    setServerFieldErrors({});
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
          {/* Invite token Field */}
          <CustomInput
            id="invite_token"
            type="text"
            label="Invite Token"
            placeholder="Enter your invite token"
            description="You should have received this token from the administrator"
            required
            {...formik.getFieldProps("inviteToken")}
            error={getFieldError("inviteToken")}
          />

          {/* Email Field */}
          <CustomInput
            id="email"
            type="email"
            label="Email"
            placeholder="Enter your email"
            required
            {...formik.getFieldProps("email")}
            error={getFieldError("email")}
          />

          {/* Password Field */}
          <CustomInput
            id="password"
            type="password"
            isViewSwitcher
            label="Password"
            placeholder="Create a password"
            required
            {...formik.getFieldProps("password")}
            onChange={handlePasswordChange}
            error={getFieldError("password")}
            description={
              <ul className="pl-6 list-disc text-muted-foreground">
                <li>Password must contain at least 8 characters</li>
                <li>
                  Password must contain at least 1 uppercase letter, 1 lowercase
                  letter, 1 number and 1 special character, and only Latin
                  letters.
                </li>
              </ul>
            }
          />

          {/* Name Field */}
          <CustomInput
            id="name"
            type="text"
            label="Organization name"
            placeholder="Enter your organization name"
            required
            {...formik.getFieldProps("name")}
            error={getFieldError("name")}
          />

          {/* Web-link Field */}
          <CustomInput
            id="webLink"
            type="text"
            label="Link to the site"
            placeholder="Enter link to the your organization site"
            required
            {...formik.getFieldProps("webLink")}
            error={getFieldError("webLink")}
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
