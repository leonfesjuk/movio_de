import * as React from "react"
import { cva, type VariantProps } from "class-variance-authority"
import { Slot } from "radix-ui"
import { cn } from "@/lib/utils"

const badgeVariants = cva(
    "group/badge inline-flex shrink-0 items-center justify-center gap-1 whitespace-nowrap border border-transparent text-sm font-medium transition-all outline-none select-none focus-visible:border-ring focus-visible:ring-3 focus-visible:ring-ring/50 aria-invalid:border-destructive aria-invalid:ring-3 aria-invalid:ring-destructive/20 dark:aria-invalid:ring-destructive/40 [&>svg]:pointer-events-none [&>svg]:shrink-0 [&>svg:not([class*='size-'])]:size-4",
    {
      variants: {
        variant: {
          default:
              "bg-primary text-primary-foreground hover:bg-primary/80 active:bg-primary/90 shadow-sm",

          secondary:
              "bg-gray-100 text-gray-900 hover:bg-gray-200 active:bg-gray-300",

          destructive:
              "bg-destructive/10 text-destructive hover:bg-destructive/20 active:bg-destructive/30",

          outline:
              "border-border bg-background text-foreground hover:bg-muted",

          ghost:
              "text-gray-600 hover:bg-gray-100",

          link:
              "text-primary underline-offset-4 hover:underline",
        },

        size: {
          default:
              "h-8 px-2.5 text-sm rounded-lg has-data-[icon=inline-end]:pr-2 has-data-[icon=inline-start]:pl-2",

          sm:
              "h-7 px-2 text-[0.8rem] rounded-lg has-data-[icon=inline-end]:pr-1.5 has-data-[icon=inline-start]:pl-1.5",

          lg:
              "h-9 px-2.5 text-sm rounded-lg",
        },
      },

      defaultVariants: {
        variant: "default",
        size: "default",
      },
    }
)

function Badge({
  className,
  variant = "default",
  asChild = false,
  ...props
}: React.ComponentProps<"span"> &
  VariantProps<typeof badgeVariants> & { asChild?: boolean }) {
  const Comp = asChild ? Slot.Root : "span"

  return (
    <Comp
      data-slot="badge"
      data-variant={variant}
      className={cn(badgeVariants({ variant }), className)}
      {...props}
    />
  )
}

export { Badge, badgeVariants }
