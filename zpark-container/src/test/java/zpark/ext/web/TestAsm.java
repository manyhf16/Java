package zpark.ext.web;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class TestAsm {

	public static class MyVisitor extends ClassVisitor {

		public MyVisitor() {
			super(4);
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			System.out.println("----------------------------");
			System.out.printf("access: %d name: %s desc: %s signature: %s exceptions:%s%n", access, name, desc, signature,
					Arrays.toString(exceptions));
			return new MyMethodVisitor();
		}

	}
	
	public static class MyMethodVisitor extends MethodVisitor {

		public MyMethodVisitor() {
			super(4);
		}
		
		@Override
		public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
			System.out.printf("name: %s desc: %s signature: %s%n", name, desc, signature);
		}
		
		@Override
		public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
			System.out.printf("name: %d desc: %s visible: %b%n", parameter, desc, visible);
			return new MyAnnotationVisitor();
		}		
	}
	
	public static class MyAnnotationVisitor extends AnnotationVisitor {

		public MyAnnotationVisitor() {
			super(4);
		}
		@Override
		public void visit(String name, Object value) {
			System.out.printf("name: %s, value: %s%n", name, value.toString());
		}
	}

	@Test
	public void test01() throws IOException {
		ClassReader reader = new ClassReader(UserAction.class.getResourceAsStream("UserAction.class"));
		reader.accept(new MyVisitor(), 0);
	}

}
