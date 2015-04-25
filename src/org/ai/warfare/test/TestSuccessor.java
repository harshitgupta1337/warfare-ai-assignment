package org.ai.warfare.test;

import org.ai.warfare.types.Successor;

public class TestSuccessor extends Successor{

	private int value;

	public TestSuccessor(int value){
		this.value = value;
	}
	public TestSuccessor(){
		
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
