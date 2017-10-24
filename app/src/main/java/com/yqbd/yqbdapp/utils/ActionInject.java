package com.yqbd.yqbdapp.utils;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yqbd.yqbdapp.actions.IBaseAction;
import com.yqbd.yqbdapp.annotation.Action;
import com.yqbd.yqbdapp.annotation.ActionService;
import com.yqbd.yqbdapp.annotation.ExecutionMode;
import com.yqbd.yqbdapp.annotation.ExecutionThreadMode;
import com.yqbd.yqbdapp.callback.IActionCallBack;
import com.yqbd.yqbdapp.utils.BaseJson;
import dalvik.system.DexFile;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class ActionInject {
    private static Map<String, Class> actionNameMap = Maps.newHashMap();
    private static Map<Class, Class> actionClassMap = Maps.newHashMap();
    private static String actionPackage = "com.yqbd.yqbdapp.actions";

    public static void init(Application application) {
        List<Class<?>> classSet = scan(application, actionPackage);
        for (Class<?> c : classSet) {
            if (c.isAnnotationPresent(ActionService.class)) {
                ActionService actionService = c.getAnnotation(ActionService.class);
                for (Class interfaceC : c.getInterfaces()) {
                    actionClassMap.put(interfaceC, c);
                }
                if (!actionService.value().isEmpty()) {
                    actionNameMap.put(actionService.value(), c);
                }
            }
        }
        Log.i("Action Inject Completed", "" + classSet.size() + "");
    }

    protected static List<Class<?>> scan(Application application, String entityPackage) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        try {
            ClassLoader classLoader = application.getClassLoader();
            String s = application.getPackageCodePath();
            DexFile dex = new DexFile(s);
            Enumeration<String> entries = dex.entries();
            while (entries.hasMoreElements()) {
                String entryName = entries.nextElement();
                if (entryName.contains(entityPackage)) {
                    Class<?> entryClass = Class.forName(entryName, true, classLoader);
                    classes.add(entryClass);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static <T extends IBaseAction> T bindActionCallBack(Object target, Class<T> cls, IActionCallBack actionCallBack) {
        try {
            //Class<T> tClass= T.class;
            Class clazz = getActionService(cls);
            ProxyHandler proxyHandler = getProxyHandler(target, clazz, actionCallBack);
            return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), proxyHandler);
        } catch (Exception e) {
            return null;
        }
    }

    public static void bindActionCallBack(Object target, IActionCallBack actionCallBack) {
        List<Field> fields = Lists.newArrayList(target.getClass().getDeclaredFields());
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Action.class)) {
                continue;
            }
            if (!IBaseAction.class.isAssignableFrom(field.getType())) {
                Log.e(target.getClass().getSimpleName(), "Wrong Action Class:" + field.getName());
                continue;
            }
            try {
                Class clazz = getActionService(field);
                ProxyHandler proxyHandler = getProxyHandler(target, clazz, actionCallBack);
                field.setAccessible(true);
                field.set(target, Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), proxyHandler));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void bindActionCallBack(Activity activity) {
        bindActionCallBack(activity, null);
    }

    public static void bindActionCallBack(Fragment fragment) {
        bindActionCallBack(fragment, null);
    }

    private static ProxyHandler getProxyHandler(Object o, Class clazz, IActionCallBack actionCallBack) throws IllegalAccessException, InstantiationException {
        Activity activity;
        if (o instanceof Fragment) {
            activity = ((Fragment) o).getActivity();
        } else {
            activity = (Activity) o;
        }
        ProxyHandler proxyHandler;
        if (actionCallBack != null) {
            proxyHandler = new ProxyHandler(activity, (IBaseAction) clazz.newInstance(), actionCallBack);
        } else if (o instanceof IActionCallBack) {
            proxyHandler = new ProxyHandler(activity, (IBaseAction) clazz.newInstance(), (IActionCallBack) o);
        } else {
            proxyHandler = new ProxyHandler(activity, (IBaseAction) clazz.newInstance());
        }
        return proxyHandler;
    }

    static class ProxyHandler implements InvocationHandler {
        private Activity activity;
        private IBaseAction baseAction;
        private IActionCallBack actionCallBack;

        public ProxyHandler(Activity activity, IBaseAction baseAction) {
            this.activity = activity;
            this.baseAction = baseAction;
        }

        public ProxyHandler(Activity activity, IBaseAction baseAction, IActionCallBack actionCallBack) {
            this.activity = activity;
            this.baseAction = baseAction;
            this.actionCallBack = actionCallBack;
        }

        @Override
        public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
            String methodName = method.getName();

            Log.d(activity.getClass().getSimpleName(), "invoke method:" + methodName);

            ExecutionMode executionMode = method.getAnnotation(ExecutionMode.class);
            if (executionMode != null && executionMode.value() == ExecutionThreadMode.SYNC) {
                Object result = method.invoke(baseAction, args);
                return result;
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final BaseJson baseJson = (BaseJson) method.invoke(baseAction, args);
                            if (actionCallBack != null) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (baseJson == null || baseJson.getReturnCode().contains("E")) {
                                            actionCallBack.onFailed(baseJson);
                                        } else {
                                            actionCallBack.OnSuccess(baseJson);
                                        }
                                    }
                                });
                            }
                        } catch (Exception e) {
                            Log.w(activity.getClass().getSimpleName() + " - " + baseAction.getClass().getSimpleName(), e);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity, "连接错误", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
                return null;
            }
        }

        @Override
        public String toString() {
            return "ProxyHandler{" +
                    "activity=" + activity +
                    ", baseAction=" + baseAction +
                    ", actionCallBack=" + actionCallBack +
                    '}';
        }
    }

    public static Class getActionService(Field field) {
        Class c = actionNameMap.get(field.getName());
        if (c == null) {
            c = actionClassMap.get(field.getType());
        }
        return c;
    }

    public static Class getActionService(Class cls) {
        Class c = actionClassMap.get(cls);
        return c;
    }

    @Deprecated
    public static Class getActionService(IBaseAction baseAction) {
        Class c = actionClassMap.get(baseAction.getClass());
        return c;
    }
}
