package develhope.exercise.Exercise.fileuploadanddownload.controllers;

import develhope.exercise.Exercise.fileuploadanddownload.dtos.ProfilePictureDTO;
import develhope.exercise.Exercise.fileuploadanddownload.entities.User;
import develhope.exercise.Exercise.fileuploadanddownload.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @SneakyThrows
    @PostMapping("/{id}/uploadPFP")
    public User uploadPFP(@PathVariable Long id, MultipartFile pfp) {
        return userService.uploadPFP(id, pfp);
    }

    @PutMapping("/{id}/update")
    public void update(@PathVariable Long id, @RequestBody User user) {
        userService.save(user);
    }

    @SneakyThrows
    @GetMapping("/{id}/downloadPFP")
    public @ResponseBody byte[] getPFP(@PathVariable Long id, HttpServletResponse response) {
        ProfilePictureDTO profilePictureDTO = userService.downloadPFP(id);
        String fileName = profilePictureDTO.getUser().getPfp();
        if (fileName == null) throw new Exception("L'utente non ha un'immagine profilo");
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension) {
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        return profilePictureDTO.getProfileImage();

    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/get/{id}")
    public Optional<User> get(@PathVariable Long id) {
        return userService.get(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
