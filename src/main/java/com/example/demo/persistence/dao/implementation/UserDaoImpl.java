package com.example.demo.persistence.dao.implementation;

import com.example.demo.persistence.dao.interfaces.IUserDAO;
import com.example.demo.persistence.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements IUserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findById(Long id){
        return Optional.ofNullable(em.find(UserEntity.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAll(){
        return em.createQuery("SELECT u FROM UserEntity u").getResultList();
    }

    @Override
    @Transactional
    public void saveUser(UserEntity userEntity) {
        em.persist(userEntity);
    }

    @Override
    @Transactional
    public void updateUser(UserEntity userEntity) {
        em.merge(userEntity);
    }

    @Override
    @Transactional
    public void deleteUser(UserEntity userEntity){
        em.remove(userEntity);
    }
}
