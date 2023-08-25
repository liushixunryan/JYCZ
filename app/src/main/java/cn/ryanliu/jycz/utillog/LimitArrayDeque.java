package cn.ryanliu.jycz.utillog;

import java.util.ArrayDeque;

public class LimitArrayDeque<E> extends ArrayDeque<E> {
    int numMaxElements;
    public LimitArrayDeque(int numElements) {
        super(numElements);
        numMaxElements = numElements;
    }

    @Override
    public boolean add(E o) {
        if(this.size()>=numMaxElements){
            this.poll();
        }
        return super.add(o);
    }
}
