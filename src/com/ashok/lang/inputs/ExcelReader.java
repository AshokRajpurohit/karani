package com.ashok.lang.inputs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ExcelReader extends InputReader {
    public ExcelReader() {
        super();
    }

    public ExcelReader(InputStream input) {
        super(input);
    }

    public ExcelReader(String file) throws FileNotFoundException {
        super(file);
    }

    /**
     * Reads the next tab in excel sheet and returns the same.
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

        for (;
             buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                     '\n' || buffer[offset] == '\r'; ++offset) {
            if (offset == bufferSize - 1) {
                offset = -1;
                bufferSize = in.read(buffer);
            }
        }
        for (; offset < bufferSize; ++offset) {
            if (buffer[offset] == '\t' || buffer[offset] == '\n' ||
                    buffer[offset] == '\r')
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
