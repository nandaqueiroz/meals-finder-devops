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
import { AntDesign } from "@expo/vector-icons";
import Colors, {
  backWhite,
  primaryGreen,
  primaryOrange,
  darkGrey,
} from "@/constants/Colors";
import { FormButton } from "@/components/FormButton";

export default function RegistrationStep4() {
  const router = useRouter();
  const [selectedDislikes, setSelectedDislikes] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);

  const foodPreferences = [
    "fitness",
    "vegana",
    "fast-food",
    "italiana",
    "japonesa",
    "vegetariana",
    "sem lactose",
    "doces",
    "salada",
    "gourmet",
  ];

  const toggleDislike = (preference: string) => {
    setSelectedDislikes((prev) =>
      prev.includes(preference)
        ? prev.filter((p) => p !== preference)
        : [...prev, preference],
    );
  };

  const handleFinish = async () => {
    if (selectedDislikes.length < 3) {
      return;
    }

    setLoading(true);
    try {
      router.replace("/(tabs)");
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleSkip = () => {
    router.replace("/(tabs)");
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
            Último passo!
          </Text>
          <Text style={[styles.subtitle, { color: darkGrey }]}>
            Selecione suas restrições:
          </Text>
        </View>

        <Text style={[styles.description, { color: darkGrey }]}>
          Selecione ao menos 3 tipos de comidas que você não gosta e não quer
          ver no seu feed!
        </Text>

        <View style={[styles.card, { backgroundColor: "#fff" }]}>
          <Text style={[styles.progressIndicator, { color: primaryGreen }]}>
            2/2
          </Text>

          <View style={styles.preferencesGrid}>
            {foodPreferences.map((preference) => (
              <Pressable
                key={preference}
                onPress={() => toggleDislike(preference)}
                style={[
                  styles.preferenceButton,
                  selectedDislikes.includes(preference) &&
                    styles.preferenceButtonSelected,
                ]}
              >
                <Text
                  style={[
                    styles.preferenceText,
                    selectedDislikes.includes(preference) &&
                      styles.preferenceTextSelected,
                  ]}
                >
                  {preference}
                </Text>
                <AntDesign
                  name="plus"
                  size={12}
                  color={
                    selectedDislikes.includes(preference) ? "#fff" : darkGrey
                  }
                  style={styles.plusIcon}
                />
              </Pressable>
            ))}
          </View>

          <View style={styles.buttonRow}>
            <FormButton
              title="Pular etapa"
              variant="secondary"
              onPress={handleSkip}
              containerStyle={styles.skipButton}
            />

            <FormButton
              title="Concluir"
              onPress={handleFinish}
              loading={loading}
              disabled={selectedDislikes.length < 3}
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
  title: {
    fontSize: 32,
    fontWeight: "500",
    fontFamily: "Poppins_Medium",
    marginBottom: 8,
  },
  subtitle: {
    fontSize: 24,
    fontWeight: "500",
    fontFamily: "Poppins_Medium",
  },
  description: {
    fontSize: 18,
    fontWeight: "500",
    fontFamily: "Poppins_Medium",
    marginBottom: 20,
    paddingHorizontal: 20,
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
  preferencesGrid: {
    flexDirection: "row",
    flexWrap: "wrap",
    alignItems: "flex-start",
    marginBottom: 30,
  },
  preferenceButton: {
    flexDirection: "row",
    alignItems: "center",
    backgroundColor: "#ff8945",
    borderRadius: 12,
    paddingHorizontal: 12,
    paddingVertical: 8,
    marginBottom: 12,
    marginRight: 8,
  },
  preferenceButtonSelected: {
    backgroundColor: primaryGreen,
  },
  preferenceText: {
    fontSize: 14,
    fontFamily: "Poppins_Regular",
    color: darkGrey,
    flexShrink: 0,
  },
  preferenceTextSelected: {
    color: "#fff",
  },
  plusIcon: {
    marginLeft: "auto",
  },
  buttonRow: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  skipButton: {
    flex: 1,
    marginRight: 8,
  },
  nextButton: {
    flex: 1,
    marginLeft: 8,
  },
});
