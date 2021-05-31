package com.se.dungcuthethao.service.impl;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.se.dungcuthethao.entity.Role;
import com.se.dungcuthethao.entity.enumEntity.RoleEnum;
import com.se.dungcuthethao.service.RoleService;

@Repository
public class RoleServiceImpl implements RoleService {

	@Autowired
	private SessionFactory SessionFactory;

	@Override
	@Transactional
	public Role findByRole(RoleEnum role) {
		Session session = SessionFactory.getCurrentSession();
		Role rs = session.createQuery("from Role where role = :role", Role.class).setParameter("role", role)
				.getSingleResult();
		return rs;
	}

	@Override
	@Transactional
	public void update(Role role) {
		Session session = SessionFactory.getCurrentSession();
		session.saveOrUpdate(role);
	}

}
