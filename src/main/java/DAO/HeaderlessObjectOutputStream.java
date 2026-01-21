package DAO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class HeaderlessObjectOutputStream extends ObjectOutputStream {
    public HeaderlessObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        //this method is overridden to prevent the stream header from being written
    }
}
