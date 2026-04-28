import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { CustomInput } from "@/components/common/input";
import { selectUser, updateProfile } from "../slice/authSlice";
import { useState } from "react";
import { useFormik } from "formik";
import * as Yup from "yup";

const ProfileForm = () => {
  const dispatch = useAppDispatch();
  const user = useAppSelector(selectUser);
  const [isEditing, setIsEditing] = useState(false);
  const [successMessage, setSuccessMessage] = useState<string | undefined>();

  const formik = useFormik({
    enableReinitialize: !isEditing,
    initialValues: {
      name: user?.name || "",
      email: user?.email || "",
      webLink: user?.webLink || "",
    },
    validationSchema: Yup.object({
      webLink: Yup.string().required("Organization web-link is required"),
    }),
    onSubmit: async (values) => {
      try {
        await dispatch(updateProfile(values)).unwrap();

        setIsEditing(false);
        setSuccessMessage("Profile updated successfully!");
        setTimeout(() => setSuccessMessage(undefined), 3000);
      } catch (error) {
        console.error("Profile update failed:", error);
        setSuccessMessage("Failed to update profile.");
      }
    },
  });

  return (
    <div className="mx-auto max-w-sm space-y-6 p-6 rounded-lg border bg-white shadow-sm mt-10">
      <div className="space-y-2 text-center">
        <h1 className="text-2xl font-semibold tracking-tight">Profile</h1>
        <p className="text-sm text-muted-foreground text-gray-500">
          {isEditing
            ? "Edit your profile information"
            : "Your profile information"}
        </p>
        {successMessage && (
          <div className="rounded-md bg-grey-50 p-3 text-sm text-grey-700 border border-grey-200">
            {successMessage}
          </div>
        )}

        {/* Role & Confirmation Status */}
        <div className="flex justify-center gap-2 flex-wrap pt-1">
          {user?.role && (
            <span className="inline-block rounded-full bg-green-100 px-3 py-1 text-xs font-medium text-green-500">
              {user.role === "ROLE_ADMIN"
                ? "Admin"
                : user.role === "ROLE_USER"
                  ? "User"
                  : user.role}
            </span>
          )}
        </div>
      </div>

      <form onSubmit={formik.handleSubmit} className="space-y-4">
        <CustomInput
          id="email"
          label="Email"
          type="email"
          disabled
          placeholder="Entry your email"
          {...formik.getFieldProps("email")}
          error={formik.touched.email && formik.errors.email}
        />

        <CustomInput
          id="name"
          label="Organization name"
          type="text"
          disabled
          placeholder="Entry organization name"
          {...formik.getFieldProps("name")}
          error={formik.touched.name && formik.errors.name}
        />

        <CustomInput
          id="web_limk"
          label="Web-link"
          type="text"
          disabled={!isEditing}
          placeholder="Entry your web-link"
          {...formik.getFieldProps("webLink")}
          error={formik.touched.webLink && formik.errors.webLink}
        />

        {/* Buttons */}
        <div className="flex gap-2">
          {isEditing && (
            <>
              <button
                type="submit"
                disabled={formik.isSubmitting}
                className="flex-1 inline-flex items-center justify-center rounded-md bg-black px-4 py-2 text-sm font-medium text-white transition-colors hover:bg-zinc-800 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-black disabled:bg-gray-400"
              >
                {formik.isSubmitting ? "Saving..." : "Save"}
              </button>
              <button
                type="button"
                onClick={() => {
                  setIsEditing(false);
                  formik.resetForm();
                }}
                className="flex-1 inline-flex items-center justify-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 transition-colors hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-black"
              >
                Cancel
              </button>
            </>
          )}
        </div>
      </form>

      {/* Edit Profile */}
      {!isEditing && (
        <button
          type="button"
          onClick={() => setIsEditing(true)}
          className="w-full inline-flex items-center justify-center rounded-md bg-black px-4 py-2 text-sm font-medium text-white transition-colors hover:bg-zinc-800 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-black"
        >
          Edit Profile
        </button>
      )}
    </div>
  );
};

export default ProfileForm;
