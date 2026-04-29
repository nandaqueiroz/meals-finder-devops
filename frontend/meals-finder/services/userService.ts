import { AuthenticatedUser } from "./authService";

const API_BASE_URL =
  process.env.EXPO_PUBLIC_API_URL || "http://localhost:8080";

export interface UserStats {
  followers: number;
  following: number;
  posts: number;
  isFollowing: boolean;
}

async function parseError(response: Response, fallback: string): Promise<string> {
  try {
    const body = await response.json();
    if (body && typeof body === "object") {
      const candidate =
        (body as { message?: string; error?: string }).message ??
        (body as { message?: string; error?: string }).error;
      if (candidate) return candidate;
    }
  } catch {
    // ignore
  }
  return fallback;
}

class UserService {
  async getUser(id: string): Promise<AuthenticatedUser> {
    const response = await fetch(`${API_BASE_URL}/users/${id}`);
    if (!response.ok) {
      throw new Error(await parseError(response, "Usuário não encontrado"));
    }
    return (await response.json()) as AuthenticatedUser;
  }

  async getStats(userId: string, viewerId?: string | null): Promise<UserStats> {
    const url = new URL(`${API_BASE_URL}/users/${userId}/stats`);
    if (viewerId) url.searchParams.set("viewerId", viewerId);

    const response = await fetch(url.toString());
    if (!response.ok) {
      throw new Error(await parseError(response, "Falha ao carregar stats"));
    }
    return (await response.json()) as UserStats;
  }

  async follow(followerId: string, followingId: string): Promise<void> {
    const response = await fetch(
      `${API_BASE_URL}/users/${followingId}/follow`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ followerId }),
      },
    );
    if (!response.ok) {
      throw new Error(await parseError(response, "Falha ao seguir"));
    }
  }

  async unfollow(followerId: string, followingId: string): Promise<void> {
    const url = new URL(`${API_BASE_URL}/users/${followingId}/follow`);
    url.searchParams.set("followerId", followerId);

    const response = await fetch(url.toString(), { method: "DELETE" });
    if (!response.ok) {
      throw new Error(await parseError(response, "Falha ao deixar de seguir"));
    }
  }
}

export const userService = new UserService();
