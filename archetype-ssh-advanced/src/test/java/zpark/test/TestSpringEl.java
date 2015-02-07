package zpark.test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
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
		Pattern p = Pattern.compile("#\\{(.+?)\\}");
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer(128);
		while(m.find()) {
			String g1 = m.group(1);
			m.appendReplacement(sb, g1);
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
