package nmw.util;

import java.lang.reflect.Method;

public final class ReflectionUtil {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T callPrivateMethod(Object targetObject, String methodName,
             Class[] argClasses, Object[] argData) {
        
        T returnValue = null;
        
        try {
            
            Method methodToCall = targetObject.getClass().getDeclaredMethod(
                    methodName, argClasses);
            
            methodToCall.setAccessible(true);
            
            returnValue = (T) methodToCall.invoke(targetObject, argData);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return returnValue;
    }
}
