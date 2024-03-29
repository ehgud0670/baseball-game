package kr.ac.ajou.main;

import kr.ac.ajou.main.util.GameUtils;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class BaseballGame {
    private Team firstTeam;
    private Team secondTeam;

    BaseballGame() {
        firstTeam = new Team(Constant.NUM_FIRST_TEAM);
        secondTeam = new Team(Constant.NUM_SECOND_TEAM);
    }

    void turnOn() {
        GameUtils.printMessageLine(Constant.STR_GAME_OVERVIEW);
        while (true) {
            printMenu();
            int menuNum = selectMenu();
            if (menuNum == Constant.MENU_GAME_EXIT) {
                break;
            }
            processByMenu(menuNum);
        }
        GameUtils.printMessageLine(Constant.STR_GAME_EXIT);
    }

    private void printMenu() {
        printMenuOption();
        GameUtils.printMessageNoLine(Constant.STR_MENU_CHOICE);
    }

    private void printMenuOption() {
        System.out.println("1. 데이터 입력");
        System.out.println("2. 데이터 출력");
        System.out.println("3. 시합 시작");
        System.out.println("4. 게임 나가기");
        System.out.println();
    }

    private int selectMenu() {
        int menuNum;
        while (true) {
            menuNum = inputMenuNum();
            if (menuNum == -1) {
                continue;
            }
            break;
        }
        return menuNum;
    }

    private int inputMenuNum() {
        Scanner sc = new Scanner(System.in);
        int menuNum;
        try {
            menuNum = sc.nextInt();
        } catch (InputMismatchException e) {
            GameUtils.printMessageNoLine(Constant.STR_REINPUT_MENU);
            return -1;
        }
        if (menuNum != Constant.MENU_INPUT && menuNum != Constant.MENU_OUTPUT &&
                menuNum != Constant.MENU_GAME_START && menuNum != Constant.MENU_GAME_EXIT) {
            GameUtils.printMessageNoLine(Constant.STR_REINPUT_MENU);
            return -1;
        }
        return menuNum;
    }

    private void processByMenu(int menuNum) {
        if (menuNum == Constant.MENU_INPUT) {
            processInputMenu();
        } else if (hasInputData(firstTeam, secondTeam)) {
            if (menuNum == Constant.MENU_OUTPUT) {
                processOutputMenu();
            } else if (menuNum == Constant.MENU_GAME_START) {
                processGameStartMenu();
            }
        }
    }

    private void processInputMenu() {
        inputTeamInfo(firstTeam);
        inputTeamInfo(secondTeam);
    }

    private void inputTeamInfo(Team team) {
        inputTeamName(team);
        inputTeamHittersInfo(team);
        inputTeamPitcherInfo(team);
    }

    private void inputTeamName(Team team) {
        Scanner sc = new Scanner(System.in);
        System.out.printf("%d팀의 이름을 입력하세요> ", team.getTeamNum());
        String teamName = sc.nextLine();
        team.setTeamName(teamName);
    }

    private void inputTeamHittersInfo(Team team) {
        for (int i = 0; i < Constant.NUM_HITTERS; i++) {
            Hitter hitter = new Hitter(i + 1);
            inputHitterName(i, hitter);
            inputBattingAvr(i, hitter);
            team.addHitter(hitter);
        }
    }

    private void inputHitterName(int i, Hitter hitter) {
        Scanner sc = new Scanner(System.in);
        System.out.printf("%d번 타자 이름 입력> ", i + 1);
        String hitterName = sc.next();
        hitter.setHitterName(hitterName);
    }

    private void inputBattingAvr(int i, Hitter hitter) {
        System.out.printf("%d번 타자 타율 입력> ", i + 1);
        double battingAvr;
        while (true) {
            try {
                battingAvr = inputBattingAvrNum();
            } catch (NumberFormatException e) { // 숫자가 아닌 문자열 입력했을 때
                GameUtils.printMessageLine(Constant.STR_REINPUT_BATTING_AVR);
                continue;
            }
            if (battingAvr == -1) {
                continue;
            }
            break;
        }
        hitter.setBattingAvr(battingAvr);
    }

    private double inputBattingAvrNum() throws NumberFormatException {
        Scanner sc = new Scanner(System.in);
        double battingAvr;
        String battingAvrStr = sc.next();
        if (isNotThirdDecimal(battingAvrStr)) {
            return -1;
        }
        battingAvr = Double.parseDouble(battingAvrStr);
        if (isNotCorrectRange(battingAvr)) {
            return -1;
        }
        return battingAvr;
    }

    private boolean isNotThirdDecimal(String battingAvrStr) {   //소수 셋째자리까지 입력 안했을 때
        if (battingAvrStr.length() != 5) {
            GameUtils.printMessageLine(Constant.STR_REINPUT_BATTING_AVR);
            return true;
        }
        return false;
    }

    private boolean isNotCorrectRange(double battingAvr) {      //범위 조건에 안맞는 경우
        if (battingAvr <= 0.1 || battingAvr >= 0.5) {
            GameUtils.printMessageLine(Constant.STR_REINPUT_BATTING_AVR);
            return true;
        }
        return false;
    }

    private void inputTeamPitcherInfo(Team team) {
        Scanner sc = new Scanner(System.in);
        Pitcher pitcher = new Pitcher();
        GameUtils.printMessageNoLine("투수 정보 입력> ");
        String pitcherName = sc.next();
        pitcher.setName(pitcherName);
        team.setPitcher(pitcher);
    }

    private void processOutputMenu() {
        printTeamInfo(firstTeam);
        printTeamInfo(secondTeam);

    }

    private boolean hasInputData(Team firstTeam, Team secondTeam) {
        String firstTeamName = firstTeam.getTeamName();
        String secondTeamName = secondTeam.getTeamName();
        if ("".equals(firstTeamName) && "".equals(secondTeamName)) {
            System.out.println("데이터 입력부터 해주세요.");
            return false;
        }
        return true;
    }

    private void printTeamInfo(Team team) {
        printTeamName(team);
        printTeamHittersInfo(team);
        printTeamPitcherInfo(team);
        System.out.println();
    }

    private void printTeamName(Team team) {
        System.out.println(team.getTeamName() + " 팀 정보");
    }

    private void printTeamHittersInfo(Team team) {
        List<Hitter> hitters = team.getHitters();
        for (int i = 0; i < Constant.NUM_HITTERS; i++) {
            Hitter curHitter = hitters.get(i);
            System.out.printf("%d번 %s, %f\n",
                    curHitter.getHitterNum(),
                    curHitter.getHitterName(),
                    curHitter.getBattingAvr());
        }
    }

    private void printTeamPitcherInfo(Team team) {
        Pitcher pitcher = team.getPitcher();
        System.out.println("투수 : " + pitcher.getName());
    }

    private void processGameStartMenu() {
        User user = new User();
        printGameStart(firstTeam, secondTeam);
        for (int i = 0; i < Constant.NUM_INNINGS; i++) {
            setInningNums(firstTeam, secondTeam, i + 1);
            attack(firstTeam, user, secondTeam);
            if (isWinBySecondTeam(firstTeam, secondTeam)) {
                break;
            }
            attack(secondTeam, user, firstTeam);
        }
        printGameResult(firstTeam, secondTeam);

    }

    private boolean isWinBySecondTeam(Team firstTeam, Team secondTeam) {
        if (secondTeam.getCurInningNum() == 6) {
            return firstTeam.getScore() < secondTeam.getScore();
        }
        return false;
    }

    private void setInningNums(Team firstTeam, Team secondTeam, int inningNum) {
        firstTeam.setCurInningNum(inningNum);
        secondTeam.setCurInningNum(inningNum);
    }

    private void printGameResult(Team firstTeam, Team secondTeam) {
        GameUtils.printMessageLine(Constant.STR_GAME_OVER);
        printTeamsVersus(firstTeam, secondTeam);
        printTeamsScore(firstTeam, secondTeam);
        System.out.println();
    }

    private void printTeamsVersus(Team firstTeam, Team secondTeam) {
        String firstTeamName = firstTeam.getTeamName();
        String secondTeamName = secondTeam.getTeamName();
        System.out.println(firstTeamName + " VS " + secondTeamName);
    }

    private void printTeamsScore(Team firstTeam, Team secondTeam) {
        String firstTeamName = firstTeam.getTeamName();
        int firstTeamScore = firstTeam.getScore();

        String secondTeamName = secondTeam.getTeamName();
        int secondTeamScore = secondTeam.getScore();
        System.out.println(firstTeamScore + " : " + secondTeamScore);

        if (firstTeamScore > secondTeamScore) {
            System.out.println(firstTeamName + "  승!");
        } else if (secondTeamScore > firstTeamScore) {
            System.out.println(secondTeamName + " 승!");
        } else {
            System.out.println("무승부");
        }
    }

    private void printGameStart(Team firstTeam, Team secondTeam) {
        System.out.println(firstTeam.getTeamName() + " VS " +
                secondTeam.getTeamName() + "의 시합을 시작합니다.");
    }

    private void attack(Team team, User user, Team otherTeam) {
        List<Hitter> hitters = team.getHitters();
        int hitterNum = 0;
        while (true) {
            if (team.isThreeOut()) {
                team.initHitsAndOut();
                break;
            }
            Hitter curHitter = hitters.get(hitterNum);
            attackByHitter(team, curHitter, user, otherTeam);
            hitterNum = (hitterNum + 1) % Constant.NUM_HITTERS;
        }
    }

    private void attackByHitter(Team team, Hitter hitter, User user, Team otherTeam) {
        Random random = new Random();
        while (true) {
            if (hitter.isOut() || hitter.isHit()) {
                hitter.initOutAndHit();
                break;
            }
            int ret = processUserInput(team, user);  // 사용자 입력
            if (ret == Constant.NUM_ERROR) {
                continue;
            }
            if (isPrintOk(team, user)) {
                printHitterInfo(hitter);
            }
            double p = random.nextDouble();
            otherTeam.pitching(); // 상대팀 투구수 올리기
            processByPercent(p, team, hitter, user);
            if (isPrintOk(team, user)) {
                printBoard(team, hitter);
            }
        }
    }

    private void printBoard(Team team, Hitter hitter) {
        System.out.println("+--------------------------------+");
        System.out.println("|        1 2 3 4 5 6  | TOT      | ");
        printTeamScore(firstTeam);
        printTeamScore(secondTeam);
        printHitterList();
        printHitterCount(team, hitter);
        System.out.println("|                               |");
        printTeamsPitching();
        printTeamsStrikeOutNum();
        printTeamsHitsNum();
        System.out.println("|                               |");
        System.out.println("+--------------------------------+");
    }

    private void printTeamScore(Team team) {
        System.out.print("| " + team.getTeamName() + "  ");
        int[] teamScores = team.getInningScores();
        for (int i = 0; i < Constant.NUM_INNINGS; i++) {
            System.out.print(teamScores[i] + " ");
        }
        System.out.print("  |  ");
        System.out.println(team.getScore() + "       |");
    }

    private void printHitterList() {
        System.out.println("|                               |");
        System.out.println("|  " + firstTeam.getTeamName() + "                  " + secondTeam.getTeamName() + "   |");
        List<Hitter> teamHitters = firstTeam.getHitters();
        List<Hitter> otherTeamHitters = secondTeam.getHitters();
        for (int i = 0; i < Constant.NUM_HITTERS; i++) {
            Hitter curHitter = teamHitters.get(i);
            Hitter curOtherHitter = otherTeamHitters.get(i);
            System.out.print("| " + curHitter.getHitterNum() + "." + curHitter.getHitterName());
            System.out.println("               " + curOtherHitter.getHitterNum() + "." + curOtherHitter.getHitterName() + "  |");
        }
    }

    private void printHitterCount(Team team, Hitter hitter) {
        System.out.println("|                               |");
        System.out.println("|   현재 타자                       |");
        System.out.println("|   " + hitter.getHitterName() + "                       |");
        System.out.println("|   S:" + hitter.getStrikeNum() + "                             |");
        System.out.println("|   B:" + hitter.getBallNum() + "                             |");
        System.out.println("|   O:" + team.getOutNum() + "                             |");
    }


    private void printTeamsPitching() {
        System.out.print("| ");
        System.out.print("투구: " + firstTeam.getPitchingNum());
        System.out.print("                  ");
        System.out.print("투구: " + secondTeam.getPitchingNum());
        System.out.println("| ");
    }

    private void printTeamsStrikeOutNum() {
        System.out.print("| ");
        System.out.print("삼진: " + firstTeam.getStrikeOutNum());
        System.out.print("                  ");
        System.out.print("삼진: " + secondTeam.getStrikeOutNum());
        System.out.println("| ");
    }

    private void printTeamsHitsNum() {
        System.out.print("| ");
        System.out.print("안타: " + firstTeam.getTotalHitsNum());
        System.out.print("                  ");
        System.out.print("안타: " + secondTeam.getTotalHitsNum());
        System.out.println("| ");
    }

    private boolean isPrintOk(Team team, User user) {
        int userCurInningNum = user.getCurInningNum();
        return userCurInningNum == 0 || userCurInningNum < team.getCurInningNum();
    }

    private int processUserInput(Team team, User user) {
        int userCurInningNum = user.getCurInningNum();
        if (userCurInningNum == 0 || userCurInningNum < team.getCurInningNum()) {
            GameUtils.printMessageNoLine(Constant.STR_USER_INPUT);
            int choice = inputUserChoice();
            if (choice == Constant.NUM_ERROR) {
                return Constant.NUM_ERROR;
            }
            user.setCurInningNum(choice);
        }
        return 0;
    }

    private int inputUserChoice() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if ("".equals(input)) { // enter
            return 0;
        } else {
            int num = Integer.parseInt(input);
            if (num > 6 || num < 1) {
                GameUtils.printMessageLine(Constant.STR_REINPUT);
                return -1;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                GameUtils.printMessageLine(Constant.STR_REINPUT);
                return -1;
            }
        }
    }

    private void printHitterInfo(Hitter hitter) {
        System.out.println(hitter.getHitterNum() + "번 타자 " + hitter.getHitterName() + "입니다.");
    }

    private void processByPercent(double p, Team team, Hitter hitter, User user) {
        double h = hitter.getBattingAvr();
        double percentOut = 0.1;
        double percentHits = h + percentOut;
        double percentStrike = (1 - h) / 2.0 - 0.05 + percentHits;
        if (p <= percentOut) {
            processOut(team, hitter, user);
        } else if (p <= percentHits) {
            processHits(team, hitter, user);
        } else if (p <= percentStrike) {
            processStrike(team, hitter, user);
        } else {
            processBall(team, hitter, user);
        }
        if (isPrintOk(team, user)) {
            printCurCount(team, hitter);
        }
    }

    private void processOut(Team team, Hitter hitter, User user) {
        if (isPrintOk(team, user)) {
            GameUtils.printMessageLine(Constant.STR_OUT);
        }
        team.out();
        hitter.setOut(true);
        hitter.initStrikeAndBall();
    }

    private void processHits(Team team, Hitter hitter, User user) {
        if (isPrintOk(team, user)) {
            GameUtils.printMessageLine(Constant.STR_HITS);
        }
        team.hits();
        hitter.setHit(true);
        calculateScore(team);
        hitter.initStrikeAndBall();
    }

    private void calculateScore(Team team) {
        int hitsNum = team.getCurHitsNum();
        if (hitsNum >= Constant.FOUR_HITS) {
            team.curInningScore();
            team.totalScore();
        }
    }

    private void processStrike(Team team, Hitter hitter, User user) {
        if (isPrintOk(team, user)) {
            GameUtils.printMessageLine(Constant.STR_STRIKE);
        }
        hitter.strike();
        if (hitter.isThreeStrike()) {
            team.strikeOut();
            processOut(team, hitter, user);
        }
    }

    private void processBall(Team team, Hitter hitter, User user) {
        if (isPrintOk(team, user)) {
            GameUtils.printMessageLine(Constant.STR_BALL);
        }
        hitter.ball();
        if (hitter.isFourBall()) {
            processHits(team, hitter, user);
        }
    }

    private void printCurCount(Team team, Hitter hitter) {
        System.out.printf("%dS %dB %dO\n", hitter.getStrikeNum(), hitter.getBallNum(), team.getOutNum());
        System.out.println();
    }
}
