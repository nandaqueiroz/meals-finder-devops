# MealsFinder Registration Implementation - Setup Guide

## ✅ What's Been Implemented

### 1. **Design System**

Updated `constants/Colors.ts` with all Figma design colors and tokens. Exported both as individual constants and organized theme objects for light/dark mode support.

### 2. **Reusable Form Components**

- **FormTextInput**: Styled input with error states (border-radius: 20px, height: 51px)
- **FormButton**: Multi-variant button component (primary/secondary/outlined) with loading states
- **FormLabel**: Form label with support for required field indicators

### 3. **Registration Screens (3-Step Flow)**

- **Step 1** ✅ **FULLY IMPLEMENTED FROM FIGMA**
  - Email input (required, with validation)
  - Phone input (optional, with format validation)
  - Progress indicator (1/3)
  - Next button with navigation
  - Login link
  - All styling matches the Figma design pixel-perfect

- **Step 2** (Placeholder - ready for expansion)
  - Username and password fields
  - Form validation
  - Back/Next navigation

- **Step 3** (Placeholder - ready for expansion)
  - Account type selection (Client/Establishment)
  - Form submission

### 4. **Authentication**

- **Login Screen** (Placeholder - ready for expansion)
  - Email and password fields
  - Form validation
  - Navigation to registration

### 5. **Architecture**

- **RegistrationContext**: Manages form data across all 3 steps
- **authService**: API service with methods for registration and login
- **Navigation**: Expo Router configured with Stack navigation

## 🚀 To Get Started - Follow These Steps

### Step 1: Install Fonts

Add Poppins fonts to your app. Update your root layout file (`app/_layout.tsx`):

```tsx
import * as Font from "expo-font";

// In your fonts loading code, add:
await Font.loadAsync({
  Poppins_Regular: require("@/assets/fonts/Poppins-Regular.ttf"),
  Poppins_Medium: require("@/assets/fonts/Poppins-Medium.ttf"),
  Poppins_SemiBold: require("@/assets/fonts/Poppins-SemiBold.ttf"),
});
```

Or download fonts from [Google Fonts](https://fonts.google.com/specimen/Poppins) and place them in `assets/fonts/`.

### Step 2: Add RegistrationProvider to Root Layout

In `app/_layout.tsx`, wrap your entire app with the RegistrationProvider:

```tsx
import { RegistrationProvider } from "@/context/RegistrationContext";

export default function RootLayout() {
  return (
    <RegistrationProvider>
      {/* Your existing layout code */}
    </RegistrationProvider>
  );
}
```

### Step 3: Configure API Base URL

Create or update `.env.local`:

```
EXPO_PUBLIC_API_URL=http://localhost:8080/api
```

Or use your production API URL.

### Step 4: Update Step 2 Screen

In `app/auth/registration-step-2.tsx`, add context usage:

```tsx
import { useRegistration } from "@/context/RegistrationContext";

export default function RegistrationStep2() {
  const { formData, updateFormData } = useRegistration();

  const handleNext = async () => {
    // Save to context
    updateFormData({
      username: username,
      password: password,
    });
    // Navigate to step 3
    router.push("/auth/registration-step-3");
  };

  // ... rest of component
}
```

### Step 5: Update Step 3 Screen

In `app/auth/registration-step-3.tsx`, implement the API call:

```tsx
import { useRegistration } from "@/context/RegistrationContext";
import { authService } from "@/services/authService";

export default function RegistrationStep3() {
  const { formData, updateFormData } = useRegistration();

  const handleRegister = async () => {
    if (!accountType) return;

    setLoading(true);
    try {
      // Save account type
      updateFormData({ accountType: accountType });

      // Call API
      const response = await authService.registerFromForm(
        formData,
        accountType,
      );

      // Store token, navigate to home
      StorageService.setAuthToken(response.token);
      router.replace("/");
    } catch (error) {
      Alert.alert("Registration Failed", error.message);
    } finally {
      setLoading(false);
    }
  };
}
```

### Step 6: Update Login Screen

In `app/auth/login.tsx`, implement login API call:

```tsx
import { authService } from "@/services/authService";

const handleLogin = async () => {
  setLoading(true);
  try {
    const response = await authService.login({ email, password });
    StorageService.setAuthToken(response.token);
    router.replace("/");
  } catch (error) {
    Alert.alert("Login Failed", error.message);
  } finally {
    setLoading(false);
  }
};
```

### Step 7: Update Navigation

In your root navigation setup, start with the auth flow:

```tsx
// Check if user is authenticated
const [isSignedIn, setIsSignedIn] = useState(false);

if (!isSignedIn) {
  return <Stack.Screen name="auth" />;
} else {
  return <Stack.Screen name="(tabs)" />;
}
```

## 📱 Screen Hierarchy

```
auth/
├── registration-step-1      [✅ Fully Implemented]
│   ├── Email input (required)
│   ├── Phone input (optional)
│   └── Next button → step-2
├── registration-step-2      [Needs API integration]
│   ├── Username input (required)
│   ├── Password input (required)
│   └── Next button → step-3
├── registration-step-3      [Needs API integration]
│   ├── Account type selector
│   └── Register button → home
└── login                    [Needs API integration]
    ├── Email input
    ├── Password input
    └── Login button → home
```

## 🎨 Design Details

### Colors Used:

- **Primary Green**: #415e42 (buttons, primary actions)
- **Primary Orange**: #fb5d21 (highlights, secondary text)
- **Light Background**: #f9f9f9
- **Dark Grey**: #1e1e1e (text)
- **Placeholder**: #444
- **Border**: rgba(30, 30, 30, 0.4)

### Typography:

- **Title (32px)**: Poppins Medium, Orange
- **Subtitle (28px)**: Poppins Medium, Dark Grey
- **Label (14px)**: Poppins Medium
- **Body (16px)**: Poppins Regular
- **Button (18px)**: Poppins SemiBold

### Component Dimensions:

- **Input Fields**: 351px width, 51px height, 20px border-radius
- **Button**: 147px width (small), 50px height, 20px border-radius
- **Card**: 24px padding horizontal, 30px padding vertical

## 🔗 API Integration Points

The following backend endpoints should be configured:

```
POST /auth/register/client
Body: {
  email: string,
  username: string,
  password: string,
  phoneNumber?: string
}

POST /auth/register/establishment
Body: {
  cnpj: string,
  email: string,
  username: string,
  password: string,
  name: string,
  type: string,
  isDelivery: boolean,
  isInPerson: boolean,
  phoneNumber?: string
}

POST /auth/login
Body: {
  email: string,
  password: string
}
```

## 📝 Additional Configuration

### For Establishment Registration:

You may need to add more fields to Step 2 or Step 3:

- CNPJ field
- Business name
- Business type selector (Restaurant, Bar, Food Truck, etc.)
- Delivery/In-person toggle

Update the RegistrationContext types accordingly.

### Error Handling:

Consider adding a notification system (toast/alert) for better UX:

- Email already exists
- Username taken
- Network errors
- Validation errors

### Token Storage:

Implement a storage service for auth tokens:

```tsx
// storage/storageService.ts
export const StorageService = {
  setAuthToken: async (token: string) =>
    AsyncStorage.setItem("authToken", token),
  getAuthToken: async () => AsyncStorage.getItem("authToken"),
  clearAuthToken: async () => AsyncStorage.removeItem("authToken"),
};
```

## 📚 File Locations

```
frontend/meals-finder/
├── app/auth/
│   ├── _layout.tsx
│   ├── registration-step-1.tsx ✅
│   ├── registration-step-2.tsx
│   ├── registration-step-3.tsx
│   ├── login.tsx
│   └── README.md
├── components/
│   ├── FormTextInput.tsx
│   ├── FormButton.tsx
│   └── FormLabel.tsx
├── context/
│   └── RegistrationContext.tsx
├── services/
│   └── authService.ts
└── constants/
    └── Colors.ts
```

## ✨ Next Enhancements

1. Add proper error handling with user feedback
2. Implement token storage and refresh logic
3. Add biometric authentication option
4. Add email verification flow
5. Add password reset functionality
6. Add social login options
7. Better form validation messages
8. Loading skeleton screens
9. Analytics/logging

---

**Status**: ✅ Registration Step 1 fully implemented from Figma design
**Ready for**: Backend API integration and additional step completion
