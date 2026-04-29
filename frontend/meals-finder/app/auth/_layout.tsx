import { Stack } from "expo-router";

export default function AuthLayout() {
  return (
    <Stack
      screenOptions={{
        headerShown: false,
      }}
    >
      <Stack.Screen name="registration-step-1" />
      <Stack.Screen name="registration-step-2" />
      <Stack.Screen name="registration-step-3" />
      <Stack.Screen name="registration-step-4" />
      <Stack.Screen name="login" />
    </Stack>
  );
}
