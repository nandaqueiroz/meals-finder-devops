import React from "react";
import {
  TextInput as RNTextInput,
  StyleSheet,
  ViewStyle,
  TextInputProps as RNTextInputProps,
} from "react-native";
import Colors, {
  darkGrey,
  textFieldBorder,
  placeholder as placeholderColor,
  backWhite,
} from "@/constants/Colors";
import { useColorScheme } from "./useColorScheme";

interface FormTextInputProps extends RNTextInputProps {
  containerStyle?: ViewStyle;
  error?: boolean;
  errorColor?: string;
}

export function FormTextInput({
  containerStyle,
  error = false,
  errorColor = "#ff6b6b",
  style,
  placeholderTextColor,
  ...props
}: FormTextInputProps) {
  const colorScheme = useColorScheme() ?? "light";
  const colors = Colors[colorScheme as keyof typeof Colors];

  const borderColor = error ? errorColor : colors.border;

  return (
    <RNTextInput
      {...props}
      placeholderTextColor={placeholderTextColor || placeholderColor}
      style={[
        styles.input,
        {
          borderColor,
          color: colors.text,
          backgroundColor: backWhite,
        },
        style,
      ]}
    />
  );
}

const styles = StyleSheet.create({
  input: {
    height: 51,
    borderRadius: 20,
    borderWidth: 2,
    paddingHorizontal: 20,
    paddingVertical: 15,
    fontFamily: "Poppins_Regular",
    fontSize: 16,
  },
});
