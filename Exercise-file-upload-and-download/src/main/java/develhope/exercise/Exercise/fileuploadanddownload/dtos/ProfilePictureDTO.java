package develhope.exercise.Exercise.fileuploadanddownload.dtos;

import develhope.exercise.Exercise.fileuploadanddownload.entities.User;
import lombok.Data;

@Data
public class ProfilePictureDTO {
    private User user;
    private byte[] profileImage;
}
