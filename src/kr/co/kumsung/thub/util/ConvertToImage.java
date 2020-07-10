package kr.co.kumsung.thub.util;

import java.util.Timer;
import java.util.TimerTask;
import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
public class ConvertToImage

{

    private final String _SP = File.separator;
    private final String THUMB_HOME = "/home/synap/imageConverter";
    private final String THUMB = THUMB_HOME + _SP + "imageConverter";
    private final String TEMP_DIR = THUMB_HOME + _SP + "temp";
    private final String FONTS_DIR = THUMB_HOME + _SP + "fonts";

    /*
     * 변환호출
     * inputFile : 변환대상 파일의 절대경로
     * outputPath : 변환된 IMAGE 파일 저장경로
     * resultName : 생성할 IMAGE 디렉토리명
     * return : 0 (변환성공) 0이외의 값 (변환실패)
     */

    public int convertToImage(String inputFile, String outputPath, String resultName)

    {
        // 전역 변수
        File file = new File(inputFile);
        String fileName = file.getName();
        String xmlFilePath = outputPath + _SP + resultName + _SP + fileName + ".xml";
        String filesDir = outputPath + _SP + resultName + _SP + fileName + ".files";
         

        // 기존 변환결과 존재여부 확인
        File checkFile = new File(xmlFilePath);

        // 기존 변환결과가 존재하지 않을 경우 변환실행

        if (!checkFile.exists())

        {
            // fileName.files 디렉토리 생성
            File outDir = new File(filesDir);

            if (!outDir.exists()) {

                outDir.mkdirs();

            }

            // 이미지 변환 RUN
            String[] cmd = { THUMB, "-b", "1", "-e", "5", "-f", FONTS_DIR, "-t", TEMP_DIR, "-o", outDir.getAbsolutePath(), "-i", inputFile };
            //String[] cmd = { THUMB, "-e", "-1", "-f", FONTS_DIR, "-t", TEMP_DIR, "-o", outDir.getAbsolutePath(), "-i", inputFile };
            try

            {

                Timer t = new Timer();
                Process proc = Runtime.getRuntime().exec(cmd);
                TimerTask killer = new TimeoutProcessKiller(proc);
                t.schedule(killer, 20000); // 200초 (변환이 20초 안에 완료되지 않으면 프로세스 종료)
                int exitValue = proc.waitFor();
                killer.cancel();

                {

                    // fileName_pageinfo.txt 파싱해서 전체 페이지 수를 읽어온다.
                    int pageCnt = getPageCount(outDir, fileName);
                    
                    //최대 5페이지만.
                    if(pageCnt > 5) pageCnt = 5;

                    String inputpath = inputFile.replace("/home/thub/www", "");
                    // fileName.xml 생성
                    int ret = createXmlFile(xmlFilePath, fileName, pageCnt, inputpath);

                    if (ret != 0) {

                        System.out.println("ERROR: createXmlFile Func");
                        return -1;

                    }

                    // DEBUG
                    System.out.println("inputFile: " + inputFile);
                    System.out.println("outputPath: " + outDir.getAbsolutePath());
                    System.out.println("fileName: " + fileName);
                    System.out.println("xmlFilePath: " + xmlFilePath);
                    System.out.println("pageCnt: " + pageCnt);

                }

                return exitValue;

            }

            catch (Exception e)

            {

                e.printStackTrace();
                return -1;

            }

        }

        else

        {

            return 0;   // 기존 변환결과가 존재함. 정상 변환으로 처리

        }

    }

     

    public int getPageCount(File outDir, String fileName) throws NumberFormatException, IOException

    {

        int pageCnt = 0;
        BufferedReader in = new BufferedReader(new FileReader(outDir.getAbsolutePath() + _SP + fileName + "_pageinfo.txt"));

        String s = "";

        while ((s = in.readLine()) != null) {

            String[] sp = s.split(":");

            if (sp[0].equals("NUMPAGES")) {

                pageCnt = Integer.valueOf(sp[1].trim());
                break;

            }

        }

        in.close();

        return pageCnt;

    }

     

    public String createIndex(int idx)

    {

        String ret = "";

        if (idx < 10) {

            ret = "000" + String.valueOf(idx);

        } else if (idx >= 10 && idx < 100) {

            ret = "00" + String.valueOf(idx);

        } else if (idx >= 100 && idx < 1000) {

            ret = "0" + String.valueOf(idx);

        } else {

            ret = "error"; // 1000장 이상은 지원x

        }

        return ret;

    }
     

    public int createXmlFile(String xmlFilePath, String fileName, int pageCnt, String inputPath)

    {

        // XML
        Document doc = new Document();

        // <index>
        Element root = new Element("index");

        // <index> / <use_single_page> 
        Element use_single_page = new Element("use_single_page");
        use_single_page.setText("false");
        root.addContent(use_single_page);

        // <index> / <file_name> 
        Element file_name = new Element("file_name");
        file_name.setContent(new CDATA(fileName));
        root.addContent(file_name);

        // <index> / <file_type>
        Element file_type = new Element("file_type");
        file_type.setText("pdf"); // 이미지 이기 때문에 pdf 로 인식하게 한다.
        root.addContent(file_type);

        // <index> / <pdf_cnt>
        Element pdf_cnt = new Element("pdf_cnt");
        pdf_cnt.setText(String.valueOf(pageCnt));
        root.addContent(pdf_cnt);

        // <index> / <pdf_list>
        Element pdf_list = new Element("pdf_list");
        root.addContent(pdf_list);
         
        // <index> / <downUrl>
/*        Element downUrl = new Element("downUrl");
        downUrl.setContent(new CDATA(inputPath));
        root.addContent(downUrl);*/


        // loop

        for (int idx=1; idx<=pageCnt; idx++)

        {

            // <index> / <pdf_list> / <pdf>
            Element pdf = new Element("pdf");
            pdf_list.addContent(pdf);

            // <index> / <pdf_list> / <pdf> / <id>
            Element id = new Element("id");
            id.setText(String.valueOf(idx));
            pdf.addContent(id);
             

            // <index> / <pdf_list> / <pdf> / <id>
            Element title = new Element("title");
            title.setText("Page " + String.valueOf(idx));
            pdf.addContent(title);

            // <index> / <pdf_list> / <pdf> / <path_html>
            Element path_html = new Element("path_html");
            path_html.setContent(new CDATA(fileName + ".files/" + fileName + "_" + createIndex(idx) + ".png"));
            pdf.addContent(path_html);

            // <index> / <pdf_list> / <pdf> / <w>
            Element w = new Element("w");
            w.setText("816");
            pdf.addContent(w);

            // <index> / <pdf_list> / <pdf> / <h>
            Element h = new Element("h");
            h.setText("1056");
            pdf.addContent(h);

        }

        // set root
        doc.setRootElement(root);

        try {                                                            

            FileOutputStream out = new FileOutputStream(xmlFilePath); 

            //xml 파일 생성을 위한 경로와 파일 이름 지정해 주기
            XMLOutputter serializer = new XMLOutputter();                
            Format f = serializer.getFormat();                          
            f.setEncoding("UTF-8");

            //encoding 타입을 UTF-8 로 설정
            f.setIndent(" ");                                          
            f.setLineSeparator("\r\n");                                  
            f.setTextMode(Format.TextMode.TRIM);                          
            serializer.setFormat(f);                                      
            serializer.output(doc, out);                                  
            out.flush();                                                  
            out.close();    

            // String 으로 xml 출력
            // XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setEncoding("UTF-8"));
            // System.out.println(outputter.outputString(doc));

        } catch (IOException e) {                                        

            System.err.println(e);                                      

        }

        return 0;

    }

}
