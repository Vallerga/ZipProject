package com.zip_project;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.zip_project.service.ExtractFile;
import com.zip_project.service.word.ExtractFileRepWord;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("ExtractFile simulation")
class ExtractFileTest {
	// @Autowired
	// ExtractFile ef;
	// static Stream<TestData> provideTestData() {
	// return Stream.of(new TestData(List.of("api"), 6));
	// }
	//
	// @Test
	// void When_extractFileRepWord_Expect_DoesNotThrowException() {
	// assertDoesNotThrow(() -> {
	// ExtractFileRepWord.extractFileManager(null);
	// });
	// }
	//
	// @Test
	// void When_extractFile_Expect_DoesNotThrowException() {
	// List<String> test = new ArrayList<>();
	// test.add("api");
	// assertDoesNotThrow(() -> {
	// ef.extractFileManager(null);
	// });
	// }
	//
	// @ParameterizedTest
	// @MethodSource("provideTestData")
	// @DisplayName("ApiNumFileCheck")
	// void When_paramIsCommonApi_Expect_ExpectedSize(TestData testData)
	// throws IOException {
	// List<List<String>> genericStringsList = null;
	// genericStringsList = ExtractFile.extractFileManager(null,
	// testData.getInput());
	//
	// assertEquals(testData.getExpectedSize(), genericStringsList.size());
	// }
	//
	// @Test
	// void When_Api_Expect_ModuleAndApisPresent() throws IOException {
	// List<String> test = new ArrayList<>();
	// test.add("api");
	// List<List<String>> genericStringsList = ExtractFile
	// .extractFileManager(null, test);
	//
	// for (List<String> stringList : genericStringsList) {
	// boolean hasModuleDefaults = stringList.stream()
	// .anyMatch(line -> line.contains("moduleDefaults"));
	// boolean hasApis = stringList.stream()
	// .anyMatch(line -> line.contains("apis"));
	//
	// assertTrue(hasModuleDefaults,
	// "moduleDefaults is missing in: " + stringList);
	// assertTrue(hasApis, "apis is missing in: " + stringList);
	// }
	// }
	//
	// @Test
	// void When_ApiProtocolIsPresent_Expect_https() throws IOException {
	// List<String> test = new ArrayList<>();
	// test.add("api");
	// List<List<String>> genericStringsList = ExtractFile
	// .extractFileManager(null, test);
	// for (List<String> Strings : genericStringsList) {
	// for (Integer i = 0; i < Strings.size(); i++) {
	// String value = Strings.get(i);
	// // System.out.println("value: " + value + " i: " + i + " - "
	// // + genericStringsList.indexOf(Strings));
	//
	// if (value.contains("protocol")) {
	// System.out.println("value: " + value);
	// assertTrue(
	// value.contains("https") || value.contains("http"),
	// "https missing at line: " + 91 + ", the line is: "
	// + value);
	// }
	// }
	// }
	// }
	//
	// @Test
	// void When_Host_Expect_RightLink() throws IOException {
	// List<String> test = new ArrayList<>();
	// test.add("api");
	// List<List<String>> genericStringsList = ExtractFile
	// .extractFileManager(null, test);
	//
	// for (List<String> stringList : genericStringsList) {
	// for (int i = 0; i < stringList.size(); i++) {
	// String value = stringList.get(i);
	//
	// if (value.contains("host")) {
	// System.out.println("host: " + value);
	// assertTrue(value.contains(
	// "trade-banca-svil.syssede.systest.sanpaoloimi.com"),
	// "Expected host not found at: "
	// + genericStringsList.getClass().getName() + "("
	// + genericStringsList.getClass().getSimpleName()
	// + ".java:" + i + ")");
	// }
	// }
	// }
	// }
	//
	// // @Test
	// // void When_ApiHostIsPresent_Expect_Sanpaoloimi() throws IOException {
	// // List<String> test = new ArrayList<>();
	// // test.add("api");
	// // List<List<String>> genericStringsList = ExtractFile
	// // .extractFileManager(null, test);
	// // for (List<String> Strings : genericStringsList) {
	// // for (Integer i = 0; i < Strings.size(); i++) {
	// // String value = Strings.get(i);
	// // // System.out.println("value: " + value + " i: " + i + " - "
	// // // + genericStringsList.indexOf(Strings));
	// //
	// // if (value.contains("host")) {
	// // System.out.println("host: " + value);
	// // assertTrue(value.contains(
	// // "trade-banca-svil.syssede.systest.sanpaoloimi.com")
	// // // || value.contains("localhost:4200")
	// // // || value.contains(
	// // // "trade-banca.sede.corp.sanpaoloimi.com")
	// // // || value.contains(
	// // // "trade-banca-test.syssede.systest.sanpaoloimi.com")
	// // // || value.contains(
	// // // "trade-banca-utes.syssede.systest.sanpaoloimi.com")
	// // , value.getClass().getName() + ".java:" + i);
	// // // || value.contains(
	// // // "trade-banca-utes.syssede.systest.sanpaoloimi.com"),
	// // // "https missing at line: " + i + ", the line is: "
	// // // + value);
	// // }
	// // }
	// // }
	// // }
	//
	// @Test
	// void When_ApiBaseUrlIsPresent_Expect_SpecificChar() throws IOException {
	// List<String> test = new ArrayList<>();
	// test.add("api");
	// List<List<String>> genericStringsList = ExtractFile
	// .extractFileManager(null, test);
	// for (List<String> Strings : genericStringsList) {
	// for (String value : Strings) {
	// if (value.contains("baseUrl"))
	// assertTrue(value.contains("/"));
	// }
	// }
	// }
	//
	// @Test
	// void When_ApiSecurityIsPresent_Expect_None() throws IOException {
	// List<String> test = new ArrayList<>();
	// test.add("api");
	// List<List<String>> genericStringsList = ExtractFile
	// .extractFileManager(null, test);
	// for (List<String> Strings : genericStringsList) {
	// for (String value : Strings) {
	// if (value.contains("security"))
	// assertTrue(value.contains("none"));
	// }
	// }
	// }
}
