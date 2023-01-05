package com.ei.riddlefx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.*;

public class AppController {
    @FXML
    Label riddleText;

    @FXML
    Label answerText;

    @FXML
    Button answerButton;

    @FXML
    Button nextButton;

    String answer;
    int index = 1;
    int size;

    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    @FXML
    public void initialize() {
        answerButton.setDefaultButton(true);
        nextButton.setVisible(false);

        Connection connection;
        Statement statement;

        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:riddles.db");

            statement = connection.createStatement();

            String sql = "SELECT * FROM riddles";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                size = resultSet.getInt(1);
            }

            String sql2 = "SELECT * FROM riddles WHERE id=?";
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setInt(1, index);
            resultSet = preparedStatement.executeQuery();

            String riddle = resultSet.getString("riddle");
            riddleText.setText(riddle);

            answer = resultSet.getString("answer");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void onButtonClick() {
        answerText.setText(answer);
        nextButton.setVisible(true);
    }

    @FXML
    protected void onNextButtonClick() {
        index++;

        if (index > size)
            index = 1;

        try {
            preparedStatement.setInt(1, index);
            resultSet = preparedStatement.executeQuery();

            String riddle = resultSet.getString("riddle");
            riddleText.setText(riddle);

            answer = resultSet.getString("answer");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        answerText.setText("");
        nextButton.setVisible(false);
    }

}