package com.se.dungcuthethao.service.impl;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.se.dungcuthethao.entity.TaiKhoan;
import com.se.dungcuthethao.service.TaiKhoanService;

@Repository
public class TaiKhoanServiceImpl implements TaiKhoanService {

	@Autowired
	private SessionFactory SessionFactory;

	@Override
	@Transactional
	public TaiKhoan findByUsername(String username) {
		Session session = SessionFactory.getCurrentSession();
		TaiKhoan taiKhoan = session
				.createQuery("from TaiKhoan where username = :username", TaiKhoan.class)
				.setParameter("username", username)
				.getSingleResult();
		return taiKhoan;
	}

	@Override
	@Transactional
	public void save(TaiKhoan taiKhoan) {
		Session session = SessionFactory.getCurrentSession();
		session.save(taiKhoan);
	}

	@Override
	@Transactional
	public boolean existsByUsername(String username) {
		Session session = SessionFactory.getCurrentSession();
		try {
			TaiKhoan taiKhoan = session
					.createQuery("from TaiKhoan where username = :username", TaiKhoan.class)
					.setParameter("username", username)
					.getSingleResult();
			if(taiKhoan != null)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
