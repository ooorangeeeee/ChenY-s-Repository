package cn.stum.servlet;

import cn.stum.service.Service;
import com.alibaba.fastjson.JSON;

public class test {
    public static void main(String[] args) {
//        System.out.println(123);
//        Result result = Service.login("123", "123");
//        System.out.println(JSON.toJSON(result));
//        Result signInfo = Service.checkStudentSign("8", "2204");
//        System.out.println(JSON.toJSONString(signInfo));
//        System.out.println(JSON.toJSONString(Service.addSign("1234567", "2203","12345", "第三次数学签到")));
//        System.out.println(JSON.toJSONString(Service.signInfo("")));
//        Map<String,Object> map = (Map<String, Object>) Service.signInfoById("1").getData();
//        System.out.println(JSON.toJSONString(map.get("sign_student")));
//        System.out.println(JSON.toJSONString(Service.studentSign("32","122345","1")));
//        System.out.println(JSON.toJSONString(Service.addClass("2205","计科五班","12345")));
//        System.out.println(JSON.toJSONString(Service.studentJoinClass("1","2204")));
//        System.out.println(JSON.toJSONString(Service.findClassByTeacherId("1234567")));
        System.out.println(JSON.toJSONString( Service.findAllSignByTeacherId("1234567")));
//        System.out.println(JSON.toJSONString(Service.checkStudentSign("1","2203")));
//        System.out.println(JSON.toJSONString(Service.findMySignByStudentId("1")));
//        System.out.println(JSON.toJSONString(Service.studentSign("1","12345","6")));
//        System.out.println(JSON.toJSONString(Service.changeSignTime("1")));
    }

}
