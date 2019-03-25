package j3_lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class MethodForTest{
    private Class clazz;
    private Method method;
    private int priority;

    public MethodForTest(Class clazz, Method method, int priority) {
        this.clazz = clazz;
        this.method = method;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return
                "MethodForTest{" +
                "clazz=" + clazz +
                ", method=" + method.getName() +
                ", priority=" + priority +
                '}';
    }

    public void execute(){
        try {
            method.invoke(clazz.newInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
