package com.syhd.math.knn.featureselect;

/**
 * Author: socket
 * Date: Aug 29, 2011
 * FileName: WordCount.java
 * Function: calculate word frequency in each document.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class WordCount {

    //参数文件是（经过预处理的）原始训练文档集所在的文件夹
    public void wordCount(File srcFile) {
        if (srcFile.isDirectory()) {
            File[] children = srcFile.listFiles();
            for (File child : children) {
                wordCount(child);
            }
        } else if (srcFile.isFile()) {
            HashMap<String,Integer> wordfreq=new HashMap<String,Integer>();        //存储单词出现的次数
            try {
                FileReader fr = new FileReader(srcFile);
                BufferedReader br = new BufferedReader(fr);
                String line=null;
                while((line=br.readLine())!=null){
                    String[] words=line.split("\\s+");
                    for(String word:words){
                        if(!wordfreq.containsKey(word)){
                            wordfreq.put(word, new Integer(1));
                        }else{
                            int n=wordfreq.get(word);
                            wordfreq.put(word, new Integer(n+1));
                        }
                    }
                }
                br.close();
                
                //把词频写入新文件
                File newFile=new File("/home/socket/frequency",srcFile.getName());
//                File newFile=new File("/home/socket",srcFile.getName());
                newFile.createNewFile();
                FileWriter fw=new FileWriter(newFile);
                BufferedWriter bw=new BufferedWriter(fw);
                Iterator<Entry<String, Integer>> iter=wordfreq.entrySet().iterator();
                while(iter.hasNext()){
                    Entry<String,Integer> entry=iter.next();
                    bw.write(entry.getKey());
                    bw.write("\t");
                    bw.write(String.valueOf(entry.getValue()));
                    bw.newLine();
                }
                bw.flush();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args){
        WordCount inst=new WordCount();
        File file=new File("/home/socket/corpus");
//        File file=new File("/home/socket/unknown_src");
        if(!file.exists()){
            System.out.println("文件不存在，程序退出.");
            System.exit(2);
        }
        inst.wordCount(file);
    }
}