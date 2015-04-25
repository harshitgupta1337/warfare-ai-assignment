package org.ai.warfare.types;

public enum Occupancy {
    BLUE('1'), GREEN('2'), UNOCCUPIED('3');
    
    private char code;

    private Occupancy(char code) {
            this.setCode(code);
    }

	public int getCode() {
		return code;
	}

	public void setCode(char code) {
		this.code = code;
	}
};   