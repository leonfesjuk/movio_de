import { useFormik } from "formik";
import * as Yup from "yup";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useAppDispatch, useAppSelector } from "../../../app/hooks";
import {
  createCinema,
  selectIsCreating,
  selectCreateErrorMessage,
} from "../slice/cinemaSlice";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { CustomInput } from "@/components/common/input/CustomInput";

export default function CinemaForm() {
  const dispatch = useAppDispatch();
  const isCreating = useAppSelector(selectIsCreating);
  const errorMessage = useAppSelector(selectCreateErrorMessage);

  const formik = useFormik({
    initialValues: {
      name: "",
      address: "",
      webLink: "",
      geonameId: "",
    },
    validationSchema: Yup.object({
      name: Yup.string()
        .min(1, "Too short")
        .max(255, "Too long")
        .required("Required"),

      address: Yup.string()
        .min(1, "Too short")
        .max(255, "Too long")
        .required("Required"),

      webLink: Yup.string().url("Invalid URL").required("Required"),

      geonameId: Yup.number()
        .typeError("Must be a number")
        .required("Required"),
    }),

    onSubmit: async (values, { resetForm }) => {
      const result = await dispatch(
        createCinema({
          ...values,
          geonameId: Number(values.geonameId),
        }),
      );

      if (createCinema.fulfilled.match(result)) {
        resetForm();
      }
    },
  });

  return (
    <>
      <Dialog>
        <DialogTrigger asChild>
          <Button className="mb-4">New cinema</Button>
        </DialogTrigger>
        <DialogContent
          showCloseButton={false}
          onInteractOutside={(e) => e.preventDefault()}
          onEscapeKeyDown={(e) => e.preventDefault()}
          className="sm:max-w-sm"
        >
          <DialogHeader>
            <DialogTitle>Create new cinema</DialogTitle>
          </DialogHeader>
          <form onSubmit={formik.handleSubmit} className="space-y-4">
            {errorMessage && (
              <div className="rounded-md bg-red-50 border border-red-200 p-3 text-sm text-red-700">
                {errorMessage}
              </div>
            )}
            {/* Name */}
            <CustomInput
              id="name"
              label="Name"
              {...formik.getFieldProps("name")}
              error={formik.touched.name && formik.errors.name}
            />
            {/* Address */}
            <CustomInput
              id="address"
              label="Address"
              {...formik.getFieldProps("address")}
              error={formik.touched.address && formik.errors.address}
            />
            {/* WebLink */}
            <CustomInput
              id="webLink"
              label="Website"
              {...formik.getFieldProps("webLink")}
              error={formik.touched.webLink && formik.errors.webLink}
            />
            {/* GeonameId */}
            <CustomInput
              id="geonameId"
              label="City ID"
              {...formik.getFieldProps("geonameId")}
              error={formik.touched.geonameId && formik.errors.geonameId}
            />
            <DialogFooter>
              <DialogClose asChild>
                <Button variant="outline">Cancel</Button>
              </DialogClose>
              <Button type="submit" disabled={isCreating}>
                {isCreating ? "Creating…" : "Create cinema"}
              </Button>
            </DialogFooter>
          </form>
        </DialogContent>
      </Dialog>
    </>
  );
}
