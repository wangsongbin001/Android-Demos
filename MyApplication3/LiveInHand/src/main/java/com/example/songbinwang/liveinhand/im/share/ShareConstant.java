package com.example.songbinwang.liveinhand.im.share;

/**
 * Created by songbinwang on 2016/9/8.
 */
public class ShareConstant {

    /**
     * 分享类型：<br/>
     * 0 文字<br/>
     * 1 视频<br/>
     * 2 图片链接<br/>
     * 3 本地图片<br/>
     * 4 链接
     *
     * @author raymondqiu
     * @version [版本号, 2014-11-27]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    public static final class ShareType
    {
        public static final int UNKNOW = -1;

        public static final int TEXT = 0;

        public static final int VIDEO_URL = 1;

        public static final int IMAGE_LOCAL = 2;

        public static final int IMAGE_URL = 3;

        public static final int URL = 4;

        public static final int BESTOW_URL = 5;
    }
}
