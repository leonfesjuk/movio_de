import { useFormik } from "formik";
import * as Yup from "yup";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useAppDispatch, useAppSelector } from "../../../app/hooks";
import {
  generateTokens,
  selectIsGenerating,
  selectGenerateErrorMessage,
} from "../slice/inviteTokensSlice";

export default function InviteTokenForm() {
  const dispatch = useAppDispatch();
  const isGenerating = useAppSelector(selectIsGenerating);
  const errorMessage = useAppSelector(selectGenerateErrorMessage);

  const formik = useFormik({
    initialValues: { count: 1 },
    validationSchema: Yup.object({
      count: Yup.number()
        .typeError("Must be a number")
        .min(1, "Minimum 1 token")
        .max(100, "Maximum 100 tokens")
        .integer("Must be a whole number")
        .required("Required"),
    }),
    onSubmit: async (values, { resetForm }) => {
      const result = await dispatch(generateTokens({ count: values.count }));
      if (generateTokens.fulfilled.match(result)) {
        resetForm();
      }
    },
  });

  return (
    <>
      <div className="rounded-lg border bg-white shadow-sm p-6 space-y-5">
        <div className="space-y-1">
          <h2 className="text-lg font-semibold tracking-tight">
            Generate invite tokens
          </h2>
          <p className="text-sm text-gray-500">
            Each token can be used once to register a new user.
          </p>
        </div>

        {errorMessage && (
          <div className="rounded-md bg-red-50 border border-red-200 p-3 text-sm text-red-700">
            {errorMessage}
          </div>
        )}

        <form onSubmit={formik.handleSubmit} className="flex items-end gap-3">
          <div className="space-y-1.5 w-36">
            <Label htmlFor="count">Number of tokens</Label>
            <Input
              id="count"
              type="number"
              min={1}
              max={100}
              {...formik.getFieldProps("count")}
              className={
                formik.touched.count && formik.errors.count
                  ? "border-red-500 focus-visible:ring-red-500"
                  : ""
              }
            />
            {formik.touched.count && formik.errors.count && (
              <p className="text-xs text-red-500">{formik.errors.count}</p>
            )}
          </div>

          <Button type="submit" disabled={isGenerating} className="mb-px">
            {isGenerating ? "Generating…" : "Generate"}
          </Button>
        </form>
      </div>
    </>
  );
}