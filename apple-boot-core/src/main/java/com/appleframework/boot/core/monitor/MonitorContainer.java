package com.appleframework.boot.core.monitor;

import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jgroups.JChannel;
import org.jgroups.Message;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.utils.NetUtils;
import com.appleframework.boot.utils.SystemPropertiesUtils;

public class MonitorContainer implements Container {

	private static Logger logger = Logger.getLogger(MonitorContainer.class);
	
	private static String CONTAINER_NAME = "MonitorContainer";

	@Override
	public void start() {
		logger.warn(CONTAINER_NAME + " start");
		this.send();
	}

	@Override
	public void stop() {
		logger.warn(CONTAINER_NAME + " stop");
	}

	@Override
	public void restart() {
		this.send();
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public String getName() {
		return CONTAINER_NAME;
	}

	@Override
	public String getType() {
		return CONTAINER_NAME;
	}
	
	private void send() {
		try {
			/**
			 * 参数里指定Channel使用的协议栈，如果是空的，则使用默认的协议栈，
			 * 位于JGroups包里的udp.xml。参数可以是一个以冒号分隔的字符串， 或是一个XML文件，在XML文件里定义协议栈。
			 */
			logger.warn("发送监控同步数据通知");
			
			Properties prop = SystemPropertiesUtils.getProp();
			String hostName = NetUtils.getLocalHost();
			prop.put("node.ip", NetUtils.getIpByHost(hostName));
			prop.put("node.host", hostName);
			prop.put("install.path", getInstallPath());
			// 创建一个通道
			JChannel channel = new JChannel();
			// 加入一个群
			channel.connect("MonitorContainer");
			// 发送事件
			// 这里的Message的第一个参数是发送端地址
			// 第二个是接收端地址
			// 第三个是发送的字符串
			// 具体参见jgroup send API
			Message msg = new Message(null, null, prop);
			// 发送
			channel.send(msg);
			// 关闭通道
			channel.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private String getInstallPath() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("");
		String path = url.getPath();
		int indexConf = path.lastIndexOf("/conf");
		String installPath = path.substring(0, indexConf);
		logger.error(installPath);
		return installPath;
	}

}
