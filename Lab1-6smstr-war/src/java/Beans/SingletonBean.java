/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Анюта
 */
@Singleton
@LocalBean
public class SingletonBean implements Serializable {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public SingletonBean() {
        count = 0;
    }
    @AroundInvoke
    public Object modifyGreeting(InvocationContext ctx) throws Exception {
        count++;
        return ctx.proceed();
    }
}
