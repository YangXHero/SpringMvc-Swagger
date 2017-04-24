package com.yyx.utils.poi;

import java.awt.Color;

import java.io.File;

import java.io.FileOutputStream;

import java.util.List;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class PdfUtil {

	public static void exportPdf(String fileName, String filePath,
			List listLable, List listData) {

		Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);

		try {

			File file = new File(filePath);

			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

			Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);

			Font FontChina = new Font(bfChinese, 24, Font.NORMAL);

			if (!file.exists()) {

				file.mkdirs();

			}

			PdfWriter.getInstance(document, new FileOutputStream(filePath
					+ fileName + SysConStant.nameSuffix.NAMESUFFIX_PDF));

			document.open();

			Table datatable = new Table(listLable.size());

			datatable.setWidth(100);

			datatable.setPadding(3);

			Cell cell = new Cell(new Phrase(fileName, FontChina));

			cell.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell.setLeading(30);

			cell.setColspan(listLable.size());

			cell.setBorder(Rectangle.NO_BORDER);

			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));

			datatable.addCell(cell);

			datatable.setBorderWidth(2);

			datatable.setAlignment(1);

			for (int i = 0; i < listLable.size(); i++) {

				datatable.addCell(new Paragraph(listLable.get(i).toString(),
						FontChinese));

			}

			datatable.endHeaders();

			datatable.setBorderWidth(1);

			for (int j = 0; j < listData.size(); j++) {

				List a = (List) listData.get(j);

				for (int o = 0; o < a.size(); o++) {

					datatable.addCell(new Paragraph(a.get(o) == null ? "" : a
							.get(o).toString(), FontChinese));

				}

			}

			document.add(datatable);

		} catch (Exception e) {

			e.printStackTrace();

		}

		document.close();

	}

}
