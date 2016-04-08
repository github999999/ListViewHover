package xzh.com.listviewhover.model;

import java.util.List;

/**
 * Created by xiangzhihong on 2016/3/23 on 11:04.
 */
public class CatergoryModel {


    public int status;
    public int errorCode;
    public String errorDesc;
    public long timestamp;
    public String hash;
    public List<DataEntity> data;

    public static class DataEntity {

        public ChapterEntity chapter;
        public List<MediaEntity> media;

        public static class ChapterEntity {
            public int id;
            public String name;
            public int cid;
            public int seq;
        }

        public static class MediaEntity {
            public int id;
            public String name;
            public List<LeafEntity> leaf;
        }


        public static class LeafEntity {
            public int leaf_id;
            public String leaf_name;
        }
    }
}
