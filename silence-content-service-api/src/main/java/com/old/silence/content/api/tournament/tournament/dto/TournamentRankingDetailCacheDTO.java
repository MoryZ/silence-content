package com.old.silence.content.api.tournament.tournament.dto;


import com.old.silence.content.api.dto.CurrentGroupVO;
import com.old.silence.content.api.tournament.tournament.dto.support.RankItem;

import java.util.List;

/**
 * @author moryzang
 */
public class TournamentRankingDetailCacheDTO {
    private CurrentGroupVO currentGroup; // 只有小组时非null

    private List<RankItem> ranks;

    public TournamentRankingDetailCacheDTO() {
    }

    public TournamentRankingDetailCacheDTO(CurrentGroupVO currentGroup, List<RankItem> ranks) {
        this.currentGroup = currentGroup;
        this.ranks = ranks;
    }

    // Getters and Setters
    public CurrentGroupVO getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(CurrentGroupVO currentGroup) {
        this.currentGroup = currentGroup;
    }

    public List<RankItem> getRanks() {
        return ranks;
    }

    public void setRanks(List<RankItem> ranks) {
        this.ranks = ranks;
    }

}
