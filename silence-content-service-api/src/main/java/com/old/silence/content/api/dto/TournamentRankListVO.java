package com.old.silence.content.api.dto;

import java.util.List;

/**
 * @author ZHAOGUANRUI108
 */
public class TournamentRankListVO {
    /**
     * 当前小组信息
     */
    private CurrentGroupVO currentGroupVO;

    /**
     * 当前排行榜信息
     */
    private List<RankItemVO> rankItemVOList;

    public CurrentGroupVO getCurrentGroupVO() {
        return currentGroupVO;
    }

    public void setCurrentGroupVO(CurrentGroupVO currentGroupVO) {
        this.currentGroupVO = currentGroupVO;
    }

    public List<RankItemVO> getRankItemVOList() {
        return rankItemVOList;
    }

    public void setRankItemVOList(List<RankItemVO> rankItemVOList) {
        this.rankItemVOList = rankItemVOList;
    }
}
