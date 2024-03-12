package develhope.exercise.Exercise.fileuploadanddownload.services;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${fileRepositoryFolder}")
    public String fileRepositoryFolder;

    @SneakyThrows
    public String upload(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName + "." + extension;

        File finalFolder = new File(fileRepositoryFolder);
        if (!finalFolder.exists()) throw new IOException("Final folder does not exists");
        if (!finalFolder.isDirectory()) throw new IOException("Final folder is not a directory");
        File finalDestination = new File(fileRepositoryFolder + "\\" + completeFileName);
        if (finalDestination.exists()) throw new IOException("File conflict");

        file.transferTo(finalDestination);
        return completeFileName;
    }

    public byte[] download(String fileName) throws IOException {
        File fileFromRepo = new File(fileRepositoryFolder + "\\" + fileName);
        if(!fileFromRepo.exists()) throw new IOException("File does not exists");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepo));
    }

    @SneakyThrows
    public void remove(String fileName) {
        File fileFromRepo = new File(fileRepositoryFolder + "\\" + fileName);
        if(!fileFromRepo.exists()) return;
        boolean deleteResult = fileFromRepo.delete();
        if(deleteResult == false) throw new Exception("Can't delete file");
    }
}
