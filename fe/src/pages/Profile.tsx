import { useAppDispatch, useAppSelector } from "@/app/hooks";
import ProfileForm from "@/features/auth/components/ProfileForm";
import { me, selectIsAuthenticated } from "@/features/auth/slice/authSlice";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Profile = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const isAuthenticated = useAppSelector(selectIsAuthenticated);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
      return;
    }
    dispatch(me());
  }, [dispatch, isAuthenticated, navigate]);

  return <ProfileForm />;
};

export default Profile;