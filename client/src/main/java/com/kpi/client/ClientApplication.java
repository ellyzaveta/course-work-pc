package com.kpi.client;

import com.kpi.client.configuration.SpringConfiguration;
import com.kpi.client.threads.MainThread;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientApplication {

	public static void main(String[] args) {
		var context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

		MainThread mainThread = context.getBean(MainThread.class);
		mainThread.run();
	}

}
