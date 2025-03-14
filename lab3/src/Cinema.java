import java.util.*;

public class Cinema {
    private String name;
    private List<Hall> halls;
    private List<Session> sessions;

    public Cinema(String name) {
        this.name = name;
        this.halls = new ArrayList<>();
        this.sessions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addHall(Hall hall) {
        halls.add(hall);
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void addSession(Session session){
        sessions.add(session);
    }

    public List<Session> getSessions(){
        return sessions;
    }
//    public void printInfo() {
//        System.out.println("Кинотеатр: " + name);
//        System.out.println("Количество залов: " + halls.size());
//        for (Hall hall : halls) {
//            System.out.println("Зал: " + hall.getName());
//        }
//    }
}