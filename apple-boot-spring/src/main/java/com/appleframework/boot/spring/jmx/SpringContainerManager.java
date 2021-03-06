package com.appleframework.boot.spring.jmx;

import com.appleframework.boot.core.Container;
import com.appleframework.boot.spring.SpringContainer;


/**
 * Main. (API, Static, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class SpringContainerManager implements SpringContainerManagerMBean {
	
	Container springContainer = new SpringContainer();

	@Override
	public String getName() {
		return springContainer.getName();
	}

	@Override
	public void restart() {
		springContainer.restart();
	}
	
	@Override
	public void start() {
		springContainer.start();
	}

	@Override
	public void stop() {
		springContainer.stop();
		
	}

	@Override
	public boolean isRunning() {
		return springContainer.isRunning();
	}
   
}