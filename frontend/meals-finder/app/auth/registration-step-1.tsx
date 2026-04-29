import React, { useState } from "react";
import {
  ScrollView,
  StyleSheet,
  View,
  Text,
  Pressable,
  Image,
  SafeAreaView,
} from "react-native";
import { useRouter } from "expo-router";
import Colors, { primaryOrange, darkGrey, lightText } from "@/constants/Colors";
import { useColorScheme } from "@/components/useColorScheme";
import { FormLabel } from "@/components/FormLabel";
import { FormTextInput } from "@/components/FormTextInput";
import { FormButton } from "@/components/FormButton";

const mealFinderLogo = require("@/assets/images/meals-finder-logo.png");

export default function RegistrationStep1() {
  const colorScheme = useColorScheme() ?? "light";
  const colors = Colors[colorScheme as keyof typeof Colors];
  const router = useRouter();

  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [errors, setErrors] = useState({ email: "", phone: "" });
  const [loading, setLoading] = useState(false);

  const formatPhoneNumber = (text: string) => {
    const cleaned = text.replace(/\D/g, "");

    if (cleaned.length === 0) return "";
    if (cleaned.length <= 2) return `(${cleaned}`;
    if (cleaned.length <= 7)
      return `(${cleaned.slice(0, 2)}) ${cleaned.slice(2)}`;
    return `(${cleaned.slice(0, 2)}) ${cleaned.slice(2, 7)}-${cleaned.slice(
      7,
      11,
    )}`;
  };

  const handlePhoneChange = (text: string) => {
    const formatted = formatPhoneNumber(text);
    setPhone(formatted);
  };

  const validateForm = () => {
    const newErrors = { email: "", phone: "" };
    let isValid = true;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!email.trim()) {
      newErrors.email = "Email é obrigatório";
      isValid = false;
    } else if (!emailRegex.test(email)) {
      newErrors.email = "Email inválido";
      isValid = false;
    }

    // Validar telefone: permitir apenas se tiver pelo menos 10 dígitos
    const phoneDigits = phone.replace(/\D/g, "");
    if (phone.trim() && phoneDigits.length < 10) {
      newErrors.phone = "Telefone inválido (mínimo 10 dígitos)";
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
      router.push("/auth/registration-step-2");
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleLoginPress = () => {
    router.push("/auth/login");
  };

  return (
    <SafeAreaView style={[styles.safeArea]}>
      <ScrollView
        contentContainerStyle={[styles.container]}
        showsVerticalScrollIndicator={false}
      >
        <View style={styles.headerSection}>
          <Text style={[styles.welcomeTitle, { color: primaryOrange }]}>
            Bem-vindo(a)
          </Text>
          <Text style={[styles.subtitle, { color: colors.text }]}>
            Faça seu cadastro.
          </Text>
        </View>

        <Text style={[styles.infoText, { color: colors.text }]}>
          Insira suas informações pessoais:
        </Text>

        <View
          style={[styles.card, { backgroundColor: colors.backgroundCards }]}
        >
          <Text
            style={[styles.progressIndicator, { color: Colors.light.primary }]}
          >
            1/3
          </Text>

          <View style={styles.formGroup}>
            <FormLabel label="E-mail" required />
            <FormTextInput
              placeholder="raquel@email.com"
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
            <FormLabel label="Telefone" />
            <FormTextInput
              placeholder="(19) 98659-9865"
              value={phone}
              onChangeText={handlePhoneChange}
              keyboardType="phone-pad"
              error={!!errors.phone}
            />
            {errors.phone ? (
              <Text style={styles.errorText}>{errors.phone}</Text>
            ) : null}
          </View>

          <FormButton
            title="Próximo"
            onPress={handleNext}
            loading={loading}
            containerStyle={styles.buttonContainer}
          />
        </View>

        <Pressable onPress={handleLoginPress}>
          <Text style={[styles.loginText, { color: colors.text }]}>
            Já tem uma conta?{" "}
            <Text style={[styles.loginLink, { color: primaryOrange }]}>
              Faça login!
            </Text>
          </Text>
        </Pressable>

        <View style={styles.logoContainer}>
          <Image
            source={mealFinderLogo}
            style={styles.logo}
            resizeMode="contain"
          />
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
  welcomeTitle: {
    fontSize: 32,
    fontWeight: "500",
    fontFamily: "Poppins_Medium",
    marginBottom: 8,
  },
  subtitle: {
    fontSize: 28,
    fontWeight: "500",
    fontFamily: "Poppins_Medium",
  },
  infoText: {
    fontSize: 18,
    fontWeight: "500",
    fontFamily: "Poppins_Medium",
    marginBottom: 24,
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
  buttonContainer: {
    marginTop: 16,
  },
  loginText: {
    fontSize: 14,
    fontFamily: "Poppins_Regular",
    textAlign: "center",
  },
  loginLink: {
    fontWeight: "bold",
  },
  logoContainer: {
    alignItems: "center",
    marginTop: 40,
    marginBottom: 20,
  },
  logo: {
    width: 120,
    height: 120,
  },
});
