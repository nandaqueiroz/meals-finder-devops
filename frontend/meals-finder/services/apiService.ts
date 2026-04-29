const API_BASE_URL = "http://localhost:8080/api"; // Adjust this to your backend URL

export interface UserActivityRequest {
  userId: string;
  entityType: string;
  entityId: string;
}

export interface UserActivity {
  id: string;
  userId: string;
  actionType: string;
  entityType: string;
  entityId: string;
}

class ApiService {
  private async request(endpoint: string, options: RequestInit = {}) {
    const url = `${API_BASE_URL}${endpoint}`;
    const response = await fetch(url, {
      headers: {
        "Content-Type": "application/json",
        ...options.headers,
      },
      ...options,
    });

    if (!response.ok) {
      throw new Error(
        `API request failed: ${response.status} ${response.statusText}`,
      );
    }

    return response.json();
  }

  // Like operations
  async likeEntity(
    userId: string,
    entityType: string,
    entityId: string,
  ): Promise<UserActivity> {
    return this.request("/user-activity/like/" + entityType + "/" + entityId, {
      method: "POST",
      body: JSON.stringify({ userId }),
    });
  }

  async unlikeEntity(
    userId: string,
    entityType: string,
    entityId: string,
  ): Promise<void> {
    return this.request("/user-activity/like/" + entityType + "/" + entityId, {
      method: "DELETE",
      body: JSON.stringify({ userId }),
    });
  }

  // Save operations
  async saveEntity(
    userId: string,
    entityType: string,
    entityId: string,
  ): Promise<UserActivity> {
    return this.request("/user-activity/save/" + entityType + "/" + entityId, {
      method: "POST",
      body: JSON.stringify({ userId }),
    });
  }

  async unsaveEntity(
    userId: string,
    entityType: string,
    entityId: string,
  ): Promise<void> {
    return this.request("/user-activity/save/" + entityType + "/" + entityId, {
      method: "DELETE",
      body: JSON.stringify({ userId }),
    });
  }

  // Get user activities
  async getUserLikes(userId: string): Promise<UserActivity[]> {
    return this.request("/user-activity/user/" + userId + "/likes");
  }

  async getUserSaves(userId: string): Promise<UserActivity[]> {
    return this.request("/user-activity/user/" + userId + "/saves");
  }
}

export const apiService = new ApiService();
