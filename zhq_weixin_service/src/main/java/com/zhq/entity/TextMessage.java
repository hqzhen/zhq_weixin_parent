package com.zhq.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextMessage {

	/**
	 * ToUserName 是 接收方帐号（收到的OpenID）
	 * FromUserName 是 开发者微信号
	 * CreateTime 是 消息创建时间
	 * （整型） MsgType 是 text
	 * Content 是 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 * MsgId消息id，64位整型
	 */

	private String ToUserName;
	private String FromUserName;
	private Long CreateTime;
	private String MsgType;
	private String Content;
	private Long MsgId;
}
