package com.se.dungcuthethao.service.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.se.dungcuthethao.entity.NhaCungCap;
import com.se.dungcuthethao.service.NhaCungCapService;

@Repository
public class NhaCungCapServiceImpl implements NhaCungCapService {

	@Autowired
	private SessionFactory SessionFactory;

	@Override
	@Transactional
	public List<NhaCungCap> findAdd() {
		Session session = SessionFactory.getCurrentSession();
		List<NhaCungCap> list = session.createQuery("from NhaCungCap", NhaCungCap.class).getResultList();
		return list;
	}

	@Override
	@Transactional
	public NhaCungCap findById(Long id) {
		Session session = SessionFactory.getCurrentSession();
		NhaCungCap nhaCungCap = session.find(NhaCungCap.class, id);
		return nhaCungCap;
	}

	@Override
	@Transactional
	public void save(NhaCungCap nhaCungCap) {
		Session session = SessionFactory.getCurrentSession();
		session.save(nhaCungCap);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		Session session = SessionFactory.getCurrentSession();
		NhaCungCap nhaCungCap = session.find(NhaCungCap.class, id);
		if (nhaCungCap != null)
			session.delete(nhaCungCap);
	}

	@Override
	@Transactional
	public void update(NhaCungCap nhaCungCap) {
		Session session = SessionFactory.getCurrentSession();
		session.saveOrUpdate(nhaCungCap);
	}

	@Override
	@Transactional
	public List<NhaCungCap> getNhaCungCapsByName(String name) {
		Session session = SessionFactory.getCurrentSession();
		TypedQuery<NhaCungCap> query = session
				.createQuery("from NhaCungCap where ten LIKE CONCAT('%', :name, '%')", NhaCungCap.class)
				.setParameter("name", name);
		List<NhaCungCap> list = query.getResultList();
		return list;
	}

}