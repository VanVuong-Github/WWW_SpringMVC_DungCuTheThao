package com.se.dungcuthethao.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.se.dungcuthethao.entity.ChiTietHoaDon;
import com.se.dungcuthethao.service.ChiTietHoaDonService;

@Repository
public class ChiTietHoaDonServiceImpl implements ChiTietHoaDonService{
	
	@Autowired
	private SessionFactory SessionFactory;

	@Override
	@Transactional
	public List<ChiTietHoaDon> findAdd() {
		Session session = SessionFactory.getCurrentSession();
		List<ChiTietHoaDon> list = session.createQuery("from ChiTietHoaDon", ChiTietHoaDon.class).getResultList();
		return list;
	}

	@Override
	@Transactional
	public ChiTietHoaDon findById(Long id) {
		Session session = SessionFactory.getCurrentSession();
		ChiTietHoaDon chiTietHoaDon = session.find(ChiTietHoaDon.class, id);
		return chiTietHoaDon;
	}

	@Override
	@Transactional
	public void save(ChiTietHoaDon chiTietHoaDon) {
		Session session = SessionFactory.getCurrentSession();
		session.save(chiTietHoaDon);
	}

	@Override
	@Transactional
	public void update(ChiTietHoaDon chiTietHoaDon) {
		Session session = SessionFactory.getCurrentSession();
		session.saveOrUpdate(chiTietHoaDon);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		Session session = SessionFactory.getCurrentSession();
		ChiTietHoaDon chiTietHoaDon = session.find(ChiTietHoaDon.class, id);
		if(chiTietHoaDon != null) 
			session.delete(chiTietHoaDon);
	}

}
