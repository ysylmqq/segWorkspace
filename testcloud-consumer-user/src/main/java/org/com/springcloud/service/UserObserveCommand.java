package org.com.springcloud.service;

import org.com.springcloud.entity.User;

import com.netflix.hystrix.HystrixObservableCommand;

import rx.Observable;

public class UserObserveCommand  extends HystrixObservableCommand<User>{

	protected UserObserveCommand(com.netflix.hystrix.HystrixObservableCommand.Setter setter) {
		super(setter);
	}

	@Override
	protected Observable<User> construct() {
		return null;
	}

}
