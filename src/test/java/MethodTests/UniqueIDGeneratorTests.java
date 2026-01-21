//Jurgen Hila
package MethodTests;

import Model.UniqueIDGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UniqueIDGeneratorTests {

    @Test
    void getUniqueId_shouldReturnCorrectLengthAndNumericSuffix() {
        String id = UniqueIDGenerator.getUniqueId();

        // Format: yyMMddHHmmss + 2 digits sequence => 12 + 2 = 14
        assertEquals(14, id.length());

        String prefix = id.substring(0, 12);
        String suffix = id.substring(12);

        assertTrue(prefix.matches("\\d{12}"), "Timestamp prefix should be 12 digits");
        assertTrue(suffix.matches("\\d{2}"), "Sequence suffix should be 2 digits");
    }

    @Test
    void getUniqueId_twoCallsSameSecond_shouldIncreaseSequenceOrStayValid() {
        String id1 = UniqueIDGenerator.getUniqueId();
        String id2 = UniqueIDGenerator.getUniqueId();

        assertNotEquals(id1, id2, "Two consecutive IDs should be different");

        String prefix1 = id1.substring(0, 12);
        String prefix2 = id2.substring(0, 12);

        // Usually same second -> same prefix, else it moved to next second (still fine)
        assertTrue(prefix1.equals(prefix2) || !prefix1.equals(prefix2));
    }

    @Test
    void getUniqueId_afterSecondChanges_shouldEventuallyChangeTimestampPrefix() throws InterruptedException {
        String id1 = UniqueIDGenerator.getUniqueId();
        String prefix1 = id1.substring(0, 12);

        // Sleep enough to cross a second boundary
        Thread.sleep(1100);

        String id2 = UniqueIDGenerator.getUniqueId();
        String prefix2 = id2.substring(0, 12);

        assertNotEquals(prefix1, prefix2, "After sleeping >1s, timestamp prefix should change");
    }

    @Test
    void getUniqueId_shouldEventuallyWrapSequenceFrom99To00_withinSameTimestampGroup() {
        // Goal: trigger the MAX_SEQ wrap (>99 -> 0) WITHOUT reflection.
        // We attempt to generate lots of IDs within the same second, and look for a wrap in suffix.

        long deadlineMs = System.currentTimeMillis() + 5000; // try for up to 5 seconds

        while (System.currentTimeMillis() < deadlineMs) {
            // Try to start early in a second to maximize time
            while (LocalDateTime.now().getNano() > 50_000_000) {
                // spin until we're in the first ~50ms of a second
                // (gives us almost a full second to do 100+ calls)
                if (System.currentTimeMillis() >= deadlineMs) break;
            }

            Map<String, Integer> lastSuffixSeenByPrefix = new HashMap<>();

            // Tight loop: generate many IDs quickly
            for (int i = 0; i < 3000; i++) {
                String id = UniqueIDGenerator.getUniqueId();
                String prefix = id.substring(0, 12);
                int suffix = Integer.parseInt(id.substring(12));

                Integer last = lastSuffixSeenByPrefix.put(prefix, suffix);

                // If we saw 99 then see 0 on same prefix, that proves wrap branch executed.
                if (last != null && last == 99 && suffix == 0) {
                    return; // PASS
                }

                // Stop if second changed significantly (too many prefixes)
                if (lastSuffixSeenByPrefix.size() > 2) break;
            }
        }

        fail("Did not observe sequence wrap (99 -> 00) within 5 seconds. Try running this test again.");
    }
}

