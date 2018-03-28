package com.zl.everydayff.model;

/**
 * Created by hui on 2016/8/24.
 */
public class UserLoginResult {

    /**
     * member_info : {"uid":"71","member_location_text":"湖南省-长沙市-芙蓉区","member_name":"曾辉","member_avatar":"http://resource.ffu365.com/upload/images/avatar/2016-06-01/574e38ab8dd2a.jpg","is_account_certification":0,"is_account_complete":"1","member_cell_phone":"14726932514"}
     * zone_info : {"trade_record":0,"my_task":0,"my_gold":0,"my_order":0,"my_publish":0,"my_post":0,"worker_center":0,"team_center":0,"expert_center":0}
     */

    private DataBean data;
    /**
     * data : {"member_info":{"uid":"71","member_location_text":"湖南省-长沙市-芙蓉区","member_name":"曾辉","member_avatar":"http://resource.ffu365.com/upload/images/avatar/2016-06-01/574e38ab8dd2a.jpg","is_account_certification":0,"is_account_complete":"1","member_cell_phone":"14726932514"},"zone_info":{"trade_record":0,"my_task":0,"my_gold":0,"my_order":0,"my_publish":0,"my_post":0,"worker_center":0,"team_center":0,"expert_center":0}}
     * errcode : 1
     * errmsg : 操作成功
     * errdialog : 0
     */

    private int errcode;
    private String errmsg;
    private int errdialog;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrdialog() {
        return errdialog;
    }

    public void setErrdialog(int errdialog) {
        this.errdialog = errdialog;
    }

    public static class DataBean {
        /**
         * uid : 71
         * member_location_text : 湖南省-长沙市-芙蓉区
         * member_name : 曾辉
         * member_avatar : http://resource.ffu365.com/upload/images/avatar/2016-06-01/574e38ab8dd2a.jpg
         * is_account_certification : 0
         * is_account_complete : 1
         * member_cell_phone : 14726932514
         */

        private MemberInfoBean member_info;
        /**
         * trade_record : 0
         * my_task : 0
         * my_gold : 0
         * my_order : 0
         * my_publish : 0
         * my_post : 0
         * worker_center : 0
         * team_center : 0
         * expert_center : 0
         */

        private ZoneInfoBean zone_info;

        public MemberInfoBean getMember_info() {
            return member_info;
        }

        public void setMember_info(MemberInfoBean member_info) {
            this.member_info = member_info;
        }

        public ZoneInfoBean getZone_info() {
            return zone_info;
        }

        public void setZone_info(ZoneInfoBean zone_info) {
            this.zone_info = zone_info;
        }

        public static class MemberInfoBean {
            private String uid;
            private String member_location_text;
            private String member_name;
            private String member_avatar;
            private int is_account_certification;
            private String is_account_complete;
            private String member_cell_phone;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getMember_location_text() {
                return member_location_text;
            }

            public void setMember_location_text(String member_location_text) {
                this.member_location_text = member_location_text;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getMember_avatar() {
                return member_avatar;
            }

            public void setMember_avatar(String member_avatar) {
                this.member_avatar = member_avatar;
            }

            public int getIs_account_certification() {
                return is_account_certification;
            }

            public void setIs_account_certification(int is_account_certification) {
                this.is_account_certification = is_account_certification;
            }

            public String getIs_account_complete() {
                return is_account_complete;
            }

            public void setIs_account_complete(String is_account_complete) {
                this.is_account_complete = is_account_complete;
            }

            public String getMember_cell_phone() {
                return member_cell_phone;
            }

            public void setMember_cell_phone(String member_cell_phone) {
                this.member_cell_phone = member_cell_phone;
            }
        }

        public static class ZoneInfoBean {
            private int trade_record;
            private int my_task;
            private int my_gold;
            private int my_order;
            private int my_publish;
            private int my_post;
            private int worker_center;
            private int team_center;
            private int expert_center;

            public int getTrade_record() {
                return trade_record;
            }

            public void setTrade_record(int trade_record) {
                this.trade_record = trade_record;
            }

            public int getMy_task() {
                return my_task;
            }

            public void setMy_task(int my_task) {
                this.my_task = my_task;
            }

            public int getMy_gold() {
                return my_gold;
            }

            public void setMy_gold(int my_gold) {
                this.my_gold = my_gold;
            }

            public int getMy_order() {
                return my_order;
            }

            public void setMy_order(int my_order) {
                this.my_order = my_order;
            }

            public int getMy_publish() {
                return my_publish;
            }

            public void setMy_publish(int my_publish) {
                this.my_publish = my_publish;
            }

            public int getMy_post() {
                return my_post;
            }

            public void setMy_post(int my_post) {
                this.my_post = my_post;
            }

            public int getWorker_center() {
                return worker_center;
            }

            public void setWorker_center(int worker_center) {
                this.worker_center = worker_center;
            }

            public int getTeam_center() {
                return team_center;
            }

            public void setTeam_center(int team_center) {
                this.team_center = team_center;
            }

            public int getExpert_center() {
                return expert_center;
            }

            public void setExpert_center(int expert_center) {
                this.expert_center = expert_center;
            }
        }
    }
}
