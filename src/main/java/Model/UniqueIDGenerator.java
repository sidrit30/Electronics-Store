package Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

//from https://stackoverflow.com/questions/67195428/unique-id-based-on-current-date-format-yymmddhhmmss-in-java
public class UniqueIDGenerator {
    private static final int MIN_SEQ = 0;
    private static final int MAX_SEQ = 99;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    private static LocalDateTime lastTime = LocalDateTime.now(ZoneId.systemDefault());
    private static int lastSeq = 0; // Initialize to any value

    public static String getUniqueId() {
        LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault())
                .truncatedTo(ChronoUnit.SECONDS);
        if (time.isAfter(lastTime)) {
            lastTime = time;
            lastSeq = MIN_SEQ;
        } else {
            lastSeq++;
        }
        if(lastSeq > MAX_SEQ) {
            lastSeq = 0;
        }
        return lastTime.format(formatter) + String.format("%02d", lastSeq);
    }

}