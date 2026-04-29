import React from "react";
import {
  Pressable,
  StyleSheet,
  ViewStyle,
  Text,
  PressableProps,
  ActivityIndicator,
} from "react-native";
import Colors, { primaryGreen, lightText } from "@/constants/Colors";
import { useColorScheme } from "./useColorScheme";

interface FormButtonProps extends Omit<PressableProps, "style"> {
  title: string;
  variant?: "primary" | "secondary" | "outlined";
  size?: "small" | "medium" | "large";
  loading?: boolean;
  disabled?: boolean;
  containerStyle?: ViewStyle;
  style?: ViewStyle;
}

export function FormButton({
  title,
  variant = "primary",
  size = "medium",
  loading = false,
  disabled = false,
  containerStyle,
  style,
  ...props
}: FormButtonProps) {
  const colorScheme = useColorScheme() ?? "light";
  const colors = Colors[colorScheme as keyof typeof Colors];

  const getButtonStyles = () => {
    let backgroundColor = primaryGreen;
    let textColor = lightText;

    if (variant === "secondary") {
      backgroundColor = colors.secondary;
      textColor = lightText;
    } else if (variant === "outlined") {
      backgroundColor = "transparent";
      textColor = colors.primary;
    }

    if (disabled || loading) {
      backgroundColor = colors.border;
      textColor = colors.placeholder;
    }

    return { backgroundColor, textColor };
  };

  const getButtonDimensions = () => {
    switch (size) {
      case "small":
        return { height: 40, paddingHorizontal: 20 };
      case "large":
        return { height: 60, paddingHorizontal: 40 };
      default:
        return { height: 50, paddingHorizontal: 30 };
    }
  };

  const { backgroundColor, textColor } = getButtonStyles();
  const dimensions = getButtonDimensions();

  return (
    <Pressable
      {...props}
      disabled={disabled || loading}
      style={[
        styles.button,
        {
          backgroundColor,
          ...dimensions,
        },
        variant === "outlined" && {
          borderWidth: 2,
          borderColor: colors.primary,
        },
        style,
      ]}
    >
      {loading ? (
        <ActivityIndicator color={textColor} />
      ) : (
        <Text style={[styles.text, { color: textColor }]}>{title}</Text>
      )}
    </Pressable>
  );
}

const styles = StyleSheet.create({
  button: {
    borderRadius: 20,
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "row",
    gap: 8,
  },
  text: {
    fontSize: 18,
    fontWeight: "600",
    fontFamily: "Poppins_SemiBold",
  },
});
