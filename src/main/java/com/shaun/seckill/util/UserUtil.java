package com.shaun.seckill.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaun.seckill.pojo.User;
import com.shaun.seckill.vo.Result;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Shaun
 * @Date 2022/7/12 16:06
 * @Description: 用户工具类，向数据库user表中添加5000条用户记录，并生成对应的userTicket，生成对应的jmeter数据文件
 */

public class UserUtil {

    public static void createUser(int count) throws Exception {
        List<User> userList = new ArrayList<>(count);
        // 1、创建 count个用户，添加到List集合中
        for (int i = 1; i <= count; i++) {
            User user = new User();
            user.setId(13000000000L + i);
            user.setNickname("user: " + i);
            user.setPasword("123456");
            user.setSalt("1a2b3c4d");
            user.setRegisterDate(new Date());
            user.setLoginCount(1);
            userList.add(user);
        }

        // 2、准备数据库连接对象，将5000千个用户添加到数据库中
        Connection connection = getCon();
        // 2.1、编写sql语句
        String sql = "insert into t_user(id, nickname, pasword, salt, register_date, login_count) values(?, ?, ?, ?, ?, ?)";
        // 2.2、获取预处理对象
        PreparedStatement statement = connection.prepareStatement(sql);
        // 2.3、取出userList集合中的所有用户
        for (User user : userList) {
            // 2.4、设置每个占位符的属性
            statement.setLong(1, user.getId());
            statement.setString(2, user.getNickname());
            statement.setString(3, MD5Util.inputPassToDbPass(user.getPasword(), user.getSalt()));
            statement.setString(4, user.getSalt());
            statement.setTimestamp(5, new Timestamp(user.getRegisterDate().getTime()));
            statement.setInt(6, user.getLoginCount());
            // 2.5、执行批插入
            statement.addBatch();
        }
        // 2.6、批处理
        statement.executeBatch();
        // 2.7、关闭数据库相关连接
        statement.close();
        connection.close();

        System.out.println("insert into db");

        // 3、为每个用户生成一个cookie值，并获取该值写入数据文件
        // 3.1、设置访问url
        String urlToken = "http://localhost:8080/login/doLogin";
        // 3.2、创建写入的文件
        File file = new File("C:\\Users\\Shaun\\Desktop\\config.txt");
        if (file.exists())
            file.delete();
        file.createNewFile();
        // 3.3、使用RandomAccessFile去写入文件
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.seek(0);
        // 3.4、遍历每一个user，为其生成cookie
        for (User user : userList) {
            // 3.5、为访问登录接口做准备
            // 3.6、创建URL对象
            URL url = new URL(urlToken);
            // 3.7、获取Http连接对象
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            // 3.8、添加参数
            OutputStream os = co.getOutputStream();
            String params = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFormPass(user.getPasword());
            os.write(params.getBytes());
            os.flush();
            // 3.9、获取响应结果
            InputStream in = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) >= 0) {
                bout.write(buffer, 0, len);
            }
            os.close();
            in.close();
            bout.close();
            // 4、从响应结果中获取userTicket
            String response = new String(bout.toByteArray());
            ObjectMapper mapper = new ObjectMapper();
            Result result = mapper.readValue(response, Result.class);
            String userTicket = (String) result.getObj();
            System.out.println("create userTicket : " + userTicket);

            // 5、将UserTicket和userId写入到文件中
            String row = user.getId() + "," + userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file " + user.getId());
        }
        raf.close();
        System.out.println("over");
    }

    /**
     * 创建数据库连接并返回
     * @return 数据库连接对象
     */
    private static Connection getCon() throws Exception {
        String username = "root";
        String password = "123456";
        String url = "jdbc:mysql://192.168.199.3:3307/seckill?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) throws Exception {
        createUser(5000);
    }
}
