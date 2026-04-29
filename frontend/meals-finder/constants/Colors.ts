const tintColorLight = "#2f95dc";
const tintColorDark = "#fff";

// MealsFinder Design System Colors
export const primaryOrange = "#fb5d21";
export const primaryGreen = "#415e42";
export const backWhite = "#f9f9f9";
export const darkGrey = "#1e1e1e";
export const grey = "#444";
export const placeholder = "#999";
export const textFieldBorder = "rgba(30, 30, 30, 0.4)";
export const lightBorder = "rgba(255, 255, 255, 0.1)";
export const lightText = "#f9f9f9";

export default {
  light: {
    text: darkGrey,
    background: backWhite,
    backgroundCards: lightText,
    tint: primaryGreen,
    tabIconDefault: "#ccc",
    tabIconSelected: primaryGreen,
    primary: primaryGreen,
    secondary: primaryOrange,
    border: textFieldBorder,
    placeholder: placeholder,
  },
  dark: {
    text: lightText,
    background: darkGrey,
    backgroundCards: grey,
    tint: tintColorDark,
    tabIconDefault: "#ccc",
    tabIconSelected: tintColorDark,
    primary: primaryGreen,
    secondary: primaryOrange,
    border: lightBorder,
    placeholder: placeholder,
  },
};
