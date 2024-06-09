package com.vascomm.service.user;

import com.vascomm.controller.user.request.EditUserRequest;
import com.vascomm.controller.user.response.DetailUserResponse;
import com.vascomm.entity.User;
import com.vascomm.repository.UserRepository;
import com.vascomm.response.ResponseAPI;
import com.vascomm.util.PageInput;
import com.vascomm.util.PageRequestUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vascomm.util.constant.ResponseMessage.OK;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseAPI> editUser(String userId, EditUserRequest request) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return new ResponseEntity<>(new ResponseAPI(400, "User not found", null, null), HttpStatus.BAD_REQUEST);
        }

        if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null && !request.getLastName().isEmpty()) {
            user.setLastName(request.getLastName());
        }

        userRepository.save(user);

        DetailUserResponse res = new DetailUserResponse();
        res.generate(user);

        return new ResponseEntity<>(new ResponseAPI(200, OK, null, res), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseAPI> detailUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return new ResponseEntity<>(new ResponseAPI(400, "User not found", null, null), HttpStatus.BAD_REQUEST);
        }

        DetailUserResponse res = new DetailUserResponse();
        res.generate(user);

        return new ResponseEntity<>(new ResponseAPI(200, OK, null, res), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseAPI> listUser(PageInput pageInput) {
        Pageable pageable = PageRequestUtil.of(pageInput);
        Specification<User> spec = (root, query, builder) -> {
            String searchLike = "%" + pageInput.getSearch() + "%";
            Predicate namePredicate = builder.like(builder.concat(builder.concat(root.get("firstName"), " "), root.get("lastName")), searchLike);
            Predicate email = builder.like(root.get("email"), pageInput.getSearch());
            return builder.or(namePredicate, email);
        };

        Page<User> resultQuery = userRepository.findAll(spec, pageable);
        List<DetailUserResponse> list = resultQuery
                .stream()
                .map(user -> {
                    DetailUserResponse dto = new DetailUserResponse();
                    dto.setUserId(user.getUserId());
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .toList();

        Page<DetailUserResponse> res = new PageImpl<>(list, pageable, resultQuery.getTotalElements());

        return new ResponseEntity<>(new ResponseAPI(200, OK, null, res), HttpStatus.OK);
    }
}
