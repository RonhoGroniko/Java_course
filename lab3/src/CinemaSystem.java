import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CinemaSystem {
    private List<Cinema> cinemas;
    private List<User> users;
    private User currentUser;
    private boolean isLogged;



    public CinemaSystem() {
        this.cinemas = new ArrayList<>();
        this.users = new ArrayList<>();
        this.isLogged = false;


        users.add(new User("admin", "admin", true));
        users.add(new User("user", "user", false));
        Cinema cinema1 = new Cinema("Кинотеатр 1");
        Hall hall1 = new Hall("Зал 1", 5, 10, cinema1); // 5 рядов, 10 мест в ряду
        Hall hall2 = new Hall("Зал 2", 8, 12, cinema1); // 8 рядов, 12 мест в ряду
        cinema1.addHall(hall1);
        cinema1.addHall(hall2);
        cinemas.add(cinema1);

        Cinema cinema2 = new Cinema("Кинотеатр 2");
        Hall hallA = new Hall("Зал 1", 6, 8, cinema2); // 6 рядов, 8 мест в ряду
        Hall hallB = new Hall("Зал 2", 10, 15, cinema2); // 10 рядов, 15 мест в ряду
        cinema2.addHall(hallA);
        cinema2.addHall(hallB);
        cinemas.add(cinema2);


        Movie movie1 = new Movie("Матрица", 120); // Фильм "Матрица", длительность 120 минут
        Movie movie2 = new Movie("Интерстеллар", 150); // Фильм "Интерстеллар", длительность 150 минут
        Movie movie3 = new Movie("Матрица", 120); // Фильм "Матрица", длительность 120 минут

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date startTime1 = dateFormat.parse("2023-10-15 18:00");
            Date startTime2 = dateFormat.parse("2023-10-15 20:00");
            Date startTime3 = dateFormat.parse("2023-10-15 22:00");

            Session session1 = new Session(movie1, hall1, startTime1); // Сеанс "Матрица" в зале 1
            Session session2 = new Session(movie2, hallA, startTime2); // Сеанс "Интерстеллар" в зале A
            Session session3 = new Session(movie3, hall1, startTime3); // Сеанс "Интерстеллар" в зале A

            cinema1.addSession(session1);
            cinema2.addSession(session2);
            cinema1.addSession(session3);
        } catch (ParseException e) {
            System.out.println("Ошибка при создании сеансов: " + e.getMessage());
        }
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public boolean isLogged() {
        return isLogged;
    }


    public void login(String username, String password) {
        if (isLogged) {
            System.out.println("Вы уже авторизованы.");
            return;
        }

        for (User user : users) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                currentUser = user;
                isLogged = true;
                System.out.println("Успешный вход. Добро пожаловать, " + username + "!");
                return;
            }
        }
        System.out.println("Неверный логин или пароль.");
    }

    public void logout() {
        if (isLogged) {
            isLogged = false;
            currentUser = null;
            System.out.println("Вы успешно вышли из учетной записи.");
        } else {
            System.out.println("Вы не авторизованы.");
        }
    }

    public void addCinema(String name) {
        if (currentUser != null && currentUser.isAdmin()) {

            Cinema existingCinema = findCinemaByName(name);
            if (existingCinema == null) {

                cinemas.add(new Cinema(name));
                System.out.println("Кинотеатр добавлен: " + name);
            } else {
                System.out.println("Кинотеатр с таким названием уже существует.");
            }
        } else {
            System.out.println("Доступ запрещен.");
        }
    }

    public List<Cinema> getCinemas(){
        return cinemas;
    }


    public void addHallToCinema(String cinemaName, String hallName, int rows, int cols) {
        if (currentUser != null && currentUser.isAdmin()) {

            Cinema cinema = findCinemaByName(cinemaName);
            if (cinema != null) {

                Hall existingHall = findHallByName(cinema, hallName);
                if (existingHall == null) {

                    cinema.addHall(new Hall(hallName, rows, cols, cinema));
                    System.out.println("Зал " + hallName + " в кинотеатре " + cinemaName + " добавлен.");
                } else {
                    System.out.println("Зал с таким названием уже существует в кинотеатре " + cinemaName + ".");
                }
            } else {
                System.out.println("Кинотеатр не найден.");
            }
        } else {
            System.out.println("Доступ запрещен.");
        }
    }

    public void createSession(String cinemaName, String hallName, Movie movie, Date startTime) {
        if (currentUser != null && currentUser.isAdmin()) {
            Cinema cinema = findCinemaByName(cinemaName);
            if (cinema != null) {
                Hall hall = findHallByName(cinema, hallName);
                if (hall != null) {
                    Session session = new Session(movie, hall, startTime);
//                    cinema.getHalls().forEach(h -> h.getSeats()); // Инициализация мест
                    cinema.addSession(session);
                    System.out.println("Сеанс создан: " + movie.getTitle() + " в " + hallName);
                } else {
                    System.out.println("Зал не найден.");
                }
            } else {
                System.out.println("Кинотеатр не найден.");
            }
        } else {
            System.out.println("Доступ запрещен.");
        }
    }

    public void printSeatsForSession(String cinemaName, String hallName, Date startTime) {
        Cinema cinema = findCinemaByName(cinemaName);
        if (cinema != null) {
            Hall hall = findHallByName(cinema, hallName);
            if (hall != null) {
                Session session = findSessionByHallAndTime(cinema, hall, startTime); // Ищем сеанс по залу и времени
                if (session != null) {
                    session.printSeats(); // Выводим места для этого сеанса
                } else {
                    System.out.println("Сеанс не найден.");
                }
            } else {
                System.out.println("Зал не найден.");
            }
        } else {
            System.out.println("Кинотеатр не найден.");
        }
    }

    public Cinema findCinemaByName(String name) {
        for (Cinema cinema : cinemas) {
            if (cinema.getName().equals(name)) {
                return cinema;
            }
        }
        return null;
    }

    public Hall findHallByName(Cinema cinema, String name) {
        for (Hall hall : cinema.getHalls()) {
            if (hall.getName().equals(name)) {
                return hall;
            }
        }
        return null;
    }

    public void bookTicket(String cinemaName, String hallName, int row, int col) {
        if (currentUser != null) {
            Cinema cinema = findCinemaByName(cinemaName);
            if (cinema != null) {
                Hall hall = findHallByName(cinema, hallName);
                if (hall != null) {
                    Session session = findSessionByHall(cinema, hall);
                    if (session != null) {
                        Seat[][] seats = session.getSeats();
                        if (row >= 0 && row < seats.length && col >= 0 && col < seats[0].length) {
                            Seat seat = seats[row][col];
                            if (!seat.isOccupied()) {
                                seat.book(); // Бронируем место
                                Ticket ticket = new Ticket(session, seat);
                                session.addTicket(ticket);
                                System.out.println("Билет успешно забронирован.");
                            } else {
                                System.out.println("Место уже занято.");
                            }
                        } else {
                            System.out.println("Неверные координаты места.");
                        }
                    } else {
                        System.out.println("Сеанс не найден.");
                    }
                } else {
                    System.out.println("Зал не найден.");
                }
            } else {
                System.out.println("Кинотеатр не найден.");
            }
        } else {
            System.out.println("Доступ запрещен.");
        }
    }

    private Session findSessionByHall(Cinema cinema, Hall hall) {
        for (Session session : cinema.getSessions()) {
            if (session.getHall().equals(hall)) {
                return session;
            }
        }
        return null;
    }

    public void chooseMovie() {
        if (currentUser != null) {
            System.out.println("Список фильмов:");
            // Здесь можно вывести список фильмов, например, из сеансов
            // Пока что просто выводим заглушку
            System.out.println("- Фильм 1");
            System.out.println("- Фильм 2");
        } else {
            System.out.println("Доступ запрещен.");
        }
    }

    public List<Session> findSessionsByMovie(String movieTitle) {
        List<Session> result = new ArrayList<>();
        for (Cinema cinema : cinemas) {
            for (Session session : cinema.getSessions()) {
                if (session.getMovie().getTitle().equalsIgnoreCase(movieTitle)) {
                    result.add(session);
                }
            }
        }

        // Сортируем сеансы по дате по возрастанию
        Collections.sort(result, new Comparator<Session>() {
            @Override
            public int compare(Session s1, Session s2) {
                return s1.getStartTime().compareTo(s2.getStartTime());
            }
        });

        return result;
    }

    private Session findSessionByHallAndTime(Cinema cinema, Hall hall, Date startTime) {
        for (Session session : cinema.getSessions()) {
            if (session.getHall().equals(hall) && session.getStartTime().equals(startTime)) {
                return session;
            }
        }
        return null;
    }

    public void printAllMovies() {
        Set<String> uniqueMovies = new HashSet<>(); // Используем Set для уникальных названий фильмов
        for (Cinema cinema : cinemas) {
            for (Session session : cinema.getSessions()) {
                uniqueMovies.add(session.getMovie().getTitle());
            }
        }

        if (uniqueMovies.isEmpty()) {
            System.out.println("Нет доступных фильмов.");
        } else {
            System.out.println("Список всех фильмов:");
            for (String movie : uniqueMovies) {
                System.out.println("- " + movie);
            }
        }
    }

    public Session findSessionByCinemaHallAndTime(String cinemaName, String hallName, Date startTime) {
        Cinema cinema = findCinemaByName(cinemaName);
        if (cinema != null) {
            Hall hall = findHallByName(cinema, hallName);
            if (hall != null) {
                for (Session session : cinema.getSessions()) {
                    if (session.getHall().equals(hall) && session.getStartTime().equals(startTime)) {
                        return session;
                    }
                }
            }
        }
        return null;
    }

    public List<Session> getSessionsByHall(String cinemaName, String hallName) {
        Cinema cinema = findCinemaByName(cinemaName);
        if (cinema != null) {
            Hall hall = findHallByName(cinema, hallName);
            if (hall != null) {
                List<Session> sessionsInHall = new ArrayList<>();
                for (Session session : cinema.getSessions()) {
                    if (session.getHall().equals(hall)) {
                        sessionsInHall.add(session);
                    }
                }
                return sessionsInHall;
            }
        }
        return Collections.emptyList(); // Возвращаем пустой список, если зал или кинотеатр не найдены
    }
}