package com.se.dungcuthethao.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.se.dungcuthethao.entity.KhachHang;
import com.se.dungcuthethao.service.KhachHangService;

@Repository
public class KhachHangServiceImpl implements KhachHangService {
	
	@Autowired
	private SessionFactory SessionFactory;

	@Override
	@Transactional
	public List<KhachHang> findAdd() {
		Session session = SessionFactory.getCurrentSession();
		List<KhachHang> list = session.createQuery("from KhachHang", KhachHang.class).getResultList();
		return list;
	}

	@Override
	@Transactional
	public KhachHang findById(Long id) {
		Session session = SessionFactory.getCurrentSession();
		KhachHang khachHang = session.find(KhachHang.class, id);
		return khachHang;
	}

	@Override
	@Transactional
	public void save(KhachHang khachHang) {
		Session session = SessionFactory.getCurrentSession();
		session.save(khachHang);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		Session session = SessionFactory.getCurrentSession();
		KhachHang khachHang = session.find(KhachHang.class, id);
		if(khachHang != null) 
			session.delete(khachHang);
		
	}

	@Override
	@Transactional
	public void update(KhachHang khachHang) {
		Session session = SessionFactory.getCurrentSession();
		session.update(khachHang);
	}

}