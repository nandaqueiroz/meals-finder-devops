# Quick Reference - Form Components

## FormTextInput

```tsx
import { FormTextInput } from "@/components/FormTextInput";

<FormTextInput
  placeholder="Enter text"
  value={value}
  onChangeText={setValue}
  keyboardType="default"
  error={false}
  errorColor="#ff6b6b"
/>;
```

### Props

| Prop              | Type                                               | Default   | Description                 |
| ----------------- | -------------------------------------------------- | --------- | --------------------------- |
| `placeholder`     | string                                             | -         | Placeholder text            |
| `value`           | string                                             | -         | Input value                 |
| `onChangeText`    | (text: string) => void                             | -         | Callback on text change     |
| `keyboardType`    | 'default'\|'email-address'\|'phone-pad'\|'numeric' | 'default' | Keyboard type               |
| `error`           | boolean                                            | false     | Shows error styling         |
| `errorColor`      | string                                             | '#ff6b6b' | Error border color          |
| `secureTextEntry` | boolean                                            | false     | Hide text (password fields) |

---

## FormButton

```tsx
import { FormButton } from "@/components/FormButton";

<FormButton
  title="Click Me"
  variant="primary"
  size="medium"
  onPress={() => {}}
  loading={false}
  disabled={false}
/>;
```

### Props

| Prop       | Type                               | Default   | Description           |
| ---------- | ---------------------------------- | --------- | --------------------- |
| `title`    | string                             | -         | Button text           |
| `variant`  | 'primary'\|'secondary'\|'outlined' | 'primary' | Button style          |
| `size`     | 'small'\|'medium'\|'large'         | 'medium'  | Button size           |
| `onPress`  | () => void                         | -         | Callback on press     |
| `loading`  | boolean                            | false     | Shows loading spinner |
| `disabled` | boolean                            | false     | Disables button       |

### Variants

- **primary**: Green background (#415e42)
- **secondary**: Orange background (#fb5d21)
- **outlined**: Transparent with green border

### Sizes

- **small**: 40px height
- **medium**: 50px height
- **large**: 60px height

---

## FormLabel

```tsx
import { FormLabel } from "@/components/FormLabel";

<FormLabel label="Email" required={true} requiredColor="#ff6b6b" />;
```

### Props

| Prop            | Type    | Default   | Description             |
| --------------- | ------- | --------- | ----------------------- |
| `label`         | string  | -         | Label text              |
| `required`      | boolean | false     | Shows required asterisk |
| `requiredColor` | string  | '#ff6b6b' | Asterisk color          |

---

## useRegistration Hook

```tsx
import { useRegistration } from "@/context/RegistrationContext";

export default function MyComponent() {
  const { formData, updateFormData, resetFormData } = useRegistration();

  // Read form data
  const email = formData.email;

  // Update form data
  updateFormData({ email: "new@email.com" });

  // Reset all form data
  resetFormData();
}
```

### Available Methods

| Method           | Params                        | Description               |
| ---------------- | ----------------------------- | ------------------------- |
| `formData`       | -                             | Current form data object  |
| `updateFormData` | Partial<RegistrationFormData> | Merge update to form data |
| `resetFormData`  | -                             | Reset all form data       |

### Form Data Structure

```tsx
interface RegistrationFormData {
  email: string;
  phone: string;
  username: string;
  password: string;
  accountType: "client" | "establishment" | null;
}
```

---

## authService

```tsx
import { authService, ClientRegisterDTO } from "@/services/authService";

// Register client
const response = await authService.registerClient({
  email: "user@example.com",
  username: "username",
  password: "password123",
  phoneNumber: "19986599865",
});

// Login
const response = await authService.login({
  email: "user@example.com",
  password: "password123",
});

// Register from multi-step form
const response = await authService.registerFromForm(formData, "client");
```

### Available Methods

| Method                  | Params                   | Returns      |
| ----------------------- | ------------------------ | ------------ |
| `registerClient`        | ClientRegisterDTO        | Promise<any> |
| `registerEstablishment` | EstablishmentRegisterDTO | Promise<any> |
| `login`                 | CredentialsDTO           | Promise<any> |
| `registerFromForm`      | formData, accountType    | Promise<any> |

---

## Common Usage Patterns

### Form with Validation

```tsx
const [values, setValues] = useState({ email: "", phone: "" });
const [errors, setErrors] = useState({ email: "", phone: "" });

const validate = () => {
  const newErrors = {};
  if (!values.email) newErrors.email = "Email is required";
  setErrors(newErrors);
  return Object.keys(newErrors).length === 0;
};

const handleSubmit = () => {
  if (!validate()) return;
  // Proceed with submission
};

return (
  <>
    <FormLabel label="Email" required />
    <FormTextInput
      placeholder="example@email.com"
      value={values.email}
      onChangeText={(e) => setValues({ ...values, email: e })}
      error={!!errors.email}
    />
    {errors.email && <Text style={{ color: "red" }}>{errors.email}</Text>}

    <FormButton title="Submit" onPress={handleSubmit} />
  </>
);
```

### Multi-Step Form with Context

```tsx
const { formData, updateFormData } = useRegistration();

// Step 1: Save email
updateFormData({ email: "user@example.com" });
navigate("step-2");

// Step 2: Save username and password
updateFormData({
  username: "john_doe",
  password: "secure123",
});
navigate("step-3");

// Step 3: Save account type and submit
updateFormData({ accountType: "client" });
const response = await authService.registerFromForm(formData, "client");
```

### Loading States

```tsx
const [loading, setLoading] = useState(false);

const handleAsync = async () => {
  setLoading(true);
  try {
    // Do async work
    await apiCall();
  } finally {
    setLoading(false);
  }
};

<FormButton title="Submit" onPress={handleAsync} loading={loading} />;
```

---

## Color Tokens

```tsx
import Colors, {
  primaryOrange,
  primaryGreen,
  backWhite,
  darkGrey,
  placeholder,
  textFieldBorder,
} from '@/constants/Colors';

// Use in styles
<Text style={{ color: primaryOrange }}>Orange text</Text>
<View style={{ backgroundColor: primaryGreen }}>Green background</View>

// Use theme object
const colors = Colors.light;
<Text style={{ color: colors.text }}>Theme text</Text>
```

---

## Keyboard Types

Common keyboard types for `FormTextInput`:

- `'default'` - Standard keyboard
- `'email-address'` - Email keyboard with @ and . suggestions
- `'phone-pad'` - Phone number pad
- `'numeric'` - Number pad
- `'url'` - URL keyboard with . / suggestions

---

## Tips & Best Practices

1. **Always wrap inputs with FormLabel** for better UX
2. **Use error state** to show validation feedback immediately
3. **Show loading state** during async operations to prevent double taps
4. **Use context for multi-step forms** instead of prop drilling
5. **Validate on blur** for better UX than real-time validation
6. **Use proper keyboardType** for better user experience
7. **Handle errors gracefully** with user-friendly messages
8. **Test on both platforms** (iOS and Android) for consistent behavior
