package com.se.dungcuthethao.service.impl;

import javax.persistence.TypedQuery;
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
		TypedQuery<TaiKhoan> query = session.createQuery("from TaiKhoan where userName = :username", TaiKhoan.class)
				.setParameter("username", username);
		TaiKhoan taiKhoan = query.getSingleResult();
		return taiKhoan;
	}

	@Override
	@Transactional
	public void save(TaiKhoan taiKhoan) {
		Session session = SessionFactory.getCurrentSession();
		session.saveOrUpdate(taiKhoan);
	}

	@Override
	@Transactional
	public boolean existsByUsername(String username) {
		Session session = SessionFactory.getCurrentSession();
		TaiKhoan taiKhoan = session.createQuery("from TaiKhoan where userName = :username", TaiKhoan.class)
				.setParameter("username", username).uniqueResult();
		if (taiKhoan != null)
			return true;
		return false;
	}

	@Override
	@Transactional
	public TaiKhoan findById(Long id) {
		Session session = SessionFactory.getCurrentSession();
		TaiKhoan taiKhoan = session.find(TaiKhoan.class, id);
		return taiKhoan;
	}

}
