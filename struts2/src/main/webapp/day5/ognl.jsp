<%@page pageEncoding="utf-8" deferredSyntaxAllowedAsLiteral="true"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="m"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<jsp:include page="/common/header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/nav.jsp"></jsp:include>
	
	<div class="panel panel-default">
		<div class="panel-heading">OGNL</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#overview" data-toggle="tab">概览</a></li>
				<li><a href="#type-converter" data-toggle="tab">类型转换</a></li>
				<li><a href="#property-accessor" data-toggle="tab">属性解析</a></li>
				<li><a href="#syntax" data-toggle="tab">语法</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
<div class="tab-pane active" id="overview">
	<br/>
	<p>OGNL类似于EL表达式，是用来简化对象<kbd>属性</kbd> 访问的一种表达式语言。</p>
	<p>什么是<kbd>属性</kbd> ? 例如，对于如下代码：</p>
<pre class="prettyprint lang-java linenums">
public class Person {

    private String name;
    
    private Date birthday;
    
    private City city;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    
}
</pre>
	<p>这个类中有一对符合java bean规范的公有get, set方法，就可以称Person对象有属性<kbd>name</kbd> <kbd>birthday</kbd> <kbd>city</kbd> </p>
	<p>下面的代码是类似的，也可以称此类的对象有name属性：</p>
<pre class="prettyprint lang-java linenums">
public class Person {

    public String name;
    
}
</pre>
	<p>那么用Ognl如何访问对象属性呢？非常简单：</p>
<pre class="prettyprint lang-java linenums">
@Test
public void test() throws OgnlException {
    Person target = new Person();
    target.setName("张三");
    
    // a. 获取属性值，格式：Ognl.getValue("OGNL表达式", 目标对象);
    Assert.assertEquals("张三", Ognl.getValue("name", target));
    
    // b. 修改属性值，格式：Ognl.setValue("OGNL表达式", 目标对象, 新值);
    Ognl.setValue("name", target, "李四");
    Assert.assertEquals("李四", target.getName());
}
</pre>	
<p>再来个稍微复杂的例子：</p>
<pre class="prettyprint lang-java linenums">
@Test
public void test() throws OgnlException {
    Person target = new Person();
    target.setName("张三");        
    City city = new City();
    city.setName("北京");        
    target.setCity(city);
        
    // 相当于: target.getCity().getName();
    Assert.assertEquals("北京", Ognl.getValue("city.name", target));
        
    // 相当于: target.getCity().setName("南京");
    Ognl.setValue("city.name", target, "南京");
    Assert.assertEquals("南京", target.getCity().getName());
}
</pre>	
<p>这里还有个 <kbd>context</kbd> （上下文）的概念，<kbd>context</kbd>里保存了获取对象时需要的类型转换器，属性解析器等组件，此外 <kbd>context</kbd> 自己也可以用来存储或获取值：</p>
<pre class="prettyprint lang-java linenums">
@Test
public void test5() throws OgnlException {
    Map context = Ognl.createDefaultContext(null);        
    Person target = new Person();
    target.setName("张三");
    // context 对象本质上是个map，可以以键值对的方式存取任意值对象（键是String类型）
    context.put("everything", "ok");
    Assert.assertEquals("张三", Ognl.getValue("name", context, target));
    
    // 利用Ognl表达式通过键（注意前面的#来区分是从context还是target）获取context 中的值对象
    Assert.assertEquals("ok", Ognl.getValue("#everything", context, target));
    
    // 利用Ognl表达式修改 context 中的值对象
    Ognl.setValue("#everything", context, target, "not ok");
    Assert.assertEquals("not ok", context.get("everything"));
}
</pre>	
<p>当然，实际编程中不需要直接使用 <kbd>Ognl</kbd> 工具类，struts2 将访问属性的功能封装在了它的标签当中
</div>

<div class="tab-pane" id="type-converter">
	<br/>
	<p>经常遇到的需求是将请求参数值(字符串类型)转换成java里的不同类型，利用ognl能否实现呢？执行：</p>
<pre class="prettyprint lang-java linenums">
@Test
public void test() throws OgnlException {
    Person target = new Person();
    // 试图将"2001-5-1"字符串转换为java.util.Date类型赋值给birthday属性
    Ognl.setValue("birthday", target, "2001-5-1");
    System.out.println(target.getBirthday());
}
</pre>		
	<p>会发现抛出了ognl.OgnlException: birthday [java.lang.IllegalArgumentException: Unable to convert type java.lang.String of 2001-5-1 to type of java.util.Date]
	异常</p>
	
	<p>原来，要增加类型转换功能，需要使用上下文对象 <kbd>context</kbd>，它可以添加一个TypeConverter来完成转换：</p>
<pre class="prettyprint lang-java linenums">
@Test
public void test() throws OgnlException {
    Map context = Ognl.createDefaultContext(null, null, new TypeConverter() {            
        @Override
        public Object convertValue(Map context, Object target, Member member, String propertyName, Object value,
                Class toType) {
            // 如果是java.util.Date或它的子类
            if(java.util.Date.class.isAssignableFrom(toType) &amp;&amp; value instanceof String){
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.parse(value.toString());
                } catch (ParseException e) {
                    // 忽略处理转换失败
                    return null;
                }
            }
            return (value!=null) ? value.toString() : value;
        }
    });
    Person target = new Person();
    Ognl.setValue("birthday", context, target, "2001-5-1");
    System.out.println(target.getBirthday());
}
</pre>	
<p>当然，这个TypeConverter只判断了日期和它的子类的转换，struts2 已经提供了功能完善的类型转换功能，能够处理String->常见类型的转换</p>
</div>

<div class="tab-pane" id="property-accessor">
	<br/>
	<p>还有一个问题是，有时访问的对象结构比较复杂，如何访问这个对象的<kbd>属性</kbd> ？看两个例子：</p>
	<p>例1：向栈(root)中压入一个list类型的对象，它并没有相应的getSize方法，但如果使用<kbd>&lt;s:property value="size"/&gt;</kbd> 仍然能够获得List集合的大小</p>
	<p>例2：向栈(root)中先后压入了两个对象，栈顶对象没有<kbd>name</kbd> 属性，但栈底对象有<kbd>name</kbd> 属性，
		这时如果使用<kbd>&lt;s:property value="name"/&gt;</kbd> 仍然会输出<kbd>name</kbd> 属性的值</p>	
	<p>这是怎么实现的呢？原来struts2 为各种不同类型都注册了不同的<kbd>属性解析器</kbd>
	<p>所有的属性解析器都实现了PropertyAccessor接口，为了更好的理解，来看看他们的源码片段：</p>
	<pre class="prettyprint lang-java linenums">
public class ListPropertyAccessor extends ObjectPropertyAccessor implements PropertyAccessor {
    // target为当前list集合对象, name 即为属性名
    public Object getProperty(Map context, Object target, Object name) throws OgnlException {
        List list = (List) target;
        if (name instanceof String) {
            Object result = null;
            // 如果属性名等于 size, 那么调用list.size()方法返回集合大小
            if (name.equals("size")) {
                result = new Integer(list.size());
            } else {
                if (name.equals("iterator")) {
                    result = list.iterator();
                } else {
                    if (name.equals("isEmpty") || name.equals("empty")) {
                        result = list.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
                    } else {
                        result = super.getProperty(context, target, name);
                    }
                }
            }
            return result;
        }
        ...
    }
    ...
}
	</pre>
	<p>再来看看值栈的Root部分，它的实际类型为CompoundRoot:</p>	
	<pre class="prettyprint lang-java linenums">
public class CompoundRoot extends ArrayList {

    public CompoundRoot() {
    }

    public CompoundRoot(List list) {
        super(list);
    }


    public CompoundRoot cutStack(int index) {
        return new CompoundRoot(subList(index, size()));
    }

    public Object peek() {
        return get(0);
    }

    public Object pop() {
        return remove(0);
    }

    public void push(Object o) {
        add(0, o);
    }
}
</pre>
	<p>可以看到它就是一个对List的简单封装，只是实现了push(), pop(), peek()方法，它对应的属性解析器的源码片段：</p>	
	<pre class="prettyprint lang-java linenums">
public class CompoundRootAccessor implements PropertyAccessor, MethodAccessor, ClassResolver {

    public Object getProperty(Map context, Object target, Object name) throws OgnlException {
        CompoundRoot root = (CompoundRoot) target;
        OgnlContext ognlContext = (OgnlContext) context;

        if (name instanceof Integer) {
            Integer index = (Integer) name;
            return root.cutStack(index.intValue());
        } else if (name instanceof String) {
            // 如果属性名为top 返回栈顶对象
            if ("top".equals(name)) {
                if (root.size() > 0) {
                    return root.get(0);
                } else {
                    return null;
                }
            }
            // 遍历整个list
            for (Object o : root) {
                if (o == null) {
                    continue;
                }
                try {
                    // 检查当前对象是否有该属性，有则返回属性值，没有进入下一次循环
                    if ((OgnlRuntime.hasGetProperty(ognlContext, o, name)) || ((o instanceof Map) &amp;&amp; ((Map) o).containsKey(name))) {
                        return OgnlRuntime.getProperty(ognlContext, o, name);
                    }
                } catch (OgnlException e) {
                    if (e.getReason() != null) {
                        final String msg = "Caught an Ognl exception while getting property " + name;
                        throw new XWorkException(msg, e);
                    }
                } catch (IntrospectionException e) {
                    // this is OK if this happens, we'll just keep trying the next
                }
            }

            // 如果属性没找到，根据设置抛出异常或返回null（默认返回null）
            if (context.containsKey(OgnlValueStack.THROW_EXCEPTION_ON_FAILURE))
                throw new NoSuchPropertyException(target, name);
            else
                return null;
        } else {
            return null;
        }
    }
    ...
}
	</pre>
</div>
<div class="tab-pane" id="syntax">
	<br/>
	<h2>1. 常量</h2>
	<p>字符串字面量：用单引号或双引号界定，例如：</p>
		<table class="table">
			<thead>
				<tr><th width="300">表达式</th><th width="300">输出结果</th><th>结果类型</th></tr>
			</thead>
			<tbody>
				<tr><td>&lt;s:property value="'hello'"/&gt;</td><td><s:property value="'hello'"/></td><td><s:property value="'hello'.class"/></td></tr>
			</tbody>
		</table>
	<p>字符字面量：用单引号界定，例如：</p>
		<table class="table">
			<thead>
				<tr><th width="300">表达式</th><th width="300">输出结果</th><th>结果类型</th></tr>
			</thead>
			<tbody>
				<tr><td>&lt;s:property value="'M'"/&gt;</td><td><s:property value="'M'"/></td><td><s:property value="'M'.class"/></td></tr>
			</tbody>
		</table>
	<p>数字字面量：可以是int, long, float, double, BigInteger, BigDecimal, 例如：</p>
		<table class="table">
			<thead>
				<tr><th width="300">表达式</th><th width="300">输出结果</th><th>结果类型</th></tr>
			</thead>
			<tbody>
				<tr><td>&lt;s:property value="100"/&gt;</td><td><s:set var="i" value="100"/><s:property value="#i" /></td><td><s:property value="#i.class"/></td></tr>
				<tr><td>&lt;s:property value="100L"/&gt;</td><td><s:set var="i" value="100L"/><s:property value="#i" /></td><td><s:property value="#i.class"/></td></tr>
				<tr><td>&lt;s:property value="99.5F"/&gt;</td><td><s:set var="i" value="99.5F"/><s:property value="#i" /></td><td><s:property value="#i.class"/></td></tr>
				<tr><td>&lt;s:property value="99.5"/&gt;</td><td><s:set var="i" value="99.5"/><s:property value="#i" /></td><td><s:property value="#i.class"/></td></tr>
				<tr><td>&lt;s:property value="100H"/&gt;</td><td><s:set var="i" value="100H"/><s:property value="#i" /></td><td><s:property value="#i.class"/></td></tr>
				<tr><td>&lt;s:property value="99.5B"/&gt;</td><td><s:set var="i" value="99.5B"/><s:property value="#i" /></td><td><s:property value="#i.class"/></td></tr>
			</tbody>
		</table>
		
	<h2>2. 访问属性</h2>
	<p>Struts通过一组标签来封装了使用Ognl访问对象属性的功能，举例说明：</p>
<pre class="prettyprint lang-java linenums">
public class OgnlAction {

    private String name;

    private Person person;

    private List&lt;String&gt; list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List&lt;String&gt; getList() {
        return list;
    }

    public void setList(List&lt;String&gt; list) {
        this.list = list;
    }

    public String execute() {
        this.name = "张三";
        this.person = new Person();
        this.person.setName("李四");
        this.person.setAge(18);
        this.person.setCity(new City(1, "北京"));
        this.list = new ArrayList&lt;String&gt;();
        list.add("武大");
        list.add("华工");
        list.add("水院");
        return "success";
    }

}
</pre>
	<table class="table">
		<thead>
			<tr><th width="300">表达式</th><th width="300">输出结果</th><th>说明</th></tr>
		</thead>
		<tbody>
			<tr><td>&lt;s:property value="name"/&gt;</td><td><s:property value="name" /></td><td></td></tr>
			<tr><td>&lt;s:property value="person.name"/&gt;</td><td><s:property value="person.name" /></td><td></td></tr>
			<tr><td>&lt;s:property value="person.age"/&gt;</td><td><s:property value="person.age" /></td><td></td></tr>
			<tr><td>&lt;s:property value="person.city.id"/&gt;</td><td><s:property value="person.city.id" /></td><td></td></tr>
			<tr><td>&lt;s:property value="person.city.name"/&gt;</td><td><s:property value="person.city.name" /></td><td></td></tr>
		</tbody>
	</table>
	<div class="alert alert-info">
		<p><span class="glyphicon glyphicon-info-sign"></span> &nbsp; 提示：以上标签封装的等价代码：</p>
		<br/>
		<pre class="prettyprint lang-java linenums">
ValueStack vs = ActionContext.getContext().getValueStack();
Map&lt;String, Object&gt; context = vs.getContext();
CompoundRoot root = vs.getRoot();
try {
    System.out.println(Ognl.getValue("name", context, root));
    System.out.println(Ognl.getValue("person.name", context, root));
    System.out.println(Ognl.getValue("person.age", context, root));
    System.out.println(Ognl.getValue("person.city.id", context, root));
    System.out.println(Ognl.getValue("person.city.name", context, root));
} catch (OgnlException e) {
    e.printStackTrace();
}
</pre>
		<p>实际上，就是利用了Ognl在 <kbd>CompoundRoot</kbd> 对象上获取属性值，该Action对象只是被放入了 <kbd>CompoundRoot</kbd> 集合</p>
	</div>
	<p>Ognl还有一种称为 <kbd>index</kbd>（索引）的概念，它类似于属性，只是用途相对更为广泛</p>
	<p>它的语法为 <kbd>[index]</kbd> ，其中index可以是数字和字符串，例如：</p>
	<table class="table">
		<thead>
			<tr><th width="300">表达式</th><th width="300">输出结果</th><th>说明</th></tr>
		</thead>
		<tbody>
			<tr><td>&lt;s:property value="['name']"/&gt;</td><td><s:property value="['name']" /></td><td></td></tr>
			<tr><td>&lt;s:property value="person['name']"/&gt;</td><td><s:property value="person['name']" /></td><td>可以与属性混用</td></tr>
			<tr><td>&lt;s:property value="['person']['name']"/&gt;</td><td><s:property value="['person']['name']" /></td><td>也可以全部用索引</td></tr>
			<tr><td>&lt;s:property value="['person']['city']['name']"/&gt;</td><td><s:property value="['person']['city']['name']" /></td><td>等价于person.city.name</td></tr>
			<tr><td>&lt;s:property value="list[0]"/&gt;</td><td><s:property value="list[0]" /></td><td>可以是数字，表示数组或List的下标</td></tr>
		</tbody>
	</table>
	
	<h2>3. 调用方法</h2>
	<p>Ognl相比EL更强的地方在于，它能够表达式中调用方法，例如：</p>
	<table class="table">
		<thead>
			<tr><th width="300">表达式</th><th width="300">输出结果</th><th>说明</th></tr>
		</thead>
		<tbody>
			<tr><td>&lt;s:property value="person.name"/&gt;</td><td><s:property value="person.name" /></td><td>用name属性来显示值</td></tr>
			<tr><td>&lt;s:property value="person.getName()"/&gt;</td><td><s:property value="person.getName()" /></td><td>还可以用getName()方法来达到相同目的</td></tr>
		</tbody>
	</table>
	<div class="alert alert-info">
		<p><span class="glyphicon glyphicon-info-sign"></span> &nbsp; 提示：注意这里说的方法是指对象方法而非静态方法，Ognl虽然能够调用静态方法和构造方法，但是这往往是造成黑客攻击的手段，超出了这里要讨论的范围</p>
	</div>
	
	<h2>4. 变量</h2>
	<table class="table">
		<thead>
			<tr><th width="300">表达式</th><th width="300">输出结果</th><th>说明</th></tr>
		</thead>
		<tbody>
			<tr><td>&lt;s:property value="#age=18"/&gt;</td><td><s:property value="#age=18"/></td><td>将18赋值给age变量，变量age实际对应context中的key</td></tr>
			<tr><td>&lt;s:property value="#age=18,null"/&gt;</td><td><s:property value="#age=18,null"/></td><td>上面的标签会直接将18输出，如果不希望输出，可以加一个null，并用 <kbd>,</kbd> 分隔，这样的目的是表达式被 <kbd>,</kbd> 分成了两个，这两个表达式都会运行，只是最后一个表达式的运行结果会被&lt;s:property&gt;标签输出，因为最后表达式是null，所以不显示任何内容</td></tr>
			<tr><td>&lt;s:set var="age" value="18"/&gt;</td><td><s:set var="age" value="18"/></td><td>等价做法是使用&lt;s:set&gt;标签来给变量赋值</td></tr>
			<tr><td>&lt;s:property value="#age - 8"/&gt;</td><td><s:property value="#age - 8"/></td><td>定义好的变量可以在同一请求的任意地方被引用</td></tr>
			<tr><td>&lt;s:property value="#session.test='ok'"/&gt;</td><td><s:property value="#session.test='ok'"/></td><td>也可以将变量存储于其它作用域</td></tr>
		</tbody>
	</table>
	
	<s:debug></s:debug>
	
	<h2>5. 构造集合</h2>
	<table class="table">
		<thead>
			<tr><th width="400">表达式</th><th width="300">输出结果</th><th>说明</th></tr>
		</thead>
		<tbody>
			<tr><td>&lt;s:property value="{'清华','北大','复旦'}"/&gt;</td><td><s:property value="{'清华','北大','复旦'}"/></td><td>创建List集合</td></tr>
			<tr><td>&lt;s:property value="new java.lang.String[]{'浙大','川大'}"/&gt;</td><td><s:property value="new java.lang.String[]{'浙大','川大'}"/></td><td>创建String数组（注意包名不能省略）</td></tr>
			<tr><td>&lt;s:property value="name in {'张三', '李四', '王五'}"/&gt;</td><td><s:property value="name in {'张三', '李四', '王五'}"/></td><td>可以用 <kbd>in</kbd> 来检查是否存在于List集合</td></tr>
			<tr><td>&lt;s:property value="&#35;{'beijing':'北京','shanghai':'上海'}"/&gt;</td><td><s:property value="#{'beijing':'北京','shanghai':'上海'}"/></td><td>创建Map集合</td></tr>
		</tbody>
	</table>
</div>
			</div>			
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
