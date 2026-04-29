import React, { useState, useEffect } from "react";
import {
  StyleSheet,
  View,
  Text,
  SafeAreaView,
  ScrollView,
  Image,
  Pressable,
  FlatList,
} from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import {
  FontAwesome,
  AntDesign,
  MaterialCommunityIcons,
} from "@expo/vector-icons";
import Colors, { primaryOrange, darkGrey } from "@/constants/Colors";
import { useColorScheme } from "@/components/useColorScheme";
import { apiService } from "@/services/apiService";

// Import images
const post1Image = require("../../assets/images/post1.png");
const post2Image = require("../../assets/images/post2.jpg");
const post3Image = require("../../assets/images/post3.png");
const post4Image = require("../../assets/images/post4.png");
const profile1Image = require("../../assets/images/profile1.png");
const profile2Image = require("../../assets/images/profile2.jpg");
const profile3Image = require("../../assets/images/profile3.png");

const mockPosts = [
  {
    id: "1",
    userId: "user_1",
    username: "@victoriahilgert",
    userAvatar: profile1Image,
    date: "29/01/2025",
    rating: 4,
    restaurantName: "SushiFan",
    price: 59.9,
    content:
      "Amei, sou viciada em sushi, só vou nesse lugar daqui pra frente!!",
    tags: ["sushi", "ambiente agradável", "bom atendimento"],
    image: post1Image,
    likes: 221,
    comments: 16,
    saves: 3,
  },
  {
    id: "2",
    userId: "user_2",
    username: "@mafe_falcao",
    userAvatar: profile2Image,
    date: "26/01/2025",
    rating: 4,
    restaurantName: "Suco Bagaço",
    price: 39.9,
    content:
      "a salada não estava muito boa, o alface estava um pouco murcho e faltava tempero",
    tags: ["fitness", "faltou tempero", "bom atendimento"],
    image: post2Image,
    likes: 145,
    comments: 8,
    saves: 2,
  },
  {
    id: "3",
    userId: "user_3",
    username: "@joao_chef",
    userAvatar: profile3Image,
    date: "24/01/2025",
    rating: 5,
    restaurantName: "Pizzaria Napoli",
    price: 85.5,
    content:
      "Melhor pizza que já comi! A massa é crocante e o molho é fresco. Recomendo muito!",
    tags: ["italiana", "ambiente agradável", "bom atendimento"],
    image: post3Image,
    likes: 412,
    comments: 24,
    saves: 15,
  },
  {
    id: "4",
    userId: "user_4",
    username: "@ana_saude",
    userAvatar: profile1Image,
    date: "22/01/2025",
    rating: 5,
    restaurantName: "Natural Vida",
    price: 45.0,
    content:
      "Comida organicamente levantando meu astral. Tudo muito fresco e saudável!",
    tags: ["vegana", "fitness", "ambiente agradável"],
    image: post4Image,
    likes: 356,
    comments: 19,
    saves: 12,
  },
];

export default function ExploreScreen() {
  const colorScheme = useColorScheme() ?? "light";
  const colors = Colors[colorScheme as keyof typeof Colors];
  const [location, setLocation] = useState("São Carlos - SP");
  const [likedPosts, setLikedPosts] = useState<Set<string>>(new Set());
  const [savedPosts, setSavedPosts] = useState<Set<string>>(new Set());

  // Carregar dados salvos quando o componente montar
  useEffect(() => {
    const loadUserActivities = async () => {
      const userId = "user_1"; // TODO: Get from authentication context
      try {
        const [likes, saves] = await Promise.all([
          apiService.getUserLikes(userId),
          apiService.getUserSaves(userId),
        ]);

        const likedPostIds = new Set(
          likes.map((activity) => activity.entityId),
        );
        const savedPostIds = new Set(
          saves.map((activity) => activity.entityId),
        );

        setLikedPosts(likedPostIds);
        setSavedPosts(savedPostIds);
      } catch (error) {
        console.error("Erro ao carregar atividades do usuário:", error);
        // Fallback to AsyncStorage if API fails
        try {
          const likedData = await AsyncStorage.getItem("likedPosts");
          const savedData = await AsyncStorage.getItem("savedPosts");
          if (likedData) {
            setLikedPosts(new Set(JSON.parse(likedData)));
          }
          if (savedData) {
            setSavedPosts(new Set(JSON.parse(savedData)));
          }
        } catch (fallbackError) {
          console.error("Erro no fallback para AsyncStorage:", fallbackError);
        }
      }
    };

    loadUserActivities();
  }, []);

  const toggleLike = async (postId: string) => {
    const userId = "user_1"; // TODO: Get from authentication context
    try {
      const isCurrentlyLiked = likedPosts.has(postId);
      if (isCurrentlyLiked) {
        await apiService.unlikeEntity(userId, "POST", postId);
        setLikedPosts((prev) => {
          const newSet = new Set(prev);
          newSet.delete(postId);
          return newSet;
        });
      } else {
        await apiService.likeEntity(userId, "POST", postId);
        setLikedPosts((prev) => {
          const newSet = new Set(prev);
          newSet.add(postId);
          return newSet;
        });
      }
    } catch (error) {
      console.error("Erro ao toggle like:", error);
      // Fallback to local state if API fails
      setLikedPosts((prev) => {
        const newSet = new Set(prev);
        if (newSet.has(postId)) {
          newSet.delete(postId);
        } else {
          newSet.add(postId);
        }
        return newSet;
      });
    }
  };

  const toggleSave = async (postId: string) => {
    const userId = "user_1"; // TODO: Get from authentication context
    try {
      const isCurrentlySaved = savedPosts.has(postId);
      if (isCurrentlySaved) {
        await apiService.unsaveEntity(userId, "POST", postId);
        setSavedPosts((prev) => {
          const newSet = new Set(prev);
          newSet.delete(postId);
          return newSet;
        });
      } else {
        await apiService.saveEntity(userId, "POST", postId);
        setSavedPosts((prev) => {
          const newSet = new Set(prev);
          newSet.add(postId);
          return newSet;
        });
      }
    } catch (error) {
      console.error("Erro ao toggle save:", error);
      // Fallback to local state if API fails
      setSavedPosts((prev) => {
        const newSet = new Set(prev);
        if (newSet.has(postId)) {
          newSet.delete(postId);
        } else {
          newSet.add(postId);
        }
        return newSet;
      });
    }
  };

  const renderStar = (rating: number, index: number) => (
    <FontAwesome
      key={index}
      name={index < rating ? "star" : "star-o"}
      size={14}
      color={primaryOrange}
      style={{ marginRight: 2 }}
    />
  );

  const renderPost = ({ item }: { item: (typeof mockPosts)[0] }) => (
    <View style={[styles.postCard, { backgroundColor: colors.background }]}>
      {/* User Info */}
      <View style={styles.userHeader}>
        <View style={styles.userInfo}>
          <Image source={item.userAvatar} style={styles.avatar} />
          <View style={styles.userDetails}>
            <Text style={[styles.username, { color: colors.text }]}>
              {item.username}
            </Text>
            <Text style={[styles.restaurant, { color: primaryOrange }]}>
              📍 {item.restaurantName}
            </Text>
          </View>
        </View>
        <View style={styles.priceDate}>
          <Text style={[styles.date, { color: darkGrey }]}>{item.date}</Text>
          <Text style={[styles.price, { color: primaryOrange }]}>
            $ {item.price.toFixed(2)}
          </Text>
        </View>
      </View>

      {/* Rating */}
      <View style={styles.ratingContainer}>
        {Array.from({ length: 5 }).map((_, i) => renderStar(item.rating, i))}
      </View>

      {/* Content */}
      <Text style={[styles.content, { color: colors.text }]}>
        {item.content}
      </Text>

      {/* Tags */}
      <View style={styles.tagsContainer}>
        {item.tags.map((tag, idx) => (
          <View
            key={idx}
            style={[styles.tag, { backgroundColor: primaryOrange }]}
          >
            <Text style={styles.tagText}>{tag}</Text>
          </View>
        ))}
      </View>

      {/* Image */}
      <Image source={item.image} style={styles.postImage} />

      {/* Interactions */}
      <View style={styles.interactions}>
        <Pressable
          style={styles.interactionGroup}
          onPress={() => toggleLike(item.id)}
        >
          <AntDesign
            name="heart"
            size={20}
            color={likedPosts.has(item.id) ? primaryOrange : darkGrey}
          />
          <Text style={[styles.interactionText, { color: darkGrey }]}>
            {item.likes + (likedPosts.has(item.id) ? 1 : 0)}
          </Text>
        </Pressable>
        <View style={styles.interactionGroup}>
          <FontAwesome name="comment-o" size={20} color={darkGrey} />
          <Text style={[styles.interactionText, { color: darkGrey }]}>
            {item.comments}
          </Text>
        </View>
        <Pressable
          style={styles.interactionGroup}
          onPress={() => toggleSave(item.id)}
        >
          <MaterialCommunityIcons
            name={savedPosts.has(item.id) ? "bookmark" : "bookmark-outline"}
            size={20}
            color={savedPosts.has(item.id) ? primaryOrange : darkGrey}
          />
          <Text style={[styles.interactionText, { color: darkGrey }]}>
            {item.saves + (savedPosts.has(item.id) ? 1 : 0)}
          </Text>
        </Pressable>
        <View style={styles.interactionGroup}>
          <MaterialCommunityIcons
            name="dots-horizontal"
            size={20}
            color={darkGrey}
          />
        </View>
        <View style={styles.interactionGroup}>
          <FontAwesome name="share" size={20} color={darkGrey} />
        </View>
      </View>
    </View>
  );

  return (
    <SafeAreaView
      style={[styles.safeArea, { backgroundColor: colors.background }]}
    >
      {/* Header */}
      <View style={[styles.header, { backgroundColor: colors.background }]}>
        <View style={styles.tabsContainer}>
          <Text style={[styles.tabInactive, { color: darkGrey }]}>
            Seguindo
          </Text>
          <Text style={[styles.tabActive, { color: primaryOrange }]}>
            Explorar
          </Text>
        </View>

        {/* Location */}
        <View style={styles.locationFilter}>
          <View style={styles.locationGroup}>
            <FontAwesome name="map-marker" size={18} color={darkGrey} />
            <Text style={[styles.locationText, { color: darkGrey }]}>
              {location}
            </Text>
          </View>

          <View style={styles.filterButtons}>
            <Pressable style={styles.filterButton}>
              <FontAwesome name="sliders" size={18} color={darkGrey} />
              <Text style={[styles.filterText, { color: darkGrey }]}>
                Filtrar
              </Text>
            </Pressable>

            <Pressable style={styles.filterButton}>
              <MaterialCommunityIcons
                name="sort-variant"
                size={18}
                color={darkGrey}
              />
              <Text style={[styles.filterText, { color: darkGrey }]}>
                Ordenar por
              </Text>
            </Pressable>
          </View>
        </View>
      </View>

      {/* Posts Feed */}
      <FlatList
        data={mockPosts}
        renderItem={renderPost}
        keyExtractor={(item) => item.id}
        scrollEnabled={true}
        contentContainerStyle={styles.feedContainer}
        showsVerticalScrollIndicator={false}
      />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
  },
  header: {
    paddingHorizontal: 20,
    paddingVertical: 16,
  },
  tabsContainer: {
    flexDirection: "row",
    justifyContent: "flex-start",
    marginBottom: 20,
  },
  tabInactive: {
    fontSize: 16,
    fontWeight: "500",
    marginRight: 32,
  },
  tabActive: {
    fontSize: 16,
    fontWeight: "600",
    borderBottomWidth: 3,
    borderBottomColor: primaryOrange,
    paddingBottom: 4,
  },
  locationFilter: {
    marginBottom: 16,
  },
  locationGroup: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 12,
  },
  locationText: {
    fontSize: 14,
    fontWeight: "500",
    marginLeft: 8,
  },
  filterButtons: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  filterButton: {
    flexDirection: "row",
    alignItems: "center",
    paddingHorizontal: 12,
    paddingVertical: 8,
    borderRadius: 8,
    backgroundColor: "rgba(200, 200, 200, 0.1)",
    marginRight: 12,
  },
  filterText: {
    fontSize: 12,
    fontWeight: "500",
    marginLeft: 6,
  },
  feedContainer: {
    paddingHorizontal: 16,
    paddingVertical: 12,
  },
  postCard: {
    borderRadius: 16,
    padding: 16,
    marginBottom: 16,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.08,
    shadowRadius: 8,
    elevation: 2,
  },
  userHeader: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "flex-start",
    marginBottom: 12,
  },
  userInfo: {
    flexDirection: "row",
    alignItems: "flex-start",
    flex: 1,
  },
  avatar: {
    width: 52,
    height: 52,
    borderRadius: 26,
    marginRight: 12,
  },
  userDetails: {
    flex: 1,
  },
  username: {
    fontSize: 14,
    fontWeight: "600",
    marginBottom: 4,
  },
  restaurant: {
    fontSize: 13,
    fontWeight: "500",
  },
  priceDate: {
    alignItems: "flex-end",
  },
  date: {
    fontSize: 12,
    fontWeight: "400",
    marginBottom: 4,
  },
  price: {
    fontSize: 14,
    fontWeight: "600",
  },
  ratingContainer: {
    flexDirection: "row",
    marginBottom: 12,
    alignItems: "center",
  },
  content: {
    fontSize: 14,
    fontWeight: "400",
    lineHeight: 20,
    marginBottom: 12,
  },
  tagsContainer: {
    flexDirection: "row",
    flexWrap: "wrap",
    marginBottom: 12,
    gap: 8,
  },
  tag: {
    borderRadius: 12,
    paddingHorizontal: 12,
    paddingVertical: 6,
  },
  tagText: {
    fontSize: 12,
    fontWeight: "500",
    color: "#fff",
  },
  postImage: {
    width: "100%",
    height: 200,
    borderRadius: 12,
    marginBottom: 12,
  },
  interactions: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    paddingTop: 12,
    borderTopWidth: 1,
    borderTopColor: "rgba(0, 0, 0, 0.1)",
  },
  interactionGroup: {
    flexDirection: "row",
    alignItems: "center",
    gap: 6,
  },
  interactionText: {
    fontSize: 12,
    fontWeight: "500",
  },
});
