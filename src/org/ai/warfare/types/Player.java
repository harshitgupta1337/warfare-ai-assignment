package org.ai.warfare.types;

public enum Player {
	BLUE('1'), GREEN('2');
    
    private char code;

    private Player(char code) {
            this.setCode(code);
    }

	public char getCode() {
		return code;
	}

	public void setCode(char code) {
		this.code = code;
	}
}
