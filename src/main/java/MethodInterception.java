import org.testng.annotations.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class MethodInterception implements MainPage {

    @Test
    public void annotationValue() throws NoSuchMethodException {
        MainPage mainPage = createPage(MainPage.class);
        createPage(Class.class);
        assertNotNull(mainPage);
        assertEquals(mainPage.buttonSearch(), ".//*[@test-attr='button_search']");
        assertEquals(mainPage.textInputSearch(), ".//*[@test-attr='input_search']");
    }

    private MainPage createPage(Class clazz) {
        return (MainPage) Handler.newInstance(new MethodInterception());
    }

    @Override
    public String textInputSearch() throws NoSuchMethodException {
        return String.valueOf(MainPage.class.getMethod("textInputSearch").getAnnotation(Selector.class).xpath());
    }

    @Override
    public String buttonSearch() throws NoSuchMethodException {
        return String.valueOf(MainPage.class.getMethod("buttonSearch").getAnnotation(Selector.class).xpath());
    }

    static class Handler implements InvocationHandler {
        private Object obj;

        public Handler(Object obj) {
            this.obj = obj;
        }

        public static Object newInstance(Object obj) {
            return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new Handler(obj));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(obj, args);
        }
    }
}