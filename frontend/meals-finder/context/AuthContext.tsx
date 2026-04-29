import React, {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
  ReactNode,
} from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";
import {
  authService,
  AuthenticatedUser,
  CredentialsDTO,
} from "@/services/authService";
import { userService } from "@/services/userService";

const STORAGE_KEY = "@meals-finder/auth-user";

interface AuthContextType {
  user: AuthenticatedUser | null;
  initializing: boolean;
  isAuthenticated: boolean;
  login: (credentials: CredentialsDTO) => Promise<AuthenticatedUser>;
  logout: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<AuthenticatedUser | null>(null);
  const [initializing, setInitializing] = useState(true);

  useEffect(() => {
    let cancelled = false;
    (async () => {
      try {
        const raw = await AsyncStorage.getItem(STORAGE_KEY);
        if (cancelled || !raw) return;

        const restored = JSON.parse(raw) as AuthenticatedUser;

        // Auto-cura: valida que o usuário ainda existe no backend (o banco
        // pode ter sido recriado, dropando contas antigas). Se não existir,
        // limpa storage e força novo login.
        try {
          await userService.getUser(restored.id);
          if (!cancelled) setUser(restored);
        } catch (err) {
          console.warn(
            "AuthContext: stored user no longer exists, clearing session",
            err,
          );
          await AsyncStorage.removeItem(STORAGE_KEY);
        }
      } catch (err) {
        console.warn("AuthContext: failed to restore session", err);
      } finally {
        if (!cancelled) setInitializing(false);
      }
    })();
    return () => {
      cancelled = true;
    };
  }, []);

  const login = useCallback(async (credentials: CredentialsDTO) => {
    const authenticated = await authService.login(credentials);
    await AsyncStorage.setItem(STORAGE_KEY, JSON.stringify(authenticated));
    setUser(authenticated);
    return authenticated;
  }, []);

  const logout = useCallback(async () => {
    await AsyncStorage.removeItem(STORAGE_KEY);
    setUser(null);
  }, []);

  const value = useMemo<AuthContextType>(
    () => ({
      user,
      initializing,
      isAuthenticated: user !== null,
      login,
      logout,
    }),
    [user, initializing, login, logout],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within AuthProvider");
  }
  return context;
}
