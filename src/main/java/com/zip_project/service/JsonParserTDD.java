package com.zip_project.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonParserTDD {

	JsonParserTDD() {
	}

	public static boolean isJsonNull(String input) {
		return input == null || input.equals("null");
	}

	public static boolean isEmptyString(String input) {
		if (input.length() == 2
				&& (input.startsWith("\"") && input.trim().endsWith("\"")))
			return true;

		return input.trim().isEmpty();
	}

	public static boolean isDoubleQuotesString(String input) {
		return input.trim().startsWith("\"") && input.trim().endsWith("\"");
	}

	public static Boolean isJsonBoolean(String input) {
		input = input.trim().toLowerCase();

		if (input.length() > 5)
			return false;

		return "true".equals(input) || "false".equals(input);
	}

	public static boolean isJsonString(String input) {
		if (input == null) {
			return false;
		}

		input = input.trim();

		if (!input.startsWith("\"") || !input.endsWith("\"")) {
			return false;
		}

		if (input.length() == 2) {
			return true;
		}

		// if the string length is 1, it is invalid (only one quote)
		if (input.length() == 1) {
			return false;
		}

		// remove the outer double quotes to analyze the content
		String content = input.substring(1, input.length() - 1);

		// handling escape characters and special characters
		boolean inEscape = false;
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (inEscape) {
				inEscape = false;
			} else {
				if (c == '\\') {
					// backslash is found, mark the start of an escape character
					inEscape = true;
				} else if (c == '\n' || c == '\r' || c == '\t') {
					// special characters allowed in a JSON string
				} else if (c == '\"') {
					// Invalid JSON string if unescaped quote found inside
					return false;
				}
			}
		}

		// if no open escape character is left the string is valid
		return !inEscape;
	}

	public static boolean isJsonArray(String input) {
		input = input.trim();

		if (!input.startsWith("[") || !input.endsWith("]")) {
			return false;
		}

		// Remove the outer square brackets to analyze the content
		String content = input.substring(1, input.length() - 1).trim();

		int bracketCount = 0;
		boolean inQuote = false;
		boolean inEscape = false;

		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);

			if (inEscape) {
				inEscape = false;
				continue;
			}

			if (c == '\\') {
				inEscape = true;
				continue;
			}

			if (c == '\"') {
				inQuote = !inQuote;
				continue;
			}

			if (!inQuote) {
				if (c == '[') {
					bracketCount++;
				} else if (c == ']') {
					bracketCount--;
					// If closing bracket count exceeds opening, it's invalid
					if (bracketCount < 0) {
						return false;
					}
				}
			}
		}

		// The array is valid if all brackets are properly closed and we are not
		// in a quote
		return bracketCount == 0 && !inQuote;
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

	public static boolean isJsonAttribute(String input) {
		if (input == null || input.trim().isEmpty()) {
			return false;
		}

		input = input.trim();
		int colonIndex = input.indexOf(':');
		if (colonIndex == -1) {
			// No colon found
			return false;
		}

		String attributeName = input.substring(0, colonIndex).trim();
		String attributeValue = input.substring(colonIndex + 1).trim();

		return isJsonString(attributeName) && (isJsonNull(attributeValue)
				|| isEmptyString(attributeName) || isJsonString(attributeValue)
				|| isJsonNumber(attributeValue) || isJsonDouble(attributeValue)
				|| isJsonBoolean(attributeValue) || isJsonObject(attributeValue)
				|| isJsonArray(attributeValue));
	}

	public static boolean isJsonObject(String input) {
		Set<String> attributeNames = new HashSet<>();
		if (input == null) {
			return false;
		}

		input = input.trim();

		// must start and end with curly braces
		if (!input.startsWith("{") || !input.endsWith("}")) {
			return false;
		}

		// remove the outer curly braces
		String content = input.substring(1, input.length() - 1).trim();

		// handle empty object
		if (content.isEmpty()) {
			return true;
		}

		// split content by commas to get individual attributes
		List<String> attributes = splitJsonAttributes(content);

		for (String attribute : attributes) {
			attribute = attribute.trim();

			// validate the attribute
			if (!isJsonAttribute(attribute)) {
				return false;
			}

			// Extract attribute name and value
			Integer colonIndex = attribute.indexOf(':');
			String attributeName = attribute.substring(0, colonIndex).trim();
			String attributeValue = attribute.substring(colonIndex + 1).trim();

			if (isEmptyString(attributeName) || !isJsonString(attributeName)
					|| attributeNames.contains(attributeName)) {
				return false;
			}

			attributeValue = attributeValue.trim();
			// check if attributeValue contains a valid single value
			if (!isSingleJsonValue(attributeValue)) {
				return false;
			}

			// Ensure there is only one JSON object in the input
			if (countJsonValues(attributeValue) != 1) {
				return false;
			}

			attributeNames.add(attributeName);
		}
		return true;
	}

	public static boolean isSingleJsonValue(String input) {
		input = input.trim();

		// Check for nested objects or arrays
		if (input.startsWith("{") && input.endsWith("}")) {
			return isJsonObject(input);
		}

		if (input.startsWith("[") && input.endsWith("]")) {
			return isJsonArray(input);
		}

		if (!(isJsonString(input) || isJsonNumber(input) || isJsonBoolean(input)
				|| isJsonNull(input)))
			return false;

		// Check if input is a valid single JSON value
		int valueCount = countJsonValues(input);
		return valueCount == 1;
	}

	public static List<String> splitJsonAttributes(String content) {
		List<String> attributes = new ArrayList<>();
		int braceCount = 0;
		int bracketCount = 0;
		int startIndex = 0;

		if (content.length() == 0)
			return attributes;

		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (c == '{')
				braceCount++;
			if (c == '}')
				braceCount--;
			if (c == '[')
				bracketCount++;
			if (c == ']')
				bracketCount--;
			if (c == ',' && braceCount == 0 && bracketCount == 0) {
				attributes.add(content.substring(startIndex, i).trim());
				startIndex = i + 1;
			}
		}

		attributes.add(content.substring(startIndex).trim());

		return attributes;
	}

	public static int countJsonValues(String input) {
		input = input.trim();
		int valueCount = 0;
		int braceCount = 0;
		int bracketCount = 0;
		boolean inQuote = false;
		boolean inEscape = false;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);

			if (inEscape) {
				inEscape = false;
				continue;
			}

			if (c == '\\') {
				inEscape = true;
				continue;
			}

			if (c == '\"') {
				inQuote = !inQuote;
				continue;
			}

			if (!inQuote) {
				if (c == '{')
					braceCount++;
				if (c == '}')
					braceCount--;
				if (c == '[')
					bracketCount++;
				if (c == ']')
					bracketCount--;

				if (braceCount == 0 && bracketCount == 0 && c == ','
						&& !inQuote) {
					String value = input.substring(0, i).trim();
					if (!value.isEmpty()) {
						valueCount++;
					}
					input = input.substring(i + 1).trim();
					i = -1;
				}
			}
		}

		if (!input.isEmpty()) {
			valueCount++;
		}

		return valueCount;
	}
}
