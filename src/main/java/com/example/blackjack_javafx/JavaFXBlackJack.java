package com.example.blackjack_javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class JavaFXBlackJack extends Application {
//    static ArrayList<Integer> playerCardIndex = new ArrayList<>();

    static int playerScore = 5;
    static int houseScore = 5;

    Text txtResult;

    Rectangle playerBg;
    Rectangle houseBg;

    @Override
    public void start(Stage Stage) throws Exception {
        Rectangle bg = new Rectangle(1050.0, 600);
        bg.setFill(Color.web("#b3b5b3"));

        HBox mainLayout = new HBox();

        GridPane playerSideGp = new GridPane();
        GridPane houseSideGp = new GridPane();

        playerSideGp.minWidth(525.0);
        houseSideGp.minWidth(525.0);
        playerSideGp.minHeight(600.0);
        houseSideGp.minHeight(600.0);

        VBox playerSideLayout = new VBox();

        playerBg = new Rectangle();
        playerBg.setWidth(525.0);
        playerBg.setHeight(600.0);
        playerBg.setFill(Color.web("#5ccc7a"));

        playerSideLayout.setMinWidth(525.0);
        HBox playerSideFirstCardRow = new HBox();
        HBox playerSideSecondCardRow = new HBox();
        HBox playerButtonLayout = new HBox();
        Text txtPlayerScore = new Text("Your card(s) value: ");
        txtResult = new Text("");


        VBox houseSideLayout = new VBox();

        houseBg = new Rectangle();
        houseBg.setWidth(525.0);
        houseBg.setHeight(600.0);
        houseBg.setFill(Color.web("#cc785c"));

        HBox houseSideFirstCardRow = new HBox();
        HBox houseSideSecondCardRow = new HBox();
        Text txtHouseCardValue = new Text("Computer card(s) value: ?");

        Button btnGetCard = new Button("Get card");
        Button btnBet = new Button("Bet");
        Button btnForfeit = new Button("Forfeit");

        AtomicBoolean bet = new AtomicBoolean(false);

        txtPlayerScore.setText("Your card(s) value: ");
//        System.out.println(playerCardIndex.toString());
        playerSideFirstCardRow.setMinHeight(254.1);
        playerSideSecondCardRow.setMinHeight(254.1);


        houseSideFirstCardRow.setMinHeight(254.1);
        houseSideSecondCardRow.setMinHeight(254.1);

        ArrayList<Integer> playerCardIndex = new ArrayList<>();
        ArrayList<Integer> houseCardIndex = new ArrayList<>();

        makeFirst2CardIndex(houseSideFirstCardRow, houseCardIndex, false);

        while (getTotalCardValue(houseCardIndex) < 16 && houseCardIndex.size() != 5) {
            System.out.println(getTotalCardValue(houseCardIndex) < 16);
            System.out.println(houseCardIndex.size() != 5);
            houseCardIndex.add(getAnotherCardIndex(houseCardIndex, houseSideFirstCardRow, houseSideSecondCardRow, false));
        }
        System.out.println(getTotalCardValue(houseCardIndex) < 16);
        System.out.println(houseCardIndex.size() != 5);

//        txtHouseResult.setText("Computer score: " + getTotalCardValue(houseFirst2CardIndex));
////        System.out.println(playerCardIndex.toString());
//
//        cardUrl = String.format("file:cards/%d.png", houseFirst2CardIndex.get(0));
//
//        tempCardHolder = new Image(cardUrl);
//        ImageView houseFirstCardImgV = new ImageView(tempCardHolder);
//        cardUrl = String.format("file:cards/%d.png", houseFirst2CardIndex.get(1));
//        tempCardHolder = new Image(cardUrl);
//        ImageView houseSecondCardImgV = new ImageView(tempCardHolder);
//
//        houseFirstCardImgV.setPreserveRatio(true);
//        houseSecondCardImgV.setPreserveRatio(true);
//
//        houseFirstCardImgV.setFitWidth(175.0);
//        houseSecondCardImgV.setFitWidth(175.0);
//
//        houseSideFirstCardRow.getChildren().addAll(houseFirstCardImgV, houseSecondCardImgV);


        playerButtonLayout.getChildren().addAll(btnGetCard, btnBet, btnForfeit);


        playerSideLayout.getChildren().addAll(playerSideFirstCardRow, playerSideSecondCardRow, txtPlayerScore, playerButtonLayout, txtResult);
        houseSideLayout.getChildren().addAll(houseSideFirstCardRow, houseSideSecondCardRow, txtHouseCardValue);

        playerSideGp.getChildren().addAll(playerBg, playerSideLayout);
        houseSideGp.getChildren().addAll(houseBg, houseSideLayout);


        mainLayout.getChildren().addAll(playerSideGp, houseSideGp);


        btnGetCard.setOnAction((ActionEvent handler) -> {
                    playerBg.setFill(Color.web("#5ccc7a"));
                    houseBg.setFill(Color.web("#cc785c"));
                    txtResult.setText("");
                    if (!bet.get()) {
                        if (playerCardIndex.size() == 0) {
                            makeFirst2CardIndex(playerSideFirstCardRow, playerCardIndex, true);
                            txtPlayerScore.setText("Your card(s) value: " + getTotalCardValue(playerCardIndex));
                        } else {
                            playerCardIndex.add(getAnotherCardIndex(playerCardIndex, playerSideFirstCardRow, playerSideSecondCardRow, true));
                            txtPlayerScore.setText("Your card(s) value: " + getTotalCardValue(playerCardIndex));
                        }
                    } else {
                        playerSideFirstCardRow.getChildren().clear();
                        playerSideSecondCardRow.getChildren().clear();
                        playerCardIndex.clear();

                        houseSideFirstCardRow.getChildren().clear();
                        houseSideSecondCardRow.getChildren().clear();
                        houseCardIndex.clear();

                        makeFirst2CardIndex(playerSideFirstCardRow, playerCardIndex, true);
                        txtPlayerScore.setText("Your card(s) value: " + getTotalCardValue(playerCardIndex));

                        makeFirst2CardIndex(houseSideFirstCardRow, houseCardIndex, false);
                        txtHouseCardValue.setText("Computer card(s) value: ???");


                        while (getTotalCardValue(houseCardIndex) < 16 && houseCardIndex.size() != 5) {
                            houseCardIndex.add(getAnotherCardIndex(houseCardIndex, houseSideFirstCardRow, houseSideSecondCardRow, false));
                        }

                        bet.set(false);
                    }
                }
        );

        btnBet.setOnAction((ActionEvent e) -> {
            if (!bet.get()) {
                displayHouse(houseSideFirstCardRow, houseSideSecondCardRow, houseCardIndex, txtHouseCardValue);
                displayResult(playerCardIndex, houseCardIndex, getTotalCardValue(playerCardIndex), getTotalCardValue(houseCardIndex));
            }
            bet.set(true);
        });

        btnForfeit.setOnAction((ActionEvent e) -> {
            playerBg.setFill(Color.web("#5ccc7a"));
            houseBg.setFill(Color.web("#cc785c"));

            playerSideFirstCardRow.getChildren().clear();
            playerSideSecondCardRow.getChildren().clear();
            playerCardIndex.clear();

            houseSideFirstCardRow.getChildren().clear();
            houseSideSecondCardRow.getChildren().clear();
            houseCardIndex.clear();

            makeFirst2CardIndex(playerSideFirstCardRow, playerCardIndex, true);
            txtPlayerScore.setText("Your card(s) value: " + getTotalCardValue(playerCardIndex));

            makeFirst2CardIndex(houseSideFirstCardRow, houseCardIndex, false);
            txtHouseCardValue.setText("Computer card(s) value: ???");
            bet.set(false);
            txtResult.setText("");

            while (getTotalCardValue(houseCardIndex) < 16 && houseCardIndex.size() != 5) {
                houseCardIndex.add(getAnotherCardIndex(houseCardIndex, houseSideFirstCardRow, houseSideSecondCardRow, false));
            }
        });


        Scene scene = new Scene(mainLayout, 1050, 600);

        Stage.setTitle("BlackJack");
        Stage.setScene(scene);
        Stage.show();
    }

    public static void makeFirst2CardIndex(HBox firstCardRow, ArrayList<Integer> cardIndex, boolean isPlayer) {
        int firstCardIndex = (int) ((Math.random() * (52 - 1)) + 1);
        int secondCardIndex = firstCardIndex;
        while (secondCardIndex == firstCardIndex) {
            secondCardIndex = (int) ((Math.random() * (52 - 1)) + 1);
        }

        cardIndex.add(firstCardIndex);
        cardIndex.add(secondCardIndex);

        ImageView firstCardImgV;
        ImageView secondCardImgV;

        if (isPlayer) {
            String cardUrl = String.format("file:cards/%d.png", cardIndex.get(0));

            Image tempCardHolder = new Image(cardUrl);
            firstCardImgV = new ImageView(tempCardHolder);

            cardUrl = String.format("file:cards/%d.png", cardIndex.get(1));

            tempCardHolder = new Image(cardUrl);
            secondCardImgV = new ImageView(tempCardHolder);
        } else {
            String cardUrl = "file:cards/mysteriouscard.png";
            Image tempCardHolder = new Image(cardUrl);
            firstCardImgV = new ImageView(tempCardHolder);
            secondCardImgV = new ImageView(tempCardHolder);
        }

        firstCardImgV.setPreserveRatio(true);
        secondCardImgV.setPreserveRatio(true);

        firstCardImgV.setFitWidth(175.0);
        secondCardImgV.setFitWidth(175.0);


        firstCardRow.getChildren().addAll(firstCardImgV, secondCardImgV);
    }

    ;

    public static int getTotalCardValue(ArrayList<Integer> cardIndex) {
        int totalCardVal = 0;
        for (int card : cardIndex) {
            int cardValue = card % 13;
            if (cardValue > 10 || cardValue == 0) {
                cardValue = 10;
            }
            if (card == 100) {
                cardValue = 0;
                return totalCardVal;
            }
            totalCardVal += cardValue;
        }

        for (int card : cardIndex) {
            if ((21 - totalCardVal) >= 10 && card % 13 == 1) {
                totalCardVal += 10;
            }
        }
        return totalCardVal;
    }

    ;

    public static Integer getAnotherCardIndex(ArrayList<Integer> cardIndexList, HBox firstCardRow, HBox secondCardRow, boolean isPlayer) {
        int newCardIndex = (int) ((Math.random() * (52 - 1)) + 1);
        for (int cardIndex : cardIndexList) {
            if (newCardIndex == cardIndex) {
                newCardIndex += 1;
            }
        }

        String cardUrl = String.format("file:cards/%d.png", newCardIndex);

        if (!isPlayer) {
            cardUrl = "file:cards/mysteriouscard.png";
        }

        Image tempCardHolder = new Image(cardUrl);
        ImageView tempCardImgV = new ImageView(tempCardHolder);


        tempCardImgV.setPreserveRatio(true);

        tempCardImgV.setFitWidth(175.0);

        if (firstCardRow.getChildren().size() < 3) {
            firstCardRow.getChildren().addAll(tempCardImgV);
        } else if (secondCardRow.getChildren().size() < 2) {
            secondCardRow.getChildren().addAll(tempCardImgV);
        } else {
            System.out.println("Maxed cards pulled");
            newCardIndex = 100;
        }
        return newCardIndex;
    }

    ;

    public void displayHouse(HBox firstCardRow, HBox secondCardRow, ArrayList<Integer> cardIndexList, Text txtHouseResult) {
        txtHouseResult.setText("Computer score: " + getTotalCardValue(cardIndexList));

        firstCardRow.getChildren().clear();
        secondCardRow.getChildren().clear();
        for (int cardIndex : cardIndexList) {
            Image tempCardHolder = new Image(String.format("file:cards/%d.png", cardIndex));
            ImageView TempCardImgV = new ImageView(tempCardHolder);
            TempCardImgV.setPreserveRatio(true);
            TempCardImgV.setFitWidth(175.0);
            if (firstCardRow.getChildren().size() == 3) {
                secondCardRow.getChildren().add(TempCardImgV);
            } else {
                firstCardRow.getChildren().add(TempCardImgV);
            }
        }
    }

    public void displayResult(ArrayList<Integer> playerCardIndex, ArrayList<Integer> houseCardIndex, int playerCardValue, int houseCardValue) {

        basicResultCheck(playerCardValue, houseCardValue);

//        Condition check for 5-cards charlie
        if (playerCardIndex.size() == 5) {

            if (playerCardValue <= 21) {
                if (houseCardIndex.size() == 5 && houseCardValue <= 21) {
                    basicResultCheck(playerCardValue, houseCardValue);
                } else {
                    txtResult.setText("YOU'VE WON");
                    playerBg.setFill(Color.web("#15ff00"));
                    houseBg.setFill(Color.web("#cc785c"));
                }
            }
        } else if (houseCardIndex.size() == 5) {

            if (houseCardValue <= 21) {
                if (playerCardIndex.size() == 5 && playerCardValue <= 21) {
                    basicResultCheck(playerCardValue, houseCardValue);
                } else {
                    txtResult.setText("YOU'VE LOST");
                    playerBg.setFill(Color.web("#5ccc7a"));
                    houseBg.setFill(Color.web("#ff3c00"));
                }
            }
        }

//        Condition check for blackjack
        if (playerCardIndex.size() == 2 && playerCardValue == 21) {
            if (houseCardIndex.size() != 2 || houseCardValue != 21) {
                txtResult.setText("YOU'VE WON");
                playerBg.setFill(Color.web("#15ff00"));
                houseBg.setFill(Color.web("#cc785c"));
            } else if (houseCardIndex.size() == 2 && houseCardValue == 21) {
                txtResult.setText("DRAW");
                playerBg.setFill(Color.web("#5ccc7a"));
                houseBg.setFill(Color.web("#cc785c"));
            } else if (houseCardIndex.size() == 2 && playerCardValue == 21) {
                txtResult.setText("YOU'VE LOST");
                playerBg.setFill(Color.web("#5ccc7a"));
                houseBg.setFill(Color.web("#ff3c00"));
            }
        } else {
            if (houseCardIndex.size() == 2 && houseCardValue == 21) {
                txtResult.setText("YOU'VE LOST");
                playerBg.setFill(Color.web("#5ccc7a"));
                houseBg.setFill(Color.web("#ff3c00"));
            }
        }

    }

    public void basicResultCheck(int playerCardValue, int houseCardValue) {
        if (playerCardValue > houseCardValue) {
            if (playerCardValue <= 21) {
                txtResult.setText("YOU'VE WON");
                playerBg.setFill(Color.web("#15ff00"));
                houseBg.setFill(Color.web("#cc785c"));
            } else {
                txtResult.setText("YOU'VE LOST");
                playerBg.setFill(Color.web("#5ccc7a"));
                houseBg.setFill(Color.web("#ff3c00"));
            }
        } else if (houseCardValue > playerCardValue) {
            if (houseCardValue <= 21) {
                txtResult.setText("YOU'VE LOST");
                playerBg.setFill(Color.web("#5ccc7a"));
                houseBg.setFill(Color.web("#ff3c00"));
            } else {
                txtResult.setText("YOU'VE WON");
                playerBg.setFill(Color.web("#15ff00"));
                houseBg.setFill(Color.web("#cc785c"));
            }
        } else if (playerCardValue == houseCardValue) {
            txtResult.setText("DRAW");
            playerBg.setFill(Color.web("#5ccc7a"));
            houseBg.setFill(Color.web("#cc785c"));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
