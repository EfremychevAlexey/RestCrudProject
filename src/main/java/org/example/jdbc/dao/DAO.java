package org.example.jdbc.dao;

import java.util.ArrayList;

public interface DAO<Entity, Key> {
    int create(Entity entity);
    Entity read(Key key);
    int update(Entity entity);
    int delete(Entity entity);
}
