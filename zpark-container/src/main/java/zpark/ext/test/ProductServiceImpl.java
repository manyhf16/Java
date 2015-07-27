package zpark.ext.test;

import zpark.ext.annotations.common.Inject;
import zpark.ext.annotations.common.Scannable;

@Scannable
public class ProductServiceImpl implements ProductService {
    
    @Inject
    private ProductDao dao;

    @Override
    public void add() {
        System.out.println(dao);
        
    }

}
