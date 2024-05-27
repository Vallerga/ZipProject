package com.zip_project.service.word;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordGenerator {

	private WordGenerator() {
	}

	private static final String FONT_FAMILY = "Arial";

	public static XWPFDocument generateWord(String titleText) {
		XWPFDocument document = null;
		XWPFParagraph title;

		try {
			document = new XWPFDocument();

			title = document.createParagraph();
			title.setAlignment(ParagraphAlignment.CENTER);

			// setting title style and text
			XWPFRun titleRun = title.createRun();
			titleRun.setBold(true);
			titleRun.setFontFamily(FONT_FAMILY);
			titleRun.setFontSize(20);

			titleRun.setText(titleText);
			titleRun.addCarriageReturn();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	public static String documentWrite(XWPFDocument document, Path destDir,
			String fileName) {
		FileOutputStream out = null;
		String nameFile = "/" + fileName.toLowerCase() + " report.docx";

		// finalize the word report
		try {
			out = new FileOutputStream(destDir.toString() + nameFile);
			document.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "WORD FILE GENERATED";
	}

	public static void summary(Integer counter, Integer folder, Integer files,
			XWPFDocument document) {
		XWPFRun parRun;

		subtitle(document, "REPORT SUMMARY");

		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.LEFT);

		parRun = paragraph.createRun();
		parRun.setBold(false);
		parRun.setFontFamily(FONT_FAMILY);
		parRun.setFontSize(11);

		parRun.setText("Files + Folders num: " + counter);
		parRun.addCarriageReturn();
		parRun.setText("Files num: " + files);
		parRun.addCarriageReturn();
		parRun.setText("Folders num: " + folder);
		parRun.addCarriageReturn();
	}

	public static void describeFile(XWPFDocument document,
			List<String> fileAttributes) {
		XWPFRun singleFileRun;
		Integer counter = 0;

		XWPFParagraph singleFileParagraph = document.createParagraph();
		singleFileParagraph.setAlignment(ParagraphAlignment.LEFT);

		for (String attribute : fileAttributes) {
			singleFileRun = singleFileParagraph.createRun();
			if (counter == 0) {
				singleFileRun.setBold(true);
				singleFileRun.setFontFamily(FONT_FAMILY);
				singleFileRun.setText(attribute);
				counter++;
			} else {
				singleFileRun.setBold(false);
				singleFileRun.setFontFamily(FONT_FAMILY);
				singleFileRun.setText(attribute);
				singleFileRun.addCarriageReturn();
				counter = 0;
			}
		}
	}

	public static void subtitle(XWPFDocument document, String subtitle) {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun parRun = paragraph.createRun();
		parRun.setBold(true);
		parRun.setFontFamily(FONT_FAMILY);
		parRun.setFontSize(14);
		parRun.setText(subtitle);
		parRun.addCarriageReturn();
	}
}