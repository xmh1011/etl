package com.example.batch;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

// 此代码基于STK_QOUTE_L2.csv文件中的数据格式实现
public class ReadCsv {
    public static void main(String[] args) throws Exception {
        // 代码执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // 读取命令行参数
        String filePath = args[0];
        String resultPath = args[1];

        // 读取csv文件，这里用的是绝对路径，相对路径不太清楚能不能用
        BufferedReader reader = new BufferedReader(new FileReader(filePath)); //将文件名改成了从命令行读取

        // 按行读取文件，第一行为属性名，存在了keys数组中
        String line = null;
        line = reader.readLine();
        String keys[] = line.split(",");
        while((line = reader.readLine()) != null){
            String item[] = line.split(","); // item数组存储了每一行的数据
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
            String rfc3339 = item[1];// RFC3339 timestamp
            if (rfc3339.length() == 19) { // 将时间戳格式改成一致
                rfc3339 = rfc3339 + ".00Z";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'"); // 格式化时间戳
            Date date = sdf.parse(rfc3339); // convert RFC3339 to Date
            long unixNano = date.getTime() * 1000000; // convert to UnixNano timestamp 转换为19位纳秒级时间戳
            result = result + ' ' + unixNano + '\n';

            // 每读完一行，就将这行写入文件中
            FileWriter writer;
            try {
                writer = new FileWriter(resultPath, true);
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
