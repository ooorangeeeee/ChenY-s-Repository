package cn.stum.service;

import cn.stum.helper.MysqlHelper;
import cn.stum.pojo.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Service {


    //检查学生账号密码
    public static HashMap<String, Object> findStudentByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users_s WHERE Sid = ? AND password = ?";
        List<HashMap<String, Object>> list = MysqlHelper.query(sql, new Object[] {email, password});
        if (list.size()>0) {
            return list.get(0);
        }else {
            return null;
        }
    }
    //检查老师账号密码
    public static HashMap<String, Object> findTeacherByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users_t WHERE Tid = ? AND password = ?";
        List<HashMap<String, Object>> list = MysqlHelper.query(sql, new Object[] {email, password});
        if (list.size()>0) {
            return list.get(0);
        }else {
            return null;
        }
    }
    //登陆
    public static Result login(String email,String password,boolean s_t){
        Result result = new Result("fail",null);
        HashMap<String, Object> user = new HashMap<String, Object>();
        HashMap<String, Object> map = new HashMap<>();
        if (!s_t){
            user = findTeacherByEmailAndPassword(email,password);
        }else {
            user = findStudentByEmailAndPassword(email,password);
        }
        if (user!=null){
            if (s_t){
                result.setFlag("success");
                map.put("ID",user.get("ID"));
                map.put("Sid",user.get("Sid"));
                map.put("Name",user.get("Name"));
                map.put("class_id",user.get("class_id"));
            }

            result.setFlag("success");
            map.put("Id",user.get("Id"));
            map.put("Tid",user.get("Tid"));
            map.put("Name",user.get("Name"));
            map.put("user",s_t);
            result.setData(map);
            return result;
        }else{
            result.setData("账户或密码错误");
            return result;
        }
    }

    //添加班级 需要班级ID，班级名称，老师ID
    public static Result addClass(String classId,String className,String teacherID){
        Result result = new Result("fail", null);
        String sql = "INSERT INTO class (class_id,className,teacher_id) VALUE (?,?,?)";
        if (MysqlHelper.update(sql,new Object[]{classId,className,teacherID})>0){
            result.setFlag("success");
        }else{
            result.setFlag("班级已存在");
        }
        return  result;
    }

    //学生加入班级 需要学生ID，班级ID
    public static Result studentJoinClass(String studentId,String classId){
        Result result = new Result("fail", null);
        String sql = "UPDATE users_s SET class_id = ? WHERE ID = ?";
        if (MysqlHelper.update(sql,new Object[]{classId,studentId})>0){
            result.setFlag("success");
        }
        return  result;
    }

    //通过id查询签到信息
    public  static  Result signInfoById(String signId){
        Result result = new Result("fail",null);
        String sql = "SELECT * FROM sign WHERE sign_id = ?";
        List<HashMap<String, Object>> query = MysqlHelper.query(sql, new Object[]{signId});
        if (query.size()>0){
            result.setFlag("success");
            result.setData(query.get(0));
        }
        return  result;
    }

    //查询学生签到信息 查询到签到表中的当次签到学生数据,再在学生表中将对应班级的所有学生信息以及签到状态返回
    public static Result checkStudentSign(String signId,String class_id){
        Result result = new Result("fail",null);
        String sql = "SELECT sign_student FROM `sign` WHERE sign_id = ?";
        List<HashMap<String,Object>> sign_student = MysqlHelper.query(sql,new Object[]{signId});
        System.out.println(sign_student.get(0).get("sign_student"));
        if (sign_student.size()>0){

            String sql2 = "SELECT DISTINCT a.ID,a.`Name`,a.Sid,a.class_id, (a.id in (";
            String s_s = (String) sign_student.get(0).get("sign_student");
            String[] split;
            Object[] objects;
            if (s_s!=null){


                split= s_s.split(",");
            objects = new Object[split.length+1];
            for (int i = 0; i < split.length; i++) {
                objects[i]=split[i];
                sql2+="?";
                if (i!=split.length-1)
                    sql2+=",";
            }
            objects[split.length]=class_id;
            }else {
                objects =new Object[]{"",class_id};
            }
            sql2+=")) as \"sign\" FROM users_s a,users_s b WHERE a.class_id = ? ";
            List<HashMap<String,Object>> list2 = MysqlHelper.query(sql2,objects);
            result.setFlag("success");
            result.setData(list2);
        }
        return  result;
    }


    //添加签到
    //INSERT INTO sign (teacher_id,class_id,sign_number,sign_name)
    //VALUE ("1234567","2203","12345","数学第二次签到")
    public  static Result addSign(String teacherId,String classId,String sign_number,String signName){
        Result result = new Result("fail",null);
        if (MysqlHelper.query("SELECT * FROM class where class_id = ?",new Object[]{classId}).size()==0){
            result.setData("班级不存在");
            return result;
        }

        String  sql = "INSERT INTO sign (teacher_id,class_id,sign_number,sign_name) VALUE (?,?,?,?)";
        int update = MysqlHelper.update(sql, new Object[]{teacherId, classId,sign_number, signName});
        if (update>0){
            result.setFlag("success");
        }
        return result;
    }

    //终止签到
    public  static Result stopSign(String signId){
        Result result = new Result("fail",null);
        String  sql = "UPDATE sign SET sign_time = 1 WHERE sign_id = ?";
        int update = MysqlHelper.update(sql, new Object[]{signId});
        if (update<0){
            result.setFlag("fail");
            result.setData("修改失败");
            return result;
        }
        return result;
    }

    //查看签到学生人数
    public  static String sumSign(String signId){
        String  sql = "SELECT sign_student FROM sign WHERE sign_id = ?";
        List<HashMap<String, Object>> query = MysqlHelper.query(sql, new Object[]{signId});
        int sign_student;
        Object s = query.get(0).get("sign_student");
        if (s!=null&&s.toString()!="")
            sign_student =((String) query.get(0).get("sign_student")).split(",").length;
        else
            sign_student = 0;

        return sign_student+"";
    }

    //学生签到 包含检查签到口令，检查重复签到，添加签到学生
    public static Result studentSign(String Sid,String signNumber,String signId){
        Result result = new Result("fail", null);
        if (MysqlHelper.query("SELECT sign_time FROM sign WHERE sign_id = ?",new Object[]{signId}).get(0).get("sign_time").toString().equals("1")){
            result.setData("签到已经结束了");
            return  result;
        }

        HashMap<String,Object> map = (HashMap<String, Object>) signInfoById(signId).getData();
        String checkNumber = map.get("sign_number")+"";
        if (!checkNumber.equals(signNumber)){
            result.setData("签到口令错误");
            return  result;
        }
        String student = (String) map.get("sign_student");
        if (student!=null){
            String[] sArr = student.split(",");
            for (String s:sArr) {
                if (Sid.equals(s)){
                    result.setData("请勿重复签到");
                    return result;
                }
            }
        }


        student = student==null?"":student + (student.equals("")?(""+Sid):(","+Sid));
        String sql = "UPDATE sign set sign_student = ? WHERE sign_id = ?";
        MysqlHelper.update(sql,new Object[]{student,signId});
        result.setFlag("success");
        return  result;
    }

    //通过老师ID查询所管理的班级
    public static Result findClassByTeacherId(String teacherId){
        Result result = new Result("success",null);
        String sql = "SELECT * FROM class WHERE teacher_id = ?";
        List<HashMap<String, Object>> classList = MysqlHelper.query(sql, new Object[]{teacherId});
        for (int i = 0;i<classList.size();i++){
            String studentSum = findStudentNumberByClassId(classList.get(i).get("class_id").toString());
            classList.get(i).put("studentSum",studentSum);
        }
        result.setData(classList);
        return  result;
    }

    //通过班级ID查询学生数量
    public static String findStudentNumberByClassId(String ClassId){
        String sql = "SELECT COUNT(*) sum FROM users_s WHERE class_id = ?";
        List<HashMap<String, Object>> classList = MysqlHelper.query(sql, new Object[]{ClassId});
        return  classList.get(0).get("sum").toString();
    }

    //通过老师Id寻找其所有的班级签到
    public static Result findAllSignByTeacherId(String Tid){
        Result result = new Result("success", null);
        Result classByTeacherId = findClassByTeacherId(Tid);
        //通过老师Id获得 签到ID，老师名字，迁到码，签到备注，签到状态
        String sql = "SELECT s.sign_id,s.class_id,className,s.teacher_id,u.`Name`,s.sign_number,s.sign_name,s.sign_time FROM sign s LEFT JOIN users_t u ON s.teacher_id = u.Tid LEFT JOIN class c ON s.class_id = c.class_id WHERE s.teacher_id = ?";
        List<HashMap<String, Object>> signInfo = MysqlHelper.query(sql, new Object[]{Tid});
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < signInfo.size(); i++) {
            HashMap<String, Object> m = signInfo.get(i);
            String signSum = sumSign(m.get("sign_id") + "");
            String classSum = findStudentNumberByClassId(m.get("class_id") + "");
            m.put("sumSignNumber",signSum+"/"+classSum);
            list.add(m);
        }
        result.setData(list);

        return  result;
    }

    //通过学生ID寻找到对应班级所有签到以及该学生的签到情况
    public static  Result findMySignByStudentId(String studentId){
        Result result = new Result("success", null);
        String sql = "SELECT *,(SELECT className FROM class WHERE class_id = sign.class_id) className,IF(CONCAT(\",\",sign_student,\",\") LIKE (?),\"已签到\",\"未签到\") \"check\"\n" +
                " FROM sign WHERE class_id = (SELECT class_id FROM users_s WHERE ID = ?)";
        List<HashMap<String, Object>> query = MysqlHelper.query(sql, new Object[]{"%," + studentId + ",%", studentId});
        result.setData(query);
        return  result;
    }

    //根据签到ID更改签到状态
    public static Result changeSignTime(String signId){
        Result result = new Result("success", null);
        String sql = "UPDATE sign SET sign_time = IF(sign_time='0',1,0) WHERE sign_id = ?";
        int update = MysqlHelper.update(sql, new Object[]{signId});
        if (update<0){
            result.setFlag("fail");
        }
        return  result;
    }

}
