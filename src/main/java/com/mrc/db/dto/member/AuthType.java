package com.mrc.db.dto.member;



public enum AuthType {
	Admin(1), AdMember(10), LocalMember(20), Member(30);
	 final private Integer authType;
	 private AuthType(Integer authType) { //enum에서 생성자 같은 역할
        this.authType = authType;
    }
	
	public static AuthType IntToEnum(int val)
	{
		switch(val)
		{
		case 1:
			return AuthType.Admin;
		case 10:
			return AuthType.AdMember;
		case 20:
			return AuthType.LocalMember;
		default:
			return AuthType.Member;
		}
	}
    public Integer getenAuthType() { // 문자를 받아오는 함수
        return authType;
    }
}

