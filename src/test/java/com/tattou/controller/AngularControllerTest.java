package com.tattou.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class AngularControllerTest {

    @InjectMocks
    private AngularController angularController;
    
    @Test
    public void forwardAngularRoutesTest() {
        Assert.notNull(angularController.forwardAngularRoutes(), "error");
    }

}
