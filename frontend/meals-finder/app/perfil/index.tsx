import React, { useCallback, useEffect, useState } from "react";
import {
  Image,
  Pressable,
  SafeAreaView,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from "react-native";
import { Redirect, useRouter } from "expo-router";
import { Ionicons } from "@expo/vector-icons";

import { useAuth } from "@/context/AuthContext";
import {
  backWhite,
  darkGrey,
  primaryOrange,
} from "@/constants/Colors";
import { userService, UserStats } from "@/services/userService";

const HEADER_ICON_HIT_SLOP = { top: 8, bottom: 8, left: 8, right: 8 } as const;

type ProfileTab = "publicacoes" | "salvos";

export default function PerfilScreen() {
  const router = useRouter();
  const { user, isAuthenticated, initializing, logout } = useAuth();
  const [activeTab, setActiveTab] = useState<ProfileTab>("publicacoes");
  const [loggingOut, setLoggingOut] = useState(false);
  const [stats, setStats] = useState<UserStats | null>(null);

  const userId = user?.id;

  const loadStats = useCallback(async () => {
    if (!userId) return;
    try {
      const result = await userService.getStats(userId, userId);
      setStats(result);
    } catch {
      setStats(null);
    }
  }, [userId]);

  useEffect(() => {
    loadStats();
  }, [loadStats]);

  if (initializing) {
    return (
      <SafeAreaView style={styles.safeArea}>
        <View style={styles.center}>
          <Text style={styles.mutedText}>Carregando...</Text>
        </View>
      </SafeAreaView>
    );
  }

  if (!isAuthenticated || !user) {
    return <Redirect href="/auth/login" />;
  }

  const displayName = user.username || user.email.split("@")[0];
  const handle = `@${user.username || user.email.split("@")[0]}`;
  const bio =
    (user.bio && String(user.bio).trim()) ||
    "Adicione uma bio para contar um pouco sobre você.";
  const initials = displayName.slice(0, 2).toUpperCase();

  const handleLogout = async () => {
    try {
      setLoggingOut(true);
      await logout();
      router.replace("/auth/login");
    } finally {
      setLoggingOut(false);
    }
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <ScrollView
        contentContainerStyle={styles.scrollContent}
        showsVerticalScrollIndicator={false}
      >
        <View style={styles.headerActions}>
          <Pressable
            onPress={() => router.back()}
            hitSlop={HEADER_ICON_HIT_SLOP}
            accessibilityLabel="Voltar"
          >
            <Ionicons name="chevron-back" size={24} color={darkGrey} />
          </Pressable>
          <View style={styles.headerActionsRight}>
            <Pressable
              hitSlop={HEADER_ICON_HIT_SLOP}
              accessibilityLabel="Editar perfil"
            >
              <Ionicons name="create-outline" size={22} color={darkGrey} />
            </Pressable>
            <Pressable
              hitSlop={HEADER_ICON_HIT_SLOP}
              accessibilityLabel="Compartilhar"
            >
              <Ionicons name="paper-plane-outline" size={22} color={darkGrey} />
            </Pressable>
            <Pressable
              onPress={handleLogout}
              hitSlop={HEADER_ICON_HIT_SLOP}
              accessibilityLabel="Sair"
              disabled={loggingOut}
            >
              <Ionicons
                name="log-out-outline"
                size={24}
                color={loggingOut ? "#aaa" : darkGrey}
              />
            </Pressable>
          </View>
        </View>

        <View style={styles.profileHeader}>
          <View style={styles.avatarWrapper}>
            {user.profilePictureUrl ? (
              <Image
                source={{ uri: String(user.profilePictureUrl) }}
                style={styles.avatar}
              />
            ) : (
              <View style={[styles.avatar, styles.avatarFallback]}>
                <Text style={styles.avatarInitials}>{initials}</Text>
              </View>
            )}
          </View>

          <View style={styles.profileInfo}>
            <Text style={styles.displayName} numberOfLines={1}>
              {displayName}
            </Text>
            <Text style={styles.handle} numberOfLines={1}>
              {handle}
            </Text>

            <View style={styles.statsRow}>
              <Stat label="seguidores" value={stats?.followers ?? 0} />
              <Stat label="seguindo" value={stats?.following ?? 0} />
              <Stat label="publicações" value={stats?.posts ?? 0} />
            </View>
          </View>
        </View>

        <Text style={styles.bio}>{bio}</Text>

        <View style={styles.tabs}>
          <TabButton
            label="Publicações"
            active={activeTab === "publicacoes"}
            onPress={() => setActiveTab("publicacoes")}
          />
          <TabButton
            label="Salvos"
            active={activeTab === "salvos"}
            onPress={() => setActiveTab("salvos")}
          />
        </View>

        <View style={styles.emptyState}>
          <Ionicons
            name={activeTab === "publicacoes" ? "images-outline" : "bookmark-outline"}
            size={48}
            color="#bbb"
          />
          <Text style={styles.emptyTitle}>
            {activeTab === "publicacoes"
              ? "Nenhuma publicação ainda"
              : "Nenhum item salvo ainda"}
          </Text>
          <Text style={styles.emptySubtitle}>
            {activeTab === "publicacoes"
              ? "Suas publicações vão aparecer aqui."
              : "Os pratos que você salvar vão aparecer aqui."}
          </Text>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

function Stat({ label, value }: { label: string; value: number }) {
  return (
    <View style={styles.stat}>
      <Text style={styles.statValue}>{value}</Text>
      <Text style={styles.statLabel}>{label}</Text>
    </View>
  );
}

function TabButton({
  label,
  active,
  onPress,
}: {
  label: string;
  active: boolean;
  onPress: () => void;
}) {
  return (
    <Pressable style={styles.tabButton} onPress={onPress}>
      <Text style={[styles.tabLabel, active && styles.tabLabelActive]}>
        {label}
      </Text>
      <View
        style={[styles.tabIndicator, active && styles.tabIndicatorActive]}
      />
    </Pressable>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: backWhite,
  },
  scrollContent: {
    paddingBottom: 40,
  },
  center: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  mutedText: {
    color: "#777",
    fontFamily: "Poppins_Regular",
  },
  headerActions: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    paddingHorizontal: 16,
    paddingTop: 8,
    paddingBottom: 4,
  },
  headerActionsRight: {
    flexDirection: "row",
    alignItems: "center",
    gap: 18,
  },
  profileHeader: {
    flexDirection: "row",
    alignItems: "center",
    gap: 16,
    paddingHorizontal: 20,
    paddingTop: 16,
  },
  avatarWrapper: {
    width: 90,
    height: 90,
    borderRadius: 45,
    overflow: "hidden",
    borderWidth: 1,
    borderColor: "rgba(0,0,0,0.08)",
  },
  avatar: {
    width: "100%",
    height: "100%",
  },
  avatarFallback: {
    backgroundColor: primaryOrange,
    alignItems: "center",
    justifyContent: "center",
  },
  avatarInitials: {
    color: "#fff",
    fontSize: 28,
    fontWeight: "600",
    fontFamily: "Poppins_Medium",
  },
  profileInfo: {
    flex: 1,
  },
  displayName: {
    fontSize: 18,
    color: darkGrey,
    fontFamily: "Poppins_Medium",
    fontWeight: "500",
  },
  handle: {
    fontSize: 14,
    color: "#383838",
    fontFamily: "Poppins_Regular",
    marginTop: 2,
  },
  statsRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginTop: 14,
  },
  stat: {
    alignItems: "center",
    flex: 1,
  },
  statValue: {
    fontSize: 14,
    color: "#383838",
    fontFamily: "Poppins_Medium",
    fontWeight: "500",
  },
  statLabel: {
    fontSize: 12,
    color: "#383838",
    fontFamily: "Poppins_Regular",
    marginTop: 2,
  },
  bio: {
    paddingHorizontal: 20,
    marginTop: 16,
    fontSize: 14,
    lineHeight: 20,
    color: "#000",
    fontFamily: "Poppins_Regular",
  },
  tabs: {
    flexDirection: "row",
    marginTop: 24,
    borderBottomWidth: StyleSheet.hairlineWidth,
    borderBottomColor: "rgba(0,0,0,0.1)",
  },
  tabButton: {
    flex: 1,
    alignItems: "center",
    paddingVertical: 14,
  },
  tabLabel: {
    fontSize: 16,
    color: darkGrey,
    fontFamily: "Poppins_Medium",
  },
  tabLabelActive: {
    color: primaryOrange,
  },
  tabIndicator: {
    marginTop: 10,
    height: 3,
    width: 60,
    borderRadius: 100,
    backgroundColor: "transparent",
  },
  tabIndicatorActive: {
    backgroundColor: primaryOrange,
  },
  emptyState: {
    alignItems: "center",
    justifyContent: "center",
    paddingVertical: 60,
    paddingHorizontal: 32,
    gap: 8,
  },
  emptyTitle: {
    fontSize: 16,
    color: darkGrey,
    fontFamily: "Poppins_Medium",
    fontWeight: "500",
    marginTop: 8,
  },
  emptySubtitle: {
    fontSize: 13,
    color: "#777",
    fontFamily: "Poppins_Regular",
    textAlign: "center",
  },
});
