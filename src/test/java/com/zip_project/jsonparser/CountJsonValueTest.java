package com.zip_project.jsonparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.zip_project.service.JsonParserTDD;

class CountJsonValueTest {

	@Test
	void testCountJsonValues_SingleString() {
		assertEquals(1, JsonParserTDD.countJsonValues("\"Hello, world!\""));
	}

	@Test
	void testCountJsonValues_SingleNumber() {
		assertEquals(1, JsonParserTDD.countJsonValues("42"));
	}

	@Test
	void testCountJsonValues_SingleBoolean() {
		assertEquals(1, JsonParserTDD.countJsonValues("true"));
	}

	@Test
	void testCountJsonValues_SingleNull() {
		assertEquals(1, JsonParserTDD.countJsonValues("null"));
	}

	@Test
	void testCountJsonValues_SingleObject() {
		assertEquals(1, JsonParserTDD.countJsonValues("{\"name\": \"John\"}"));
	}

	@Test
	void testCountJsonValues_SingleArray() {
		assertEquals(1, JsonParserTDD.countJsonValues("[1, 2, 3]"));
	}

}
