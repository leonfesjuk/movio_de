import InviteTokenForm from "../features/invite-tokens/components/InviteTokenForm";
import InviteTokensList from "../features/invite-tokens/components/InviteTokensList";

export default function InviteTokensPage() {
  return (
    <div className="mx-auto max-w-4xl space-y-6 p-6">
      <div className="space-y-1">
        <h1 className="text-2xl font-semibold tracking-tight">Invite tokens</h1>
        <p className="text-sm text-gray-500">
          Generate single-use tokens to invite new users to the system.
        </p>
      </div>

      <InviteTokenForm />
      <InviteTokensList />
    </div>
  );
}