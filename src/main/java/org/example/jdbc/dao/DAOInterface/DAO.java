package org.example.jdbc.dao.DAOInterface;

public interface DAO<Entity, Key> {
    int create(Entity entity);
//    List<Entity> read(Key key);
    Entity read(Key key);
    int update(Entity entity);
    int delete(Entity entity);
}
