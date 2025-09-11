package com.icnet.capstonehub.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class LocalDateTest {

    @Test
    public void localdateCalc() {
        LocalDate date = LocalDate.of(2022, 12, 31);

        assertEquals(LocalDate.of(2023, 1, 2), date.plusDays(2));
    }
}

