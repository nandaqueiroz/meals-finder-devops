import React, { createContext, useContext, useState, ReactNode } from "react";

export type AccountType = "client" | "establishment";

export interface RegistrationFormData {
  // Step 1
  email: string;
  phone: string;

  // Step 2
  username: string;
  password: string;

  // Step 3
  accountType: AccountType | null;
}

interface RegistrationContextType {
  formData: RegistrationFormData;
  updateFormData: (data: Partial<RegistrationFormData>) => void;
  resetFormData: () => void;
}

const initialFormData: RegistrationFormData = {
  email: "",
  phone: "",
  username: "",
  password: "",
  accountType: null,
};

const RegistrationContext = createContext<RegistrationContextType | undefined>(
  undefined,
);

export function RegistrationProvider({ children }: { children: ReactNode }) {
  const [formData, setFormData] =
    useState<RegistrationFormData>(initialFormData);

  const updateFormData = (data: Partial<RegistrationFormData>) => {
    setFormData((prev) => ({ ...prev, ...data }));
  };

  const resetFormData = () => {
    setFormData(initialFormData);
  };

  return (
    <RegistrationContext.Provider
      value={{
        formData,
        updateFormData,
        resetFormData,
      }}
    >
      {children}
    </RegistrationContext.Provider>
  );
}

export function useRegistration() {
  const context = useContext(RegistrationContext);
  if (!context) {
    throw new Error("useRegistration must be used within RegistrationProvider");
  }
  return context;
}
