package kr.ac.ajou.main;

import java.util.ArrayList;
import java.util.List;

class Team {

    private int teamNum;
    private String teamName;

    private List<Hitter> hitters;
    private Pitcher pitcher;
    private int outNum;
    private int hitsNum;
    private int score;
    private int curInningNum;
    private int[] inningScores;

    Team(int teamNum, String teamOrder) {
        this.teamNum = teamNum;
        teamName = "";
        hitters = new ArrayList<>(Constant.NUM_HITTERS);
        inningScores = new int[Constant.NUM_INNINGS];
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addTotalScore(int score) {
        this.score += score;
    }

    int getTeamNum() {
        return teamNum;
    }

    void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    String getTeamName() {
        return teamName;
    }

    void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    void addHitter(Hitter hitter) {
        hitters.add(hitter);
    }

    void setPitcher(Pitcher pitcher) {
        this.pitcher = pitcher;
    }

    List<Hitter> getHitters() {
        return hitters;
    }

    Pitcher getPitcher() {
        return pitcher;
    }

    public int getOutNum() {
        return outNum;
    }

    public void setOutNum(int outNum) {
        this.outNum = outNum;
    }

    void out() {
        outNum++;
    }

    boolean isThreeOut() {
        return outNum == Constant.THREE_OUT;
    }

    public void initOut() {
        outNum = 0;
    }

    public int getHitsNum() {
        return hitsNum;
    }

    public void setHitsNum(int hitsNum) {
        this.hitsNum = hitsNum;
    }

    public void hits() {
        hitsNum++;
    }

    public void initHits() {
        hitsNum = 0;
    }

    public int getCurInningNum() {
        return curInningNum;
    }

    public void setCurInningNum(int curInningNum) {
        this.curInningNum = curInningNum;
    }

    public int[] getInningScores() {
        return inningScores;
    }

    public void setInningScores(int[] inningScores) {
        this.inningScores = inningScores;
    }

    public void setCurInningScore(int score) {
        this.inningScores[this.curInningNum - 1] = score;
    }
}
