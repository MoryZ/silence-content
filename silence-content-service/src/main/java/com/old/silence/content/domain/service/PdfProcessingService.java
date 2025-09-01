package com.old.silence.content.domain.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MurrayZhang
 */
@Service
public class PdfProcessingService {

    public List<String> extractTextFromPdf(InputStream inputStream) throws IOException {
        List<String> pagesText = new ArrayList<>();
        try (PDDocument document = Loader.loadPDF(inputStream.readAllBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                stripper.setStartPage(page + 1);
                stripper.setEndPage(page + 1);
                String text = stripper.getText(document);
                pagesText.add(text);
            }
        }
        return pagesText;
    }

    public List<String> chunkText(List<String> pagesText) {
        List<String> chunks = new ArrayList<>();
        for (String pageText : pagesText) {
            // 简单的按段落分割
            String[] paragraphs = pageText.split("\\n\\s*\\n");
            for (String paragraph : paragraphs) {
                if (paragraph.trim().length() > 50) { // 过滤太短的段落
                    chunks.add(paragraph.trim());
                }
            }
        }
        return chunks;
    }
}
