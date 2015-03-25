package org.ua.shop.api.context;

import org.h2.tools.Server;

import java.sql.SQLException;

/**
 * @author Yaroslav.Gryniuk
 */
public class DBStarter {
    private boolean start;


    public void startDB() {
        if (false) {
            try {
                Server server = Server.createTcpServer(new String[]{
                        "-tcpAllowOthers", "-webPort", String.valueOf(8092),
                }).start();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
