package com.zip_project.service;

import org.springframework.stereotype.Service;

import com.zip_project.service.costant.Costant;
import com.zip_project.service.exception.error.ValidationSchema;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TextContentRead {

	public ValidationSchema readTextContent(String textContent,
			ValidationSchema validationSchema) {

		textContent = tagElimination(textContent);

		// validationSchema.setContext(contextGen(textContent));

		validationSchema.setContext(convertContextToObject(textContent));
		validationSchema.setErrorPosition(errorPositionGen(textContent));
		validationSchema
				.setExpectedStructure(expectedStructureGen(textContent));
		validationSchema.setProblemDescription(problemDescription(textContent));

		return validationSchema;
	}

	private String convertContextToObject(String textContent) {
		// isolate the context of the error
		int end = textContent.indexOf(Costant.ErrorParser.AT_TAG.getValue());
		String errorObject = textContent.substring(0, end);

		// check if last attribute is structural complete
		int lastComma = errorObject.lastIndexOf(",");
		String lastAttribute = errorObject.substring(lastComma,
				errorObject.length());
		lastAttribute = lastAttribute.substring(1, lastAttribute.length() - 3)
				+ "\"";
		log.info("is an attribute? {} attribute: {}",
				JsonParserTDD.isJsonAttribute(lastAttribute), lastAttribute);

		// obtain a coerent Json Object
		if (!JsonParserTDD.isJsonAttribute(lastAttribute)) {
			errorObject = errorObject.substring(0, lastComma);
		} else {
			errorObject = errorObject.substring(0, errorObject.length() - 3)
					+ "\"";
		}
		errorObject = errorObject + "}";

		log.info("is an object? {} - object: {} \n",
				JsonParserTDD.isJsonObject(errorObject), errorObject);

		return errorObject;
	}

	private String tagElimination(String textContent) {
		if (textContent.contains(Costant.ErrorParser.REMOVING_TAG.getValue())) {
			int start = textContent
					.indexOf(Costant.ErrorParser.REMOVING_TAG.getValue());
			int end = start
					+ Costant.ErrorParser.REMOVING_TAG.getValue().length();
			textContent = textContent.substring(end + 1, textContent.length());
		}
		return textContent;
	}

	// private String contextGen(String textContent) {
	// int end = textContent.indexOf(Costant.ErrorParser.AT_TAG.getValue());
	// return textContent.substring(0, end);
	// }

	private String errorPositionGen(String textContent) {
		int start = textContent.indexOf(Costant.ErrorParser.AT_TAG.getValue());
		int end = textContent
				.indexOf(Costant.ErrorParser.FAILED_TAG.getValue());

		return textContent.substring(start, end);
	}

	private String expectedStructureGen(String textContent) {
		int start = textContent
				.indexOf(Costant.ErrorParser.FAILED_TAG.getValue());
		int end = textContent.indexOf(Costant.ErrorParser.WITH_TAG.getValue());

		return textContent.substring(start, end);
	}

	private String problemDescription(String textContent) {
		int start = textContent
				.indexOf(Costant.ErrorParser.WITH_TAG.getValue());

		return textContent.substring(start, textContent.length());
	}
}
