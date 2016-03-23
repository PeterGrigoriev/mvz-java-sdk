package com.movilizer.push;

import com.movilizer.module.MovilizerModule;
import com.movilizer.templaterepository.TestTemplateRepository;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerPushCallModule extends MovilizerModule {
    @Override
    protected void setUp() {
        bindTemplateRepository(TestTemplateRepository.class);
    }
}
