package com.dev.utils.email;

import com.dev.model.email.EmailModel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;

/**
 * Created on 2019-08-02 16:06.
 *
 * @author zgq7
 */
public class MailSendUtils {

    private static final Logger logger = LoggerFactory.getLogger(MailSendUtils.class);

    /**
     * 发送者地址
     **/
    private static String posterAdress = "1140661106@qq.com";

    /**
     * 发送者姓名
     **/
    private static final String posterName = "廖南洲";

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(final EmailModel emailModel) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            mimeMessage.setFrom(posterAdress);
            mimeMessage.setRecipients(Message.RecipientType.TO, emailModel.getRecieverEmailAddress());
            mimeMessage.setText("<html><body>"
                    + "hello：" + emailModel.getRecieverName()
                    + "<br>" + "msg：" + emailModel.getEmailContent()
                    + "<br>" + "from :" + posterName
                    + "</body></html>");
        };

        try {
            this.javaMailSender.send(mimeMessagePreparator);
        } catch (MailException e) {
            logger.error("邮箱异常：{}", e);
        }

    }
}
