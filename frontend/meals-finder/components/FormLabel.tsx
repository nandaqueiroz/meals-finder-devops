import React from "react";
import { Text, StyleSheet, TextProps } from "react-native";
import Colors, { darkGrey } from "@/constants/Colors";
import { useColorScheme } from "./useColorScheme";

interface FormLabelProps extends TextProps {
  label: string;
  required?: boolean;
  requiredColor?: string;
}

export function FormLabel({
  label,
  required = false,
  requiredColor = "#ff6b6b",
  style,
  ...props
}: FormLabelProps) {
  const colorScheme = useColorScheme() ?? "light";
  const colors = Colors[colorScheme as keyof typeof Colors];

  return (
    <Text
      {...props}
      style={[
        styles.label,
        {
          color: colors.text,
        },
        style,
      ]}
    >
      {label}
      {required && (
        <Text style={[styles.required, { color: requiredColor }]}>*</Text>
      )}
    </Text>
  );
}

const styles = StyleSheet.create({
  label: {
    fontSize: 14,
    fontWeight: "500",
    fontFamily: "Poppins_Medium",
    marginBottom: 8,
  },
  required: {
    fontSize: 14,
    fontWeight: "500",
    marginLeft: 2,
  },
});
