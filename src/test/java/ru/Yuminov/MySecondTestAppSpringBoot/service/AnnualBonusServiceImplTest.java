package ru.yuminov.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.yuminov.MySecondTestAppSpringBoot.model.Positions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AnnualBonusServiceImplTest {

    @Test
    void calculate() {
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        double result = new AnnualBonusServiceImpl().calculate(position, salary, bonus, workDays);

        double expected = 361481.48148148146;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyBonus_forManager() {
        Positions position = Positions.MANAGER;
        double salary = 100000.00;

        double result = new AnnualBonusServiceImpl().calculateQuarterlyBonus(position, salary);

        double expected = salary * 0.25 * position.getPositionCoefficient();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyBonus_forNonManager() {
        Positions position = Positions.DEV;
        double salary = 100000.00;

        AnnualBonusServiceImpl service = new AnnualBonusServiceImpl();

        assertThrows(IllegalArgumentException.class, () -> service.calculateQuarterlyBonus(position, salary));
    }
}