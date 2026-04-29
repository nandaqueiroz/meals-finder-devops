import { RegistrationFormData } from "@/context/RegistrationContext";

const API_BASE_URL =
  process.env.EXPO_PUBLIC_API_URL || "http://localhost:8080";

export type LoginType = "EMAIL" | "USERNAME" | "PHONE_NUMBER";

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
  identifier: string;
  password: string;
  type: LoginType;
}

export interface AuthenticatedUser {
  id: string;
  email: string;
  username: string;
  phoneNumber?: string | null;
  profilePictureUrl?: string | null;
  bio?: string | null;
  accountConfirmed?: boolean;
  accountCreationDate?: string;
  type?: "CLIENT" | "ESTABLISHMENT" | "USER";
  name?: string | null;
  [key: string]: unknown;
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
    // body not JSON; ignore and use fallback
  }
  return fallback;
}

class AuthService {
  async registerClient(data: ClientRegisterDTO) {
    const response = await fetch(`${API_BASE_URL}/auth/register/client`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });
    if (!response.ok) {
      throw new Error(await parseError(response, "Falha ao registrar cliente"));
    }
    return await response.json();
  }

  async registerEstablishment(data: EstablishmentRegisterDTO) {
    const response = await fetch(
      `${API_BASE_URL}/auth/register/establishment`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      },
    );
    if (!response.ok) {
      throw new Error(
        await parseError(response, "Falha ao registrar estabelecimento"),
      );
    }
    return await response.json();
  }

  async login(credentials: CredentialsDTO): Promise<AuthenticatedUser> {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(credentials),
    });
    if (!response.ok) {
      throw new Error(await parseError(response, "Credenciais inválidas"));
    }
    return (await response.json()) as AuthenticatedUser;
  }

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
      const establishmentData: EstablishmentRegisterDTO = {
        cnpj: "",
        email: formData.email,
        username: formData.username,
        password: formData.password,
        name: "",
        type: "",
        isDelivery: false,
        isInPerson: true,
        phoneNumber: formData.phone || undefined,
      };
      return this.registerEstablishment(establishmentData);
    }
  }
}

export const authService = new AuthService();
