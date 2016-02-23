package com.bpk.app.emrapp;

import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author surachai.tw
 */
public class PDFBoxTest
{

    public static void main(String args[])
    {
        // Create a new empty document
        // PDDocument document = new PDDocument();
        PDDocument document = null;

        // Create a new blank page and add it to the document
        // PDPage blankPage = new PDPage();
        // document.addPage(blankPage);

        try
        {
            document = PDDocument.load("D:\\BPKHIS\\trunk\\Sources\\CodeJSP\\jboss\\server\\default\\deploy\\bpkjasper.war\\TempFilename.pdf");
            document.decrypt("SURACHAI");
            document.setAllSecurityToBeRemoved(true);

            // Create a new font object selecting one of the PDF base fonts
            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDDocumentCatalog pdfDoc = document.getDocumentCatalog();

            List list = pdfDoc.getAllPages();
            PDPage page =  (PDPage)list.get(0);

            // Start a new content stream which will "hold" the to be created content
            PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);

            // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount(20, 820);
            contentStream.drawString("Hello World");
            contentStream.endText();

            // Make sure that the content stream is closed:
            contentStream.close();

            // Define the length of the encryption key.
            // Possible values are 40 or 128 (256 will be available in PDFBox 2.0).
            int keyLength = 128;

            AccessPermission ap = new AccessPermission();

            // disable printing, everything else is allowed
            ap.setCanPrint(false);

            // owner password (to open the file with all permissions) is "12345"
            // user password (to open the file but with restricted permissions, is empty here)
            StandardProtectionPolicy spp = new StandardProtectionPolicy("SURACHAI", "", ap);
            // spp.setEncryptionKeyLength(keyLength);
            spp.setPermissions(ap);
            // document.encrypt("SURACHAI", "AAA");
            document.protect(spp);

            // Save the results and ensure that the document is properly closed:
            document.save("Hello World.pdf");

            // finally make sure that the document is properly
            // closed.
            document.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
