package ca.nangasoft.tweetscribe.testhelpers;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestWorkspace {
    private Path rootPath;

    public void create() {
        try {
            rootPath = Files.createTempDirectory("");
        } catch (IOException e) {
            throw new RuntimeException("Failed to create test workspace", e);
        }
    }

    public void delete() {
        try {
            FileUtils.deleteDirectory(getRootFolder());
        } catch (IOException e) {
            // We don't want to fail a test just because the cleanup failed. Just output the cause of the error.
            System.err.println("Failed to delete test workspace");
            e.printStackTrace();
        }
    }

    public File getFile(String fileName) {
        if (rootPath == null) {
            throw new RuntimeException("You must first create the test workspace prior to using it");
        }
        return rootPath.resolve(fileName).toFile();
    }

    public File getRootFolder() {
        return rootPath.toFile();
    }

    public Path getRootPath() {
        return rootPath;
    }
}
