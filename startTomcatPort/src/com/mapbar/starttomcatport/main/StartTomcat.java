package com.mapbar.starttomcatport.main;

import com.mapbar.starttomcatport.util.GetTomcatPid;
import com.mapbar.starttomcatport.util.TomcatOperator;

public class StartTomcat {
	public static void main(String[] args) {
		String[] serverNames = {"apache-tomcat-zk","apache-tomcat-work"};
		for (String serverName : serverNames) {
			try {
				startByName(serverName);
			} catch (Exception e) {
			}
		}
	}
	private static void startByName(String serverName) throws Exception{
		// 先检测后没有启动
				long pid = GetTomcatPid.getByTomcatName(serverName);
				if (pid == 0) {
					// 没有启动，启动
					TomcatOperator.startTomcat(serverName);
					int count = 0;
					while (pid == 0) {
						Thread.sleep(2000);
						pid = GetTomcatPid.getByTomcatName(serverName);
						count++;
						if (count > 20) {
							pid = 0;
							break;
						}
					}
				} else {
					//已经启动，先关闭， 再启动
					TomcatOperator.stopTomcat(pid);
					int count = 0;
					while (pid != 0) {
						Thread.sleep(2000);
						pid = GetTomcatPid.getByTomcatName(serverName);
						count++;
						if (count > 20 || pid == 0) {
							break;
						}
					}
					System.out.println("start");
					TomcatOperator.startTomcat(serverName);
				}
	}
}
