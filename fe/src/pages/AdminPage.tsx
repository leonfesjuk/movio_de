import { useEffect, useState } from "react";
import type { Session } from "@/features/session-card/types";
import { AdminSessionCard } from "@/features/admin-session-card/adminSessionCard";
import {
  getSessions,
  deleteSession,
  createSession,
  updateSession,
} from "@/features/services/sessionService";
import { Separator } from "@/components/ui/separator";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { CustomInput } from "@/components/common/input";
import CinemaForm from "@/features/cinemas/components/CinemaForm";
import CinemaList from "@/features/cinemas/components/CinemaList";
export default function AdminPage() {
  const [sessions, setSessions] = useState<Session[]>([]);

  const [editingId, setEditingId] = useState<string | null>(null);

  const [title, setTitle] = useState("");
  const [time, setTime] = useState("");
  const [city, setCity] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getSessions();
        setSessions(data);
      } catch (e) {
        console.error(e);

        setSessions([
          {
            id: "1",
            title: "Avatar",
            time: "18:00",
            date: "2026-04-21",
            city: "berlin",
            notificationsSent: false,
          },
          {
            id: "2",
            title: "Batman",
            time: "19:00",
            date: "2026-04-29",
            city: "dresden",
            notificationsSent: false,
          },
        ]);
      }
    };

    void fetchData();
  }, []);

  const handleDelete = async (id: string) => {
    try {
      await deleteSession(id);
      setSessions((prev) => prev.filter((s) => s.id !== id));
    } catch (e) {
      console.error(e);
      alert("Delete failed");
    }
  };

  const resetForm = () => {
    setTitle("");
    setTime("");
    setCity("");
    setEditingId(null);
  };

  const handleCreateOrEdit = async () => {
    if (!title || !time || !city) {
      alert("Please fill all fields");
      return;
    }

    try {
      if (editingId) {
        await updateSession(editingId, {
          title,
          datetime: time,
          cinema: {
            cityName: city,
          },
        });
      } else {
        await createSession({
          title,
          datetime: time,
          cinema: {
            cityName: city,
          },
        });
      }
      const data = await getSessions();
      setSessions(data);

      resetForm();
    } catch (e) {
      console.error(e);
      alert("Save failed");
    }
  };

  const handleEdit = (session: Session) => {
    setTitle(session.title);
    setTime(`${session.date}T${session.time}`);
    setCity(session.city);
    setEditingId(session.id);
  };

  return (
    <div className="p-10">
      <h1 className="text-2xl font-bold mb-6">Dashboard</h1>
      <Separator />

      <h2 className="text-xl font-bold my-6">Cinemas</h2>

      <CinemaForm />
      <CinemaList />

      <Separator />
      <h2 className="text-xl font-bold my-6">Sessions</h2>

      <Dialog>
        <DialogTrigger asChild>
          <Button
              className="mb-4"
            onClick={() => {
              setEditingId(null);
            }}
          >
            New poster
          </Button>
        </DialogTrigger>
        <DialogContent
          showCloseButton={false}
          onInteractOutside={(e) => e.preventDefault()}
          onEscapeKeyDown={(e) => e.preventDefault()}
          className="sm:max-w-sm"
        >
          <DialogHeader>
            <DialogTitle>
              {editingId ? "Update poster" : "Create new poster"}
            </DialogTitle>
          </DialogHeader>
          <CustomInput
            id="poster_title"
            label="Poster title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
          <CustomInput
            id="poster_time"
            type="datetime-local"
            label="Date/Time"
            value={time}
            onChange={(e) => setTime(e.target.value)}
          />
          <CustomInput
            id="poster_city"
            label="City"
            value={city}
            onChange={(e) => setCity(e.target.value)}
          />
          <DialogDescription className="text-red-500">
            Please ensure all information is entered correctly. Once a poster is
            created, editing is not possible!
          </DialogDescription>
          <DialogFooter>
            <DialogClose asChild>
              <Button variant="outline" onClick={resetForm}>
                Cancel
              </Button>
            </DialogClose>
            <Button type="submit" onClick={handleCreateOrEdit}>
              {editingId ? "Update" : "Create"}
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>

      {sessions.length === 0 ? (
        <div className="text-gray-500">No sessions</div>
      ) : (
        <>
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
            {sessions.map((session) => (
              <AdminSessionCard
                key={session.id}
                session={session}
                onEdit={handleEdit}
                onDelete={handleDelete}
              />
            ))}
          </div>
        </>
      )}
    </div>
  );
}
