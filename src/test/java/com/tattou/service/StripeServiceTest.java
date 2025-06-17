package com.tattou.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import com.tattou.repository.DisenyoRepository;

@ExtendWith(MockitoExtension.class)
public class StripeServiceTest {

    @InjectMocks
    private StripeService stripeService;

    @Mock
    private DisenyoRepository disenyoRepository;

    @Test
    public void createCheckoutSessionTest() {
        Assert.notNull(stripeService, "error");
    }

}
