package com.x.bp.core.utils;

import com.sun.security.sasl.Provider;
import com.x.bp.common.enums.EnumTextTrans;
import com.x.bp.common.enums.LanguageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

/**
 * @author tianhe
 * @Description
 * @date 2023/12/14 11:25 AM
 */
@Slf4j
@Component
public class EmailUtil {
    @Value("${spring.mail.host}")
    private String HOST;

    @Value("${spring.mail.port}")
    private String PORT;

    @Value("${spring.mail.username}")
    private String USERNAME;//邮箱发送账号

    @Value("${spring.mail.password}")
    private String PASSWORD;//邮箱授权码

    @Value("${spring.mail.personal:BusinessPromotion}")
    private String PERSONAL;//邮箱发送账号

    @Value("${spring.mail.official}")
    private String OFFICIAL_EMAIL;

    public Session createSession() {
        Security.addProvider(new Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.socketFactory.port", PORT);
        props.put("mail.smtp.auth", "true");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        return session;
    }

    @Async
    public void sendTextNoThrows(EnumTextTrans enumTextTrans, Integer language, String toEmailAddress, Object... args) {
        try {
            this.sendText(enumTextTrans, language, toEmailAddress, args);
        } catch (MessagingException | UnsupportedEncodingException messagingException) {
            log.error("send email fail,e:{}", messagingException.getMessage());
        }
    }

    public void sendText(EnumTextTrans enumTextTrans, Integer language, String toEmailAddress, Object... args) throws MessagingException, UnsupportedEncodingException {
        //	创建Session会话
        Session session = createSession();
        String text = EnumTextTrans.getTextTransContent(enumTextTrans, language, args);
        //建立邮件对象
        MimeMessage message = new MimeMessage(session);
        //设置邮件的发信人、收件人、主题
        //附带发件人名字
        message.setFrom(new InternetAddress(USERNAME, PERSONAL));
        message.setRecipients(Message.RecipientType.TO, toEmailAddress);
        message.setSubject(EnumTextTrans.getTextTransTitle(enumTextTrans, language, args));
        //文本
        String sendText = text;//EmailTemplateEnum.getTextTransContent(EmailTemplateEnum.SYSTEM_TEMPLATE, language, toEmailAddress.split("@")[0], text);
        message.setText(sendText, "utf-8", "html");
        message.setSentDate(new Date());
        message.saveChanges();
        //发送邮件
        Transport.send(message);
        log.info("===emailUtil send successful email:{}, content:{}", toEmailAddress, sendText);
    }

    @Async
    public void sendText2OfficialNoThrows(EnumTextTrans enumTextTrans, Object... args) {
        try {
            this.sendText(enumTextTrans, LanguageTypeEnum.ZH.getCode(), OFFICIAL_EMAIL, args);
        } catch (MessagingException | UnsupportedEncodingException messagingException) {
            log.error("send email fail,e:{}", messagingException.getMessage());
        }
    }

//    @Async
//    public void sendTextNoThrows(EnumTextTrans enumTextTrans, Integer language, Long userId, Object... args) {
//        try {
//            SfUser user = userRepository.getUserById(userId);
//            if (user == null) {
//                log.error("send email fail user is null userId:{}", userId);
//                return;
//            }
//            this.sendText(enumTextTrans, language, user.getEmail(), args);
//        } catch (Exception e) {
//            log.error("send email fail enumTextTrans:{}, userId:{} args:{}",
//                    JSON.toJSONString(enumTextTrans), userId, JSON.toJSONString(args), e);
//        }
//    }
}
