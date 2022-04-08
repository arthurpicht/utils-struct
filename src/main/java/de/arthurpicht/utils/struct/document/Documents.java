package de.arthurpicht.utils.struct.document;

import de.arthurpicht.utils.io.InputStreams;
import de.arthurpicht.utils.io.nio2.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNull;

public class Documents {

    public static Document createFrom(Path file) throws IOException {
        assertArgumentNotNull("file", file);
        if (!FileUtils.isExistingRegularFile(file))
            throw new IllegalArgumentException("File not found: [" + file.toAbsolutePath() + "].");

        List<String> lines = Files.readAllLines(file);
        return new Document(lines);
    }

    public static Document createFrom(Path file, Charset charset) throws IOException {
        assertArgumentNotNull("file", file);
        assertArgumentNotNull("charset", charset);
        if (!FileUtils.isExistingRegularFile(file))
            throw new IllegalArgumentException("File not found: [" + file.toAbsolutePath() + "].");

        List<String> lines = Files.readAllLines(file, charset);
        return new Document(lines);
    }

    public static Document createFrom(InputStream inputStream) throws IOException {
        assertArgumentNotNull("inputStream", inputStream);

        List<String> lines = InputStreams.toStrings(inputStream);
        return new Document(lines);
    }

    public static void writeTo(DocumentInterface document, Path file) throws IOException {
        assertArgumentNotNull("document", document);
        assertArgumentNotNull("file", file);
        Path parentDir = file.getParent();
        if (!FileUtils.isExistingDirectory(parentDir)) throw new IllegalArgumentException("Cannot write file. " +
                "Parent directory not found. [" + file.toAbsolutePath() + "]");
        if (FileUtils.isExistingRegularFile(file))
            throw new IllegalArgumentException("Cannot write file. Already existing. [" + file.toAbsolutePath() + "]");
        Files.writeString(file, document.asString());
    }

}
