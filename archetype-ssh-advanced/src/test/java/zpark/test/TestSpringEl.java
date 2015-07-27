package zpark.test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypedValue;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class TestSpringEl {

	@SuppressWarnings("serial")
	@Test
	public void test01() {
		Map<String, Object> evaluationContext = new HashMap<String, Object>() {
			{
				put("name", "zhang");
				put("age", 18);
			}
		};
		ExpressionParser parser = new SpelExpressionParser();

		StandardEvaluationContext context = new StandardEvaluationContext(evaluationContext);
		String randomPhrase = parser.parseExpression(
				"from Product where 1=1 #{['name']!=null?'and name=:name':''} #{['age']!=null?'and age=:age':''}",
				new TemplateParserContext()).getValue(context, String.class);
		System.out.println(randomPhrase);
	}
	
	@Test
	public void test11() {
	    class Person {
	        private String name;
            public String getName() {
                return name;
            }
            public void setName(String name) {
                this.name = name;
            }	        
	    }
	    Person person = new Person();
	    person.setName("zhang");
        StandardEvaluationContext context = new StandardEvaluationContext(person);
        
	    class User{
	        private int state; // 1. 正常 2. 冻结 ...
            public int getState() {
                return state;
            }
            public void setState(int state) {
                this.state = state;
            }
            public boolean isFrozen() { // 检查是否冻结的方法
                System.out.println("执行isFroze()");
                return this.state == 2;
            }
            public boolean other(Object obj) { // 其他验证方法
                // 进行其他验证
                System.out.println(obj);
                return true;
            }
	    }	    
	    // 创建User对象, 假设从数据库中查询出来的状态是2（冻结）
	    User user = new User();
	    user.setState(2);
	    
	    // 编译spring 表达式
	    ExpressionParser parser = new SpelExpressionParser();
	    // 利用表达式调用user 中的方法 , 我看你们是把 "isFroze() and other()" 这种字符串写在@AccessRule()里面的
	    // isFroze和 other 都是user对象中的方法
	    Expression  exp = parser.parseExpression("#user.isFrozen() and #user.other(#root)"); 
	    
	    // 执行spring 表达式
	    context.setVariable("user", user);
	    Boolean result = exp.getValue(context,  Boolean.class);
	    System.out.println(result);
	    
	    // 注意1：【编译spring 表达式】和【执行spring 表达式】都是你们框架内部做了的，
	    // 就是你昨天给我看的：DefaultAccessRuleEngine这个类里面的代码
	    // 你只需要写这些验证表达式：类似于【isFroze() and other()】
	    
	    // 注意2：isFroze() 和 other 这些方法在user的实现类中都必须有，否则找不到就会出现异常
	}
	

	public static String condition(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return "and " + obj + "=:" + obj;
		}
	}
	
	@Test
	public void test03() {
		String hql = "and age between :begin and :end";
		Pattern p = Pattern.compile(":(\\w+)");
		Matcher m = p.matcher(hql);
		while(m.find()) {
			System.out.println(m.group(1));
		}
	}
	
	@Test
	public void test04() {
		String hql = "from Product where 1=1 #{and name = :name} #{and price between :begin and :end}";
//	    String hql = "from Product";
		Pattern p = Pattern.compile("#\\{(.+?)\\}");
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer(128);
		if(!m.find()) {
		    sb.append(hql);
		} else {
    		do {
    			String g1 = m.group(1);
    			m.appendReplacement(sb, g1);
    		}while(m.find());
		}
		System.out.println(sb);
	}

	@Test
	public void test02() throws NoSuchMethodException, SecurityException {
		ExpressionParser parser = new SpelExpressionParser();
		Map<String, Object> evaluationContext = new MyMap();
		StandardEvaluationContext context = new StandardEvaluationContext();
//		context.addMethodResolver(new ReflectiveMethodResolver(){
//			@Override
//			public MethodExecutor resolve(EvaluationContext context, Object targetObject, String name, List<TypeDescriptor> argumentTypes)
//					throws AccessException {
//				System.out.println("enter......" + targetObject.getClass() + "   " + name);
//				return super.resolve(context, targetObject, name, argumentTypes);
//			}
//		});
		PropertyAccessor accessor = new ReflectivePropertyAccessor(){
			@Override
			public boolean canRead(EvaluationContext context, Object target, String name) throws AccessException {
				System.out.println("enter....canRead" + target.getClass() + "   " + name);
				if(target instanceof MyMap) {
					return true;
				}
				return false;
			}
			@Override
			public TypedValue read(EvaluationContext context, Object target, String name) throws AccessException {
				System.out.println("enter......" + target.getClass() + "   " + name);
				if(target instanceof MyMap) {
					MyMap map = (MyMap) target;
					return new TypedValue(name);
				}
				throw new AccessException("Cannot access key on MyMap");
			}
		};
		context.addPropertyAccessor(accessor);

		TemplateParserContext template = new TemplateParserContext();
		String b = parser.parseExpression("from Product where 1=1 #{exists(name, 'and name=:name')} #{exists(age, 'and age=:age')}", template).getValue(context,evaluationContext, String.class);

		System.out.println(b);
	}

}
