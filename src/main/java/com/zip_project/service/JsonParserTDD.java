package com.zip_project.service;

public class JsonParserTDD {

	JsonParserTDD() {
	}

	public static boolean isJsonNull(String input) {
		return input == null;
	}

	public static boolean isEmptyString(String input) {
		return input.trim().isEmpty();
	}

	public static boolean isDoubleQuotesString(String input) {
		return input.trim().startsWith("\"") && input.trim().endsWith("\"");
	}

	public static boolean isJsonString(String input) {
		if (input == null || input.trim().isEmpty()) {
			return true;
		}
		input = input.trim();
		return input.length() >= 2 && input.startsWith("\"")
				&& input.endsWith("\"");
	}

	public static boolean isJsonObject(String input) {
		if (input == null || input.trim().isEmpty()) {
			return false;
		}
		input = input.trim();
		return input.startsWith("{") && input.endsWith("}");
	}

	public static boolean isJsonArray(String input) {
		if (input == null || input.trim().isEmpty()) {
			return false;
		}
		input = input.trim();
		return input.startsWith("[") && input.endsWith("]");
	}

	public static boolean isJsonLong(String input) {
		if (input == null || input.trim().isEmpty()) {
			return false;
		}
		try {
			Long.parseLong(input.trim());
			return true;
		} catch (NumberFormatException | NullPointerException e) {
			return false;
		}
	}

	public static boolean isJsonInteger(String input) {
		if (input == null || input.trim().isEmpty()) {
			return false;
		}
		try {
			Integer.parseInt(input.trim());
			return true;
		} catch (NumberFormatException | NullPointerException e) {
			return false;
		}
	}

	public static boolean isJsonDouble(String input) {
		if (input == null || input.trim().isEmpty()) {
			return false;
		}

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
		if (input == null || input.trim().isEmpty()) {
			return false;
		}
		input = input.trim().toLowerCase();
		return "true".equals(input) || "false".equals(input);
	}
}
