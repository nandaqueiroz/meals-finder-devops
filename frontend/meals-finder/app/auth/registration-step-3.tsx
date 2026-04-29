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
import Colors, {
  backWhite,
  primaryGreen,
  primaryOrange,
} from "@/constants/Colors";
import { FormButton } from "@/components/FormButton";

type AccountType = "client" | "establishment" | null;

export default function RegistrationStep3() {
  const router = useRouter();
  const [accountType, setAccountType] = useState<AccountType>(null);
  const [loading, setLoading] = useState(false);

  const handleRegister = async () => {
    if (!accountType) {
      return;
    }

    setLoading(true);
    try {
      // Submit registration data to backend
      // Based on accountType, call appropriate endpoint:
      // - 'client': /auth/register/client
      // - 'establishment': /auth/register/establishment

      // For now, just navigate to home
      router.replace("/");
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
          <Text style={styles.title}>Tipo de Conta</Text>
        </View>

        <View style={[styles.card, { backgroundColor: "#fff" }]}>
          <Text style={styles.progressIndicator}>3/3</Text>

          <Text style={styles.label}>Selecione o tipo de conta:</Text>

          {/* Client Option */}
          <Pressable
            onPress={() => setAccountType("client")}
            style={[
              styles.option,
              accountType === "client" && styles.optionSelected,
            ]}
          >
            <View
              style={[
                styles.radioDot,
                accountType === "client" && styles.radioDotSelected,
              ]}
            />
            <Text style={styles.optionText}>Cliente</Text>
          </Pressable>

          {/* Establishment Option */}
          <Pressable
            onPress={() => setAccountType("establishment")}
            style={[
              styles.option,
              accountType === "establishment" && styles.optionSelected,
            ]}
          >
            <View
              style={[
                styles.radioDot,
                accountType === "establishment" && styles.radioDotSelected,
              ]}
            />
            <Text style={styles.optionText}>Estabelecimento</Text>
          </Pressable>

          <FormButton
            title="Confirmar Cadastro"
            onPress={handleRegister}
            disabled={!accountType}
            loading={loading}
            containerStyle={styles.buttonContainer}
          />

          <FormButton
            title="Voltar"
            variant="outlined"
            onPress={handleBack}
            containerStyle={styles.backButton}
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
  title: {
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
    color: primaryGreen,
    alignSelf: "flex-end",
    marginBottom: 16,
  },
  label: {
    fontSize: 16,
    fontWeight: "500",
    fontFamily: "Poppins_Medium",
    color: "#1e1e1e",
    marginBottom: 20,
  },
  option: {
    flexDirection: "row",
    alignItems: "center",
    paddingVertical: 16,
    paddingHorizontal: 12,
    marginBottom: 12,
    borderRadius: 12,
    borderWidth: 2,
    borderColor: "#ccc",
  },
  optionSelected: {
    borderColor: primaryGreen,
    backgroundColor: "rgba(65, 94, 66, 0.05)",
  },
  radioDot: {
    width: 20,
    height: 20,
    borderRadius: 10,
    borderWidth: 2,
    borderColor: "#ccc",
    marginRight: 12,
  },
  radioDotSelected: {
    backgroundColor: primaryGreen,
    borderColor: primaryGreen,
  },
  optionText: {
    fontSize: 16,
    fontFamily: "Poppins_Regular",
    color: "#1e1e1e",
  },
  buttonContainer: {
    marginTop: 24,
  },
  backButton: {
    marginTop: 12,
  },
});
