package com.code.generate.Util;

import com.code.generate.Entity.CodeEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    public static File createFile(String path,String fileName,String text,CodeEntity entity){
        File f = new File(path);
        if(!f.exists()){
            f.mkdirs();//创建目录
        }
        File file = new File(path, fileName);
        FileWriter writer = null;
        if(!file.exists()){
            try {
                file.createNewFile();
                writer = new FileWriter(file, true);
                writer.append(text);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(writer != null){
                    try {
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return file;
    }


    public static void downLoadFile(CodeEntity e,File file){
        HttpServletResponse response = e.getResponse();
        // 下载本地文件
        String fileName = e.getRarFilename(); // 文件的默认保存名
        // 读到流中
        InputStream inStream = null;// 文件的存放路径
        System.out.println(e.getRarFilePath() + e.getRarFilename());
        try {
            inStream = new FileInputStream(e.getRarFilePath() + e.getRarFilename());
            // 设置输出的格式
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            // 循环取出流中的数据
            byte[] b = new byte[100];
            int len;
            try {
                while ((len = inStream.read(b)) > 0)
                    response.getOutputStream().write(b, 0, len);
                inStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

     public static File zipFiles(File[] srcFiles, File zipFile) {
         // 判断压缩后的文件存在不，不存在则创建
         if (!zipFile.exists()) {
             try {
                 zipFile.createNewFile();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         // 创建 FileOutputStream 对象
         FileOutputStream fileOutputStream = null;
         // 创建 ZipOutputStream
         ZipOutputStream zipOutputStream = null;
         // 创建 FileInputStream 对象
         FileInputStream fileInputStream = null;

         try {
             // 实例化 FileOutputStream 对象
             fileOutputStream = new FileOutputStream(zipFile);
             // 实例化 ZipOutputStream 对象
             zipOutputStream = new ZipOutputStream(fileOutputStream);
             // 创建 ZipEntry 对象
             ZipEntry zipEntry = null;
             // 遍历源文件数组
             for (int i = 0; i < srcFiles.length; i++) {
                 // 将源文件数组中的当前文件读入 FileInputStream 流中
                 fileInputStream = new FileInputStream(srcFiles[i]);
                 // 实例化 ZipEntry 对象，源文件数组中的当前文件
                 zipEntry = new ZipEntry(srcFiles[i].getName());
                 zipOutputStream.putNextEntry(zipEntry);
                 // 该变量记录每次真正读的字节个数
                 int len;
                 // 定义每次读取的字节数组
                 byte[] buffer = new byte[1024];
                 while ((len = fileInputStream.read(buffer)) > 0) {
                     zipOutputStream.write(buffer, 0, len);
                 }
             }
             zipOutputStream.closeEntry();
             zipOutputStream.close();
             fileInputStream.close();
             fileOutputStream.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
        return zipFile;
     }
    /**
	 * 删除文件夹和文件夹下所有文件
	 *
     * @param sPath
	 * @return
     */
    public static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        else
            return true;
    }

    /**
     * 删除文件
     *
     * @param sPath
     * @return
     */
    private static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }



}
