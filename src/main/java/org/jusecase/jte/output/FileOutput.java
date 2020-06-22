package org.jusecase.jte.output;

import org.jusecase.jte.TemplateOutput;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileOutput implements TemplateOutput, Closeable {

    private final BufferedWriter writer;

    public FileOutput(Path file) throws IOException {
        Files.createDirectories(file.getParent());
        writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

    @Override
    public void writeSafeContent(String value) {
        try {
            writer.write(value);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
