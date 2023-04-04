package domain;

import domain.Friendship;
import domain.User;

import java.util.Objects;

/**
 * Friendship Validator class
 * implements interface of Validator
 */
public class FriendshipValidator implements  Validator<Friendship>{

    @Override
    public void validate(Friendship entity) throws ValidationException {
        if(Objects.equals(entity.getId().getLeft(), entity.getId().getRight()))
            throw new ValidationException(" the ids must be different");
    }
}