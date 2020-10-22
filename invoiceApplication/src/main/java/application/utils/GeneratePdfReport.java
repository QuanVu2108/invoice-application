package application.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import application.model.Invoice;
import application.model.Product;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratePdfReport {

	public static ByteArrayInputStream invoiceReport(Invoice invoice) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(60);
			table.setWidths(new int[] { 1, 5, 2, 2, 2 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("No", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Items", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Price", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Quantity", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Subtotal", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(hcell);

			for (Product product : invoice.getProductList()) {
				Integer i = 1;
				Long price = product.getPrice();
				Long quantity = product.getQuantity();

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(i.toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(product.getName()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(price)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(String.valueOf(quantity)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(price*quantity)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
				
				i++;
			}

			PdfWriter.getInstance(document, out);
			document.open();
			document.add(table);

			document.close();

		} catch (DocumentException ex) {

			Logger.getLogger(GeneratePdfReport.class.getName()).log(Level.SEVERE, null, ex);
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
}
