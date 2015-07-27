package zpark.dao;

import zpark.entity.SampleEntity;
import zpark.ext.query.Page;

public abstract class User2Dao {
    
    public abstract Page<SampleEntity> find1(String name);
    
    
    public void find2(String name){
        
    }

}
