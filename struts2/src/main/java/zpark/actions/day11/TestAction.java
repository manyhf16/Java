package zpark.actions.day11;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import zpark.entity.City;
import zpark.entity.Product;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

//@Validations(requiredStrings=@RequiredStringValidator(message="必须提供name", fieldName="name"))
public class TestAction extends ActionSupport implements ModelDriven<City>{


    public String execute() {
        System.out.println(model);
        
        Product target = new Product();
//        BeanUtils.copyProperties(model, target );
        BeanWrapper bw2 = PropertyAccessorFactory.forBeanPropertyAccess(model);
        bw2.getPropertyDescriptors()
        BeanWrapper wr = PropertyAccessorFactory.forBeanPropertyAccess(target);
        wr.getpro
        System.out.println(target);
        return "success";
    }

    private City model = new City();
    @Override
    public City getModel() {
        // TODO Auto-generated method stub
        return model;
    }

}
