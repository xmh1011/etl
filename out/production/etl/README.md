# etl

给中宝当一下产品经理

### InfluxDB行协议

#### InfluxDB line protocol

```SQL
measurement,tag1=value1,tag2=value2 field1=value1,field2=value2 timestamp
```
`measurement`与`tag`之间用逗号分隔，`tag`与`field`之间用空格分隔，`field`与`timestamp`之间用逗号分隔

#### 示例数据

```SQL
STK_QUOTE_L2,RIC=0700 L1-Price=238.50,L1-BidSize=3663,L1-AskPrice=280.44,L1-AskSize=1818,L2-Price=298.18,L2-BidSize=3743,L2-AskPrice=242.19,L2-AskSize=1826,L3-Price=202.57,L3-BidSize=2359,L3-AskPrice=288.15,L3-AskSize=1716,L4-Price=261.29,L4-BidSize=3727,L4-AskPrice=234.32,L4-AskSize=1741,L5-Price=276.80,L5-BidSize=2601,L5-AskPrice=213.33,L5-AskSize=1292,L6-Price=256.69,L6-BidSize=2597,L6-AskPrice=297.67,L6-AskSize=1249,L7-Price=208.70,L7-BidSize=3335,L7-AskPrice=246.78,L7-AskSize=1916,L8-Price=204.15,L8-BidSize=2262,L8-AskPrice=248.48,L8-AskSize=1053,L9-Price=207.00,L9-BidSize=3518,L9-AskPrice=269.73,L9-AskSize=1642,L10-Price=288.31,L10-BidSize=3087,L10-AskPrice=277.98,L10-AskSize=1559,L11-Price=290.43,L11-BidSize=3876,L11-AskPrice=277.57,L11-AskSize=1962,L12-Price=204.84,L12-BidSize=2534,L12-AskPrice=242.43,L12-AskSize=1434,L13-Price=214.21,L13-BidSize=2227,L13-AskPrice=249.00,L13-AskSize=1673,L14-Price=272.63,L14-BidSize=3231,L14-AskPrice=274.71,L14-AskSize=1860,L15-Price=207.23,L15-BidSize=2426,L15-AskPrice=215.85,L15-AskSize=1284,L16-Price=203.65,L16-BidSize=3798,L16-AskPrice=290.44,L16-AskSize=1360,L17-Price=203.74,L17-BidSize=2582,L17-AskPrice=273.29,L17-AskSize=1654,L18-Price=258.85,L18-BidSize=3933,L18-AskPrice=205.60,L18-AskSize=1890,L19-Price=281.73,L19-BidSize=2394,L19-AskPrice=224.38,L19-AskSize=1905,L20-Price=277.89,L20-BidSize=3865,L20-AskPrice=250.14,L20-AskSize=1709,L21-Price=204.81,L21-BidSize=3373,L21-AskPrice=288.25,L21-AskSize=1865,L22-Price=274.09,L22-BidSize=3180,L22-AskPrice=222.77,L22-AskSize=1711,L23-Price=256.06,L23-BidSize=3230,L23-AskPrice=279.43,L23-AskSize=1892,L24-Price=268.14,L24-BidSize=3853,L24-AskPrice=297.02,L24-AskSize=1731,L25-Price=274.69,L25-BidSize=2916,L25-AskPrice=276.62,L25-AskSize=1622,L26-Price=298.80,L26-BidSize=2820,L26-AskPrice=220.05,L26-AskSize=1593,L27-Price=205.52,L27-BidSize=3392,L27-AskPrice=225.83,L27-AskSize=1332,L28-Price=249.01,L28-BidSize=2054,L28-AskPrice=235.36,L28-AskSize=1535,L29-Price=268.34,L29-BidSize=3652,L29-AskPrice=297.92,L29-AskSize=1879,L30-Price=278.67,L30-BidSize=3636,L30-AskPrice=268.80,L30-AskSize=1102 1660089600000000000
```
`STK_QUOTE_L2` 为`measurement`，`RIC` 为`tag`，`L1-Price` 等为`field`，`1660089600000000000`为`timestamp`。·

在示例的CSV文件`STK_QUOTE_L2.csv`中，`measurement` 为第一列，`timestamp` 为第二列，`field` 等为第三列至倒数第二列，`tag`为最后一列。注意在示例数据中，`measurement`、`tag`、`field`、`timestamp`的排列顺序。
其中，`timestamp`为`RFC3339`类型，需要将其转化成符合`InfluxDB`的`UnixNano`类型。

### 目标

将`STK_QUOTE_L2.csv`中的数据转化成符合InfluxDB行协议的数据，并写入相应文件。使用Java的flink包。


### 时间戳格式转换代码

```java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// convert a RFC3339 timestamp to a UnixNano timestamp
public class convertRFC3339ToUnixNano {
    public static void main(String[] args) throws ParseException {
        String rfc3339 = "2022-06-07T10:05:00.081083545+08";// RFC3339 timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'+'08");
        Date date = sdf.parse(rfc3339); // convert RFC3339 to Date
        long unixNano = date.getTime() * 1000000; // convert to UnixNano timestamp
        System.out.println(unixNano); // print UnixNano timestamp
    }
}
```