package com.ashok.lang.inputs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CsvReader extends InputReader {
    public CsvReader() {
        super();
    }

    public CsvReader(InputStream input) {
        super(input);
    }

    public CsvReader(String file) throws FileNotFoundException {
        super(file);
    }

    /**
     * Reads the next comma seperated value in file and returns the same.
     *
     * @return
     * @throws IOException
     */
    public String next() throws IOException {
        StringBuilder sb = new StringBuilder();
        if (offset == bufferSize) {
            offset = 0;
            bufferSize = in.read(buffer);
        }

        if (bufferSize == -1 || bufferSize == 0)
            throw new IOException("No new bytes");

        for (; buffer[offset] == ',' || buffer[offset] == '\n'; ++offset) {
            if (offset == bufferSize - 1) {
                offset = -1;
                bufferSize = in.read(buffer);
            }
        }
        for (; offset < bufferSize; ++offset) {
            if (buffer[offset] == ',' || buffer[offset] == '\n')
                break;
            if (Character.isValidCodePoint(buffer[offset])) {
                sb.appendCodePoint(buffer[offset]);
            }
            if (offset == bufferSize - 1) {
                offset = -1;
                bufferSize = in.read(buffer);
            }
        }

        return sb.toString();
    }
}
