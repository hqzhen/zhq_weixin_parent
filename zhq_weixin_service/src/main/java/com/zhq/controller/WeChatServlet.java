package com.zhq.controller;

import com.zhq.entity.TextMessage;
import com.zhq.utils.CheckUtil;
import com.zhq.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @program: zhq_weixin_parent
 * @description: 微信公众号回调地址
 * @author: HQ Zheng
 * @create: 2019-09-17 21:35
 */
@RestController
@Slf4j
public class WeChatServlet {

    /**
     * 微信验签
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping("/weChatServlet")
    public String getWeChatServlet(String signature, String timestamp, String nonce, String echostr) {
        boolean checkSignature = CheckUtil.checkSignature(signature, timestamp, nonce);
        if (!checkSignature) {
            return null;
        }

        return echostr;
    }

    /**
     * 解析微信消息 回复消息
     *
     * @param request
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     * @throws Exception
     */
    @PostMapping("/weChatServlet")
    public String getWeChatServlet(HttpServletRequest request, HttpServletResponse response,
                                   String signature, String timestamp, String nonce, String echostr)
            throws Exception {
        Map<String, String> result = XmlUtils.parseXml(request);
        String toUserName = result.get("ToUserName");
        String fromUserName = result.get("FromUserName");
        String createTime = result.get("CreateTime");
        String msgType = result.get("MsgType");
        String content = result.get("Content");
        String msgId = result.get("MsgId");
        switch (msgType) {
            case "text":
                String resultXml = null;
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                if (content.equals("微信消息测试")) {
                    TextMessage textMessage = new TextMessage();
                    textMessage.setToUserName(fromUserName);
                    textMessage.setFromUserName(toUserName);
                    textMessage.setCreateTime(System.currentTimeMillis());
                    textMessage.setContent("微信消息测试回复");
                    textMessage.setMsgType("text");
                    resultXml = XmlUtils.messageToXml(textMessage);
                    out.print(resultXml);
                    out.close();
                }
                break;
            default:
                break;

        }
        return echostr;
    }

}
