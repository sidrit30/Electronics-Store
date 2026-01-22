package UnitTesting.DAOTest;

import DAO.HeaderlessObjectOutputStream;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class HeaderlessObjectOutputStreamTest {

    @Test
    void headerlessStream_allowsAppendingObjectsWithoutCorruptingStream() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Write first object with normal ObjectOutputStream (writes header once)
        try (ObjectOutputStream out1 = new ObjectOutputStream(baos)) {
            out1.writeObject("FIRST");
        }

        // Append second object WITHOUT writing another header
        try (HeaderlessObjectOutputStream out2 = new HeaderlessObjectOutputStream(baos)) {
            out2.writeObject("SECOND");
        }

        // Read back both objects
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        try (ObjectInputStream in = new ObjectInputStream(bais)) {
            Object a = in.readObject();
            Object b = in.readObject();

            assertEquals("FIRST", a);
            assertEquals("SECOND", b);
        }
    }
}
