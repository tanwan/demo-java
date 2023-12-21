//使用brew install thrift安装,thrift -version查看当前版本 thrift -gen java demo.thrift
//可以引用其它文件
//include "other.thrift"

//namespace <语言> <包的位置>
namespace java com.lzy.demo.serivce.thrift

//常量
const i32 CONST_VALUE = 10;

//结构体
struct ThriftMessage {
    //byte:有符号字节
    //i16:16位有符号整数
    //i32:32位有符号整数
    //i64:64位有符号整数
    //double:64位浮点数
    //string:字符串类型
    //必填
    1: required string str;
    //可选
    2: optional i32 integer = CONST_VALUE;
    3: double dou;
    4: list<string> stringList;
    5: set<ThriftMessage> thriftMessageSet;
    6: map<string, ThriftMessage> thriftMessageMap;
}
//自定义异常
exception RequestException {
    1: required i32 code;
    2: optional string reason;
}

// 服务名
service ThriftService {
    ThriftMessage thriftService(1: ThriftMessage thriftMessage) throws (1:RequestException e);
}