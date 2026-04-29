# Registration Flow Components

This directory contains all the reusable components and screens for the MealsFinder registration and authentication flow.

## Components

### FormTextInput

A reusable text input component for forms with built-in validation styling.

**Props:**

- `containerStyle?: ViewStyle` - Container styling
- `error?: boolean` - Shows error styling
- `errorColor?: string` - Custom error color (default: '#ff6b6b')
- All standard React Native TextInput props

**Usage:**

```tsx
<FormTextInput
  placeholder="Enter email"
  value={email}
  onChangeText={setEmail}
  keyboardType="email-address"
  error={!!errors.email}
/>
```

### FormButton

A reusable button component with multiple variants and loading states.

**Props:**

- `title: string` - Button text
- `variant?: 'primary' | 'secondary' | 'outlined'` - Button style variant (default: 'primary')
- `size?: 'small' | 'medium' | 'large'` - Button size (default: 'medium')
- `loading?: boolean` - Shows loading spinner
- `disabled?: boolean` - Disables button
- `containerStyle?: ViewStyle` - Container styling
- All standard React Native Pressable props

**Usage:**

```tsx
<FormButton
  title="Next"
  variant="primary"
  size="medium"
  onPress={handleNext}
  loading={loading}
/>
```

### FormLabel

A reusable label component for form fields.

**Props:**

- `label: string` - Label text
- `required?: boolean` - Shows required asterisk
- `requiredColor?: string` - Custom required color (default: '#ff6b6b')
- All standard React Native Text props

**Usage:**

```tsx
<FormLabel label="Email" required />
```

## Context

### RegistrationContext

Manages registration form data across all three steps.

**Usage:**

```tsx
import { useRegistration } from "@/context/RegistrationContext";

export default function MyComponent() {
  const { formData, updateFormData } = useRegistration();

  // Update form data
  updateFormData({ email: "user@example.com" });

  // Access form data
  console.log(formData.email);
}
```

## Services

### authService

Provides API methods for authentication operations.

**Available Methods:**

- `registerClient(data: ClientRegisterDTO)` - Register a new client user
- `registerEstablishment(data: EstablishmentRegisterDTO)` - Register a new establishment
- `login(credentials: CredentialsDTO)` - Login with email and password
- `registerFromForm(formData, accountType)` - Convenience method for multi-step registration

**Usage:**

```tsx
import { authService, ClientRegisterDTO } from "@/services/authService";

const registerNewClient = async (data: ClientRegisterDTO) => {
  try {
    const response = await authService.registerClient(data);
    console.log("Registration successful:", response);
  } catch (error) {
    console.error("Registration failed:", error);
  }
};
```

## Screens

### Registration Step 1 (`registration-step-1.tsx`)

Collects email and phone number. First step of the registration process.

- Email (required)
- Phone number (optional)
- Progress indicator (1/3)
- Navigation to login and next step

### Registration Step 2 (`registration-step-2.tsx`)

Collects credentials for account access.

- Username (required)
- Password (required)
- Progress indicator (2/3)

### Registration Step 3 (`registration-step-3.tsx`)

Allows selection of account type.

- Account type selection (Client or Establishment)
- Progress indicator (3/3)

### Login Screen (`login.tsx`)

Allows existing users to log in.

- Email (required)
- Password (required)
- Navigation to registration

## Color Scheme

The design system uses the following colors (from Figma):

- Primary Orange: `#fb5d21` (secondary actions, highlights)
- Primary Green: `#415e42` (primary buttons, accents)
- Light Background: `#f9f9f9` (page background)
- Dark Grey: `#1e1e1e` (text, dark elements)
- Placeholder: `#444` (placeholder text)
- Text Field Border: `rgba(30, 30, 30, 0.4)` (input borders)

These colors are defined in `constants/Colors.ts` and can be imported as named exports.

## Setup Instructions

1. **Install the RegistrationProvider** in your root layout:

   ```tsx
   import { RegistrationProvider } from "@/context/RegistrationContext";

   export default function RootLayout() {
     return (
       <RegistrationProvider>{/* Your app content */}</RegistrationProvider>
     );
   }
   ```

2. **Configure API Base URL** by setting the `EXPO_PUBLIC_API_URL` environment variable in your `.env` file:

   ```
   EXPO_PUBLIC_API_URL=http://localhost:8080/api
   ```

3. **Update registration screens** to use the context and call the API service when needed:
   - Save form data to context after each step
   - Call `authService.registerFromForm()` or the specific register method in Step 3

## Next Steps for Implementation

1. Add font imports (Poppins) to your app layout
2. Integrate the RegistrationProvider with your root layout
3. Connect the registration screens to your main navigation
4. Update Step 2 and Step 3 to save data to context
5. Implement actual API calls in Step 3's submit handler
6. Add error handling and user feedback (toast notifications, etc.)
7. Add photo/image upload for profile pictures (if needed)
8. For establishments, add additional fields to Step 2 or Step 3
