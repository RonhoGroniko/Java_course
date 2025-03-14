import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        CinemaSystem system = new CinemaSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            if (!system.isLogged()) {
                System.out.println("Введите команду (login, exit):");
                String command = scanner.nextLine();

                if (command.equals("exit")) {
                    break;
                }

                if (command.equals("login")) {
                    System.out.println("Введите логин:");
                    String username = scanner.nextLine();
                    System.out.println("Введите пароль:");
                    String password = scanner.nextLine();
                    system.login(username, password);
                } else {
                    System.out.println("Доступно только: login или exit.");
                }
            } else {

                if (system.getCurrentUser().isAdmin()) {
                    System.out.println("Введите команду (logout, addCinema, addHall, createSession, printSeats, exit):");
                } else {
                    System.out.println("Введите команду (logout, bookTicket, printSeats, exit):");
                }

                String command = scanner.nextLine();

                if (command.equals("exit")) {
                    break;
                }

                switch (command) {
                    case "logout":
                        system.logout();
                        break;
                    case "addCinema":
                        if (system.getCurrentUser().isAdmin()) {
                            System.out.println("Введите название кинотеатра:");
                            String cinemaName = scanner.nextLine();
                            system.addCinema(cinemaName);
                        } else {
                            System.out.println("Доступ запрещён.");
                        }
                        break;

                    case "addHall":
                        if (system.getCinemas().isEmpty()){
                            System.out.println("Сначала необходимо добавить хотя бы 1 кинотеатр");
                        }
                        else {
                            System.out.println("Список кинотеатров:");
                            for (Cinema cinema : system.getCinemas()) {
                                System.out.println("- " + cinema.getName());
                            }
                            if (system.getCurrentUser().isAdmin()) {
                                System.out.println("Введите название кинотеатра:");
                                String cinemaNameForHall = scanner.nextLine();
                                Cinema cinema = system.findCinemaByName(cinemaNameForHall);
                                if (cinema != null) {
                                    if(!cinema.getHalls().isEmpty()) {
                                        System.out.println("Список залов:");
                                        for (Hall hall : cinema.getHalls()) {
                                            System.out.println("- " + hall.getName());
                                        }
                                    }
                                    System.out.println("Введите название зала:");
                                    String hallName = scanner.nextLine();
                                    System.out.println("Введите количество рядов:");
                                    int rows = scanner.nextInt();
                                    System.out.println("Введите количество мест в ряду:");
                                    int cols = scanner.nextInt();
                                    scanner.nextLine();
                                    system.addHallToCinema(cinemaNameForHall, hallName, rows, cols);
                                } else{
                                    System.out.println("Кинотеатр с таким названием не найден.");
                                }
                            } else {
                                System.out.println("Доступ запрещён.");
                            }
                        }
                        break;
                    case "createSession":
                        if (system.getCinemas().isEmpty()) {
                            System.out.println("Сначала необходимо добавить хотя бы 1 кинотеатр");
                        } else if (system.getCurrentUser().isAdmin()) {
                            System.out.println("Список кинотеатров:");
                            for (Cinema cinema : system.getCinemas()) {
                                System.out.println("- " + cinema.getName());
                            }

                            System.out.println("Введите название кинотеатра:");
                            String cinemaNameForSession = scanner.nextLine();

                            Cinema cinema = system.findCinemaByName(cinemaNameForSession);
                            if (cinema != null) {
                                if (cinema.getHalls().isEmpty()) {
                                    System.out.println("Сначала необходимо добавить хотя бы 1 зал");
                                } else {
                                    System.out.println("Список залов в кинотеатре " + cinemaNameForSession + ":");
                                    for (Hall hall : cinema.getHalls()) {
                                        System.out.println("- " + hall.getName());
                                    }

                                    System.out.println("Введите название зала:");
                                    String hallNameForSession = scanner.nextLine();

                                    System.out.println("Введите название фильма:");
                                    String movieTitle = scanner.nextLine();
                                    System.out.println("Введите длительность фильма (в минутах):");
                                    int duration = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.println("Введите время начала сеанса (в формате 'yyyy-MM-dd HH:mm'):");
                                    String startTimeStr = scanner.nextLine();


                                    Date startTime;
                                    try {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                        startTime = dateFormat.parse(startTimeStr);
                                    } catch (ParseException e) {
                                        System.out.println("Неверный формат даты. Используйте формат 'yyyy-MM-dd HH:mm'.");
                                        break;
                                    }

                                    system.createSession(cinemaNameForSession, hallNameForSession, new Movie(movieTitle, duration), startTime);
                                }
                            } else {
                                System.out.println("Кинотеатр с таким названием не найден.");
                            }
                        } else {
                            System.out.println("Доступ запрещён.");
                        }
                        break;
                    case "printSeats":
                        if (system.getCurrentUser() != null) {
                            System.out.println("Список кинотеатров:");
                            for (Cinema cinema : system.getCinemas()) {
                                System.out.println("- " + cinema.getName());
                            }

                            System.out.println("Введите название кинотеатра:");
                            String cinemaNameForSeats = scanner.nextLine();

                            Cinema cinema = system.findCinemaByName(cinemaNameForSeats);
                            if (cinema != null) {
                                System.out.println("Список залов в кинотеатре " + cinemaNameForSeats + ":");
                                for (Hall hall : cinema.getHalls()) {
                                    System.out.println("- " + hall.getName());
                                }

                                System.out.println("Введите название зала:");
                                String hallNameForSeats = scanner.nextLine();

                                List<Session> sessionsInHall = system.getSessionsByHall(cinemaNameForSeats, hallNameForSeats);
                                if (sessionsInHall.isEmpty()) {
                                    System.out.println("В этом зале нет сеансов.");
                                } else {
                                    System.out.println("Сеансы в зале " + hallNameForSeats + ":");
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                    for (Session session : sessionsInHall) {
                                        System.out.println("Фильм: " + session.getMovie().getTitle() +
                                                ", Время: " + dateFormat.format(session.getStartTime()));
                                    }

                                    System.out.println("Введите время начала сеанса (в формате 'yyyy-MM-dd HH:mm'):");
                                    String startTimeStr = scanner.nextLine();
                                    try {
                                        Date startTime = dateFormat.parse(startTimeStr);
                                        system.printSeatsForSession(cinemaNameForSeats, hallNameForSeats, startTime);
                                    } catch (ParseException e) {
                                        System.out.println("Неверный формат даты. Используйте формат 'yyyy-MM-dd HH:mm'.");
                                    }
                                }
                            } else {
                                System.out.println("Кинотеатр с таким названием не найден.");
                            }
                        } else {
                            System.out.println("Доступ запрещен.");
                        }
                        break;
                    case "bookTicket":
                        if (system.getCurrentUser() != null && !system.getCurrentUser().isAdmin()) {
                            system.printAllMovies();

                            System.out.println("Введите название фильма:");
                            String movieTitle = scanner.nextLine();

                            List<Session> sessions = system.findSessionsByMovie(movieTitle);
                            if (sessions.isEmpty()) {
                                System.out.println("Сеансы по фильму '" + movieTitle + "' не найдены.");
                                break;
                            }

                            System.out.println("Доступные сеансы по фильму '" + movieTitle + "':");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            for (Session session : sessions) {
                                System.out.println("Кинотеатр: " + session.getHall().getCinema().getName() +
                                        ", Зал: " + session.getHall().getName() +
                                        ", Время: " + dateFormat.format(session.getStartTime()));
                            }

                            System.out.println("Введите название кинотеатра:");
                            String cinemaName = scanner.nextLine();

                            System.out.println("Введите название зала:");
                            String hallName = scanner.nextLine();

                            System.out.println("Введите время начала сеанса (в формате 'yyyy-MM-dd HH:mm'):");
                            String startTimeStr = scanner.nextLine();
                            Date startTime;
                            try {
                                startTime = dateFormat.parse(startTimeStr);
                            } catch (ParseException e) {
                                System.out.println("Неверный формат даты. Используйте формат 'yyyy-MM-dd HH:mm'.");
                                break;
                            }

                            Session session = system.findSessionByCinemaHallAndTime(cinemaName, hallName, startTime);
                            if (session == null) {
                                System.out.println("Сеанс не найден.");
                                break;
                            }

                            session.printSeats();


                            System.out.println("Введите номер ряда (от 1 до " + session.getSeats().length + "):");
                            int row = scanner.nextInt();
                            System.out.println("Введите номер места (от 1 до " + session.getSeats()[0].length + "):");
                            int col = scanner.nextInt();
                            scanner.nextLine();

                            row--;
                            col--;


                            if (row >= 0 && row < session.getSeats().length && col >= 0 && col < session.getSeats()[0].length) {
                                Seat seat = session.getSeats()[row][col];
                                if (!seat.isOccupied()) {
                                    seat.book();
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
                            System.out.println("Доступ запрещен.");
                        }
                        break;
                    default:
                        System.out.println("Неизвестная команда.");
                }
            }
        }
    }
}
