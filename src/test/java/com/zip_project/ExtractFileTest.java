package com.zip_project;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.zip_project.service.ExtractFile;
import com.zip_project.service.word.ExtractFileRepWord;

@DisplayName("ExtractFileRepWord simulation")
class ExtractFileRepWordTest {
	// KEY VALUE =
	// Api,endpoints,public,funcAuths,aliases,funcaliases,microservices
	@Test
	void When_extractFileRepWord_Expect_DoesNotThrowException() {
		assertDoesNotThrow(() -> {
			ExtractFileRepWord.extractFileManager(null);
		});
	}

	@Test
	void When_extractFile_Expect_DoesNotThrowException() {
		List<String> test = new ArrayList<>();
		test.add("test");
		assertDoesNotThrow(() -> {
			ExtractFile.extractFileManager(null, test);
		});
	}

	@Test
	@DisplayName("Api")
	void When_paramIsCommonApi_Expect_SizeEqual6() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("Api");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		assertEquals(6, genericStringsList.size());
	}

	@Test
	void When_Api_Expect_ModuleAndApisPresent() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("commonApi");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		for (List<String> Strings : genericStringsList) {
			assertTrue(Strings.contains("moduleDefaults"));
			assertTrue(Strings.contains("apis"));
		}
	}

	@Test
	void When_ApiProtocolIsPresent_Expect_https() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("commonApi");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		for (List<String> Strings : genericStringsList) {
			for (String value : Strings) {
				if (value.contains("protocol"))
					assertTrue(value.contains("https"));
			}
		}
	}

	@Test
	void When_ApiHostIsPresent_Expect_Sanpaoloimi() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("commonApi");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		for (List<String> Strings : genericStringsList) {
			for (String value : Strings) {
				if (value.contains("host"))
					assertTrue(value.contains(
							"trade-banca-svil.syssede.systest.sanpaoloimi.com"));
			}
		}
	}

	@Test
	void When_ApiBaseUrlIsPresent_Expect_SpecificChar() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("commonApi");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		for (List<String> Strings : genericStringsList) {
			for (String value : Strings) {
				if (value.contains("baseUrl"))
					assertTrue(value.contains("/"));
			}
		}
	}

	@Test
	void When_ApiSecurityIsPresent_Expect_None() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("commonApi");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		for (List<String> Strings : genericStringsList) {
			for (String value : Strings) {
				if (value.contains("security"))
					assertTrue(value.contains("none"));
			}
		}
	}

	@Test
	@DisplayName("Endpoints")
	void When_paramIsEndpoints_Expect_SizeEqual5() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("endpoints");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		assertEquals(5, genericStringsList.size());
	}

	@Test
	@DisplayName("Public")
	void When_paramIsPublic_Expect_SizeEqual5() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("public");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		assertEquals(5, genericStringsList.size());
	}

	@Test
	@DisplayName("FuncAuths")
	void When_paramIsFuncAuths_Expect_SizeEqual1() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("funcAuths");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		assertEquals(1, genericStringsList.size());
	}

	@Test
	@DisplayName("Aliases")
	void When_paramIsAliases_Expect_SizeEqual1() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("aliases");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		assertEquals(1, genericStringsList.size());
	}

	@Test
	@DisplayName("Funcaliases")
	void When_paramIsFuncaliases_Expect_SizeEqual1() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("funcaliases");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		assertEquals(1, genericStringsList.size());
	}

	@Test
	@DisplayName("Microservices")
	void When_paramIsMicroservices_Expect_SizeEqual1() throws IOException {
		List<String> test = new ArrayList<>();
		test.add("microservices");
		List<List<String>> genericStringsList = ExtractFile
				.extractFileManager(null, test);
		assertEquals(1, genericStringsList.size());
	}
}
