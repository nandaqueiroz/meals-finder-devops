import { Redirect } from "expo-router";
import { useAuth } from "@/context/AuthContext";

export default function AuthIndex() {
  const { isAuthenticated, initializing } = useAuth();

  if (initializing) {
    return null;
  }

  return (
    <Redirect href={isAuthenticated ? "/(tabs)" : "/auth/login"} />
  );
}
