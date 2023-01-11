package com.spring.javawspring.dao;

import org.apache.ibatis.annotations.Param;

public interface AdminDAO {

	public int setMemberLevelUpdate(@Param("idx") int idx,@Param("level")  int level);

	public int setMemberDelete(@Param("idx") int idx);
	
}
