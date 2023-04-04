package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.validation.ValidationException;
import com.example.socialnetworkgui.repository.Repository;
import com.example.socialnetworkgui.utils.*;
import com.example.socialnetworkgui.utils.Observable;
import com.example.socialnetworkgui.utils.Observer;

import java.time.LocalDateTime;
import java.util.*;

/**
 * class Service
 * repoUser-Repository for Users
 * repoFriends-Repository for Friendships
 */
public class ServiceUser implements Observable<FriendshipEntityChangeEvent> {
    private final Repository<Long, User> repoUser;
    private final Repository<SortedSet<Long>, Friendship> repoFriends;
    private final Repository<Long, Message> repoMessage;

    private long utilizatorCurentId = 0;


    private List<Observer<FriendshipEntityChangeEvent>> observers=new ArrayList<>();

    /**
     * constructor for the service
     *
     * @param RepoUser    UserRepository
     * @param RepoFriends FriendsRepository
     * @param RepoMessage
     */
    public ServiceUser(Repository<Long, User> RepoUser, Repository<SortedSet<Long>, Friendship> RepoFriends, Repository<Long,Message> RepoMessage) {
        repoFriends = RepoFriends;
        repoUser = RepoUser;
       repoMessage = RepoMessage;

    }



    /**
     * save a user, call the repo function
     * if is not valid throw ValidationException
     * @param firstname - String
     * @param lastname - String
     */
    public void save(String firstname, String lastname, String email, String parola) {
        User user = new User(firstname, lastname, null, email, parola,null);
        long id = get_size();
        id++;
        user.setID(id);
        Iterable<User> users = repoUser.findAll();
        boolean ok=true;
        for(User u: users)
        {
            if (u.getEmail().equals(email)) {
                ok = false;
                break;
            }
        }
        if(ok) {
            User save = repoUser.save(user);
            if (save != null)
                throw new ValidationException(" id already used");
        }
        else throw new ValidationException("exista cont cu acest email");
    }


    /**
     * delete a user, call the repo function
     * @param id-Long
     * @return the deleted user
     * otherwise, throw ValidationException
     */
    public Entity delete(Long id) {

        User deleted = repoUser.delete(id);

        if (deleted == null)
            throw new ValidationException(" id invalid!");
        ///return deleted;
        Iterable<User> FriendList= deleted.getFriends();
        for(User friend: FriendList)
        {
            SortedSet<Long> s = new TreeSet<Long>();
            s.add(friend.getID());
            s.add(deleted.getID());
            repoFriends.delete(s);
            friend.deleteFriend(deleted);

        }
        return deleted;
    }

    public void update(Long id, String firstname, String lastname, String email, String parola) {
        User userUpdate=findOne(id);
        User user = new User(firstname, lastname,userUpdate.getFriends(), email, parola,userUpdate.getRequests());
        user.setID(id);
        User save = repoUser.update(user);
        if (save != null)
            throw new ValidationException(" id invalid!");
    }

    /**
     * get the maximum id
     * @return the result-Long
     */
    public Long get_size() {
        Long maxim = 0L;
        for (User ur : repoUser.findAll())
            if (ur.getID() > maxim)
                maxim = ur.getID();
        return maxim;
    }

    /**
     * Display friends for a given user
     * @return the list of users friends with the one given
     * @throws ValidationException if the id for user given is invalid
     */
    public List<User> getFriends(){
        List<User> friends = new ArrayList<>();
        for(Friendship fr : repoFriends.findAll()){
            boolean hadFr = (fr.getU1().equals(getUtilizatorCurent()) || fr.getU2().equals(getUtilizatorCurent()));
            if(hadFr){
                if(!fr.getPending()) {
                    if (fr.getU1().equals(getUtilizatorCurent())) {
                        friends.add(fr.getU2());
                    } else {
                        friends.add(fr.getU1());
                    }
                }
            }
        }
        return friends;
    }

    public List<User> getFriendsWithPending(){
        List<User> friends = new ArrayList<>();
        for(Friendship fr : repoFriends.findAll()){
            boolean hadFr = (fr.getU1().equals(getUtilizatorCurent()) || fr.getU2().equals(getUtilizatorCurent()));
            if(hadFr){
                    if (fr.getU1().equals(getUtilizatorCurent())) {
                        friends.add(fr.getU2());
                    } else {
                        friends.add(fr.getU1());
                    }
            }
        }
        return friends;
    }

    public List<User> getNoFriends(){
        List<User> nonFriends = new ArrayList<>();
        boolean ok = true;
        List<User> friends = getFriendsWithPending();
        for(User user :repoUser.findAll()){
            for(User u : friends){
                if (user.equals(u)) {
                    ok = false;
                    break;
                }
            }
            if(ok && !user.equals(getUtilizatorCurent()))
                nonFriends.add(user);
            ok = true;
        }
        return nonFriends;
    }



    /** Function which returns all the users from list
     * @return all the users
     */
    public Iterable<User> printUs() {
        return repoUser.findAll();
    }



    /**Display friends for a given user
     * @param nr integer id of a posible user
     * @return the user with the given id
     * @throws ValidationException if the id for user given is invalid
     */
    public User findOne(Long nr) {
        if(repoUser.findOne(nr) != null)
            return repoUser.findOne(nr);
        else
            throw new ValidationException("id invalid!");
    }


    public User findByName(String firstname, String lastname) {
        if(repoUser.findByName(firstname,lastname) != null)
            return repoUser.findByName(firstname,lastname);
        else
            throw new ValidationException("nume invalid!");
    }

    public User connectUser(String email, String parola) {
        for (User user : repoUser.findAll()) {
            if (user.getEmail().equals(email)) {
                if (user.getPassword().equals(parola)) {
                    utilizatorCurentId = user.getID();
                    return user;
                } else {
                    throw new ValidationException("user invalid!");
                }
            }
        }
        throw new ValidationException("user invalid!");
    }


    public void disconnectUser(){
        this.utilizatorCurentId = 0L;
    }


    public Long getIdCurent() {

        return utilizatorCurentId;
    }

    public void setIdCurent(User user){this.utilizatorCurentId = user.getID();}

    public User getUtilizatorCurent() {
        return repoUser.findOne(utilizatorCurentId);
    }

//    public void sendRequest(User user) throws ValidationException{
//        Iterable<Friendship> friendships = repoFriends.findAll();
//        for(Friendship fr : friendships){
//            if(fr.getU1().getID().equals(utilizatorCurentId) && fr.getU2().getID().equals(user.getID())){
//                throw new ValidationException("Request already sent!");
//            }
//            if(fr.getU2().getID().equals(utilizatorCurentId) && fr.getU1().getID().equals(user.getID())){
//                throw new ValidationException("Request already sent!");
//            }
//        }
////        Friendship fr = new Friendship(getUtilizatorCurent(),user);
////        SortedSet<Long> s = new TreeSet<Long>();
////        s.add(utilizatorCurentId);
////        s.add(user.getID());
////        fr.setID(s);
////        fr.setDate(LocalDateTime.now());
////        repoFriends.save(fr);
//        User userc=findOne(utilizatorCurentId);
//        user.addRequests(userc);
//       // notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD, fr));
//    }
//
//    public List<User> getFriendRequests() {
//        User u= findOne(utilizatorCurentId);
//        return u.getRequests();
//    }
//
//
//    public void acceptRequest(User user){
//        User u = findOne(utilizatorCurentId);
//        List<User> request = u.getRequests();
//        for(User fr : request){
//            if(fr.equals(user)){
//                Friendship newFrnd = new Friendship(u,fr,false);
//               /// repoFriends.update(fr,newFrnd);
//                SortedSet<Long> s = new TreeSet<Long>();
//                s.add(utilizatorCurentId);
//                s.add(user.getID());
//                newFrnd.setID(s);
//                newFrnd.setDate(LocalDateTime.now());
//                u.deleteRequest(user);
//                repoFriends.save(newFrnd);
//                notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD,newFrnd));
//                break;
//            }
//        }
//    }
//
//    public void declineRequest(User user) {
//        User u = findOne(utilizatorCurentId);
//        List<User> request = u.getRequests();
//        for(User fr : request){
//            if(fr.equals(user)){
//                Friendship newFrnd = new Friendship(u,fr,false);
//                /// repoFriends.update(fr,newFrnd);
//                SortedSet<Long> s = new TreeSet<Long>();
//                s.add(utilizatorCurentId);
//                s.add(user.getID());
//                newFrnd.setID(s);
//                newFrnd.setDate(LocalDateTime.now());
//                u.deleteRequest(user);
//                notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD,newFrnd));
//                break;
//            }
//        }
//    }

    public void sendRequest(User user) throws ValidationException{
        Iterable<Friendship> friendships = repoFriends.findAll();
        for(Friendship fr : friendships){
            if(fr.getU1().equals(getUtilizatorCurent()) && fr.getU2().equals(user)){
                throw new ValidationException("Request already sent!");
            }
        }
        Friendship fr = new Friendship(getUtilizatorCurent(),user,LocalDateTime.now(),true);
        //System.out.println(fr);
        //fr.getU1().setDate(LocalDateTime.now());
        //System.out.println("2" + fr.getU1().getDate());
        //System.out.println("3 " + fr.getDate());
        SortedSet<Long> s= new TreeSet<>();
        s.add(getUtilizatorCurent().getID());
        s.add(user.getID());
        fr.setID(s);
        repoFriends.save(fr);
        notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD, fr));
    }

    public List<User> getFriendRequests(){
        List<User> requests = new ArrayList<>();
        for(Friendship fr : repoFriends.findAll()){
            if(fr.getU2().equals(getUtilizatorCurent()) && fr.getPending()){
                //System.out.println("1" + fr.getDate());
                fr.setDate(fr.getDate());
                requests.add(fr.getU1());
            }
        }
        return requests;
    }

    public List<User> getSentFriendRequests(){
        List<User> requests = new ArrayList<>();
        for(Friendship fr : repoFriends.findAll()){
            if(fr.getU1().equals(getUtilizatorCurent()) && fr.getPending()){
                //System.out.println("1" + fr.getDate());
                fr.setDate(fr.getDate());
                requests.add(fr.getU2());
            }
        }
        return requests;
    }

    public void acceptRequest(User user){
        Iterable<Friendship> friendships = repoFriends.findAll();
        for(Friendship fr : friendships){
            if(fr.getU1().getID().equals(user.getID()) && fr.getU2().getID().equals(utilizatorCurentId) && fr.getPending()){
                Friendship newFrnd = new Friendship(fr.getU1(),fr.getU2(),fr.getDate(),false);
                ///friendshipsRepo.update(fr,newFrnd);
                SortedSet<Long> s= new TreeSet<>();
                s.add(getUtilizatorCurent().getID());
                s.add(user.getID());
                newFrnd.setID(s);
                newFrnd.setDate(fr.getDate());
                repoFriends.update(newFrnd);
                //repoFriends.delete(s);
                ///repoFriends.save(newFrnd);
                notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD,newFrnd));
                break;
            }
            if(fr.getU2().getID().equals(user.getID()) && fr.getU1().getID().equals(utilizatorCurentId) && fr.getPending()){
                Friendship newFrnd = new Friendship(fr.getU1(),fr.getU2(),fr.getDate(),false);
                ///friendshipsRepo.update(fr,newFrnd);
                SortedSet<Long> s= new TreeSet<>();
                s.add(getUtilizatorCurent().getID());
                s.add(user.getID());
                newFrnd.setID(s);
                repoFriends.update(newFrnd);
                ///repoFriends.delete(s);
                ///repoFriends.save(newFrnd);
                notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD,newFrnd));
                break;
            }
        }
    }

    public void declineRequest(User user)  {
        Iterable<Friendship> friendships = repoFriends.findAll();
        for(Friendship fr : friendships){
            if(fr.getU1().equals(user) && fr.getU2().equals(getUtilizatorCurent()) && fr.getPending()){
                Friendship delete = new Friendship(fr.getU1(), fr.getU2(), fr.getDate(), true);
                SortedSet<Long> s= new TreeSet<>();
                s.add(getUtilizatorCurent().getID());
                s.add(user.getID());
                delete.setID(s);
               repoFriends.delete(delete.getID());
               /// notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD,delete));
                break;
            }
            if(fr.getU2().equals(user) && fr.getU1().equals(getUtilizatorCurent()) && fr.getPending()){
                Friendship delete = new Friendship(fr.getU1(), fr.getU2(), fr.getDate(), true);
                SortedSet<Long> s= new TreeSet<>();
                s.add(getUtilizatorCurent().getID());
                s.add(user.getID());
                delete.setID(s);
                repoFriends.delete(delete.getID());
                /// notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.ADD,delete));
                break;
            }
        }
    }

    public Message sendMessage (String message, User to){
        Message m = new Message(getUtilizatorCurent(),to, message);
        SortedSet<Long> s= new TreeSet<Long>();
        s.add(getUtilizatorCurent().getID());
        s.add(to.getID());
        if(repoFriends.findOne(s)!= null) {
            repoMessage.save(m);
            return m;
        }
        else return null;
    }

    public List<Message> receivedMessages(){
        List<Message> received= new ArrayList<>();
        for( Message m: repoMessage.findAll()){
            if (m.getTo().equals(getUtilizatorCurent()))
                received.add(m);
        }
        return received;
    }

    public List<Message> sentMessages(){
        List<Message> received= new ArrayList<>();
        for( Message m: repoMessage.findAll()){
            if (m.getFrom().equals(getUtilizatorCurent()))
                received.add(m);
        }
        return received;
    }

    public String firstnameTo(){
        for( Message m: repoMessage.findAll()){
            if (m.getFrom().equals(getUtilizatorCurent()))
                return m.getTo().getFirstName();
        }
        return "";
    }

    public String lastnameTo(){
        for( Message m: repoMessage.findAll()){
            if (m.getFrom().equals(getUtilizatorCurent()))
                return m.getTo().getLastName();
        }
        return "";
    }
    public String firstnameFrom(){
        for( Message m: repoMessage.findAll()){
            if (m.getFrom().equals(getUtilizatorCurent()))
                return m.getFrom().getFirstName();
        }
        return "";
    }
    public String lastnameFrom(){
        for( Message m: repoMessage.findAll()){
            if (m.getFrom().equals(getUtilizatorCurent()))
                return m.getFrom().getLastName();
        }
        return "";
    }

    @Override
    public void addObserver(Observer<FriendshipEntityChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<FriendshipEntityChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(FriendshipEntityChangeEvent t) {
        observers.forEach(x->x.update(t));
    }

}