package com.movilizer;

import com.movilizer.connector.IMovilizerRequestSender;
import com.movilizer.connector.mock.MockMovilizerRequestSender;
import com.movilizer.module.MovilizerModule;
import com.movilizer.templaterepository.TestTemplateRepository;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class TestModule extends MovilizerModule {

    private IMovilizerRequestSender mockRequestSender;

    public TestModule(MockMovilizerRequestSender mockRequestSender) {
        this.mockRequestSender = mockRequestSender;
    }

    @Override
    protected void setUp() {
        bind(IMovilizerRequestSender.class).toInstance(mockRequestSender);
        bindTemplateRepository(TestTemplateRepository.class);
    }
}
