package com.movilizer.masterdata.excel;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface Operation2<TFirst, TSecond> {
    public void apply(TFirst first, TSecond second);
}
