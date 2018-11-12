package com.simple.codemake.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
/**
 * https://www.cnblogs.com/benpaodexiaojue/p/6972509.html
 * @author zhengfy1
 *
 */
public class DynamicLoaderTestCase {

	private static String javaSrc = "public class TestClass{" +
			"public void sayHello(String msg) {" +
			"System.out.printf(\"Hello %s! This message from a Java String.%n\",msg);" +
			"}" +
			"public int add(int a,int b){" +
			"return a+b;" +
			"}" +
			"}";
	
	public static  void testCompile() {
		Map<String, byte[]> bytecode = DynamicLoader.compile("TestClass.java", javaSrc);
		for (Iterator<String> iterator = bytecode.keySet().iterator(); iterator.hasNext(); ) {
		String key = iterator.next();
		byte[] code = bytecode.get(key);
		System.out.printf("Class: %s, Length: %d%n", key, code.length);
		}
	}
	
	public static void testInvoke() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		Random random = new Random();
		int a = random.nextInt(1024);
		int b = random.nextInt(1024);
		Map<String, byte[]> bytecode = DynamicLoader.compile("TestClass.java", javaSrc);
		DynamicLoader.MemoryClassLoader classLoader = new DynamicLoader.MemoryClassLoader(bytecode);
		Class clazz = classLoader.loadClass("TestClass");
		Object object = clazz.newInstance();
		Method method = clazz.getMethod("add", int.class, int.class);
		Object returnValue = method.invoke(object, a, b);
		//System.out.println(method.);
		//Assert.assertEquals(a + b, returnValue);
		System.out.println(returnValue);
		}
	
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		testCompile();
		testInvoke();
	}
	
}
