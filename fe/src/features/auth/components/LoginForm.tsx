import { useFormik } from "formik";
import * as Yup from "yup";
import { clearAuthErrors, login, selectLoginError } from "../slice/authSlice";
import { useAppDispatch, useAppSelector } from "../../../app/hooks";
import { CustomInput } from "@/components/common/input/CustomInput";
import { Button } from "@/components/ui/button";
import { Link, useNavigate } from "react-router-dom";
import { Card, CardAction, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { AlertCircleIcon } from "lucide-react";
import { useEffect } from "react";

const LoginForm = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const loginError = useAppSelector(selectLoginError);
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
    onSubmit: async (values) => {
      const result = await dispatch(login(values));
      if (login.fulfilled.match(result)) {
        navigate("/");
      }
    },
  });

  useEffect(() => {
    dispatch(clearAuthErrors());
  }, [dispatch]);

  return (
    <Card className="w-full max-w-sm mx-auto mt-10">
      <CardHeader>
        <CardTitle>Login</CardTitle>
        <CardDescription>
          Enter your email and password to sign in
        </CardDescription>
        <CardAction>
          <Link
            to="/register"
            className="text-sm font-medium text-gray-500 hover:text-black transition-colors"
          >
            Register
          </Link>
        </CardAction>
      </CardHeader>
      <CardContent>
        <form onSubmit={formik.handleSubmit} className="flex flex-col gap-6">
          {loginError && (
            <Alert variant="destructive" className="max-w-md">
              <AlertCircleIcon />
              <AlertTitle>Login failed</AlertTitle>
              <AlertDescription>
                {loginError}
              </AlertDescription>
            </Alert>
          )}
          {/* Email Field */}
          <CustomInput
            id="email"
            type="email"
            label="Email"
            placeholder="Enter your email"
            {...formik.getFieldProps("email")}
            error={formik.errors.email}
          />

          {/* Password Field */}
          <CustomInput
            id="password"
            type="password"
            isViewSwitcher
            label="Password"
            placeholder="Enter your password"
            {...formik.getFieldProps("password")}
            error={formik.errors.password}
          />

          {/* Submit Button */}
          <Button
            type="submit"
            className="w-full hover:bg-zinc-800 focus:outline-none"
            size="lg"
          >
            Login
          </Button>
        </form>
      </CardContent>
    </Card>
  );
};

export default LoginForm;
