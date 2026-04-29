import { RegistrationFormData } from "@/context/RegistrationContext";

// Base URL should be configured from environment
const API_BASE_URL =
  process.env.EXPO_PUBLIC_API_URL || "http://localhost:8080/api";

export interface ClientRegisterDTO {
  email: string;
  username: string;
  password: string;
  phoneNumber?: string;
}

export interface EstablishmentRegisterDTO {
  cnpj: string;
  email: string;
  username: string;
  password: string;
  name: string;
  type: string;
  isDelivery: boolean;
  isInPerson: boolean;
  phoneNumber?: string;
}

export interface CredentialsDTO {
  email: string;
  password: string;
}

class AuthService {
  /**
   * Register a new client
   */
  async registerClient(data: ClientRegisterDTO) {
    try {
      const response = await fetch(`${API_BASE_URL}/auth/register/client`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || "Failed to register client");
      }

      return await response.json();
    } catch (error) {
      console.error("Registration error:", error);
      throw error;
    }
  }

  /**
   * Register a new establishment
   */
  async registerEstablishment(data: EstablishmentRegisterDTO) {
    try {
      const response = await fetch(
        `${API_BASE_URL}/auth/register/establishment`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data),
        },
      );

      if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || "Failed to register establishment");
      }

      return await response.json();
    } catch (error) {
      console.error("Registration error:", error);
      throw error;
    }
  }

  /**
   * Login with email and password
   */
  async login(credentials: CredentialsDTO) {
    try {
      const response = await fetch(`${API_BASE_URL}/auth/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(credentials),
      });

      if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || "Login failed");
      }

      return await response.json();
    } catch (error) {
      console.error("Login error:", error);
      throw error;
    }
  }

  /**
   * Register a new user from multi-step form
   */
  async registerFromForm(
    formData: RegistrationFormData,
    accountType: "client" | "establishment",
  ) {
    if (accountType === "client") {
      const clientData: ClientRegisterDTO = {
        email: formData.email,
        username: formData.username,
        password: formData.password,
        phoneNumber: formData.phone || undefined,
      };
      return this.registerClient(clientData);
    } else {
      // For establishment, you may need additional fields from the form
      const establishmentData: EstablishmentRegisterDTO = {
        cnpj: "", // Should be collected in the form
        email: formData.email,
        username: formData.username,
        password: formData.password,
        name: "", // Should be collected in the form
        type: "", // Should be collected in the form
        isDelivery: false, // Should be collected in the form
        isInPerson: true, // Should be collected in the form
        phoneNumber: formData.phone || undefined,
      };
      return this.registerEstablishment(establishmentData);
    }
  }
}

export const authService = new AuthService();
