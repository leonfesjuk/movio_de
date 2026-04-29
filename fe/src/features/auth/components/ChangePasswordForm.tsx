import { useFormik } from "formik";
import * as Yup from "yup";
import { useAppDispatch, useAppSelector } from "../../../app/hooks";
import {
  changePassword,
  clearAuthErrors,
  logout,
  selectChangePasswordError,
} from "../slice/authSlice";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { AlertCircleIcon, CheckCircleIcon } from "lucide-react";
import { useEffect, useState } from "react";
import { CustomInput } from "@/components/common/input/CustomInput";
import { useNavigate } from "react-router-dom";

const ChangePasswordForm = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const error = useAppSelector(selectChangePasswordError);

  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  const formik = useFormik({
    initialValues: {
      currentPassword: "",
      newPassword: "",
      confirmPassword: "",
    },

    validationSchema: Yup.object({
      currentPassword: Yup.string().required("Current password is required"),

      newPassword: Yup.string()
        .min(8, "Password must be at least 8 characters")
        .required("New password is required"),

      confirmPassword: Yup.string()
        .oneOf([Yup.ref("newPassword")], "Passwords must match")
        .required("Confirm password is required"),
    }),

    onSubmit: async (values, { resetForm }) => {
      setSuccessMessage(null);

      const result = await dispatch(changePassword(values));

      if (changePassword.fulfilled.match(result)) {
        setSuccessMessage("Password changed successfully");
        resetForm();

        setTimeout(() => navigate("/profile"), 2000);
      }
    },
  });

  useEffect(() => {
    dispatch(clearAuthErrors());
  }, [dispatch]);

  return (
    <Card className="w-full max-w-sm mx-auto mt-10">
      <CardHeader>
        <CardTitle>Change Password</CardTitle>
        <CardDescription>
          Enter your current password and set a new one
        </CardDescription>
      </CardHeader>

      <CardContent>
        <form onSubmit={formik.handleSubmit} className="flex flex-col gap-6">
          {/* ERROR */}
          {error && (
            <Alert variant="destructive">
              <AlertCircleIcon />
              <AlertTitle>Failed</AlertTitle>
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          )}

          {/* SUCCESS */}
          {successMessage && (
            <Alert className="border-green-500 text-green-700">
              <CheckCircleIcon />
              <AlertTitle>Success</AlertTitle>
              <AlertDescription>{successMessage}</AlertDescription>
            </Alert>
          )}

          {/* Current password */}
          <CustomInput
            id="currentPassword"
            type="password"
            isViewSwitcher
            label="Current Password"
            placeholder="Enter current password"
            {...formik.getFieldProps("currentPassword")}
            error={formik.touched.currentPassword && formik.errors.currentPassword}
          />

          {/* New password */}
          <CustomInput
            id="newPassword"
            type="password"
            isViewSwitcher
            label="New Password"
            placeholder="Enter new password"
            {...formik.getFieldProps("newPassword")}
            error={formik.touched.newPassword && formik.errors.newPassword}
          />

          {/* Confirm password */}
          <CustomInput
            id="confirmPassword"
            type="password"
            isViewSwitcher
            label="Confirm Password"
            placeholder="Confirm new password"
            {...formik.getFieldProps("confirmPassword")}
            error={
              formik.touched.confirmPassword && formik.errors.confirmPassword
            }
          />

          <Button
            type="submit"
            className="w-full hover:bg-zinc-800"
            size="lg"
          >
            Change password
          </Button>
        </form>
      </CardContent>
    </Card>
  );
};

export default ChangePasswordForm;