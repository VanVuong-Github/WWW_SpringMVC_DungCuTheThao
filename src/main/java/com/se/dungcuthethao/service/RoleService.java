package com.se.dungcuthethao.service;

import com.se.dungcuthethao.entity.Role;
import com.se.dungcuthethao.entity.enumEntity.RoleEnum;

public interface RoleService {

	public Role findByRole(RoleEnum role);
	
	public void update(Role role);

}