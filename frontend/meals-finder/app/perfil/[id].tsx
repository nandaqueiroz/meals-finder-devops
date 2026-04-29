import React, { useCallback, useEffect, useMemo, useState } from "react";
import {
  ActivityIndicator,
  Image,
  Pressable,
  SafeAreaView,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from "react-native";
import { Redirect, useLocalSearchParams, useRouter } from "expo-router";
import { Ionicons } from "@expo/vector-icons";

import { useAuth } from "@/context/AuthContext";
import {
  backWhite,
  darkGrey,
  primaryOrange,
} from "@/constants/Colors";
import { userService, UserStats } from "@/services/userService";
import { AuthenticatedUser } from "@/services/authService";

const HEADER_ICON_HIT_SLOP = { top: 8, bottom: 8, left: 8, right: 8 } as const;

export default function PerfilDeOutroUsuario() {
  const router = useRouter();
  const { id } = useLocalSearchParams<{ id: string }>();
  const { user: viewer, isAuthenticated, initializing } = useAuth();

  const [profile, setProfile] = useState<AuthenticatedUser | null>(null);
  const [stats, setStats] = useState<UserStats | null>(null);
  const [loading, setLoading] = useState(true);
  const [loadError, setLoadError] = useState("");
  const [followBusy, setFollowBusy] = useState(false);
  const [followError, setFollowError] = useState("");

  const isOwnProfile = useMemo(
    () => Boolean(viewer && id && viewer.id === id),
    [viewer, id],
  );

  const loadProfile = useCallback(async () => {
    if (!id) return;
    setLoading(true);
    setLoadError("");
    try {
      const [user, userStats] = await Promise.all([
        userService.getUser(id),
        userService.getStats(id, viewer?.id ?? null),
      ]);
      setProfile(user);
      setStats(userStats);
    } catch (err) {
      setLoadError(
        err instanceof Error ? err.message : "Não foi possível carregar o perfil",
      );
    } finally {
      setLoading(false);
    }
  }, [id, viewer?.id]);

  useEffect(() => {
    loadProfile();
  }, [loadProfile]);

  if (initializing) {
    return (
      <SafeAreaView style={styles.safeArea}>
        <View style={styles.center}>
          <ActivityIndicator color={primaryOrange} />
        </View>
      </SafeAreaView>
    );
  }

  if (!isAuthenticated) {
    return <Redirect href="/auth/login" />;
  }

  if (isOwnProfile) {
    return <Redirect href="/perfil" />;
  }

  const displayName =
    profile?.username || profile?.email?.split("@")[0] || "...";
  const handle = `@${profile?.username || profile?.email?.split("@")[0] || ""}`;
  const bio =
    (profile?.bio && String(profile.bio).trim()) ||
    "Esse usuário ainda não escreveu uma bio.";
  const initials = displayName.slice(0, 2).toUpperCase();

  const handleToggleFollow = async () => {
    if (!viewer || !id || !stats) return;
    setFollowError("");
    setFollowBusy(true);

    const previous = stats;
    const optimistic: UserStats = stats.isFollowing
      ? {
          ...stats,
          isFollowing: false,
          followers: Math.max(0, stats.followers - 1),
        }
      : { ...stats, isFollowing: true, followers: stats.followers + 1 };
    setStats(optimistic);

    try {
      if (previous.isFollowing) {
        await userService.unfollow(viewer.id, id);
      } else {
        await userService.follow(viewer.id, id);
      }
    } catch (err) {
      setStats(previous);
      setFollowError(
        err instanceof Error ? err.message : "Falha na ação. Tente novamente.",
      );
    } finally {
      setFollowBusy(false);
    }
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <ScrollView
        contentContainerStyle={styles.scrollContent}
        showsVerticalScrollIndicator={false}
      >
        <View style={styles.headerRow}>
          <Pressable
            onPress={() => router.back()}
            hitSlop={HEADER_ICON_HIT_SLOP}
            style={styles.backCircle}
            accessibilityLabel="Voltar"
          >
            <Ionicons name="chevron-back" size={20} color="#fff" />
          </Pressable>
          <View style={styles.headerActionsRight}>
            <Pressable
              hitSlop={HEADER_ICON_HIT_SLOP}
              accessibilityLabel="Compartilhar"
            >
              <Ionicons name="paper-plane-outline" size={22} color={darkGrey} />
            </Pressable>
            <Pressable
              hitSlop={HEADER_ICON_HIT_SLOP}
              accessibilityLabel="Mais opções"
            >
              <Ionicons name="ellipsis-horizontal" size={22} color={darkGrey} />
            </Pressable>
          </View>
        </View>

        {loading ? (
          <View style={[styles.center, { paddingTop: 80 }]}>
            <ActivityIndicator color={primaryOrange} />
          </View>
        ) : loadError ? (
          <View style={[styles.center, { paddingTop: 80, paddingHorizontal: 32 }]}>
            <Ionicons name="alert-circle-outline" size={36} color="#bbb" />
            <Text style={styles.errorText}>{loadError}</Text>
            <Pressable onPress={loadProfile} style={styles.retryButton}>
              <Text style={styles.retryButtonText}>Tentar novamente</Text>
            </Pressable>
          </View>
        ) : profile && stats ? (
          <>
            <View style={styles.profileHeader}>
              <View style={styles.avatarWrapper}>
                {profile.profilePictureUrl ? (
                  <Image
                    source={{ uri: String(profile.profilePictureUrl) }}
                    style={styles.avatar}
                  />
                ) : (
                  <View style={[styles.avatar, styles.avatarFallback]}>
                    <Text style={styles.avatarInitials}>{initials}</Text>
                  </View>
                )}
              </View>

              <View style={styles.profileInfo}>
                <View style={styles.nameRow}>
                  <View style={{ flex: 1 }}>
                    <Text style={styles.displayName} numberOfLines={1}>
                      {displayName}
                    </Text>
                    <Text style={styles.handle} numberOfLines={1}>
                      {handle}
                    </Text>
                  </View>

                  <Pressable
                    onPress={handleToggleFollow}
                    disabled={followBusy}
                    style={[
                      styles.followButton,
                      stats.isFollowing && styles.followButtonActive,
                      followBusy && styles.followButtonBusy,
                    ]}
                    accessibilityRole="button"
                    accessibilityLabel={
                      stats.isFollowing ? "Deixar de seguir" : "Seguir"
                    }
                  >
                    {followBusy ? (
                      <ActivityIndicator size="small" color="#fff" />
                    ) : (
                      <Text style={styles.followButtonLabel}>
                        {stats.isFollowing ? "deixar de seguir" : "seguir"}
                      </Text>
                    )}
                  </Pressable>
                </View>

                <View style={styles.statsRow}>
                  <Stat label="seguidores" value={stats.followers} />
                  <Stat label="seguindo" value={stats.following} />
                  <Stat label="publicações" value={stats.posts} />
                </View>
              </View>
            </View>

            {followError ? (
              <Text style={styles.followErrorText}>{followError}</Text>
            ) : null}

            <Text style={styles.bio}>{bio}</Text>

            <View style={styles.tabs}>
              <View style={styles.tabButton}>
                <Text style={[styles.tabLabel, styles.tabLabelActive]}>
                  Publicações
                </Text>
                <View style={[styles.tabIndicator, styles.tabIndicatorActive]} />
              </View>
            </View>

            <View style={styles.emptyState}>
              <Ionicons name="images-outline" size={48} color="#bbb" />
              <Text style={styles.emptyTitle}>
                {stats.posts === 0
                  ? "Nenhuma publicação ainda"
                  : `${stats.posts} publicação(ões)`}
              </Text>
              <Text style={styles.emptySubtitle}>
                A grade de publicações ainda não está implementada.
              </Text>
            </View>
          </>
        ) : null}
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

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: backWhite,
  },
  scrollContent: {
    paddingBottom: 40,
  },
  center: {
    alignItems: "center",
    justifyContent: "center",
    gap: 12,
  },
  errorText: {
    color: "#666",
    fontFamily: "Poppins_Regular",
    textAlign: "center",
  },
  retryButton: {
    marginTop: 8,
    paddingVertical: 8,
    paddingHorizontal: 18,
    borderRadius: 10,
    backgroundColor: primaryOrange,
  },
  retryButtonText: {
    color: "#fff",
    fontFamily: "Poppins_Medium",
  },
  headerRow: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    paddingHorizontal: 16,
    paddingTop: 8,
    paddingBottom: 4,
  },
  backCircle: {
    width: 36,
    height: 36,
    borderRadius: 18,
    backgroundColor: primaryOrange,
    alignItems: "center",
    justifyContent: "center",
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
  nameRow: {
    flexDirection: "row",
    alignItems: "flex-start",
    gap: 8,
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
  followButton: {
    backgroundColor: primaryOrange,
    paddingHorizontal: 12,
    paddingVertical: 5,
    borderRadius: 10,
    minWidth: 70,
    alignItems: "center",
    justifyContent: "center",
  },
  followButtonActive: {
    paddingHorizontal: 14,
  },
  followButtonBusy: {
    opacity: 0.8,
  },
  followButtonLabel: {
    color: "#fff",
    fontSize: 12,
    fontFamily: "Poppins_Regular",
  },
  followErrorText: {
    color: "#ff6b6b",
    fontSize: 12,
    fontFamily: "Poppins_Regular",
    textAlign: "center",
    marginTop: 8,
    paddingHorizontal: 20,
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
    justifyContent: "center",
  },
  tabButton: {
    alignItems: "center",
    paddingVertical: 14,
    paddingHorizontal: 32,
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
    width: 80,
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
