package com.kpi.server;

import com.kpi.server.configuration.SpringConfiguration;
import com.kpi.server.threads.MainThread;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ServerApplication {

	public static void main(String[] args) {

		var context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

		MainThread mainThread = context.getBean(MainThread.class);
		mainThread.run();
	}
}
