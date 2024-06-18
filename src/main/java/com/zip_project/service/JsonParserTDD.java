package com.zip_project.service;

public class JsonParserTDD {

	JsonParserTDD() {
	}

	public static boolean isJsonNull(String input) {
		return input == null || input.equals("null");
	}

	public static boolean isEmptyString(String input) {
		return input.trim().isEmpty();
	}

	public static boolean isDoubleQuotesString(String input) {
		return input.trim().startsWith("\"") && input.trim().endsWith("\"");
	}

	public static boolean isJsonString(String input) {
		input = input.trim();
		return input.length() >= 2 && input.startsWith("\"")
				&& input.endsWith("\"");
	}

	public static boolean isJsonObject(String input) {
		input = input.trim();
		return input.startsWith("{") && input.endsWith("}");
	}

	public static boolean isJsonArray(String input) {
		input = input.trim();
		return input.startsWith("[") && input.endsWith("]");
	}

	public static boolean isJsonLong(String input) {
		try {
			Long.parseLong(input.trim());
			return true;
		} catch (NumberFormatException | NullPointerException e) {
			return false;
		}
	}

	public static boolean isJsonNumber(String input) {
		try {
			Integer.parseInt(input.trim());
			return true;
		} catch (NumberFormatException | NullPointerException e) {
			return false;
		}
	}

	public static boolean isJsonDouble(String input) {
		input = input.trim();
		// Check if the input contains a decimal point
		if (!input.contains(".")) {
			return false;
		}

		try {
			Double.parseDouble(input);
			return true;
		} catch (NumberFormatException | NullPointerException e) {
			return false;
		}
	}

	public static boolean isJsonBoolean(String input) {
		input = input.trim().toLowerCase();
		return "true".equals(input) || "false".equals(input);
	}

	public static boolean isJsonAttribute(String input) {
		if (input == null || input.trim().isEmpty()) {
			return false;
		}

		input = input.trim();
		int colonIndex = input.indexOf(':');
		if (colonIndex == -1) {
			return false; // No colon found
		}

		String attributeName = input.substring(0, colonIndex).trim();
		String attributeValue = input.substring(colonIndex + 1).trim();

		return isJsonString(attributeName) && (isJsonNull(attributeValue)
				|| isEmptyString(attributeName) || isJsonString(attributeValue)
				|| isJsonNumber(attributeValue) || isJsonDouble(attributeValue)
				|| isJsonBoolean(attributeValue) || isJsonObject(attributeValue)
				|| isJsonArray(attributeValue));
	}
}
