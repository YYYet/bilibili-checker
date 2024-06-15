package com.bili.sdk.service.tv.entity.resp.verifytokenInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class VerifyTokenInfo {
    private long mid;
    private String name;
    private Avatar avatar;
    private boolean show_name_guide;
    private String face;
    private boolean show_face_guide;
    private double coin;
    private int bcoin;
    private int sex;
    private int rank;
    private int silence;
    private int show_videoup;
    private int show_creative;
    private int level;
    private int vip_type;
    private int audio_type;
    private int dynamic;
    private int following;
    private int follower;
    private int new_followers;
    private int new_followers_rtime;
    private OfficialVerify official_verify;
    private Vip vip;
    private int in_reg_audit;
    private int first_live_time;
    private int face_nft_new;
    private boolean show_nft_face_guide;
    private SeniorGate senior_gate;
    private Achievement achievement;
    private String bubbles;
}
