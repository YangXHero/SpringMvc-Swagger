package com.yyx.utils.poi;
 
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
 
/**
 * 创建word文档
 */
public class WordCreate { 
    /**
     * 2007word文档创建
     */
    public void createWord2007() {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p1 = doc.createParagraph();
 
        XWPFTable table = doc.createTable(11, 4);
        // CTTblBorders borders=table.getCTTbl().getTblPr().addNewTblBorders();
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        tblPr.getTblW().setType(STTblWidth.DXA);
        tblPr.getTblW().setW(new BigInteger("7000"));
 
        // 设置上下左右四个方向的距离，可以将表格撑大
        table.setCellMargins(20, 20, 20, 20);
 
        // 表格
        List<XWPFTableCell> tableCells = table.getRow(0).getTableCells();
 
        XWPFTableCell cell = tableCells.get(0);
        XWPFParagraph newPara = new XWPFParagraph(cell.getCTTc().addNewP(), cell);
        XWPFRun run = newPara.createRun();
        /** 内容居中显示 **/
        newPara.setAlignment(ParagraphAlignment.CENTER);
        // run.getCTR().addNewRPr().addNewColor().setVal("FF0000");/**FF0000红色*/
        // run.setUnderline(UnderlinePatterns.THICK);
        run.setText("第一 数据");
 
        tableCells.get(1).setText("第一 数据");
        tableCells.get(2).setText("第一 据");
        tableCells.get(3).setText("第 据");
 
        tableCells = table.getRow(1).getTableCells();
        tableCells.get(0).setText("第数据");
        tableCells.get(1).setText("第一 数据");
        tableCells.get(2).setText("第一 据");
        tableCells.get(3).setText("第 据");
        
        //试用合并单元格
        mergeCellsHorizontal(table, 1, 2,3);
 
        // 设置字体对齐方式
        p1.setAlignment(ParagraphAlignment.CENTER);
        p1.setVerticalAlignment(TextAlignment.TOP);
 
        // 第一页要使用p1所定义的属性
        XWPFRun r1 = p1.createRun();
 
        // 设置字体是否加粗
        r1.setBold(true);
        r1.setFontSize(20);
 
        // 设置使用何种字体
        r1.setFontFamily("Courier");
 
        // 设置上下两行之间的间距
        r1.setTextPosition(20);
        r1.setText("标题");
 
        FileOutputStream out;
        try {
        	out = new FileOutputStream("e:/ax.docx");
            //out = new FileOutputStream("c:/test/word2007.docx");
            // 以下代码可进行文件下载
            // response.reset();
            // response.setContentType("application/x-msdownloadoctet-stream;charset=utf-8");
            // response.setHeader("Content-Disposition",
            // "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8"));
            // OutputStream out = response.getOutputStream();
            // this.doc.write(out);
            // out.flush();
 
            doc.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("success");
    }
    
    // word跨列合并单元格  
    public  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {    
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {    
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);    
            if ( cellIndex == fromCell ) {    
                // The first merged cell is set with RESTART merge value    
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);    
            } else {    
                // Cells which join (merge) the first one, are set with CONTINUE    
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);    
            }    
        }    
    }    
    // word跨行并单元格  
    public void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {    
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {    
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);    
            if ( rowIndex == fromRow ) {    
                // The first merged cell is set with RESTART merge value    
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);    
            } else {    
                // Cells which join (merge) the first one, are set with CONTINUE    
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);    
            }    
        }    
    }   
    
    //另外加上单元格字体设置的方法：
    private void getParagraph(XWPFTableCell cell,String cellText){  
        CTP ctp = CTP.Factory.newInstance();  
        XWPFParagraph p = new XWPFParagraph(ctp, cell);  
        p.setAlignment(ParagraphAlignment.CENTER);  
        XWPFRun run = p.createRun();  
        run.setText(cellText);  
        CTRPr rpr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();  
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();  
        fonts.setAscii("仿宋");  
        fonts.setEastAsia("仿宋");  
        fonts.setHAnsi("仿宋");  
        cell.setParagraph(p);  
    } 
 
}