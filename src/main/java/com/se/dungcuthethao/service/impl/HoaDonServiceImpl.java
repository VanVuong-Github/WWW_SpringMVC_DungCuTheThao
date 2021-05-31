package com.se.dungcuthethao.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.se.dungcuthethao.entity.HoaDon;
import com.se.dungcuthethao.service.HoaDonService;

@Repository
public class HoaDonServiceImpl implements HoaDonService {
	
	@Autowired
	private SessionFactory SessionFactory;

	@Override
	@Transactional
	public List<HoaDon> findAdd() {
		Session session = SessionFactory.getCurrentSession();
		List<HoaDon> list = session.createQuery("from HoaDon", HoaDon.class).getResultList();
		return list;
	}

	@Override
	@Transactional
	public HoaDon findById(Long id) {
		Session session = SessionFactory.getCurrentSession();
		HoaDon hoaDon = session.find(HoaDon.class, id);
		return hoaDon;
	}

	@Override
	@Transactional
	public void save(HoaDon hoaDon) {
		Session session = SessionFactory.getCurrentSession();
		session.save(hoaDon);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		Session session = SessionFactory.getCurrentSession();
		HoaDon hoaDon = session.find(HoaDon.class, id);
		if(hoaDon != null) 
			session.delete(hoaDon);
	}

	@Override
	@Transactional
	public void update(HoaDon hoaDon) {
		Session session = SessionFactory.getCurrentSession();
		session.saveOrUpdate(hoaDon);
	}

}