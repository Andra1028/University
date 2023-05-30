using System;
using System.Collections.Generic;

namespace ISS_Restaurant.repository
{
    public interface Repository<TId, TEntity>
    {
        List<TEntity> findAll();
        TEntity save(TEntity entity);
        TEntity update(TEntity entity);
        TEntity delete(TEntity entity);
    }
}