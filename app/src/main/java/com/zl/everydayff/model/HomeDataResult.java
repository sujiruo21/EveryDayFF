package com.zl.everydayff.model;

import java.util.List;

/**
 * Created by Boom on 2017/6/22.
 */

public class HomeDataResult {

    /**
     * data : {"ad_list":[{"image":"http://resource.ffu365.com/upload/images/default/2016-06-04/57529302a2f602.54526763.jpg","link":"http://m.ffu365.com/static/bas/1.html"}],"company_list":[{"image":"http://resource.ffu365.com/upload/images/default/2016-06-02/574fd1fb4b91d8.93153953.jpg","link":"http://app.ffu365.com/pages/company.html"}],"news_list":[{"title":"天天防腐APP产品定位及功能介绍","create_time":"2016-05-01","link":"http://m.ffu365.com/static/News/news.html?id=10"},{"title":"全球腐蚀调研项目结果公布","create_time":"2016-03-23","link":"http://m.ffu365.com/static/News/news.html?id=11"},{"title":"天天防腐APP上线啦！！！","create_time":"2015-09-05","link":"http://m.ffu365.com/static/News/news.html?id=12"},{"title":"天天防腐APP开始内测","create_time":"2015-05-08","link":"http://m.ffu365.com/static/News/news.html?id=13"},{"title":"天天防腐团队组建","create_time":"2014-07-20","link":"http://m.ffu365.com/static/News/news.html?id=14"}]}
     * errcode : 1
     * errmsg : 操作成功
     * errdialog : 0
     */

    private DataBean data;
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
        private List<AdListBean> ad_list;
        private List<CompanyListBean> company_list;
        private List<NewsListBean> news_list;

        public List<AdListBean> getAd_list() {
            return ad_list;
        }

        public void setAd_list(List<AdListBean> ad_list) {
            this.ad_list = ad_list;
        }

        public List<CompanyListBean> getCompany_list() {
            return company_list;
        }

        public void setCompany_list(List<CompanyListBean> company_list) {
            this.company_list = company_list;
        }

        public List<NewsListBean> getNews_list() {
            return news_list;
        }

        public void setNews_list(List<NewsListBean> news_list) {
            this.news_list = news_list;
        }

        public static class AdListBean {
            /**
             * image : http://resource.ffu365.com/upload/images/default/2016-06-04/57529302a2f602.54526763.jpg
             * link : http://m.ffu365.com/static/bas/1.html
             */

            private String image;
            private String link;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class CompanyListBean {
            /**
             * image : http://resource.ffu365.com/upload/images/default/2016-06-02/574fd1fb4b91d8.93153953.jpg
             * link : http://app.ffu365.com/pages/company.html
             */

            private String image;
            private String link;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class NewsListBean {
            /**
             * title : 天天防腐APP产品定位及功能介绍
             * create_time : 2016-05-01
             * link : http://m.ffu365.com/static/News/news.html?id=10
             */

            private String title;
            private String create_time;
            private String link;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }
}
