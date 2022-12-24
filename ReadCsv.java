package com.example.batch;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;

import java.io.*;
import java.util.Map;

public class ReadCsv {
    public static void main(String[] args) throws Exception {
        // 代码执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 读取csv文件，这里用的是绝对路径，相对路径不太清楚能不能用
        BufferedReader reader = new BufferedReader(new FileReader("D:\\ETL\\flink\\flink-learning\\STK_QUOTE_L2.csv"));

        // 按行读取文件，第一行为属性名，存在了keys数组中
        String line = null;
        line = reader.readLine();
        String keys[] = line.split(",");
        while((line = reader.readLine()) != null){
            String item[] = line.split(",");
            String result = "";
            int len = item.length;

            // 这里item[0]是measurement，item[len-1]是tag
            result = result + item[0] + ',' + keys[len-1] + '=' + item[len-1] + ' ';

            // 这里field
            for(int i = 2; i < len-2; i++){
                result = result + keys[i] + '=' + item[i] + ',';
            }
            // field最后一个后面接空格，不接逗号
            result = result + keys[len-2] + '=' + item[len-2] + ' ';

            // 这里是timestamp，需要把转换格式的那部分加到这里
            result = result + keys[1] + '=' + item[1] + '\n';

            // 每读完一行，就将这行写入文件中
            FileWriter writer;
            try {
                writer = new FileWriter("D:\\ETL\\flink\\flink-learning\\result.txt", true);
                writer.write(result);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(result);
        }
    }
}
