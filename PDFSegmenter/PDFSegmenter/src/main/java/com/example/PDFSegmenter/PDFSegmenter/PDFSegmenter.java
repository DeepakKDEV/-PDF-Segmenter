package com.example.PDFSegmenter.PDFSegmenter;

import com.example.PDFSegmenter.PDFSegmenter.textBOx.TextBlock;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFSegmenter {
    public PDDocument loadPDF(String filePath) {
        PDDocument document = null;
        try {
            document = PDDocument.load(new File(filePath));
            System.out.println("PDF loaded successfully.");
        } catch (IOException e) {
            System.err.println("Failed to load PDF file: " + e.getMessage());
        }
        return document;
    }

    // Step 2: Extract text with Y-axis positions
    public List<TextBlock> extractTextWithPositions(PDDocument document) {
        List<TextBlock> textBlocks = new ArrayList<>();
        try {
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            for (int i = 0; i < document.getNumberOfPages(); i++) {
                PDPage page = document.getPage(i);
                Rectangle region = new Rectangle(0, 0, 600, 800); // Define area to extract

                stripper.addRegion("region", region);
                stripper.extractRegions(page);

                String text = stripper.getTextForRegion("region");
                float yPosition = (float) region.getY(); // Example Y-position extraction (this needs refinement)

                textBlocks.add(new TextBlock(text, yPosition));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textBlocks;
    }

    // Step 3: Process the document further (e.g., segmenting based on Y-gaps)
    public void processPDF(String filePath) {
        PDDocument document = loadPDF(filePath);

        if (document != null) {
            List<TextBlock> textBlocks = extractTextWithPositions(document);

            // You can add more processing here (e.g., segmenting by vertical gaps)
            // For now, let's just print out the extracted blocks
            for (TextBlock block : textBlocks) {
                System.out.println("Text: " + block.getText() + " | Y-Position: " + block.getY());
            }

            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Main method to test the class
    public static void main(String[] args) {
        PDFSegmenter segmenter = new PDFSegmenter();
        segmenter.processPDF("C:\\Users\\DEEPAK KUMAR SINGH\\OneDrive\\Desktop\\PDFSEGEMENT\\PDFSegmenter\\PDFSegmenter\\Inheritance and its Types.pdf"); // Replace with your PDF file path
    }


}
