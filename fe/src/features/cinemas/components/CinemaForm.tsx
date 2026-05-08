import { useFormik } from "formik";
import * as Yup from "yup";
import { Button } from "@/components/ui/button";
import { useAppDispatch, useAppSelector } from "../../../app/hooks";
import { updateCinema } from "../slice/cinemaSlice";
import {
  createCinema,
  selectIsCreating,
  selectCreateErrorMessage,
} from "../slice/cinemaSlice";
import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { CustomInput } from "@/components/common/input/CustomInput";
import { useEffect, useState } from "react";
import axiosInstance from "@/lib/axiosInstance";

type CinemaFormValues = {
  id?: string;
  name: string;
  address: string;
  webLink: string;
  geonameId: number;
};

type Props = {
  initialValues?: CinemaFormValues;
  isEdit?: boolean;
  onClose?: () => void;
};

export default function CinemaForm({ initialValues, isEdit, onClose }: Props) {
  const dispatch = useAppDispatch();
  const isCreating = useAppSelector(selectIsCreating);
  const errorMessage = useAppSelector(selectCreateErrorMessage);

  const [cities, setCities] = useState<{ id: number; name: string }[]>([]);

  useEffect(() => {
    axiosInstance.get("/geonames/search").then((res) => {
      setCities(res.data.data.items);
    });
  }, []);

  const formik = useFormik({
    initialValues: {
      name: initialValues?.name || "",
      address: initialValues?.address || "",
      webLink: initialValues?.webLink || "",
      geonameId: initialValues?.geonameId || 0,
    },

    validationSchema: Yup.object({
      name: Yup.string()
        .required("Required")
        .min(1, "Too short")
        .max(255, "Too long")
        .required("Required"),

      address: Yup.string()
        .required("Required")
        .min(1, "Too short")
        .max(255, "Too long")
        .required("Required"),

      webLink: Yup.string().url("Invalid URL").required("Required"),

      geonameId: Yup.number()
        .required("Required")
        .typeError("Must be a number")
        .required("Required"),
    }),

    onSubmit: async (values) => {
      if (isEdit && initialValues?.id) {
        await dispatch(
          updateCinema({
            id: initialValues.id,
            dto: values,
          }),
        );
        onClose?.();
      } else {
        const result = await dispatch(createCinema(values));

        if (createCinema.fulfilled.match(result)) {
          formik.resetForm();
        }
      }
    },
  });

  return (
    <>
      <Dialog open={isEdit ? true : undefined}>
        {!isEdit && (
          <DialogTrigger asChild>
            <Button className="mb-4">New cinema</Button>
          </DialogTrigger>
        )}

        <DialogContent
          showCloseButton={false}
          onInteractOutside={(e) => e.preventDefault()}
          onEscapeKeyDown={(e) => e.preventDefault()}
          className="sm:max-w-sm"
        >
          <DialogHeader>
            <DialogTitle>
              {isEdit ? "Edit cinema" : "Create new cinema"}
            </DialogTitle>
          </DialogHeader>

          <form onSubmit={formik.handleSubmit} className="space-y-4">
            {errorMessage && <div>{errorMessage}</div>}

            {/* Name */}
            <CustomInput
              id="name"
              label="Name"
              {...formik.getFieldProps("name")}
              error={
                formik.touched.name && typeof formik.errors.name === "string"
                  ? formik.errors.name
                  : undefined
              }
            />

            {/* Address */}
            <CustomInput
              id="address"
              label="Address"
              {...formik.getFieldProps("address")}
              error={
                formik.touched.address &&
                typeof formik.errors.address === "string"
                  ? formik.errors.address
                  : undefined
              }
            />

            {/* WebLink */}
            <CustomInput
              id="webLink"
              label="Website"
              {...formik.getFieldProps("webLink")}
              error={
                formik.touched.webLink &&
                typeof formik.errors.webLink === "string"
                  ? formik.errors.webLink
                  : undefined
              }
            />

            {/* GeonameId */}
            <select
              value={formik.values.geonameId}
              onChange={(e) =>
                formik.setFieldValue("geonameId", Number(e.target.value))
              }
            >
              <option value={0}>Select city</option>
              {cities.map((city) => (
                <option key={city.id} value={city.id}>
                  {city.name}
                </option>
              ))}
            </select>

            <DialogFooter>
              <Button
                  type="button"
                  variant="outline"
                  onClick={() => {
                    onClose?.();
                  }}
              >
                Cancel
              </Button>
              <Button type="submit" disabled={isCreating}>
                {isEdit ? "Save" : "Create"}
              </Button>
            </DialogFooter>
          </form>
        </DialogContent>
      </Dialog>
    </>
  );
}
