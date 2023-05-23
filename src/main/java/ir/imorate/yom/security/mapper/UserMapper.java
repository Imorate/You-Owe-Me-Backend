package ir.imorate.yom.security.mapper;

import ir.imorate.yom.security.entity.User;
import ir.imorate.yom.security.request.CreateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUser(CreateUserRequest request);

    CreateUserRequest toCreateUserRequest(User user);

}
