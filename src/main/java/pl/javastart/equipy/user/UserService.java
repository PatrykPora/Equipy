package pl.javastart.equipy.user;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    List<UserDto> findAll() {
      return userRepository.findAll()
              .stream()
              .map(UserMapper::toUserDto)
              .collect(Collectors.toList());
    }

    List<UserDto> findByLastName(String lastName) {
        return userRepository.findAllByLastNameContainsIgnoreCase(lastName)
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }


    UserDto save(UserDto user) {
        Optional<User> userByPesel = userRepository.findByPesel(user.getPesel());
        userByPesel.ifPresent(u -> {throw new DuplicatedPeselException();
        });
        User entityUser = UserMapper.toUser(user);
        User savedUser = userRepository.save(entityUser);
        return UserMapper.toUserDto(savedUser);
    }


    UserDto update(UserDto user) {
        Optional<User> userByPesel = userRepository.findByPesel(user.getPesel());
        userByPesel.ifPresent( u -> {
           if (!u.getId().equals(user.getId())) {
               throw new DuplicatedPeselException();
           }
        });
        userByPesel.get().setFirstName(user.getFirstName());
        userByPesel.get().setLastName(user.getLastName());
        userByPesel.get().setPesel(user.getPesel());
        userRepository.save(userByPesel.get());
        return UserMapper.toUserDto(userByPesel.get());
    }

    Optional<UserDto> findByID(Long id) {
        return userRepository.findById(id).map(UserMapper::toUserDto);
    }
}
