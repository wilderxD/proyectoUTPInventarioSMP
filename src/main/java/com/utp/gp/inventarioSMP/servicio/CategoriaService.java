
package com.utp.gp.inventarioSMP.servicio;

import com.utp.gp.inventarioSMP.dao.CategoriaDao;
import com.utp.gp.inventarioSMP.entidades.Categoria;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService implements ICategoria{

    @Autowired
    private CategoriaDao categoriaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        return categoriaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Categoria> findAll(Pageable pageable) {
        return categoriaDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Categoria categoria) {
        categoriaDao.save(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria findOne(Long id) {
        return categoriaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoriaDao.deleteById(id);;
    }
    
}
