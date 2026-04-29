import { StyleSheet, View, Text, SafeAreaView } from "react-native";
import Colors from "@/constants/Colors";
import { useColorScheme } from "@/components/useColorScheme";

export default function ProfileScreen() {
  const colorScheme = useColorScheme() ?? "light";
  const colors = Colors[colorScheme as keyof typeof Colors];

  return (
    <SafeAreaView
      style={[styles.container, { backgroundColor: colors.background }]}
    >
      <Text style={[styles.title, { color: colors.text }]}>Meu Perfil</Text>
      <View style={styles.separator} />
      <Text style={[styles.message, { color: colors.text }]}>
        Veja e edite seu perfil aqui
      </Text>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    paddingHorizontal: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 16,
  },
  separator: {
    marginVertical: 16,
    height: 1,
    width: "80%",
    backgroundColor: "rgba(0, 0, 0, 0.1)",
  },
  message: {
    fontSize: 16,
    textAlign: "center",
  },
});
