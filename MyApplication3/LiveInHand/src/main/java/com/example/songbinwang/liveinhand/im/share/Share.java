package com.example.songbinwang.liveinhand.im.share;

import com.example.songbinwang.liveinhand.im.entity.ShareParam;

/**
 * Created by songbinwang on 2016/9/8.
 */
public class Share {

    public int share(ShareParam param){
        int result = 0;
        switch(param.getShareType()){
            case ShareConstant.ShareType.TEXT:
                break;
            case ShareConstant.ShareType.VIDEO_URL:
                break;
            case ShareConstant.ShareType.IMAGE_LOCAL:
                break;
            case ShareConstant.ShareType.IMAGE_URL:
                break;
            case ShareConstant.ShareType.URL:
                break;
            case ShareConstant.ShareType.BESTOW_URL:
                break;
        }
        return result;
    }


}
