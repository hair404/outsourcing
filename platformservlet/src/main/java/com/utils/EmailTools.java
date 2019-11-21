package com.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class EmailTools {

    private static final String SMTP_SERVER = "smtp.163.com";
    private static final String EMAIL = "hair404@163.com";
    private static final String VERIFY_CODE = "hair404";
    private static final String WRITER = "人力服务外包平台";

    /**
     * 发送邮件
     * @param email 目标邮箱
     * @param title 邮件标题
     * @param content 邮件内容
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static void sendEmail(String email, String title, String content) throws MessagingException, UnsupportedEncodingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_SERVER);
        props.put("mail.smtp.auth", true);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", 25);
        props.put("mail.debug", true);
        Session mailSession = Session.getInstance(props);

        MimeMessage message = new MimeMessage(mailSession);
        // 设置邮件主题
        message.setSubject(title);
        // 设置邮件正文
        message.setContent(content, "text/html;charset=UTF-8");
        // 设置邮件发送日期
        message.setSentDate(new Date());
        InternetAddress address = new InternetAddress(EMAIL, WRITER);
        // 设置邮件发送者的地址
        message.setFrom(address);
        message.saveChanges();
        // 设置邮件接收方的地址
        message.setRecipients(Message.RecipientType.CC, EMAIL);
        message.setRecipients(Message.RecipientType.TO, email);
        Transport transport = null;
        transport = mailSession.getTransport();
        message.saveChanges();

        transport.connect(SMTP_SERVER, EMAIL, VERIFY_CODE);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }
}
