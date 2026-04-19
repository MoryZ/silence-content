package com.old.silence.bp.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * @author EX-ZHANGMENGWEI001
 */
public final class TournamentTimeCalculationUtils {

    private TournamentTimeCalculationUtils() {
        throw new AssertionError();
    }

    public static boolean isLastCycle(Instant currentTime, Instant tournamentEndTime) {
        ZoneId zone = ZoneId.systemDefault();
        LocalDate endDate = tournamentEndTime.atZone(zone).toLocalDate();
        LocalDate currentDate = currentTime.atZone(zone).toLocalDate();
        return currentDate.isAfter(endDate);
    }
}
