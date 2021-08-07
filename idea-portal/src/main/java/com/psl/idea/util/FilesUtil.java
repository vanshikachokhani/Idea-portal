package com.psl.idea.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class FilesUtil {

	public byte[] getFileBytes(String fileLocation) throws IOException {
		return Files.readAllBytes(Paths.get(fileLocation));
	}
}
