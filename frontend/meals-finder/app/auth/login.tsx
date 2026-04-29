import React, { useState } from "react";
import {
  ScrollView,
  StyleSheet,
  View,
  Text,
  SafeAreaView,
  Pressable,
} from "react-native";
import { useRouter } from "expo-router";
import Colors, { backWhite, primaryOrange } from "@/constants/Colors";
import { FormLabel } from "@/components/FormLabel";
import { FormTextInput } from "@/components/FormTextInput";
import { FormButton } from "@/components/FormButton";
import { useAuth } from "@/context/AuthContext";

export default function LoginScreen() {
  const router = useRouter();
  const { login } = useAuth();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({ email: "", password: "" });
  const [submitError, setSubmitError] = useState("");
  const [loading, setLoading] = useState(false);

  const validateForm = () => {
    const newErrors = { email: "", password: "" };
    let isValid = true;

    if (!email.trim()) {
      newErrors.email = "Email é obrigatório";
      isValid = false;
    }

    if (!password.trim()) {
      newErrors.password = "Senha é obrigatória";
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  const handleLogin = async () => {
    setSubmitError("");
    if (!validateForm()) {
      return;
    }

    setLoading(true);
    try {
      await login({
        identifier: email.trim(),
        password,
        type: "EMAIL",
      });
      router.replace("/");
    } catch (error) {
      const message =
        error instanceof Error ? error.message : "Não foi possível entrar";
      setSubmitError(message);
    } finally {
      setLoading(false);
    }
  };

  const handleRegisterPress = () => {
    router.replace("/auth/registration-step-1");
  };

  return (
    <SafeAreaView style={[styles.safeArea, { backgroundColor: backWhite }]}>
      <ScrollView
        contentContainerStyle={[
          styles.container,
          { backgroundColor: backWhite },
        ]}
        showsVerticalScrollIndicator={false}
      >
        <View style={styles.headerSection}>
          <Text style={[styles.title, { color: primaryOrange }]}>
            Bem-vindo(a)
          </Text>
          <Text style={styles.subtitle}>Faça login na sua conta.</Text>
        </View>

        <View style={[styles.card, { backgroundColor: "#fff" }]}>
          <View style={styles.formGroup}>
            <FormLabel label="E-mail" required />
            <FormTextInput
              placeholder="seu@email.com"
              value={email}
              onChangeText={setEmail}
              keyboardType="email-address"
              autoCapitalize="none"
              error={!!errors.email}
            />
            {errors.email ? (
              <Text style={styles.errorText}>{errors.email}</Text>
            ) : null}
          </View>

          <View style={styles.formGroup}>
            <FormLabel label="Senha" required />
            <FormTextInput
              placeholder="Sua senha"
              value={password}
              onChangeText={setPassword}
              secureTextEntry
              error={!!errors.password}
            />
            {errors.password ? (
              <Text style={styles.errorText}>{errors.password}</Text>
            ) : null}
          </View>

          {submitError ? (
            <Text style={styles.submitErrorText}>{submitError}</Text>
          ) : null}

          <FormButton
            title="Entrar"
            onPress={handleLogin}
            loading={loading}
            containerStyle={styles.buttonContainer}
          />
        </View>

        <Pressable onPress={handleRegisterPress}>
          <Text style={styles.registerText}>
            Não tem uma conta?{" "}
            <Text style={[styles.registerLink, { color: primaryOrange }]}>
              Crie agora!
            </Text>
          </Text>
        </Pressable>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
  },
  container: {
    flexGrow: 1,
    paddingHorizontal: 20,
    paddingVertical: 20,
  },
  headerSection: {
    marginTop: 60,
    marginBottom: 40,
  },
  title: {
    fontSize: 32,
    fontWeight: "500",
    fontFamily: "Poppins_Medium",
    marginBottom: 8,
  },
  subtitle: {
    fontSize: 18,
    fontWeight: "500",
    fontFamily: "Poppins_Medium",
    color: "#1e1e1e",
  },
  card: {
    borderRadius: 30,
    paddingHorizontal: 24,
    paddingVertical: 30,
    marginBottom: 30,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.08,
    shadowRadius: 8,
    elevation: 3,
  },
  formGroup: {
    marginBottom: 24,
  },
  errorText: {
    color: "#ff6b6b",
    fontSize: 12,
    fontFamily: "Poppins_Regular",
    marginTop: 4,
  },
  submitErrorText: {
    color: "#ff6b6b",
    fontSize: 13,
    fontFamily: "Poppins_Regular",
    textAlign: "center",
    marginBottom: 8,
  },
  buttonContainer: {
    marginTop: 16,
  },
  registerText: {
    fontSize: 14,
    fontFamily: "Poppins_Regular",
    textAlign: "center",
    color: "#1e1e1e",
  },
  registerLink: {
    fontWeight: "bold",
  },
});
