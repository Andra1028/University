package service;

import domain.Entity;
import domain.Friendship;
import domain.Tuple;
import domain.User;
import domain.ValidationException;
import repository.Repository;

import java.time.LocalDateTime;

/**
 * class Service
 * repoUser-Repository for Users
 * repoFriends-Repository for Friendships
 */
public class ServiceFriendship {
    private Repository<Long, User> repoUser;
    private Repository<Tuple<Long,Long>, Friendship> repoFriends;

    /**
     * constructor for the service
     * @param RepoUser UserRepository
     * @param RepoFriends FriendsRepository
     */
    public ServiceFriendship(Repository<Long, User> RepoUser, Repository<Tuple<Long, Long>, Friendship> RepoFriends){
        repoFriends = RepoFriends;
        repoUser = RepoUser;
    }

    /**
     * add a friendship
     * if is not valid throw ValidationException
     * @param id1-Long
     * @param id2-Long
     */
    public void addFriend(Long id1, Long id2) throws ValidationException {
        User u1 = repoUser.findOne(id1);
        User u2 = repoUser.findOne(id2);
        try{
            if(u1 != null && u2 != null) {
                if(repoFriends.findOne(new Tuple<>(id1,id2)) == null) {
                    Friendship friendship = new Friendship();
                    Tuple t = new Tuple(id1, id2);
                    friendship.setId(t);
                    friendship.setDate(LocalDateTime.now());
                    Friendship response = repoFriends.save(friendship);
                    if (response != null)
                        throw new ValidationException("Friendship already made!");
                }
                else
                    throw new ValidationException("Friendship already made!");
            }
            else{
                String errors = "";
                if(u1 == null)
                    errors += "First id does not exist!";
                if(u2 == null)
                    errors += "Second id does not exist!";
                throw new ValidationException(errors);
            }
        }
        catch (ValidationException e){
            throw new ValidationException(e);
        }
    }

    /**
     * delete a friendship
     * if is not valid throw ValidationException
     * @param id1-Long
     * @param id2-Long
     */
    public void deleteFriend(Long id1, Long id2) throws ValidationException {

        Tuple t = new Tuple(id1, id2);
        if (repoUser.findOne(id1) == null)
            throw new ValidationException(" first id does not exist");
        if (repoUser.findOne(id2) == null)
            throw new ValidationException(" second id does not exist");
        Entity save = repoFriends.delete(t);
        if (save == null)
            throw new ValidationException(" ids are not used in a friendship");

    }

    /**
     * @return all the friendships
     */
    public Iterable<Friendship> printFr() {
        return repoFriends.findAll();
    }
}