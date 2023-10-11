package gdsc_4th_hw.week4;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class A {
    private B b;
    private C c;

    @Autowired
    public A(B b, C c) {
        this.b = b;
        this.c = c;
    }
    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }
}
