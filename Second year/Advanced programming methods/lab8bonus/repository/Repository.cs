using System.Collections.Generic;

namespace lab8bonus.repository.generic
{
    public interface Repository<TId, TEntity>
    {
        List<TEntity> findAll();
    }
}