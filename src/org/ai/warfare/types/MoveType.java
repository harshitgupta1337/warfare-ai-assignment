package org.ai.warfare.types;

public enum MoveType {
    M1DeathBlitz(1), CommandoParaDrop(2);
    
    private int code;

    private MoveType(int code) {
            this.setCode(code);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
};   