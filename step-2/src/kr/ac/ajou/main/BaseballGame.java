package kr.ac.ajou.main;

import kr.ac.ajou.main.util.GameUtils;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class BaseballGame {
    void turnOn() {
        GameUtils.printMessageLine(Constant.STR_GAME_OVERVIEW);
        Team firstTeam = new Team(Constant.NUM_FIRST_TEAM, Constant.STR_TOP_ORDER);
        Team secondTeam = new Team(Constant.NUM_SECOND_TEAM, Constant.STR_BOTTOM_ORDER);
        while (true) {
            printMenu();
            int menuNum = selectMenu();
            if (menuNum == Constant.MENU_GAME_EXIT) {
                break;
            }
            processByMenu(menuNum, firstTeam, secondTeam);
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
            if (menuNum != Constant.MENU_INPUT && menuNum != Constant.MENU_OUTPUT &&
                    menuNum != Constant.MENU_GAME_START && menuNum != Constant.MENU_GAME_EXIT) {
                GameUtils.printMessageNoLine(Constant.STR_REINPUT_MENU);
                return -1;
            }
        } catch (InputMismatchException e) {
            GameUtils.printMessageNoLine(Constant.STR_REINPUT_MENU);
            return -1;
        }
        return menuNum;
    }

    private void processByMenu(int menuNum, Team firstTeam, Team secondTeam) {
        switch (menuNum) {
            case Constant.MENU_INPUT:
                processInputMenu(firstTeam, secondTeam);
                break;
            case Constant.MENU_OUTPUT:
                processOutputMenu(firstTeam, secondTeam);
                break;
            case Constant.MENU_GAME_START:
                processGameStartMenu(firstTeam, secondTeam);
                break;
        }
    }

    private void processInputMenu(Team firstTeam, Team secondTeam) {
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
                if (battingAvr == -1) {
                    continue;
                }
            } catch (NumberFormatException e) { // 숫자가 아닌 문자열 입력했을 때
                GameUtils.printMessageLine(Constant.STR_REINPUT_BATTING_AVR);
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

    private void processOutputMenu(Team firstTeam, Team secondTeam) {
        if (hasInputData(firstTeam, secondTeam)) {
            printTeamInfo(firstTeam);
            printTeamInfo(secondTeam);
        }
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

    private void processGameStartMenu(Team firstTeam, Team secondTeam) {
        if (hasInputData(firstTeam, secondTeam)) {
            printGameStart(firstTeam, secondTeam);
            for (int i = 0; i < Constant.NUM_GAME_TIMES; i++) {
                attack(firstTeam, i);
                attack(secondTeam, i);
            }
            printGameResult(firstTeam, secondTeam);
        }
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

    private void attack(Team team, int i) {
        printTeamAttack(team, i);
        List<Hitter> hitters = team.getHitters();
        int hitterNum = 0;
        while (true) {
            if (team.isThreeOut()) {
                calculateScore(team);
                team.initOut();
                break;
            }
            Hitter curHitter = hitters.get(hitterNum);
            attackByHitter(team, curHitter);
            hitterNum = (hitterNum + 1) % Constant.NUM_HITTERS;
        }
    }

    private void calculateScore(Team team) {
        int hitsNum = team.getHitsNum();
        if (hitsNum > 3) {
            team.score(hitsNum - 3);
            team.initHits();
        }
    }

    private void printTeamAttack(Team team, int i) {
        System.out.println(i + 1 + "회" + team.getTeamOrder() +
                " " + team.getTeamName() +
                " 공격");
    }

    private void attackByHitter(Team team, Hitter hitter) {
        Random random = new Random();
        System.out.println(hitter.getHitterNum() + "번 " + hitter.getHitterName());
        while (true) {
            if (hitter.isOut() || hitter.isHit()) {
                hitter.initOutAndHit();
                break;
            }
            double p = random.nextDouble();
            processByPercent(p, team, hitter);
        }
    }

    private void processByPercent(double p, Team team, Hitter hitter) {
        double h = hitter.getBattingAvr();
        double percentOut = 0.1;
        double percentHits = h + percentOut;
        double percentStrike = (1 - h) / 2.0 - 0.05 + percentHits;
        if (p <= percentOut) {
            processOut(team, hitter);
        } else if (p <= percentHits) {
            processHits(team, hitter);
        } else if (p <= percentStrike) {
            processStrike(team, hitter);
        } else {
            processBall(team, hitter);
        }
        printCurSituation(team, hitter);
    }

    private void processOut(Team team, Hitter hitter) {
        GameUtils.printMessageLine(Constant.STR_OUT);
        team.out();
        hitter.setOut(true);
        hitter.initStrikeAndBall();
    }

    private void processHits(Team team, Hitter hitter) {
        GameUtils.printMessageLine(Constant.STR_HITS);
        team.hits();
        hitter.setHit(true);
        hitter.initStrikeAndBall();
    }

    private void processStrike(Team team, Hitter hitter) {
        GameUtils.printMessageLine(Constant.STR_STRIKE);
        hitter.strike();
        if (hitter.isThreeStrike()) {
            processOut(team, hitter);
        }
    }

    private void processBall(Team team, Hitter hitter) {
        GameUtils.printMessageLine(Constant.STR_BALL);
        hitter.ball();
        if (hitter.isFourBall()) {
            processHits(team, hitter);
        }
    }

    private void printCurSituation(Team team, Hitter hitter) {
        System.out.printf("%dS %dB %dO\n", hitter.getStrikeNum(), hitter.getBallNum(), team.getOutNum());
        System.out.println(team.getTeamName() + "팀의 현재 안타수 : " + team.getHitsNum());
        System.out.println();
    }
}
