package zpark.ext.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import zpark.ext.annotations.common.Scannable;

public class Container implements ClassfileVisitor {

    private static Map<Class<?>, Object> managedClassMap = new HashMap<Class<?>, Object>();
    
    private static TreeSet<FutureTask> taskSet = new TreeSet<FutureTask>();

    public static Map<Class<?>, Object> getManagedClassMap() {
        return managedClassMap;
    }

    @Override
    public void visit(String className) throws IOException {
        ClassReader cr = new ClassReader(className);
        cr.accept(new ClassVisitor(Opcodes.ASM4) {
            private String currentClassName;
            private Class<?> currentClass;
            private boolean isScannable = false;
            private String SCANNABLE_ANNOTATION = "Lzpark/ext/annotations/common/Scannable;";

            @Override
            public void visit(int version, int access, String name, String signature, String superName,
                    String[] interfaces) {
                currentClassName = name;
            }

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                if (SCANNABLE_ANNOTATION.equals(desc)) {
                    isScannable = true;
                    try {
                        currentClass = Class.forName(currentClassName.replace('/', '.'));
                        if (!currentClass.isInterface()) {
                            Class<?>[] interfaces = currentClass.getInterfaces();
                            for (Class<?> i : interfaces) {
                                if (i.isAnnotationPresent(Scannable.class)) {
                                    managedClassMap.put(i, currentClass);
                                }
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return super.visitAnnotation(desc, visible);
            }
            
            @Override
            public FieldVisitor visitField(int access, final String name, String desc, String signature, Object value) {
                if(isScannable) {
                    return new FieldVisitor(Opcodes.ASM4) {
                        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                            System.out.println(currentClass+ "==>" + name + "==>" + desc);
                            try {
                                Field f = currentClass.getDeclaredField(name);
                                if(!f.isAccessible()) {
                                    f.setAccessible(true);
                                }
                                f.set(obj, value);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return super.visitAnnotation(desc, visible);                
                        };
                    };
                }
                return super.visitField(access, name, desc, signature, value);
            }
        }, 0);
    }

}
