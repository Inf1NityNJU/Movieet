package moviereview.util;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;

public class FileTransaction {
    private static final String URL = "123.206.185.186";
    private static final int PORT = 22;
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "infinity2017";
    private static final String PATH = "/mydata/moviereview/algorithm";
    private static final String FILE_NAME = "M5P_model";


    public static void upload() {
        try {
            upload0(URL, USER_NAME, PASSWORD, PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void download(String fileName, String cacheName) {
        try {
            download0(URL, USER_NAME, PASSWORD, PORT, fileName, cacheName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用JSch包实现SFTP下载、上传文件
     *
     * @param ip   主机IP
     * @param user 主机登陆用户名
     * @param psw  主机登陆密码
     * @param port 主机ssh2登陆端口，如果取默认值，传-1
     */
    private static void upload0(String ip, String user, String psw, int port) throws Exception {
        Session session = null;
        Channel channel = null;


        JSch jsch = new JSch();


        if (port <= 0) {
            //连接服务器，采用默认端口
            session = jsch.getSession(user, ip);
        } else {
            //采用指定的端口连接服务器
            session = jsch.getSession(user, ip, port);
        }

        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new Exception("session is null");
        }

        //设置登陆主机的密码
        session.setPassword(psw);//设置密码
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间
        session.connect(30000);

        try {
            //创建sftp通信通道
            channel = (Channel) session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;


            //进入服务器指定的文件夹
            sftp.cd(PATH);


            //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
            OutputStream outstream = sftp.put(FILE_NAME);
            InputStream instream = new FileInputStream(new File("TEST"));

            byte b[] = new byte[1024];
            int n;
            while ((n = instream.read(b)) != -1) {
                outstream.write(b, 0, n);
            }

            outstream.flush();
            outstream.close();
            instream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            channel.disconnect();
        }
    }

    /**
     * 利用JSch包实现SFTP下载、上传文件
     *
     * @param ip   主机IP
     * @param user 主机登陆用户名
     * @param psw  主机登陆密码
     * @param port 主机ssh2登陆端口，如果取默认值，传-1
     */
    private static void download0(String ip, String user, String psw, int port, String fileName, String cacheName) throws Exception {
        Session session = null;
        Channel channel = null;


        JSch jsch = new JSch();


        if (port <= 0) {
            //连接服务器，采用默认端口
            session = jsch.getSession(user, ip);
        } else {
            //采用指定的端口连接服务器
            session = jsch.getSession(user, ip, port);
        }

        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new Exception("session is null");
        }

        //设置登陆主机的密码
        session.setPassword(psw);//设置密码
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间
        session.connect(30000);

        try {
            //创建sftp通信通道
            channel = (Channel) session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;


            //进入服务器指定的文件夹
            sftp.cd(PATH);


            //以下代码实现从服务器下载一个文件到本地
            InputStream inputStream = sftp.get(fileName);
            OutputStream outputStream = new FileOutputStream(new File(cacheName));

            byte b[] = new byte[1024];
            int n;
            while ((n = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, n);
            }

            outputStream.flush();
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            channel.disconnect();
        }
    }
}
