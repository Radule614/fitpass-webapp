package dto;

import java.io.InputStream;

public class FileDTO {
    public InputStream inputStream;
    public String filename;
    public String extension;
    public FileDTO(){}

    public FileDTO(InputStream inputStream, String filename, String extension) {
        this.inputStream = inputStream;
        this.filename = filename;
        this.extension = extension;
    }
}
