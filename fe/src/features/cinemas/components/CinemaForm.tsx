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
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { Check, ChevronsUpDown } from "lucide-react";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command";
import { Alert, AlertDescription } from "@/components/ui/alert";

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

  const [open, setOpen] = useState(false);
  const [cityQuery, setCityQuery] = useState("");
  const [cities, setCities] = useState<
    {
      geonameId: number;
      name: string;
      countryCode: string;
      latitude: number;
      longitude: number;
    }[]
  >([]);
  const [isLoadingCities, setIsLoadingCities] = useState(false);

  useEffect(() => {
    if (cityQuery.trim().length < 2) {
      setCities([]);
      return;
    }

    const timeout = setTimeout(async () => {
      try {
        setIsLoadingCities(true);

        const res = await axiosInstance.get("/geonames/search", {
          params: {
            q: cityQuery,
          },
        });

        setCities(res.data.data.items);
      } catch (e) {
        console.error(e);
      } finally {
        setIsLoadingCities(false);
      }
    }, 300);

    return () => clearTimeout(timeout);
  }, [cityQuery]);

  const formik = useFormik({
    initialValues: {
      name: initialValues?.name || "",
      address: initialValues?.address || "",
      webLink: initialValues?.webLink || "",
      geonameId: initialValues?.geonameId ?? 0,
    },

    validationSchema: Yup.object({
      name: Yup.string()
        .required("Required")
        .min(1, "Too short")
        .max(255, "Too long"),

      address: Yup.string()
        .required("Required")
        .min(1, "Too short")
        .max(255, "Too long"),

      webLink: Yup.string().url("Invalid URL").required("Required"),

      geonameId: Yup.number()
        .moreThan(0, "Select city")
        .required("Required")
        .typeError("Must be a number"),
    }),

    onSubmit: async (values) => {
      formik.setFieldTouched("geonameId", true, true);
      
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

  const geonameError = formik.submitCount > 0 && formik.errors.geonameId

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
            {errorMessage && (
              <Alert variant="destructive" className="max-w-md">
                <AlertDescription>{errorMessage}</AlertDescription>
              </Alert>
            )}

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
            <div className="space-y-2">
              <label className="text-sm font-medium">City</label>

              <Popover open={open} onOpenChange={setOpen}>
                <PopoverTrigger asChild>
                  <Button
                    type="button"
                    variant="outline"
                    role="combobox"
                    aria-expanded={open}
                    className="w-full justify-between"
                  >
                    {cityQuery || "Select city"}

                    <ChevronsUpDown className="opacity-50" />
                  </Button>
                </PopoverTrigger>

                <PopoverContent className="w-full p-0">
                  <Command shouldFilter={false}>
                    <CommandInput
                      placeholder="Search city..."
                      value={cityQuery}
                      onValueChange={setCityQuery}
                    />

                    <CommandList>
                      {isLoadingCities && (
                        <div className="p-2 text-sm text-muted-foreground">
                          Searching...
                        </div>
                      )}

                      {!isLoadingCities &&
                        cities.length === 0 &&
                        cityQuery.length >= 2 && (
                          <CommandEmpty>No cities found.</CommandEmpty>
                        )}

                      <CommandGroup>
                        {cities.map((city) => (
                          <CommandItem
                            key={city.geonameId}
                            value={String(city.geonameId)}
                            onSelect={() => {
                              formik.setFieldValue("geonameId", city.geonameId);
                              formik.setFieldTouched("geonameId", true, true);
                              setCityQuery(city.name);
                              setOpen(false);
                            }}
                          >
                            {city.name}

                            <Check
                              className={`ml-auto ${
                                formik.values.geonameId === city.geonameId
                                  ? "opacity-100"
                                  : "opacity-0"
                              }`}
                            />
                          </CommandItem>
                        ))}
                      </CommandGroup>
                    </CommandList>
                  </Command>
                </PopoverContent>
              </Popover>
              <input type="hidden" {...formik.getFieldProps("geonameId")} />
            </div>

            {geonameError && (
              <div className="text-sm text-red-500">
                {formik.errors.geonameId}
              </div>
            )}

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
