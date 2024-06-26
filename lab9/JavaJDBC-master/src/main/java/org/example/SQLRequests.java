package org.example;

import java.sql.*;

public class SQLRequests {
    Connection connection;
    PreparedStatement preparedStatement;

    public SQLRequests(Connection connection) {
        this.connection = connection;
    }

    public String createNewRowInPlaceTable(int floor, int wardrobe, int shelf) {
        try {
            preparedStatement = connection.prepareStatement(
                    "insert into place(floor, wardrobe, shelf) values (?, ?, ?)");
            preparedStatement.setInt(1, floor);
            preparedStatement.setInt(2, wardrobe);
            preparedStatement.setInt(3, shelf);
            preparedStatement.executeUpdate();
            return "Новое место создано!";
        } catch (SQLException e) {
            return "Соединение закрыто!";
        }
    }

    public String showAllPlaces() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "select * from place order by id");
            StringBuilder table = new StringBuilder(" id|этаж| шкаф   |полка\n");
            while (resultSet.next()) {
                table.append(String.format("%3d %4d %6d %5d\n",
                        resultSet.getInt("id"),
                        resultSet.getInt("floor"),
                        resultSet.getInt("wardrobe"),
                        resultSet.getInt("shelf")));
            }
            return table.toString();
        } catch (SQLException e) {
            return "Произошла ошибка при просмотре!";
        }
    }

    public String createNewRowInBookTable(
            String author, String publication, String publishingHouse,
            int yearPublic, int pages, Integer yearWrite, int weight, int placeId) {
        try {
            preparedStatement = connection.prepareStatement(
                    "insert into book(author, publication, publishing_house," +
                            " year_public, pages, year_write, weight, place_id)" +
                            " values (?, ?, ?, ?, ?, ?, ?, ?)");
            setBookParametersInStatement(author, publication, publishingHouse, yearPublic, pages, yearWrite, weight);
            preparedStatement.setInt(8, placeId);
            preparedStatement.executeUpdate();
            return "Новая книга создана!";
        } catch (SQLException e) {
            return "Произошла ошибка при добавлении!";
        }
    }

    public String showAllBooks() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "select * from book order by id");
            StringBuilder table = new StringBuilder(
                    " id|       автор       |       издание       |издательство|" +
                            "год публикации|страниц|год написания|вес|id места\n");
            while (resultSet.next()) {
                table.append(String.format(
                        "%3d %20s %20s %16s %11d %5d %10d %6d %8d\n",
                        resultSet.getInt("id"),
                        resultSet.getString("author"),
                        resultSet.getString("publication"),
                        resultSet.getString("publishing_house"),
                        resultSet.getInt("year_public"),
                        resultSet.getInt("pages"),
                        resultSet.getObject("year_write", Integer.class),
                        resultSet.getInt("weight"),
                        resultSet.getInt("place_id")));
            }

            return table.toString();
        } catch (SQLException e) {
            return "Произошла ошибка при просмотре!";
        }
    }

    public boolean checkExistsPlaceById(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) from place where id = " + id);
            resultSet.next();
            return resultSet.getInt(1) == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public String updatePlace(int id, int floor, int wardrobe, int shelf) {
        try {
            preparedStatement = connection.prepareStatement(
                    "update place set floor = ?, wardrobe = ?, shelf = ? where id = ?");
            preparedStatement.setInt(1, floor);
            preparedStatement.setInt(2, wardrobe);
            preparedStatement.setInt(3, shelf);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            return "Место обновлено!";
        } catch (SQLException e) {
            return "Соединение закрыто!";
        }

    }

    public boolean checkExistsBookById(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) from book where id = " + id);
            resultSet.next();
            return resultSet.getInt(1) == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public String updateBook(int id, String author, String publication, String publishingHouse,
                             int yearPublic, int pages, Integer yearWrite, int weight, int placeId) {
        try {
            preparedStatement = connection.prepareStatement(
                    "update book set author = ?, publication = ?, publishing_house = ?," +
                            " year_public = ?, pages = ?, year_write = ?, weight = ?, place_id = ? where id = ?");
            setBookParametersInStatement(author, publication, publishingHouse, yearPublic, pages, yearWrite, weight);
            preparedStatement.setInt(8, placeId);
            preparedStatement.setInt(9, id);
            preparedStatement.executeUpdate();
            return "Книга обновлена!";
        } catch (SQLException e) {
            return "Произошла ошибка при обновлении!";
        }
    }

    private void setBookParametersInStatement(
            String author, String publication, String publishingHouse, int yearPublic,
            int pages, Integer yearWrite, int weight) throws SQLException {
        preparedStatement.setString(1, author);
        preparedStatement.setString(2, publication);
        preparedStatement.setString(3, publishingHouse);
        preparedStatement.setInt(4, yearPublic);
        preparedStatement.setInt(5, pages);
        if (yearWrite != null) {
            preparedStatement.setInt(6, yearWrite);
        } else {
            preparedStatement.setNull(6, Types.INTEGER);
        }
        preparedStatement.setInt(7, weight);
    }

    public String deletePlace(int id) {
        try {
            preparedStatement = connection.prepareStatement(
                    "delete from place where id = ?");
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0)
                return "Ошибка при удалении места!";
            return "Место удалено!";
        } catch (SQLException e) {
            return "Ошибка при удалении места!";
        }
    }

    public String deleteBook(int id) {
        try {
            preparedStatement = connection.prepareStatement(
                    "delete from book where id = ?");
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0)
                return "Ошибка при удалении книги!";
            return "Книга удалена!";
        } catch (SQLException e) {
            return "Ошибка при удалении книги!";
        }
    }

    public String showFirstFieldInAllBooks() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select publication from book");
            StringBuilder table = new StringBuilder();
            while (resultSet.next()) {
                table.append(resultSet.getString("publication"))
                        .append("\n");
            }
            return table.toString();

        } catch (SQLException e) {
            return "Произошла ошибка при просмотре!";
        }
    }

    public String showSecondFieldInAllBooksByLexicOrder() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "select publication from book order by book.publication");
            StringBuilder table = new StringBuilder();
            while (resultSet.next()) {
                table.append(resultSet.getString("publication"))
                        .append("\n");
            }
            return table.toString();
        } catch (SQLException e) {
            return "Произошла ошибка при просмотре!";
        }
    }

    public void deleteAllBook() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from book");
        } catch (SQLException ignored) {
        }
    }

    public void deleteAllPlace() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from place");
        } catch (SQLException ignored) {
        }
    }

    public String createNewRowInBookTableWOPlaceId(
            String author, String publication, String publishingHouse,
            int yearPublic, int pages, Integer yearWrite, int weight) {
        try {
            preparedStatement = connection.prepareStatement(
                    "insert into book(author, publication, publishing_house," +
                            " year_public, pages, year_write, weight)" +
                            " values (?, ?, ?, ?, ?, ?, ?)");
            setBookParametersInStatement(author, publication, publishingHouse, yearPublic, pages, yearWrite, weight);
            preparedStatement.executeUpdate();
            return "Новая книга создана!";
        } catch (SQLException e) {
            return "Произошла ошибка при добавлении!";
        }

    }

    public String showAuthorsOnFloorInLexicOrder(int floor) {
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT DISTINCT author FROM book WHERE place_id IN (SELECT id FROM place WHERE floor = ?) ORDER BY author");
            preparedStatement.setInt(1, floor);
            ResultSet resultSet = preparedStatement.executeQuery();
            StringBuilder authors = new StringBuilder();
            while (resultSet.next()) {
                authors.append(resultSet.getString("author")).append("\n");
            }
            if (authors.length() > 0) {
                return "Авторы на этаже " + floor + " в лексикографическом порядке:\n" + authors.toString();
            } else {
                return "Авторы не найдены на указанном этаже!";
            }
        } catch (SQLException e) {
            return "Произошла ошибка при просмотре!";
        }
    }


    public String showPublicationsWithoutYearWrite() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "select publication from book where year_write is null");
            StringBuilder table = new StringBuilder();
            while (resultSet.next()) {
                table.append(resultSet.getString("publication"))
                        .append("\n");
            }
            return table.toString();

        } catch (SQLException e) {
            return "Произошла ошибка при просмотре!";
        }
    }
}
