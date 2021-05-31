package com.se.dungcuthethao.service;

import java.util.List;

import com.se.dungcuthethao.entity.NhaCungCap;

public interface NhaCungCapService {
	public List<NhaCungCap> findAdd();

	public NhaCungCap findById(Long id);

	public void save(NhaCungCap nhaCungCap);

	public void deleteById(Long id);

}