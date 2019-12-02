package kr.ac.ajou.main;

class BaseballGame {
    void turnOn() {
        printGameStart();
        printMenu();
    }

    private void printMenu() {
        printMenuOption();
        printMenuChoice();
    }

    private void printMenuChoice() {
        System.out.print("메뉴선택 (1 - 3) ");
    }

    private void printMenuOption() {
        System.out.println("1. 데이터 입력");
        System.out.println("2. 데이터 출력");
        System.out.println("3. 시합 시작");
        System.out.println();
    }

    private void printGameStart() {
        System.out.println("신나는 야구시합");
    }
}
