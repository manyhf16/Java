package zpark.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.transaction.annotation.Transactional;

import zpark.dao.SampleDao;
import zpark.dao.SampleQueryBuilder;
import zpark.dao.User2Dao;
import zpark.entity.Category;
import zpark.entity.Product;
import zpark.entity.SampleEntity;
import zpark.ext.query.Page;

@Transactional
public class TestDao extends TestBasic {

    @Autowired
    private SampleDao dao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void test0() {
        initDatabase();
    }

    @Test
    public void test1() {
        SampleEntity e = new SampleEntity();
        e.setName("赵敏");
        dao.save(e);
        Long count = dao.findOne(Long.class, "select count(*) from SampleEntity where name like ? and name=?", "赵%",
                "赵敏");
        Assert.assertEquals(Long.valueOf(1), count);
    }

    @Test
    public void test2() {
        SampleEntity e = new SampleEntity();
        e.setName("赵敏");
        dao.save(e);
        Map<String, Object> params = new HashMap<>();
        params.put("n1", "赵%");
        params.put("n2", "赵敏");
        Long count = dao.findOne(Long.class, "select count(*) from SampleEntity where name like :n1 and name=:n2",
                params);
        Assert.assertEquals(Long.valueOf(1), count);
    }

    @Test
    public void test3() {
        SampleEntity e = dao.findOne(1);
        Assert.assertEquals("赵一伤", e.getName());
    }

    @Test
    public void test4() {
        SampleEntity e = dao.findOne(2);
        e.setName("北京");
        dao.update(e);
        SampleEntity se = dao.findOne("from SampleEntity where id = ?", 2);
        Assert.assertEquals(e, se);
    }

    @Test
    public void test5() {
        Page<SampleEntity> page = dao.sample();
        Assert.assertEquals(8, page.getList().size());
    }

    @Test
    public void test6() {
        dao.delete(1);
        Long count = dao.findOne(Long.class, "select count(*) from SampleEntity");
        Assert.assertEquals(Long.valueOf(7), count);
    }

    @Test
    public void test7() {
        Page<SampleEntity> page = dao.find1(2, 5, 0, "%");
        Assert.assertEquals(3, page.getList().size());
        Assert.assertEquals(8, page.getTotal());
    }

    @Test
    public void test8() {
        Page<SampleEntity> page = dao.find2(1, 5, 0, "%", "id", "desc", "name", "asc");
        Assert.assertEquals(5, page.getList().size());
        Assert.assertEquals(8, page.getTotal());
        Assert.assertEquals("王八衰", page.getList().get(0).getName());
    }

    @Test
    public void test9() {
        Page<SampleEntity> page = dao.find3(1, 5, "id", "desc", new SampleQueryBuilder(null, "%"));
        Assert.assertEquals(5, page.getList().size());
        Assert.assertEquals(8, page.getTotal());
        Assert.assertEquals("王八衰", page.getList().get(0).getName());
    }

    @Test
    public void test10() {
        Page<SampleEntity> page = dao.find4(1, 10, Arrays.asList(1, 2, 3));
        System.out.println(page.getList());
    }

    @SuppressWarnings("serial")
    @Test
    public void test11() {
        List<SampleEntity> list = dao.findList(1, 10, "from SampleEntity where id in :ids and name like :name",
                new HashMap<String, Object>() {
                    {
                        put("ids", Arrays.asList(1, 2, 3));
                        put("name", "赵%");
                    }
                });
        System.out.println(list);
    }

    @Test
    public void test12() throws NoSuchMethodException, SecurityException {
        ParameterNameDiscoverer pmd = new LocalVariableTableParameterNameDiscoverer();
        Method m = User2Dao.class.getMethod("find2", String.class);
        System.out.println(m);
        MethodParameter mp1 = new MethodParameter(m, 0);
        mp1.initParameterNameDiscovery(pmd);
        System.out.println(mp1.getParameterName());
        
        String[] s = getMethodParamNames(m);
        System.out.println(Arrays.toString(s));
    }

    @Test
    public void test13() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Category.class);
//         c.setFetchMode("products", FetchMode.SELECT);

        c.setProjection(Projections.rowCount());
        System.out.println(c.uniqueResult());

        c.setProjection(null);
        c.setFirstResult(0);
        c.setMaxResults(3);
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        @SuppressWarnings("unchecked")
        List<Category> list = c.list();
        for (Category ca : list) {
            System.out.println(ca);
            System.out.println(ca.getProducts());
        }
    }

    @Test
    public void test14() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Category.class);
        String propertyName = "id";
        c.setProjection(Projections.count(propertyName));
        System.out.println(c.uniqueResult());

        c.addOrder(Order.asc(propertyName));
        c.setProjection(Projections.property(propertyName));
        c.setFirstResult(0);
        c.setMaxResults(3);
        List<Integer> ids = c.list();

        c.setFetchMode("products", FetchMode.JOIN);
        c.setProjection(null);
        c.setFirstResult(0);
        c.setMaxResults(Integer.MAX_VALUE);
        Criterion cr = null;
        int inLimit = 1000;
        if (ids.size() <= inLimit) {
            cr = Restrictions.in(propertyName, ids);
        } else {
            int fromIndex = 0;
            int toIndex = fromIndex + inLimit;
            cr = Restrictions.in(propertyName, ids.subList(fromIndex, inLimit));
            int size = ids.size();
            while (toIndex < size) {
                fromIndex = toIndex;
                toIndex = fromIndex + inLimit;
                if (toIndex > size) {
                    toIndex = size;
                }
                cr = Restrictions.or(cr, Restrictions.in(propertyName, ids.subList(fromIndex, toIndex)));
            }
        }
        c.add(cr);
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        @SuppressWarnings("unchecked")
        List<Category> list = c.list();
        for (Category ca : list) {
            System.out.println(ca);
            System.out.println(ca.getProducts());
        }
    }

    @Test
    public void test15() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from Category c left join fetch c.products order by c.id");
        // 加了分页条件后，没有使用物理分页! 并且不用排除重复
        q.setFirstResult(0);
        q.setMaxResults(3);
        List<Category> list = q.list();
        for (Category ca : list) {
            System.out.println(ca);
            System.out.println(ca.getProducts());
        }
    }
    
    @Test
    public void test115() {
        Session session = sessionFactory.getCurrentSession();
        Criteria q = session.createCriteria(Product.class);
        Product p = new Product();
        p.setName("iP");
        Category c = new Category();
        c.setId(1);
        q.add(Example.create(p).enableLike(MatchMode.ANYWHERE)).createCriteria("category").add(Restrictions.eq("id", 1));
        q.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        List<Product> list = q.list();
        for(Product pp : list) {
            System.out.println(pp.getId() + " " + pp.getName() + " " + pp.getPrice());
        }
    }

    @Test
    public void test16() {
        Session session = sessionFactory.getCurrentSession();
        Criteria q = session.createCriteria(Category.class);
        q.setFetchMode("products", FetchMode.JOIN);
        q.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        q.setFirstResult(0);
        q.setMaxResults(3);
        List<Category> list = q.list();
        for (Category ca : list) {
            System.out.println(ca);
            System.out.println(ca.getProducts());
        }
    }

    @Test
    public void test17() {
        List<Integer> ids = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int fromIndex = 0;
        int inLimit = 3;
        int toIndex = fromIndex + inLimit;
        System.out.println(ids.subList(fromIndex, toIndex));
        int size = ids.size();
        while (toIndex < size) {
            fromIndex = toIndex;
            toIndex = fromIndex + inLimit;
            System.out.println(fromIndex + " " + toIndex);
            if (toIndex > size) {
                toIndex = size;
            }
            System.out.println(ids.subList(fromIndex, toIndex));
        }
    }

    /**
     * 
     * <p>
     * 比较参数类型是否一致
     * </p>
     * 
     * @param types
     *            asm的类型({@link Type})
     * @param clazzes
     *            java 类型({@link Class})
     * @return
     */
    private static boolean sameType(Type[] types, Class<?>[] clazzes) {
        // 个数不同
        if (types.length != clazzes.length) {
            return false;
        }

        for (int i = 0; i < types.length; i++) {
            if (!Type.getType(clazzes[i]).equals(types[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * <p>
     * 获取方法的参数名
     * </p>
     * 
     * @param m
     * @return
     */
    public static String[] getMethodParamNames(final Method m) {
        final String[] paramNames = new String[m.getParameterTypes().length];
        final String n = m.getDeclaringClass().getName();
        ClassReader cr = null;
        try {
            cr = new ClassReader(n);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cr.accept(new ClassVisitor(Opcodes.ASM4) {
            @Override
            public MethodVisitor visitMethod(final int access, final String name, final String desc,
                    final String signature, final String[] exceptions) {
                final Type[] args = Type.getArgumentTypes(desc);
                // 方法名相同并且参数个数相同
                if (!name.equals(m.getName()) || !sameType(args, m.getParameterTypes())) {
                    return super.visitMethod(access, name, desc, signature, exceptions);
                }
                MethodVisitor v = super.visitMethod(access, name, desc, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM4, v) {
                    @Override
                    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end,
                            int index) {
                        int i = index - 1;
                        // 如果是静态方法，则第一就是参数
                        // 如果不是静态方法，则第一个是"this"，然后才是方法的参数
                        if (Modifier.isStatic(m.getModifiers())) {
                            i = index;
                        }
                        if (i >= 0 && i < paramNames.length) {
                            paramNames[i] = name;
                        }
                        super.visitLocalVariable(name, desc, signature, start, end, index);
                    }

                };
            }
        }, 0);
        return paramNames;
    }

}
