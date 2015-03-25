package org.ua.shop;

import org.h2.tools.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ua.shop.api.context.ShopAppContext;
import org.ua.shop.ui.Starter;

import java.applet.AppletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by wheel on 10.08.14.
 */
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        Server server = Server.createTcpServer(new String[]{
//                "-tcpAllowOthers" ,"-webPort" , String.valueOf(8092),
//        }).start();
//        server.start();
//        System.out.println(server.getPort());
//        System.out.println(server.getURL());
//        System.out.println(server.getService().getName());
//        System.out.println(server.getService().getAllowOthers());
//        System.out.println(server.getService().getPort());
//        Class.forName("org.h2.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:"+server.getPort()+"/D:\\shop-shop-shop\\shop-con-2\\shop-con\\shop-con\\h2\\shop", "321", "321");
//        Statement statement = conn.createStatement();
//        statement.execute("SELECT * FROM example");
//        System.out.println(statement.getResultSet().toString());
        ApplicationContext context = new ClassPathXmlApplicationContext("/configs/context.xml");
        ShopAppContext appContext = (ShopAppContext) context.getBean("shopAppContext");
        //Starter.launch(args);
    }
}
