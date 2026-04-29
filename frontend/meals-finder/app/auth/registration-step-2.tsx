import React, { useState } from "react";
import { ScrollView, StyleSheet, View, Text, SafeAreaView } from "react-native";
import { useRouter } from "expo-router";
import Colors, { backWhite } from "@/constants/Colors";
import { FormLabel } from "@/components/FormLabel";
import { FormTextInput } from "@/components/FormTextInput";
import { FormButton } from "@/components/FormButton";

export default function RegistrationStep2() {
  const router = useRouter();
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({
    username: "",
    password: "",
    confirmPassword: "",
  });
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const validateForm = () => {
    const newErrors = { username: "", password: "", confirmPassword: "" };
    let isValid = true;

    if (!username.trim()) {
      newErrors.username = "Nome de usuário é obrigatório";
      isValid = false;
    }

    if (!password.trim()) {
      newErrors.password = "Senha é obrigatória";
      isValid = false;
    } else if (password.length < 6) {
      newErrors.password = "Senha deve ter pelo menos 6 caracteres";
      isValid = false;
    }

    if (!confirmPassword.trim()) {
      newErrors.confirmPassword = "Confirmação de senha é obrigatória";
      isValid = false;
    } else if (password !== confirmPassword) {
      newErrors.confirmPassword = "As senhas não coincidem";
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  const handleNext = async () => {
    if (!validateForm()) {
      return;
    }

    setLoading(true);
    try {
      // Store form data and navigate to step 3
      router.push("/auth/registration-step-3");
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleBack = () => {
    router.back();
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
          <Text style={styles.subtitle}>Dados de Acesso</Text>
        </View>

        <View style={[styles.card, { backgroundColor: "#fff" }]}>
          <Text style={styles.progressIndicator}>2/3</Text>

          <View style={styles.formGroup}>
            <FormLabel label="Nome de usuário" required />
            <FormTextInput
              placeholder="seu_usuario"
              value={username}
              onChangeText={setUsername}
              autoCapitalize="none"
              error={!!errors.username}
            />
            {errors.username ? (
              <Text style={styles.errorText}>{errors.username}</Text>
            ) : null}
          </View>

          <View style={styles.formGroup}>
            <FormLabel label="Senha" required />
            <FormTextInput
              placeholder="****"
              value={password}
              onChangeText={setPassword}
              secureTextEntry
              error={!!errors.password}
            />
            {errors.password ? (
              <Text style={styles.errorText}>{errors.password}</Text>
            ) : null}
          </View>

          <View style={styles.formGroup}>
            <FormLabel label="Confirmar Senha" required />
            <FormTextInput
              placeholder="****"
              value={confirmPassword}
              onChangeText={setConfirmPassword}
              secureTextEntry
              error={!!errors.confirmPassword}
            />
            {errors.confirmPassword ? (
              <Text style={styles.errorText}>{errors.confirmPassword}</Text>
            ) : null}
          </View>

          <View style={styles.buttonRow}>
            <FormButton
              title="Voltar"
              variant="secondary"
              onPress={handleBack}
              containerStyle={styles.backButton}
            />

            <FormButton
              title="Próximo"
              onPress={handleNext}
              loading={loading}
              containerStyle={styles.nextButton}
            />
          </View>
        </View>
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
    marginTop: 20,
    marginBottom: 30,
  },
  subtitle: {
    fontSize: 28,
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
  progressIndicator: {
    fontSize: 14,
    fontWeight: "500",
    color: "#415e42",
    alignSelf: "flex-end",
    marginBottom: 16,
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
  buttonRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginTop: 16,
  },
  backButton: {
    flex: 1,
    marginRight: 8,
  },
  nextButton: {
    flex: 1,
    marginLeft: 8,
  },
});
