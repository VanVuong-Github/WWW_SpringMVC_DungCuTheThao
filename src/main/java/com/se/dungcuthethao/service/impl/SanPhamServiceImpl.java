package com.se.dungcuthethao.service.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.se.dungcuthethao.entity.SanPham;
import com.se.dungcuthethao.service.SanPhamService;

@Repository
public class SanPhamServiceImpl implements SanPhamService {
	
	@Autowired
	private SessionFactory SessionFactory;

	@Override
	@Transactional
	public List<SanPham> findAdd() {
		Session session = SessionFactory.getCurrentSession();
		List<SanPham> list = session.createQuery("from SanPham", SanPham.class).getResultList();
		return list;
	}

	@Override
	@Transactional
	public SanPham findById(Long id) {
		Session session = SessionFactory.getCurrentSession();
		SanPham sanPham = session.find(SanPham.class, id);
		return sanPham;
	}

	@Override
	@Transactional
	public void save(SanPham sanPham) {
		Session session = SessionFactory.getCurrentSession();
		session.save(sanPham);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		Session session = SessionFactory.getCurrentSession();
		SanPham sanPham = session.find(SanPham.class, id);
		if(sanPham != null) 
			session.delete(sanPham);
	}

	@Override
	@Transactional
	public void update(SanPham sanPham) {
		Session session = SessionFactory.getCurrentSession();
		session.saveOrUpdate(sanPham);
	}

	@Override
	@Transactional
	public List<SanPham> getSanPhamsByName(String name) {
		Session session = SessionFactory.getCurrentSession();
		TypedQuery<SanPham> query = session.createQuery("from SanPham where ten LIKE CONCAT('%', :name, '%')", SanPham.class).setParameter("name", name);
		List<SanPham> list = query.getResultList();
		return list;
	}

}
