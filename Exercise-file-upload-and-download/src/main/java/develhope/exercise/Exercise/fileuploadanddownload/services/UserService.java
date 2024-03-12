package develhope.exercise.Exercise.fileuploadanddownload.services;

import develhope.exercise.Exercise.fileuploadanddownload.dtos.ProfilePictureDTO;
import develhope.exercise.Exercise.fileuploadanddownload.entities.User;
import develhope.exercise.Exercise.fileuploadanddownload.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileStorageService fileStorageService;

    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    @SneakyThrows
    public User uploadPFP(Long id, MultipartFile pfp) {
        Optional<User> user = get(id);
        if (user.get().getPfp() != null) {
            fileStorageService.remove(user.get().getPfp());
        }
        String fileName = fileStorageService.upload(pfp);
        user.get().setPfp(fileName);
        return save(user.get());
    }
    @SneakyThrows
    public ProfilePictureDTO downloadPFP(Long id) {
        Optional<User> user = get(id);
        ProfilePictureDTO dto = new ProfilePictureDTO();
        dto.setUser(user.get());
        if (user.get().getPfp() == null) return dto;
        byte[] pfpBytes = fileStorageService.download(user.get().getPfp());
        dto.setProfileImage(pfpBytes);
        return dto;
    }

    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.delete(get(id).get());
    }
}
