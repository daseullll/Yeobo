package com.jagi.yeobo.service;

import com.jagi.yeobo.domain.User;
import com.jagi.yeobo.domain.repository.UserRepository;
import com.jagi.yeobo.domain.repository.UserRepository2;
import com.jagi.yeobo.dto.UserDto;
import com.jagi.yeobo.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository2 userRepository2;
    private final UserRepository userRepository;

    @Transactional
    public UserDto searchUser(long userId) {
        return userRepository2.searchUser(userId);
    }
    @Transactional
    public int updateUserNick(long userId, String nick){
        return userRepository2.updateUserNick(userId,nick);
    }
    @Transactional
    public int deleteUser(long userId) {
        return userRepository2.deleteById(userId);
    }
    @Transactional
    public List<UserDto> searchByNick(String nickname){
        return userRepository2.searchByNick(nickname);
    }
    @Transactional
    public long signUp(User user){
        userRepository2.save(user);
        return user.getId();
    }

    @Transactional
    public User login(UserLoginDto userLoginDto){
        Optional<User> user = userRepository.findByEmail(userLoginDto.getEmail());
        if(user.isEmpty()) throw new IllegalStateException("해당 이메일을 가진 사용자가 없습니다.");
        if(!user.get().getPassword().equals(userLoginDto.getPassword())) {
            throw new IllegalStateException("잘못된 비밀번호 입니다.");
        }
        //나중에 토큰이나 다른 정보들 넘겨줌
        return user.get();
    }
}
