package com.mysite.sbb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloLombok {
	private String hello;
	private int lombox;

	public static void main(String[] args) {
		HelloLombok helloLombok = new HelloLombok();

		helloLombok.setHello("헬로"); // setter 호출
		helloLombok.setLombox(5); // setter 호출

		System.out.println(helloLombok.getHello()); // getter 호출 -> "헬로"
		System.out.println(helloLombok.getLombox()); // getter 호출 -> 5
	}

}
