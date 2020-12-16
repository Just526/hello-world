package com.dhcc.demo.test;

import cn.hutool.core.io.file.FileWriter;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @description: 文件测试
 * @author: guogang
 * @date: 2020/12/16 10:45
 * @version: 1.0
 */
public class FileTest {
    @Test
    public void test()  {
        System.out.println(Arrays.toString("郭刚".getBytes(Charset.forName("ISO-8859-1"))));
        System.out.println(Arrays.toString("郭刚".getBytes(Charset.forName("GBK"))));
        System.out.println(Arrays.toString("郭刚".getBytes(Charset.forName("UTF-8"))));

//        FileWriter writer = new FileWriter("test.txt", Charset.forName("GBK"));
//        FileWriter writer = new FileWriter("test.txt", Charset.forName("UTF-8"));
//        writer.write("11郭2刚00");
//        String absolutePath = writer.getFile().getAbsolutePath();
//        FileReader reader=new FileReader(absolutePath, Charset.forName("UTF-8"));
//        String str = reader.readString();
//        System.out.println(str);

        FileWriter writer = new FileWriter("test.txt", Charset.forName("UTF-8"));
        byte[] gbks = "11郭2刚00".getBytes(Charset.forName("GBK"));
        writer.write(gbks,0,gbks.length);


//        File file = new File("a.txt");
//        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "GBK");
//        writer.write("中文测试");
//        writer.close();


    }
}
