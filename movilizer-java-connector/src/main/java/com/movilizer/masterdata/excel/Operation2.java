package com.movilizer.masterdata.excel;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface Operation2<TFirst, TSecond> {
    public void apply(TFirst first, TSecond second);

    public static Operation2 VOID = new Operation2() {
        @Override
        public void apply(Object o, Object o2) {
        }
    };
}
