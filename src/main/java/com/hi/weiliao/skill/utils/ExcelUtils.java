package com.hi.weiliao.skill.utils;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExcelUtils {

    /**
     * 将数据写入到一个流中，供用户下载，即流形成的一个Excel文件（重写）
     *
     * @param fileName 输出文件名
     * @param list   存放的数据，此数据要写入到Excel表格
     * @param titles  每列数据的标题&key
     * @param maxRow 每个sheet中最大的数据行数，如果超过了，再新建一个sheet写入数据
     * @return
     */
    public static void toExcel (OutputStream out, String fileName, List<Map<String, String>> list,
                                 Map<String, String> titles, int maxRow) throws Exception{
        //组装Excel
        XSSFWorkbook wb = new XSSFWorkbook();

        //计算sheet页数量
        int listSize=list.size();
        int sheetPage;
        if(0<listSize%maxRow){
            //则要建立的sheet数为
            sheetPage=listSize/maxRow+1;
        }else{
            //若能整除，则为
            sheetPage=listSize/maxRow;
            sheetPage=(sheetPage==0?1:sheetPage);
        }

        //循环要建立的sheet的数量，为每一个sheet开始写入数据
        int current = 0;//当前处理的数据index
        int sheetCurrentCount = 0;//当前页签已经处理的数据量
        for(int i=0;i<sheetPage;i++){
            //创建sheet
            XSSFSheet sheet =wb.createSheet("sheet" + i);

            //第一行插入标题
            XSSFRow title=sheet.createRow(0);

            //标题样式
            XSSFCellStyle cellStyleTitle=wb.createCellStyle();
            cellStyleTitle.setFillPattern(FillPatternType.FINE_DOTS);
            cellStyleTitle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.ROYAL_BLUE.getIndex()); //背景
            cellStyleTitle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ROYAL_BLUE.getIndex()); //前景
            cellStyleTitle.setWrapText(true); //自动换行
            cellStyleTitle.setAlignment(HorizontalAlignment.CENTER); // 水平居中

            cellStyleTitle.setBorderBottom(BorderStyle.THIN);//下边框    
            cellStyleTitle.setBorderLeft(BorderStyle.THIN);//左边框    
            cellStyleTitle.setBorderTop(BorderStyle.THIN);//上边框    
            cellStyleTitle.setBorderRight(BorderStyle.THIN);//右边框 

            //标题字体样式
            XSSFFont fontStyle=wb.createFont();
            fontStyle.setBold(true); //粗体
            fontStyle.setFontHeightInPoints((short) 11); //字体大小

            cellStyleTitle.setFont(fontStyle);

            //插入标题
            Set<String> keySet = titles.keySet();
            Object[] keys = keySet.toArray();
            Arrays.sort(keys);
            for(int k = 0; k<keys.length; k++){
                XSSFCell cell = title.createCell(k);
                cell.setCellStyle(cellStyleTitle);
                cell.setCellValue(titles.get(keys[k]+""));
                sheet.setColumnWidth(k,(titles.get(keys[k]+"")).length() * 1024);
            }

            //内容样式
            XSSFCellStyle cellStyleData=wb.createCellStyle();
            cellStyleData.setFillBackgroundColor(HSSFColor.BLUE.index);
            cellStyleData.setWrapText(true);

            cellStyleData.setBorderBottom(BorderStyle.THIN);//下边框    
            cellStyleData.setBorderLeft(BorderStyle.THIN);//左边框   
            cellStyleData.setBorderRight(BorderStyle.THIN);//右边框

            //内容字体样式
            XSSFFont fontStyleData=wb.createFont();
            fontStyleData.setFontHeightInPoints((short)11);

            cellStyleData.setFont(fontStyleData);

            //数据插入
            for(int d = current; d < list.size(); d++){
                //超出每页最大量则在下一页保存
                if(sheetCurrentCount == maxRow){
                    sheetCurrentCount = 0;
                    current = d;
                    break;
                }

                //装载数据
                XSSFRow row = sheet.createRow(sheetCurrentCount+1);
                for(int r = 0; r<keys.length; r++){
                    XSSFCell cell = row.createCell(r);
                    cell.setCellStyle(cellStyleData);
                    cell.setCellValue(list.get(d).get(keys[r])+"");

                    int width = (list.get(d).get(keys[r])+"").length() * 256;
                    if(width > sheet.getColumnWidth(r)){
                        sheet.setColumnWidth(r,width);
                    }
                }
                sheetCurrentCount++;
            }

        }

        //Excel文件写到输出流
        /*respose.setHeader("Content-disposition", "attachment; filename="+fileName+".xlsx");
        respose.setContentType("application/msexcel");*/

        wb.write(out);
        //关闭流
        out.close();
    }
}
