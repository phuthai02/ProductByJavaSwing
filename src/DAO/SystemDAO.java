/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;

/**
 *
 * @author doanp
 * @param <EntityType>
 * @param <KeyType>
 */
public interface SystemDAO<EntityType, KeyType> {

    int insert(EntityType entity);

    int update(EntityType entity);

    int delete(KeyType id);

    List<EntityType> selectBySql(String sql, Object... args);

    List<EntityType> selectPaging(int Status, int pageIndex);

    List<EntityType> selectByKeyWord(KeyType keyWord, int Status);

    EntityType selectById(KeyType id);

}
